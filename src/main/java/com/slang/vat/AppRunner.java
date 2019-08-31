package com.slang.vat;

import com.slang.vat.service.impl.RawDataFromUrl;
import com.slang.vat.service.impl.VatDataOutput;
import com.slang.vat.service.impl.VatInfoDataFromJson;

public class AppRunner {

    public static void main(String[] args) {
        new VatDataOutput(
                new VatInfoDataFromJson(
                        new RawDataFromUrl("http://jsonvat.com")
                ),
                3
        ).display();
    }

}