package com.br.shared;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
 
public class InputMoeda extends JTextField {
    private static final long serialVersionUID = -7506506392528621022L;
    private static final NumberFormat MONETARY_FORMAT = new DecimalFormat("#,##0.00");
    private NumberFormat numberFormat;
    private int limit = -1;
 
    public InputMoeda(int casasDecimais, int colunas) {
        this(new DecimalFormat((casasDecimais == 0 ? "#,##0" : "#,##0.") + makeZeros(casasDecimais)));
        setColumns(colunas);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        setMargin(new Insets(3,3,3,3));
    }

    public InputMoeda(int colunas) {
        this(new DecimalFormat((2 == 0 ? "#,##0" : "#,##0.") + makeZeros(2)));
        setColumns(colunas);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        setMargin(new Insets(3,3,3,3));
    }
    
    public InputMoeda() {
        this(MONETARY_FORMAT);
        setBorder(new EmptyBorder(3, 3, 3, 3));
        setMargin(new Insets(3,3,3,3));
    }
 
    public InputMoeda(NumberFormat format) {// define o formato do
        // número
        setBorder(new EmptyBorder(3, 3, 3, 3));
        setMargin(new Insets(3,3,3,3));
        numberFormat = format;// alinhamento horizontal para o texto
        setHorizontalAlignment(RIGHT);// documento responsável pela formatação
        // do campo
        setDocument(new PlainDocument() {
            private static final long serialVersionUID = 1L;
 
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                String text = new StringBuilder(InputMoeda.this.getText().replaceAll("[^0-9]", "")).append(str.replaceAll("[^0-9]", "")).toString();
                super.remove(0, getLength());
                if (text.isEmpty()) {
                    text = "0";
                } else {
                    text = new BigInteger(text).toString();
                }
                super.insertString(0, numberFormat.format(new BigDecimal(getLimit() > 0 == text.length() > getLimit() ? text.substring(0, getLimit()) : text).divide(new BigDecimal(Math.pow(10, numberFormat.getMaximumFractionDigits())))), a);
            }
 
            @Override
            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
                if (len != getLength()) {
                    insertString(0, "", null);
                }
            }
        });// mantem o cursor no final
        // do campo
        addCaretListener(new CaretListener() {
            boolean update = false;
 
            
            public void caretUpdate(CaretEvent e) {
                if (!update) {
                    update = true;
                    setCaretPosition(getText().length());
                    update = false;
                }
            }
        });// limpa o campo se
        // apertar DELETE
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    setText("");
                }
            }
        });// formato
        // inicial
        setText("0");
        setCaretPosition(getText().length());
    }
 
    public void setValue(BigDecimal value) {
        super.setText(numberFormat.format(value));
    }
 
    public final BigDecimal getValue() {
        return new BigDecimal(getText().replaceAll("[^0-9]", "")).divide(new BigDecimal(Math.pow(10, numberFormat.getMaximumFractionDigits())));
    }
    public NumberFormat getNumberFormat() {
        return numberFormat;
    }
    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }
    private static final String makeZeros(int zeros) {
        if (zeros >= 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < zeros; i++) {
                builder.append('0');
            }
            return builder.toString();
        } else {
            throw new RuntimeException("Número de casas decimais inválida (" + zeros + ")");
        }
    }
    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    public Float getValorFloat(){
        return Float.parseFloat(this.getText().replace(".", "").replace(",", "."));
    }
    
    public boolean valorInvalido(){
        if(getText().equals("0")
                || getText().equals("00")
                || getText().equals("0,0")
                || getText().equals("0,00")
                || getText().equals("0.00")
                || getText().equals(""))
            return true;
        else 
            return false;
    }
 
    // testes, pode ser removido
    public static void main(String[] args) {
        JFrame frame = new JFrame("Teste do campo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new InputMoeda(new DecimalFormat("#,##0.00")) {
            {// limita a 4
                // caracteres
                setLimit(10);
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
