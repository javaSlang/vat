package com.slang.vat.service.impl;

import com.slang.vat.domain.VatInfo;
import com.slang.vat.service.DataOutput;
import com.slang.vat.service.VatInfoData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static java.text.MessageFormat.format;

public class VatDataOutput implements DataOutput {

    private static final Logger LOGGER = LogManager.getLogger(VatDataOutput.class);

    private static final String RESULT_DIVIDING_LINE = "------------------- R E S U L T -------------------";
    private static final String DIVIDING_LINE = "---------------------------------------------------";
    private static final String OUTPUT_TEXT_PATTERN = " EU countries with the {0} standard VAT rate: ";
    private static final String SERVICE_LINE_PATTERN = "{0}\n{1}{2}";

    private final TreeSet<VatInfo> vatInfos;
    private final int itemsToDisplay;

    public VatDataOutput(VatInfoData vatInfos, int itemsToDisplay) {
        this.vatInfos = new TreeSet<>(vatInfos.parse());
        this.itemsToDisplay = itemsToDisplay;
    }

    @Override
    public void display() {
        LOGGER.debug("Displaying the results...");
        printServiceLine(format(SERVICE_LINE_PATTERN, RESULT_DIVIDING_LINE, itemsToDisplay, format(OUTPUT_TEXT_PATTERN, "lowest")));
        printSelectedResults(vatInfos::pollFirst);
        printServiceLine(format(SERVICE_LINE_PATTERN, DIVIDING_LINE, itemsToDisplay, format(OUTPUT_TEXT_PATTERN, "highest")));
        printSelectedResults(vatInfos::pollLast);
        printServiceLine(DIVIDING_LINE);
    }

    private void printServiceLine(String line) {
        System.out.println(line);
    }

    private void printSelectedResults(Supplier<VatInfo> selector) {
        IntStream.rangeClosed(1, itemsToDisplay)
                .mapToObj(i -> selector.get())
                .forEachOrdered(System.out::println);
    }
}
