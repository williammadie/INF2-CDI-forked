package dev.miage.inf2.course.cdi.domain;

import dev.miage.inf2.course.cdi.exception.OutOfStockException;
import dev.miage.inf2.course.cdi.model.Candy;
import dev.miage.inf2.course.cdi.model.Customer;
import dev.miage.inf2.course.cdi.model.Receipt;
import dev.miage.inf2.course.cdi.service.InventoryWithQtyService;
import dev.miage.inf2.course.cdi.service.ReceiptTransmissionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Collection;
import java.util.Random;

@ApplicationScoped
public class CandyShop implements Shop<Candy>, ShopWithQty<Candy> {

    private static final int AUTO_RESTOCK_WEIGHT = 100;
    private static final int AUTO_RESTOCK_LIMIT = 50;
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
        event.fire(new CandySoldEvent(soldCandy, qty));
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

    public void onCandySold(@Observes CandySoldEvent candySoldEvent) {
        Candy soldCandy = candySoldEvent.getCandy();
        if (soldCandy.weight() < AUTO_RESTOCK_LIMIT) {
            autoRestockCandy(soldCandy);
        }
    }

    private void autoRestockCandy(Candy candy) {
        System.out.println("Candy " + candy.toString() + " has been automatically restocked");
        this.inventoryService.takeFromInventory(candy.id());
        int actualWeight = candy.weight();
        this.inventoryService.addToInventory(new Candy(candy.flavor(), actualWeight + AUTO_RESTOCK_WEIGHT, candy.id()));
    }
}
