package com.br.util;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Log4j2
public class Validate {
    public static boolean isValidDate(String data){
        try {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            LocalDate.parse(data, formatter);
            return true;
        } catch (DateTimeParseException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
