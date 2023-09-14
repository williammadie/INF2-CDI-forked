package dev.miage.inf2.course.cdi.service.impl;

import dev.miage.inf2.course.cdi.exception.OutOfStockException;
import dev.miage.inf2.course.cdi.model.Candy;
import dev.miage.inf2.course.cdi.service.InventoryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

@ApplicationScoped
@Named("InventoryGoodForCandyStore")
public class InMemoryCandyInventoryService implements InventoryService<Candy> {

    ConcurrentMap<String, BlockingDeque<Candy>> inventory = new ConcurrentHashMap<>();

    @Override
    public void addToInventory(Candy candy) {
        synchronized (candy.id()) {
            //System.out.println("Adding a new book to inventory, we have " + this.inventory.values().stream().mapToInt(i -> i.size()).sum() + " remaining");
            if (inventory.containsKey(candy.id())) {
                inventory.get(candy.id()).offer(candy);
            } else {
                inventory.put(candy.id(), new LinkedBlockingDeque<>(List.of(candy)));
            }
        }
    }

    @Override
    public Candy takeFromInventory() {
        try {
            //System.out.println("Taking a book from inventory, we have " + this.inventory.values().stream().mapToInt(i -> i.size()).sum() + " remaining");
            var candy = inventory.values().stream().filter(v -> v.size() > 0).findAny().orElseThrow().poll();
            if (candy == null) {
                throw new NoSuchElementException();
            }
            return candy;
        } catch (NoSuchElementException e) {
            throw new OutOfStockException("we don't have any book for you");
        }
    }

    @Override
    public Candy takeFromInventory(String id) {
        return this.inventory.get(id).poll();
    }

    @Override
    public long countItemsInInventory() {
        return inventory.entrySet().stream().mapToInt(e -> e.getValue().size()).sum();
    }

    @Override
    public Collection<Candy> listAllItems() {
        return this.inventory.values().stream().flatMap(c -> c.stream()).collect(Collectors.toSet());
    }

    public void deleteBook(String id) {
        this.inventory.remove(id);
    }
}
