package com.epam.taskthree.reader.impl;

import com.epam.taskthree.exception.ReadDataException;
import com.epam.taskthree.reader.DataReader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class DataReaderImplTest {
    private DataReader reader;


    @BeforeClass
    public void init() {
        reader = DataReaderImpl.getInstance();
    }


    @Test(description = "read data from file")
    public void testReadStringsInFileSuccessfully() throws ReadDataException {
        //given
        List<String> expected = Arrays.asList("4 false", "5 false", "12 false", "1 true", "234 true");
        //when
        List<String> actual = reader.readStringsInFile("data\\orders.txt");
        //then
        Assert.assertEquals(actual, expected);
    }


    @Test(description = "file is not exist or empty", expectedExceptions = ReadDataException.class)
    public void testFileExistence() throws ReadDataException {
        List<String> actual = reader.readStringsInFile("data\\another.txt");
    }

}