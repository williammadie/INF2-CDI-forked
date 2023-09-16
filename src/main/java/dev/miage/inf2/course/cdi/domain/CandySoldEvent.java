package dev.miage.inf2.course.cdi.domain;

import dev.miage.inf2.course.cdi.model.Book;
import dev.miage.inf2.course.cdi.model.Candy;

public class CandySoldEvent {
    final private Candy candy;
    final private int qty;

    public CandySoldEvent(Candy candy, int qty) {
        this.candy = candy;
        this.qty = qty;
    }

    public Candy getCandy() {
        return candy;
    }

    public int getSoldQty() { return qty; }
}
