package dev.miage.inf2.course.cdi.service;

import dev.miage.inf2.course.cdi.domain.shop.BookShop;
import dev.miage.inf2.course.cdi.model.Book;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RestockInventoryService {

    @Inject
    BookShop shop;

    public void restockBook(Book book) {
        //pretend we order the book somewhere
        Book newBook = new Book(book.author(),book.title()+" (restocked) ",book.isbn());
        shop.stock(newBook);
    }

}
