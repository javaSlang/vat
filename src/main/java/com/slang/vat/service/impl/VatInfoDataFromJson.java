package com.slang.vat.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.slang.vat.domain.VatInfo;
import com.slang.vat.service.RawData;
import com.slang.vat.service.VatInfoData;

import java.math.BigDecimal;
import java.util.TreeSet;

public class VatInfoDataFromJson implements VatInfoData {

    private final RawData rawJson;

    public VatInfoDataFromJson(RawData rawJson) {
        this.rawJson = rawJson;
    }

    @Override
    public TreeSet<VatInfo> parse() {
        JsonObject jsonObject = new JsonParser().parse(rawJson.fetch()).getAsJsonObject();
        JsonArray rates = jsonObject.getAsJsonArray("rates");
        TreeSet<VatInfo> vatInfos = new TreeSet<>();
        for (JsonElement elem : rates) {
            String country = elem.getAsJsonObject().get("name").getAsString();
            JsonArray periods = elem.getAsJsonObject().getAsJsonArray("periods");
            JsonElement jsonElement = periods.get(0);
            BigDecimal vat = jsonElement.getAsJsonObject().get("rates").getAsJsonObject().get("standard").getAsBigDecimal();
            vatInfos.add(new VatInfo(country, vat));
        }
        return vatInfos;
    }
}
