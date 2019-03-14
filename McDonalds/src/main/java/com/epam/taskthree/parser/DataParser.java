package com.epam.taskthree.parser;

import com.epam.taskthree.entity.Order;

public interface DataParser {

    Order parseStringToOrder(String string);
}
