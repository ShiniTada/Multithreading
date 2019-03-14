package com.epam.taskthree.repository;

public class SearchSpecification {

    public final Specification ORDINARY = t -> !t.getOrder().isPreOrder();

    public final Specification PRE_ORDER = t -> t.getOrder().isPreOrder();

}
