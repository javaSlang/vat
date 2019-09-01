package com.slang.vat.service.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slang.vat.exception.NoVatDataException;
import org.junit.Test;

import java.util.TreeSet;

import static com.slang.vat.service.impl.VatInfoDataFromJson.COUNTRY_ITEM;
import static com.slang.vat.service.impl.VatInfoDataFromJson.PERIODS_ITEM;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VatInfoDataFromJsonTest {

    private static final String TEST_COUNTRY = "TEST COUNTRY";

    private final VatInfoDataFromJson vatFromJson;

    public VatInfoDataFromJsonTest() {
        this.vatFromJson = new VatInfoDataFromJson(
                new RawDataFromUrl("https://dummy-url.com")
        );
    }

    @Test(expected = NoVatDataException.class)
    public void testNoVatData() throws NoVatDataException {
        JsonElement mockedJsonElement = mock(JsonElement.class);
        JsonObject testRates = prepareTestRatesJson();
        when(mockedJsonElement.getAsJsonObject()).thenReturn(testRates);
        try {
            vatFromJson.processVatData(mockedJsonElement, new TreeSet<>());
        } catch (NoVatDataException e) {
            assertEquals("There are no VAT data sets for " + TEST_COUNTRY + " -> skipping...", e.getMessage());
            throw e;
        }
    }

    private JsonObject prepareTestRatesJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(COUNTRY_ITEM, TEST_COUNTRY);
        jsonObject.add(PERIODS_ITEM, new JsonArray());
        return jsonObject;
    }
}