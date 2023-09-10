package dev.miage.inf2.course.cdi.service.impl;

import dev.miage.inf2.course.cdi.model.Customer;
import dev.miage.inf2.course.cdi.model.Receipt;
import dev.miage.inf2.course.cdi.service.ReceiptTransmissionService;

public class StringReceiptTransmissionService implements ReceiptTransmissionService {

    private static final StringBuilder stringBuilder = new StringBuilder();

    @Override
    public void sendReceipt(Customer customer, Receipt receipt) {
        stringBuilder.append("Merci d'avoir acheté " + receipt.item().toString() + " pour un montant de " + receipt.price()).append("\n");
    }
}
