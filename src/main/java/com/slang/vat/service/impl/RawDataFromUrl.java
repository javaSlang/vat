package com.slang.vat.service.impl;

import com.slang.vat.service.RawData;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public class RawDataFromUrl implements RawData {

    private static final String UTF_8_CHARSET = "UTF-8";

    private final String sourceUrl;

    public RawDataFromUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Override
    public String fetch() {
        try (InputStream inputStream = new URL(sourceUrl).openStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName(UTF_8_CHARSET));
             BufferedReader rd = new BufferedReader(inputStreamReader)) {
            return IOUtils.toString(rd);
        } catch (IOException e) {
            throw new RuntimeException(ExceptionUtils.getStackTrace(e));
        }
    }
}
