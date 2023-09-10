package dev.miage.inf2.course.cdi.domain;

import dev.miage.inf2.course.cdi.exception.OutOfStockException;
import dev.miage.inf2.course.cdi.model.Book;
import dev.miage.inf2.course.cdi.model.Customer;
import dev.miage.inf2.course.cdi.model.Receipt;
import dev.miage.inf2.course.cdi.service.InventoryService;
import dev.miage.inf2.course.cdi.service.ReceiptTransmissionService;
import dev.miage.inf2.course.cdi.service.impl.InMemoryInventoryService;
import dev.miage.inf2.course.cdi.service.impl.StringReceiptTransmissionService;

import java.util.Random;

public class BookShop implements Shop<Book> {

    private InventoryService<Book> inventoryService;
    private ReceiptTransmissionService<Book> receiptTransmissionService;

    public BookShop() {
        this.inventoryService = new InMemoryInventoryService();
        this.receiptTransmissionService = new StringReceiptTransmissionService();
    }

    public long countBooks() {
        return this.inventoryService.countItemsInInventory();
    }


    @Override
    public Book sell(Customer customer) throws OutOfStockException {
        var soldBook = this.inventoryService.takeFromInventory();
        Receipt<Book> receipt = new Receipt<Book>(soldBook, new Random().nextInt(0, 30), 0.055);
        receiptTransmissionService.sendReceipt(customer, receipt);
        return soldBook;

    }

    @Override
    public void stock(Book book) {
        this.inventoryService.addToInventory(book);
    }
}