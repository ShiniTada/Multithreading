package com.epam.taskthree.reader.impl;

import com.epam.taskthree.exception.ReadDataException;
import com.epam.taskthree.reader.DataReader;
import com.epam.taskthree.validator.ReadFileValidator;
import com.epam.taskthree.validator.impl.ReadFileValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class DataReaderImpl implements DataReader {

    private final static Logger LOGGER = LogManager.getLogger(DataReader.class);

    private final static DataReaderImpl INSTANCE = new DataReaderImpl();

    private DataReaderImpl() {
    }

    public static DataReaderImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<String> readStringsInFile(String path) throws ReadDataException {
        File file = new File(path);
        ReadFileValidator readFileValidator = ReadFileValidatorImpl.getInstance();
        if (!readFileValidator.checkFile(file)) {
            LOGGER.warn("File:" + path + "  is not exist or empty.");
            throw new ReadDataException("File is not exist or empty - " + path);
        }
        try {
            List<String> allLines = Files.readAllLines(file.toPath());
            LOGGER.info("File:" + file + " was successfully read.");
            return allLines;
        } catch (IOException e) {
            LOGGER.warn("File:" + path + ". Exception with readAllLines method.");
            throw new ReadDataException(e);
        }
    }

}

