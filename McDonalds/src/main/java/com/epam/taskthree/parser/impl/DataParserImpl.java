package com.epam.taskthree.parser.impl;

import com.epam.taskthree.entity.Order;
import com.epam.taskthree.parser.DataParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;


public class DataParserImpl implements DataParser {

    private final static Logger LOGGER = LogManager.getLogger(DataParserImpl.class);

    private final static String ORDER_NUMBER_REGEX = "\\d+";

    private final static String PRELIMINARY_ORDER_REGEX = "true|false";

    private final static String SEPARATOR = "\\s+";

    private static DataParserImpl INSTANCE = new DataParserImpl();

    private DataParserImpl() {
    }

    public static DataParserImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Order parseStringToOrder(String string) {
        List<String> values = Arrays.asList(string.split(SEPARATOR));
        int orderNumber = 0;
        boolean preliminary = false;
        for (String value : values) {
            if (value.matches(ORDER_NUMBER_REGEX)) {
                orderNumber = Integer.parseInt(value);
            }
            if (value.matches(PRELIMINARY_ORDER_REGEX)) {
                preliminary = Boolean.parseBoolean(value);
            }
        }
        Order order = new Order(orderNumber, preliminary);
        LOGGER.info("Parse was successful complete.");
        return order;
    }
}
