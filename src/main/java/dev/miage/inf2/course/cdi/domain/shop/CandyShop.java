package dev.miage.inf2.course.cdi.domain.shop;

import dev.miage.inf2.course.cdi.domain.Shop;
import dev.miage.inf2.course.cdi.domain.ShopWithQty;
import dev.miage.inf2.course.cdi.domain.event.CandySoldEvent;
import dev.miage.inf2.course.cdi.exception.OutOfStockException;
import dev.miage.inf2.course.cdi.model.Candy;
import dev.miage.inf2.course.cdi.model.Customer;
import dev.miage.inf2.course.cdi.model.Receipt;
import dev.miage.inf2.course.cdi.service.InventoryWithQtyService;
import dev.miage.inf2.course.cdi.service.ReceiptTransmissionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Collection;
import java.util.Random;

@ApplicationScoped
public class CandyShop implements Shop<Candy>, ShopWithQty<Candy> {
    @Inject
    Event<CandySoldEvent> event;

    @Inject
    @Named("InventoryGoodForCandyStore")
    protected InventoryWithQtyService<Candy> inventoryService;

    @Inject
    @Named("ReceiptGoodForCandyStore")
    protected ReceiptTransmissionService<Candy> receiptTransmissionService;

    @Override
    public Candy sell(Customer customer) throws OutOfStockException {
        Candy soldCandy = this.inventoryService.takeFromInventory();
        Receipt<Candy> receipt = new Receipt<>(soldCandy, new Random().nextInt(0, 10), 0.055);
        receiptTransmissionService.sendReceipt(customer, receipt);
        return soldCandy;
    }

    @Override
    public Candy sell(Customer customer, String id) {
        return this.inventoryService.takeFromInventory(id);
    }

    @Override
    public Candy sell(Customer customer, String id, int qty) {
        Candy soldCandy = this.inventoryService.decreaseElementQtyInInventory(id, qty);
        event.fire(new CandySoldEvent(soldCandy, qty, this.inventoryService));
        return soldCandy;
    }

    @Override
    public void stock(Candy candy) {
        this.inventoryService.addToInventory(candy);
    }

    @Override
    public Collection<Candy> getAllItems() {
        return this.inventoryService.listAllItems();
    }
}
