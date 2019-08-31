package com.slang.vat.domain;

import java.math.BigDecimal;

public class VatInfo implements Comparable<VatInfo> {

    private final String country;
    private final BigDecimal vat;

    public VatInfo(String country, BigDecimal vat) {
        this.country = country;
        this.vat = vat;
    }

    private String getCountry() {
        return country;
    }

    private BigDecimal getVat() {
        return vat;
    }

    @Override
    public String toString() {
        return country + ": " + vat;
    }

    @Override
    public int compareTo(VatInfo o) {
        int vatComparison = vat.compareTo(o.getVat());
        return vatComparison != 0 ? vatComparison : country.compareTo(o.getCountry());
    }
}
