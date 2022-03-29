package com.acme.mytrader.impl;

import com.acme.mytrader.model.Security;
import com.acme.mytrader.price.PriceListener;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class PriceSourceImplTest extends TestCase {


    private  List<Security> securityList ;

    private PriceListener priceListener;

   @InjectMocks
    private PriceSourceImpl priceSource;
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this.getClass());
        priceListener = new PriceListenerImpl(new LinkedBlockingDeque<>());
        securityList = new ArrayList<>();
        securityList.add(new Security("ibm","IBM"));
        priceSource = new PriceSourceImpl(securityList);
    }

    public void testAddPriceListener() {
        priceSource.addPriceListener(priceListener);
        assertEquals(priceSource.getPriceListenerList().size(),1);
    }

    public void testRemovePriceListener() {
        priceSource.addPriceListener(priceListener);
        priceSource.removePriceListener(priceListener);
        assertEquals(priceSource.getPriceListenerList().size(),0);

    }

    public void testGenerateSecurityPrice() throws NoSuchMethodException {

        priceSource.addPriceListener(priceListener);
        priceSource.generateSecurityPrice(12);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testGenerateSecurityPriceThrowException() throws NoSuchMethodException {

        priceSource.addPriceListener(priceListener);
        priceSource.generateSecurityPrice(12);
    }
}