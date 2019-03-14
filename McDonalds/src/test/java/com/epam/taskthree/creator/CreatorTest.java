package com.epam.taskthree.creator;

import com.epam.taskthree.entity.Customer;
import com.epam.taskthree.entity.Order;
import com.epam.taskthree.repository.Repository;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class CreatorTest {

    private Creator creator = new Creator();

    @Test
    public void testCreateOrdersFromFile() {
        //given
        List<Order> expected = new ArrayList<>();
        expected.add(new Order(4, false));
        expected.add(new Order(5, false));
        expected.add(new Order(12, false));
        expected.add(new Order(1, true));
        expected.add(new Order(234, true));
        //when
        List<Order> actual = creator.createOrdersFromFile("data\\orders.txt");
        //then
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testCreateCustomersWithOrders() {
        //given
        List<Customer> expected = new ArrayList<>();
        expected.add(new Customer(new Order(4, false)));
        expected.add(new Customer(new Order(234, true)));
        expected.add(new Customer(new Order(17, false)));
        //when
        List<Order> actualOrders = new ArrayList<>();
        actualOrders.add(new Order(4, false));
        actualOrders.add(new Order(234, true));
        actualOrders.add(new Order(17, false));
        Repository<Customer> actualRepository = creator.createCustomersWithOrders(actualOrders);
        List<Customer> actual = actualRepository.getAll();
        //then
        Assert.assertEquals(actual, expected);
    }
}