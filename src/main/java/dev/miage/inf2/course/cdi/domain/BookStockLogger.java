package dev.miage.inf2.course.cdi.domain;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class BookStockLogger {
    void onBookSold(@Observes BookCreatedEvent bookCreatedEvent) {
        System.out.println("a new Book " + bookCreatedEvent.getBook().toString() + " has been created!");
    }

}
