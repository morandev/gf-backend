package com.morandev.relevamientogf.exception;

public class DataValidationException extends RuntimeException {

    private static final String DESCRIPTION = "Data Validation Exception";

    public DataValidationException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
