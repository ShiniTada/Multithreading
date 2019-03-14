package com.epam.taskthree.creator;

import com.epam.taskthree.entity.Customer;
import com.epam.taskthree.entity.Order;
import com.epam.taskthree.exception.ReadDataException;
import com.epam.taskthree.main.Main;
import com.epam.taskthree.parser.DataParser;
import com.epam.taskthree.parser.impl.DataParserImpl;
import com.epam.taskthree.reader.DataReader;
import com.epam.taskthree.reader.impl.DataReaderImpl;
import com.epam.taskthree.repository.Repository;
import com.epam.taskthree.repository.impl.RepositoryImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Creator {
    private final static Logger LOGGER = LogManager.getLogger(Main.class);

    private DataReader read = DataReaderImpl.getInstance();
    private DataParser parser = DataParserImpl.getInstance();
    private Repository<Customer> customerRepository = new RepositoryImpl();

    public Repository<Customer> createCustomersFromFileAndAddToRepository(String path) {
        return createCustomersWithOrders(createOrdersFromFile(path));
    }

    public List<Order> createOrdersFromFile(String path) {
        List<Order> orders = new ArrayList<>();
        List<String> strings = getStringsFromFile(path);
        for (String string : strings) {
            orders.add(parser.parseStringToOrder(string));
        }
        return orders;
    }


    public List<String> getStringsFromFile(String path) {
        try {
            List<String> strings = read.readStringsInFile(path);
            return strings;
        } catch (ReadDataException e) {
            LOGGER.fatal(e);
            throw new RuntimeException(e);
        }
    }


    public Repository<Customer> createCustomersWithOrders(List<Order> orders) {
        List<Customer> customers = new ArrayList<>();
        for (Order order : orders) {
            customers.add(new Customer(order));
        }
        customerRepository.addAll(customers);
        return customerRepository;
    }

}
