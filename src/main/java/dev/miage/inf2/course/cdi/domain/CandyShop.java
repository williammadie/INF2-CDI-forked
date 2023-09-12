package dev.miage.inf2.course.cdi.domain;

import dev.miage.inf2.course.cdi.exception.OutOfStockException;
import dev.miage.inf2.course.cdi.model.Candy;
import dev.miage.inf2.course.cdi.model.Customer;
import dev.miage.inf2.course.cdi.model.Receipt;
import dev.miage.inf2.course.cdi.service.InventoryService;
import dev.miage.inf2.course.cdi.service.ReceiptTransmissionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Collection;
import java.util.Random;

@ApplicationScoped
public class CandyShop implements Shop<Candy> {

    @Inject
    @Named("InventoryGoodForCandyStore")
    protected InventoryService<Candy> inventoryService;

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
    public void stock(Candy candy) {
        this.inventoryService.addToInventory(candy);
    }

    @Override
    public Collection<Candy> getAllItems() {
        return this.inventoryService.listAllItems();
    }
}
