package com.acme.mytrader.price;

public interface PriceSource {
    void addPriceListener(PriceListener listener);
    void removePriceListener(PriceListener listener);
    void generateSecurityPrice(double price);
}
