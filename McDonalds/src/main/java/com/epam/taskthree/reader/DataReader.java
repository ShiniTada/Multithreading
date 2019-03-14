package com.epam.taskthree.reader;

import com.epam.taskthree.exception.ReadDataException;

import java.util.List;

public interface DataReader {
    List<String> readStringsInFile(String path) throws ReadDataException;
}
