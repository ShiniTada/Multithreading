package com.epam.taskthree.repository.impl;

import com.epam.taskthree.entity.Customer;
import com.epam.taskthree.repository.Repository;
import com.epam.taskthree.repository.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class RepositoryImpl implements Repository {

    private List<Customer> customers = new ArrayList<>();

    @Override
    public void add(Customer customer) {
        customers.add(customer);
    }


    @Override
    public void remove(Customer customer) {
        customers.remove(customer);
    }


    @Override
    public void addAll(List listCustomers) {
        customers.addAll(listCustomers);
    }


    @Override
    public Customer get(int index) {
        return customers.get(index);
    }


    @Override
    public List<Customer> query(Specification specification) {
        return customers.stream()
                .filter(specification::specified)
                .collect(Collectors.toList());
    }


    @Override
    public List<Customer> getAll() {
        return customers;
    }

}
