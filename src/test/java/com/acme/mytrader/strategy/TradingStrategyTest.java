package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.impl.ExecutionServiceImpl;
import com.acme.mytrader.impl.PriceListenerImpl;
import com.acme.mytrader.impl.PriceSourceImpl;
import com.acme.mytrader.model.Security;
import com.acme.mytrader.model.SecurityPrice;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;
import com.acme.mytrader.util.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.mockito.Mockito.when;

public class TradingStrategyTest {
    @InjectMocks
    private TradingStrategy tradingStrategy;
    private Security security;
    ExecutionService executionService;
    @Mock
    List<Security> securities;
    @Mock
    BlockingQueue<SecurityPrice> blockingQueue;
    @Mock
    ExecutorService executorServiceForExecutionService;

    @Before
    public void setUp() throws InterruptedException {
        MockitoAnnotations.initMocks(this);
        executionService = new ExecutionServiceImpl();
        tradingStrategy = new TradingStrategy();
        security = new Security("ibm", "IBM");
        securities.add(security);
        executorServiceForExecutionService = Executors.newSingleThreadExecutor();
    }


    @Test
    public void testExecuteBuyOrSellTrue() throws InterruptedException {
        when(blockingQueue.poll(Util.time_in_ms, TimeUnit.MILLISECONDS)).thenReturn(new SecurityPrice(security, 55));
        tradingStrategy.executeBuyOrSell(blockingQueue, 55, executionService, executorServiceForExecutionService);
    }

    @Test
    public void testExecuteBuyOrSellFalse() throws InterruptedException {
        when(blockingQueue.poll(Util.time_in_ms, TimeUnit.MILLISECONDS)).thenReturn(new SecurityPrice(security, 67));
        tradingStrategy.executeBuyOrSell(blockingQueue, 55, executionService, executorServiceForExecutionService);
        Assert.assertFalse(executionService.isSecurityBoughtOrSold());
        Assert.assertFalse(executorServiceForExecutionService.isShutdown());

    }
}
