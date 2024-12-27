package com.example.application.services;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StringToDateConverter implements Converter<String, LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Result<LocalDate> convertToModel(String value, ValueContext context) {
        try {
            if (value == null || value.isEmpty()) {
                return Result.ok(null);
            }
            return Result.ok(LocalDate.parse(value, formatter));
        } catch (DateTimeParseException e) {
            return Result.error("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    @Override
    public String convertToPresentation(LocalDate value, ValueContext context) {
        if (value == null) {
            return "";
        }
        return value.format(formatter);
    }
}
