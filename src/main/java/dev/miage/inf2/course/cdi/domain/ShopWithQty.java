package dev.miage.inf2.course.cdi.domain;

import dev.miage.inf2.course.cdi.model.Customer;

public interface ShopWithQty<T> {
    T sell(Customer customer, String id, int qty);
}
