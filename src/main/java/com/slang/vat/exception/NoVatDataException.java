package com.slang.vat.exception;

public class NoVatDataException extends Exception {

    private final String country;

    public NoVatDataException(String country) {
        this.country = country;
    }

    @Override
    public String getMessage() {
        return "There are no VAT data sets for " + country + " -> skipping...";
    }

}
