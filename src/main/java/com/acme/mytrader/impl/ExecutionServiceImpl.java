package com.acme.mytrader.impl;

import com.acme.mytrader.execution.ExecutionService;


public class ExecutionServiceImpl implements ExecutionService {

    private boolean securityBoughtOrSold;

    @Override
    public void buy(String security, double price, int volume) {
        this.securityBoughtOrSold = true;
        System.out.println("+++++ Security " + security + " bought at " + price + " volume " + volume);
    }

    @Override
    public void sell(String security, double price, int volume) {
        this.securityBoughtOrSold = true;
        System.out.println("----- Security " + security + " sold at " + price + " volume " + volume);
    }

    public boolean isSecurityBoughtOrSold() {
        return securityBoughtOrSold;
    }
}
