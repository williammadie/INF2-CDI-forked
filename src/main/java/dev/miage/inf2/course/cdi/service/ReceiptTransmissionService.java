package dev.miage.inf2.course.cdi.service;

import dev.miage.inf2.course.cdi.model.Customer;
import dev.miage.inf2.course.cdi.model.Receipt;

public interface ReceiptTransmissionService<T> {

    void sendReceipt(Customer customer, Receipt<T> receipt);
}
