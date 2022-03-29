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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy {
    public static void main(String[] args) {
        List<Security> securities = new ArrayList<>();
        TradingStrategy tradingStrategy = new TradingStrategy();
        double buyPrice=55;
        Security security = new Security("ibm", "IBM");
        securities.add(security);
        ExecutionService executionService = new ExecutionServiceImpl();
        BlockingQueue<SecurityPrice> blockingQueue = new LinkedBlockingDeque<>();

        PriceListener priceListener = new PriceListenerImpl(blockingQueue);

        PriceSource priceSource = new PriceSourceImpl(securities);
        priceSource.addPriceListener(priceListener);
        ExecutorService executorServiceForExecutionService = Executors.newSingleThreadExecutor();
        tradingStrategy. executeBuyOrSell(blockingQueue, buyPrice,executionService,executorServiceForExecutionService);
        priceSource.generateSecurityPrice(55);
    }


    /**
     * This method execute thread which poll blockingQueue and verify condition of buying security once condition meet
     * executor service got terminated
     * @param blockingQueue
     * @param buyPrice
     * @param executionService
     * @param executorServiceForExecutionService
     */
    public  void executeBuyOrSell(BlockingQueue<SecurityPrice> blockingQueue, double buyPrice, ExecutionService executionService, ExecutorService executorServiceForExecutionService){
        Runnable r2 = () -> {
            try {
                while (true) {
                    SecurityPrice securityPrice = blockingQueue.poll(Util.time_in_ms, TimeUnit.MILLISECONDS);
                    if(securityPrice!=null && securityPrice.getSecurity().getSecurityName().equalsIgnoreCase("IBM") && securityPrice.getPrice()<= buyPrice) {
                        executionService.buy(securityPrice.getSecurity().getSecurityId(), securityPrice.getPrice(), 100);
                        if(executionService.isSecurityBoughtOrSold()){
                            break;
                        }
                        System.out.println("Security not bought/sold yet :(");
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("Execution Service Stopped");
                e.printStackTrace();
            }
        };
        try {
            Thread.sleep(Util.time_in_ms);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        executorServiceForExecutionService.submit(r2);
    }
}
