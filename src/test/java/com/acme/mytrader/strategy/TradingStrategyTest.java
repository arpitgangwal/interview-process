package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.impl.ExecutionServiceImpl;
import com.acme.mytrader.model.Security;
import com.acme.mytrader.model.SecurityPrice;
import com.acme.mytrader.util.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.when;

public class TradingStrategyTest {
    private TradingStrategy tradingStrategy;
    private Security security;
    ExecutionService executionService;
    @Mock
    BlockingQueue<SecurityPrice> blockingQueue;

    ExecutorService executorServiceForExecutionService;

    @Before
    public void setUp() throws InterruptedException {
        MockitoAnnotations.initMocks(this);
        executionService = new ExecutionServiceImpl();
        tradingStrategy = new TradingStrategy();
        security = new Security("ibm", "IBM");
        executorServiceForExecutionService = Executors.newSingleThreadExecutor();
    }


    @Test
    public void testExecuteBuyOrSellTrue() throws InterruptedException {
        when(blockingQueue.poll(Util.time_in_ms, TimeUnit.MILLISECONDS)).thenReturn(new SecurityPrice(security, 55));
        tradingStrategy.executeBuyOrSell(blockingQueue, 55, executionService, executorServiceForExecutionService);
    }

    @Test
    public void testExecuteBuyOrSellFalse() throws InterruptedException {
        when(blockingQueue.poll(Util.time_in_ms, TimeUnit.MILLISECONDS)).thenReturn(new SecurityPrice(security, 88));
        tradingStrategy.executeBuyOrSell(blockingQueue, 55, executionService, executorServiceForExecutionService);
        Assert.assertFalse(executionService.isSecurityBoughtOrSold());
        Assert.assertFalse(executorServiceForExecutionService.isShutdown());

    }
}
