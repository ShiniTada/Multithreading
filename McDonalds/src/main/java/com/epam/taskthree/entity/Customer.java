package com.epam.taskthree.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Customer extends Thread {

    private final static Logger LOGGER = LogManager.getLogger(Customer.class);

    private static Lock customerLock = new ReentrantLock();

    public Condition condition = customerLock.newCondition();

    private Order order;


    /**
     * Set {@code Order order} and call {@link #start}.
     *
     * @param order the element which each customer have
     */
    public Customer(Order order) {
        this.order = order;
        setDaemon(true);
        start();
    }

    public Order getOrder() {
        return order;
    }
    
    puublic wakeUp() {
         condition.signal();
    }

    @Override
    public void run() {
        try {
            try {
                customerLock.lock();
                LOGGER.info("Customer waiting...");
                condition.await();
                TimeUnit.SECONDS.sleep(1);
            } finally {
                customerLock.unlock();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warn("Exception from : " + Thread.currentThread().getName() + " in run() method.");
        }

    }

    @Override
    public String toString() {
        return "Customer{" +
                "order=" + order +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(order, customer.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order);
    }


}
