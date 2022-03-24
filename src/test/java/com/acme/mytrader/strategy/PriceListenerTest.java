package com.acme.mytrader.strategy;

import com.acme.mytrader.impl.PriceListenerImpl;
import com.acme.mytrader.model.Security;
import com.acme.mytrader.model.SecurityPrice;
import com.acme.mytrader.price.PriceListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.BlockingQueue;


public class PriceListenerTest {
    @Mock
    BlockingQueue<SecurityPrice> blockingQueue ;
    PriceListener priceListener;

    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        priceListener = new PriceListenerImpl(blockingQueue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void priceUpdateTest1(){
        priceListener.priceUpdate("" , 100);

    }

    @Test(expected = IllegalArgumentException.class)
    public void priceUpdateOutput(){
        priceListener.priceUpdate("" , 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void priceUpdateTest2(){
        priceListener.priceUpdate("ïbm" , -1);
    }

    @Test
    public void priceUpdateTest3(){
        Security s = new Security("ibm");
        SecurityPrice sp = new SecurityPrice(s, 100.0);
        Mockito.when(blockingQueue.offer(sp)).thenReturn(true);
    }

    @Test
    public void priceUpdateTest4(){
        priceListener.priceUpdate("ïbm" , 55);
        Assert.assertFalse(blockingQueue.isEmpty());
    }


}
