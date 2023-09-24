package dev.miage.inf2.course.cdi.domain.event;

import dev.miage.inf2.course.cdi.model.Book;
import dev.miage.inf2.course.cdi.model.Candy;
import dev.miage.inf2.course.cdi.service.InventoryService;
import dev.miage.inf2.course.cdi.service.InventoryWithQtyService;

public class CandySoldEvent {
    final private Candy candy;
    final private int qty;

    private InventoryWithQtyService<Candy> inventoryService;

    public CandySoldEvent(Candy candy, int qty, InventoryWithQtyService<Candy> inventoryService) {
        this.candy = candy;
        this.qty = qty;
        this.inventoryService = inventoryService;
    }

    public Candy getCandy() {
        return candy;
    }

    public int getSoldQty() { return qty; }

    public InventoryWithQtyService<Candy> getInventoryService() {
        return inventoryService;
    }
}
