package dev.miage.inf2.course.cdi.service.impl;

import dev.miage.inf2.course.cdi.service.InventoryService;
import jakarta.enterprise.context.Dependent;

import java.util.Collection;
import java.util.Collections;

@Dependent
public class InDatabaseInventoryService<T> implements InventoryService<T> {
    @Override
    public void addToInventory(T t) {
        //INSERT INTO
    }

    @Override
    public T takeFromInventory() {
        //SELECT FROM
        return null;
    }

    @Override
    public long countItemsInInventory() {
        // SELECT COUNT(*) FROM...
        return 0;
    }

    @Override
    public Collection<T> listAllItems() {
            //select * from

        return Collections.EMPTY_LIST;
    }
}
