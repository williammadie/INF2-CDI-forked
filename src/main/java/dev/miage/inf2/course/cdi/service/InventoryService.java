package dev.miage.inf2.course.cdi.service;

public interface InventoryService<T> {
    void addToInventory(T t);

    T takeFromInventory();

    long countItemsInInventory();
}
