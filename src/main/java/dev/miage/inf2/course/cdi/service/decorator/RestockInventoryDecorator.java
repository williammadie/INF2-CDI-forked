package dev.miage.inf2.course.cdi.service.decorator;

import dev.miage.inf2.course.cdi.domain.shop.BookShop;
import dev.miage.inf2.course.cdi.exception.OutOfStockException;
import dev.miage.inf2.course.cdi.model.Book;
import dev.miage.inf2.course.cdi.model.Customer;
import dev.miage.inf2.course.cdi.service.RestockInventoryService;
import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;

@Priority(1)
@Decorator
public class RestockInventoryDecorator extends BookShop {

    @Inject
    @Any
    @Delegate
    BookShop delegate;

    @Inject
    RestockInventoryService restockInventoryService;

    public Book sell(Customer customer, String isbn) throws OutOfStockException {
        var book = delegate.sell(customer, isbn);
        restockIfNeeded(book);

        return book;
    }

    public Book sell(Customer customer) throws OutOfStockException {
        var book = delegate.sell(customer);
        restockIfNeeded(book);

        return book;
    }

    private void restockIfNeeded(Book book) {
        if (!delegate.getAllItems().contains(book)) {
            restockInventoryService.restockBook(book);
        }
    }
}
