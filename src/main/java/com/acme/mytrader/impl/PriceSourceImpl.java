package com.acme.mytrader.impl;

import com.acme.mytrader.model.Security;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;

import java.util.ArrayList;
import java.util.List;

public class PriceSourceImpl implements PriceSource {
    public List<PriceListener> getPriceListenerList() {
        return priceListenerList;
    }

    public void setPriceListenerList(List<PriceListener> priceListenerList) {
        this.priceListenerList = priceListenerList;
    }

    private final List<Security> securityList ;

    private  List<PriceListener> priceListenerList = new ArrayList<>();

    public PriceSourceImpl(List<Security> securityList) {
        this.securityList = securityList;
    }


    @Override
    public void addPriceListener(PriceListener listener) {
        priceListenerList.add(listener);
    }

    @Override
    public void removePriceListener(PriceListener listener) {
        priceListenerList.remove(listener);
    }

    /**
     * This method traverse list of security set the price or generate it and notify all observer
     * @param price we can explicitly set price
     */
    @Override
    public void generateSecurityPrice(double price) {
        securityList.parallelStream().forEach(security -> {
            double p =  price > 0? price: 1 + Math.random() * 100;
            System.out.println("price for security " + security.getSecurityName() + " " + p);
            notifyObservers(security, price);
        });

    }

    private void notifyObservers(Security security, double price) {
        if (this.priceListenerList != null) {
            for (PriceListener priceListener : priceListenerList) {
                priceListener.priceUpdate(security.getSecurityId(), price);
            }
        }
    }

}
