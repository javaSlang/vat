package com.slang.vat.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.slang.vat.domain.VatInfo;
import com.slang.vat.exception.NoVatDataException;
import com.slang.vat.service.RawData;
import com.slang.vat.service.VatInfoData;

import java.math.BigDecimal;
import java.util.TreeSet;

public class VatInfoDataFromJson implements VatInfoData {

    private static final String RATES_ITEM = "rates";
    static final String COUNTRY_ITEM = "name";
    static final String PERIODS_ITEM = "periods";
    private static final String STANDARD_ITEM = "standard";

    private final RawData rawJson;

    public VatInfoDataFromJson(RawData rawJson) {
        this.rawJson = rawJson;
    }

    @Override
    public TreeSet<VatInfo> parse() {
        // relying on json structure from upstream
        JsonObject initData = new JsonParser().parse(rawJson.fetch()).getAsJsonObject();
        JsonArray rates = initData.getAsJsonArray(RATES_ITEM);
        TreeSet<VatInfo> vatInfos = new TreeSet<>();
        for (JsonElement rateElement : rates) {
            try {
                processVatData(rateElement, vatInfos);
            } catch (NoVatDataException e) {
                System.out.println(e.getMessage());
            }
        }
        return vatInfos;
    }

    void processVatData(JsonElement rateElement, TreeSet<VatInfo> vatInfos) throws NoVatDataException {
        String country = rateElement.getAsJsonObject().get(COUNTRY_ITEM).getAsString();
        JsonArray periods = rateElement.getAsJsonObject().getAsJsonArray(PERIODS_ITEM);
        if (periods.size() == 0) {
            throw new NoVatDataException(country);
        } else if (periods.size() > 1) {
            System.out.println(country + " contains more than one VAT data set... proceeding with the latest one");
        }
        JsonElement vatInfoDataSet = periods.get(0);
        BigDecimal vat = vatInfoDataSet.getAsJsonObject().get(RATES_ITEM).getAsJsonObject().get(STANDARD_ITEM).getAsBigDecimal();
        vatInfos.add(new VatInfo(country, vat));
    }
}
