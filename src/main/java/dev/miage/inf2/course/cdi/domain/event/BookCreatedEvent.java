package dev.miage.inf2.course.cdi.domain.event;

import dev.miage.inf2.course.cdi.model.Book;

public class BookCreatedEvent {
    final private Book book;

    public BookCreatedEvent(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
