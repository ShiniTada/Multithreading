package com.epam.taskthree.repository;

import com.epam.taskthree.entity.Customer;
import com.epam.taskthree.entity.Order;
import com.epam.taskthree.repository.impl.RepositoryImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class CustomerRepositoryQueryTest {

    private final static Customer ONE = new Customer(new Order(4, false));

    private final static Customer TWO = new Customer(new Order(234, true));

    private final static Customer THREE = new Customer(new Order(17, false));

    private final static Customer FOUR = new Customer(new Order(41, true));

    private Repository<Customer> repository;


    @BeforeTest
    public void setUp() {
        repository = new RepositoryImpl();
        repository.addAll(Arrays.asList(ONE, TWO, THREE, FOUR));
    }

    @Test
    public void testQueryOrdinary() {
        //given
        List<Customer> expected = Arrays.asList(ONE, THREE);
        //when
        List<Customer> actual = repository.query((new SearchSpecification()).ORDINARY);
        //then
        Assert.assertEquals(actual, expected);
    }


    @Test
    public void testQueryPre() {
        //given
        List<Customer> expected = Arrays.asList(TWO, FOUR);
        //when
        List<Customer> actual = repository.query((new SearchSpecification()).PRE_ORDER);
        //then
        Assert.assertEquals(actual, expected);
    }

}