package com.br.shared;

import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class InputData extends JFormattedTextField {

    public InputData() {
		setMargin(new Insets(3,3,3,3));
		setPreferredSize(new Dimension(150, 24));
		setBorder(new EmptyBorder(3, 3, 3, 3));
		setHorizontalAlignment(JTextField.CENTER);
        //setBorder(BorderFactory.createCompoundBorder(getBorder(),BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                setSelectionStart(0);
                setSelectionEnd(10);
            }
        });
        try {
            this.maskData();
        } catch (ParseException ex) {
            log.info(ex);
        }
    }

    public void reset(){
        this.setText("");
    }
    public void setDateValor(LocalDate date){
        if(date!=null){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            setText(format.format(date));
        }
    }
    public LocalDate getDateValor(){
        if(getDateString()!=null){
            try {
                final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(getDateString(), dtf);
            } catch (DateTimeParseException ex) {
                log.info(ex);
                return null;
            }
        }else{
            return null;
        }
    }
    public String getDateString() {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (this.getText().equals("  /  /    ") || this.getText().trim().length()<10) {
            return null;
        } else {

            try {
                if (this.getText() != null && !this.getText().trim().isEmpty()){
                    String[] datavalor = this.getText().split("/");
                    if(Integer.parseInt(datavalor[2])<2000){
                        return null;
                    }else if(Integer.parseInt(datavalor[1])<=0 || Integer.parseInt(datavalor[1])>12){
                        return null;
                    }else if(Integer.parseInt(datavalor[0])<=0 || Integer.parseInt(datavalor[0])>31){
                        return null;
                    }
                    String dateString = datavalor[2] + "-" + datavalor[1] + "-" + datavalor[0];
                    Date data = format.parse(dateString);
                    return dateString;
                } else {
                    return null;
                }

            } catch (ParseException ex) {
                log.info(ex);
                return null;
            }
        }

    }

    public void maskData() throws ParseException {
        MaskFormatter mask = null;
        // nessa linha abaixo vc define o formato, então ###.###.###-## seria para cpf  
        mask = new MaskFormatter("##/##/####");
        //permite sobrescrever os caracteres padrão de separação das barras ( _ )  
        mask.setOverwriteMode(true);
        //os caracteres permitidos para digitação  
        mask.setValidCharacters("0123456789");
        //tipo de caracter que ficará nos espaços para serem preenchidos  
        //mask.setPlaceholderCharacter('_');

        //instala a mascara no maskFormat e retorna no return abaixo  
        mask.install(this);
    }

    public boolean dataInvalidaVazia(){
        if(getDateString()==null)
            return true;
        return false;
    }

    public static void main(String[] args){
        DateFormat teste = new  SimpleDateFormat("yyyy-mm-dd");
        try {
            teste.format(teste.parse("9999-99-99"));
        } catch (ParseException ex) {
            log.info(ex);
        }
    }
}
