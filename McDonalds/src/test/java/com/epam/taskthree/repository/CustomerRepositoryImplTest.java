package com.epam.taskthree.repository;

import com.epam.taskthree.entity.Customer;
import com.epam.taskthree.entity.Order;
import com.epam.taskthree.repository.impl.RepositoryImpl;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;


public class CustomerRepositoryImplTest {

    private final static Customer ONE = new Customer(new Order(4, false));

    private final static Customer TWO = new Customer(new Order(234, true));

    private final static Customer THREE = new Customer(new Order(17, false));

    private final static Customer FOUR = new Customer(new Order(41, true));


    @Test
    public void testAdd() {
        //given
        Repository<Customer> repository = new RepositoryImpl();
        repository.addAll(Arrays.asList(ONE, TWO, THREE, FOUR));

        List<Customer> expected = Arrays.asList(ONE, TWO, THREE, FOUR, FOUR);
        //when
        repository.add(FOUR);
        List<Customer> actual = repository.getAll();
        //then
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testRemove() {
        //given
        Repository<Customer> repository = new RepositoryImpl();
        repository.addAll(Arrays.asList(ONE, TWO, THREE, FOUR));

        List<Customer> expected = Arrays.asList(ONE, TWO, FOUR);
        //when
        repository.remove(THREE);
        List<Customer> actual = repository.getAll();
        //then
        Assert.assertEquals(actual, expected);
    }
}