package com.acme.mytrader.impl;

import junit.framework.TestCase;

public class ExecutionServiceImplTest extends TestCase {
    private ExecutionServiceImpl executionServiceImpl;
    public void setUp() throws Exception {
        super.setUp();
        executionServiceImpl= new ExecutionServiceImpl();
    }

    public void testBuy() {
        executionServiceImpl.buy("ibm", 55, 100);
        assertTrue(executionServiceImpl.isSecurityBoughtOrSold());
    }

    public void testSell() {
        executionServiceImpl.sell("ibm", 55, 100);
        assertTrue(executionServiceImpl.isSecurityBoughtOrSold());
    }


    public void testIsSecurityBoughtOrSold() {
        assertFalse(executionServiceImpl.isSecurityBoughtOrSold());

    }
}