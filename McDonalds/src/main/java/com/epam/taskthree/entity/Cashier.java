package com.epam.taskthree.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Cashier implements Callable<String> {

    private final static Logger LOGGER = LogManager.getLogger(Cashier.class);

    private static Lock cashierLock = new ReentrantLock();



    private List<Customer> listCustomer;

    private Customer specialCustomer;

    private boolean helpSpecialCustomer = false;


    public Cashier(List<Customer> listCustomer) {
        this.listCustomer = listCustomer;
    }


    public void setSpecialCustomer(Customer specialCustomer) {
        this.specialCustomer = specialCustomer;
        helpSpecialCustomer = true;
    }

    private void getOrderToCustomer(Customer customer) {
        try {
            TimeUnit.SECONDS.sleep(1);
            int orderNumber = customer.getOrder().getOrderNumber();
            LOGGER.info("Order " + orderNumber + " is completed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warn("Exception from : " + Thread.currentThread().getName() + " in getOrderToCustomer method.");
        }
    }


    @Override
    public String call() {

        int customerCounter = 0;
        try {
            cashierLock.lock();
            for (int i = 0; i < listCustomer.size(); i++) {
                if (helpSpecialCustomer) {
                    LOGGER.info(" Special customer buys...");
                    specialCustomer.wakeUp();
                    getOrderToCustomer(specialCustomer);
                    helpSpecialCustomer = false;
                    ++customerCounter;
                }
                LOGGER.info(customerCounter + "  customer buys...");
                customer.wakeUp();
                getOrderToCustomer(listCustomer.get(i));
                ++customerCounter;
            }
            return " cashier : " + customerCounter + " clients!";

        } finally {
            cashierLock.unlock();
        }
    }

}
