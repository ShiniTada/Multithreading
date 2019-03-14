package com.epam.taskthree.repository;

import com.epam.taskthree.entity.Customer;


public interface Specification<T extends Customer> {

    boolean specified(T t);
}
