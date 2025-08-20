package com.br.util;

import java.awt.Component;
import java.awt.Window;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

public class Util {
    public static SimpleDateFormat dfUS = new SimpleDateFormat("yyyy-MM-dd");
    public static DateTimeFormatter dfBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static DecimalFormat mf = new DecimalFormat("#,##0.00");
    
    public static boolean stringNullOrEmpty(String text){
        if(text==null || text.trim().isEmpty() || text.equals("null"))
            return true;
        return false;
    }
    
    public static DefaultTableCellRenderer colunaCentralizada() {
        return new DefaultTableCellRenderer() {

            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(CENTER);
            }
        };
    }

    public static DefaultTableCellRenderer colunaDireita() {
        return new DefaultTableCellRenderer() {

            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(RIGHT);
            }
        };
    } 
    

    public static void alertErro(Component c, String msg) {
        JOptionPane.showMessageDialog(c, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void alertSucesso(Component c, String msg) {
        JOptionPane.showMessageDialog(c, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirma(Window win, String msg) {
        Object stringArray[] = {"SIM", "NÃO"};
        int resposta = JOptionPane.showOptionDialog(win, msg, "Excluir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, stringArray[0]);
        if (resposta == 0) {
            return true;
        } else {
            return false;
        }
    }
    
  //para colunas com valor moeda
    public static DefaultTableCellRenderer colunaValor() {
        return new DefaultTableCellRenderer() {

            @Override
            protected void setValue(Object value) {
                super.setValue(Util.mf.format(value));
            }

            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(RIGHT);
            }
        };
    }
    //para colunas com data
    public static DefaultTableCellRenderer colunaData() {
        return new DefaultTableCellRenderer() {

            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(CENTER);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null && value instanceof LocalDate) {
                    this.setText(((LocalDate)value).format(dfBR));
                } else {
                    this.setText("");
                }
                return this;
            }
        };
    }
    
    //para colunas com cnpj
    public static DefaultTableCellRenderer colunaCNPJ() {
        return new DefaultTableCellRenderer() {

            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(CENTER);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null && !String.valueOf(value).trim().isEmpty()) {
                    this.setText(format("##.###.###/####-##", value));
                } else {
                    this.setText("");
                }
                return this;
            }
        };
    }

    //para colunas de impressao
    public static DefaultTableCellRenderer colunaImpressao() {
        return new DefaultTableCellRenderer() {

            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(CENTER);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                JLabel rotulo = new JLabel("", JLabel.CENTER);
                if (Boolean.parseBoolean(value.toString())) {
                    //rotulo.setIcon(new Resource().getIcon("printer"));                    
                    rotulo.setText("impresso");
                }
                return rotulo;

            }
        };
    }

    private static String format(String pattern, Object value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(value);
        } catch (ParseException e) {
            System.out.println(value);
            e.printStackTrace();
            return "";
        }
    }

    public static boolean dataInvalida(String dataBR) {
        try {
            DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");  
            df.setLenient (false); // aqui o pulo do gato 
            df.parse(dataBR);
            return false;
        } catch (ParseException ex) {
            return true;
        }
    }
    
    
    public static Date getZeraHora(Date dataIn) {
        Calendar currDtCal = Calendar.getInstance();
        currDtCal.setTime(dataIn);
        currDtCal.set(Calendar.HOUR_OF_DAY, 0);
        currDtCal.set(Calendar.MINUTE, 0);
        currDtCal.set(Calendar.SECOND, 0);
        currDtCal.set(Calendar.MILLISECOND, 0);
        return currDtCal.getTime();
    }

    public static final String capitalize(String str)
    {   str = str.toLowerCase(Locale.ROOT);
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1);
    }
}
