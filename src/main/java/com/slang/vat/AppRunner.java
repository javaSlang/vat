package com.slang.vat;

import com.slang.vat.service.impl.RawDataFromUrl;
import com.slang.vat.service.impl.VatDataOutput;
import com.slang.vat.service.impl.VatInfoDataFromJson;

public class AppRunner {

    private static final String VAT_SOURCE_URL = "http://jsonvat.com";
    private static final int ITEMS_TO_DISPLAY = 3;

    public static void main(String[] args) {
        new VatDataOutput(
                new VatInfoDataFromJson(
                        new RawDataFromUrl(VAT_SOURCE_URL)
                ),
                ITEMS_TO_DISPLAY
        ).display();
    }

}