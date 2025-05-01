package com.br.util;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Log4j2
public class Validate {
    public static boolean isValidDate(String data){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        try {
            return Objects.nonNull(LocalDate.parse(data, formatter));
        } catch (DateTimeParseException e) {
            //throw new RuntimeException("Your custom exception");
            log.error(e.getMessage());
            return false;
        }
    }
}
