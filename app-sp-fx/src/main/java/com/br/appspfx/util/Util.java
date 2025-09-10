package com.br.appspfx.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static SimpleDateFormat dfUS = new SimpleDateFormat("yyyy-MM-dd");
    public static DateTimeFormatter dfBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static DecimalFormat mf = new DecimalFormat("#,##0.00");
    
    public static boolean stringNullOrEmpty(String text){
        if(text==null || text.trim().isEmpty() || text.equals("null"))
            return true;
        return false;
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
