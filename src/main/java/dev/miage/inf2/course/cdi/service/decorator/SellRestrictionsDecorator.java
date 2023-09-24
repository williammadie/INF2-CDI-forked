package dev.miage.inf2.course.cdi.service.decorator;

import dev.miage.inf2.course.cdi.domain.shop.BookShop;
import dev.miage.inf2.course.cdi.domain.shop.CandyShop;
import dev.miage.inf2.course.cdi.exception.UnauthorizedBuyerException;
import dev.miage.inf2.course.cdi.model.Candy;
import dev.miage.inf2.course.cdi.model.Customer;
import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;

import java.text.MessageFormat;

@Priority(1)
@Decorator
public class SellRestrictionsDecorator extends CandyShop {

    private static final int MIN_AGE_FOR_BUYERS = 3;
    private static final int MIN_QTY_FOR_AGE_RESTRICTION = 10;
    @Inject
    @Any
    @Delegate
    CandyShop delegate;


    @Override
    public Candy sell(Customer customer, String id, int qty) {
        System.out.println(customer.age() < MIN_AGE_FOR_BUYERS && qty > MIN_QTY_FOR_AGE_RESTRICTION);
        if (customer.age() < MIN_AGE_FOR_BUYERS && qty > MIN_QTY_FOR_AGE_RESTRICTION) {
            throw new UnauthorizedBuyerException(MessageFormat.format("Customer is too young ({0}y/o) for buying more than {1}g of candies", customer.age(), qty));
        }
        return delegate.sell(customer, id, qty);
    }

}
