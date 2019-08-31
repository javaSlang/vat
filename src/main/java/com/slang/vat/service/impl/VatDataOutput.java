package com.slang.vat.service.impl;

import com.slang.vat.domain.VatInfo;
import com.slang.vat.service.DataOutput;
import com.slang.vat.service.VatInfoData;

import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class VatDataOutput implements DataOutput {

    private final TreeSet<VatInfo> vatInfos;
    private final int itemsToDisplay;

    public VatDataOutput(VatInfoData vatInfos, int itemsToDisplay) {
        this.vatInfos = new TreeSet<>(vatInfos.parse());
        this.itemsToDisplay = itemsToDisplay;
    }

    @Override
    public void display() {
        System.out.println(itemsToDisplay + " EU countries with the lowest standard VAT rate: ");
        print(vatInfos::pollFirst);
        System.out.println(itemsToDisplay + " EU countries with the highest standard VAT rate: ");
        print(vatInfos::pollLast);
    }

    private void print(Supplier<VatInfo> selector) {
        IntStream.rangeClosed(1, itemsToDisplay)
                .mapToObj(i -> selector.get())
                .forEachOrdered(System.out::println);
    }
}
