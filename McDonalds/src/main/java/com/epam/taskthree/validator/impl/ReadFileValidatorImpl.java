package com.epam.taskthree.validator.impl;

import com.epam.taskthree.validator.ReadFileValidator;

import java.io.File;


public class ReadFileValidatorImpl implements ReadFileValidator {

    private static ReadFileValidatorImpl INSTANCE = new ReadFileValidatorImpl();

    private ReadFileValidatorImpl() {
    }

    public static ReadFileValidatorImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean checkFile(File file) {
        return !(file == null || !file.exists() || file.isDirectory() || file.length() == 0);
    }
}
