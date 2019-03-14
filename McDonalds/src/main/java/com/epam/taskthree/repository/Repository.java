package com.epam.taskthree.repository;

import com.epam.taskthree.entity.Customer;

import java.util.List;


public interface Repository<T extends Customer> {

    void add(T t);

    void remove(T t);

    void addAll(List<T> t);

    T get(int index);

    List<T> query(Specification specification);

    List<T> getAll();

}
