package com.epam.taskthree.entity;

import java.util.Objects;

public class Order {

    private int orderNumber;

    private boolean isPreOrder;


    public Order(int orderNumber, boolean isPreOrder) {
        this.orderNumber = orderNumber;
        this.isPreOrder = isPreOrder;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public boolean isPreOrder() {
        return isPreOrder;
    }


    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", isPreOrder=" + isPreOrder +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber &&
                isPreOrder == order.isPreOrder;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, isPreOrder);
    }
}
