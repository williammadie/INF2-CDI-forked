package dev.miage.inf2.course.cdi.domain;

import jakarta.enterprise.event.Observes;

public class CandyStockLogger {
    void onCandySold(@Observes CandySoldEvent candySoldEvent) {
        System.out.println(candySoldEvent.getSoldQty() + "g of candy " + candySoldEvent.getCandy().id() + " have been sold!");
    }
}
