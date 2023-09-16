package dev.miage.inf2.course.cdi.service;

public interface InventoryWithQtyService<T> extends InventoryService<T> {
    T decreaseElementQtyInInventory(String id, int qty);
}
