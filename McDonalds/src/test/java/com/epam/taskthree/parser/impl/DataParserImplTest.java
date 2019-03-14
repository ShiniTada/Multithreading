package com.epam.taskthree.parser.impl;

import com.epam.taskthree.entity.Order;
import com.epam.taskthree.parser.DataParser;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DataParserImplTest {

    private DataParser parser;

    @BeforeTest
    public void setUp() {
        parser = DataParserImpl.getInstance();
    }


    @Test(description = "correct data")
    public void testParseStringToOrderSuccessfully() {
        //given
        Order expected = new Order(4, true);
        //when
        Order actual = parser.parseStringToOrder("4 true");
        //then
        Assert.assertEquals(actual, expected);
    }


    @Test(description = "incorrect data")
    public void testParseStringToOrderUnsuccessfully() {
        //given
        Order expected = new Order(0, false);
        //when
        Order actual = parser.parseStringToOrder("here we are");
        //then
        Assert.assertEquals(actual, expected);

    }
}