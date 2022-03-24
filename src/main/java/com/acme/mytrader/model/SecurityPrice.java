package com.acme.mytrader.model;

public final class SecurityPrice {
    private final Security security;
    private final double price;

    public SecurityPrice(Security security, double price) {
        this.security = security;
        this.price = price;
    }

    public Security getSecurity() {
        return security;
    }

    public double getPrice() {
        return price;
    }
}
