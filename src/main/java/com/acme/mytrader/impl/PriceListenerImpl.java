package com.acme.mytrader.impl;

import com.acme.mytrader.model.Security;
import com.acme.mytrader.model.SecurityPrice;
import com.acme.mytrader.price.PriceListener;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

public class PriceListenerImpl implements PriceListener {
    public final BlockingQueue<SecurityPrice> blockingQueue;
    private final String priceListenerId;

    public PriceListenerImpl(BlockingQueue<SecurityPrice> blockingQueue) {
        this.blockingQueue = blockingQueue;
        this.priceListenerId = UUID.randomUUID().toString();
    }

    /**
     *  This method got executed when this class get notified by PriceSource service
     *  it validates the security & price and add them into the blockingQueue for further action
     * @param security
     * @param price
     */
    @Override
    public void priceUpdate(String security, double price) {
        if (security == null || security.isEmpty()) {
            throw new IllegalArgumentException("Security Id cannot be null");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        Security securityObj = new Security("ibm");
        SecurityPrice securityPrice = new SecurityPrice(securityObj, price);
        boolean pricePushed = blockingQueue.offer(securityPrice);
        System.out.println("Security price pushed " + pricePushed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceListenerImpl that = (PriceListenerImpl) o;
        return blockingQueue.equals(that.blockingQueue) && priceListenerId.equals(that.priceListenerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockingQueue, priceListenerId);
    }
}
