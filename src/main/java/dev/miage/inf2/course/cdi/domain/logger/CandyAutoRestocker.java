package dev.miage.inf2.course.cdi.domain.logger;

import dev.miage.inf2.course.cdi.domain.event.CandySoldEvent;
import dev.miage.inf2.course.cdi.model.Candy;
import dev.miage.inf2.course.cdi.service.InventoryWithQtyService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class CandyAutoRestocker {
    private static final int AUTO_RESTOCK_LIMIT = 50;
    private static final int AUTO_RESTOCK_WEIGHT = 100;


    public void onCandySold(@Observes CandySoldEvent candySoldEvent) {

        Candy soldCandy = candySoldEvent.getCandy();
        InventoryWithQtyService<Candy> inventoryService = candySoldEvent.getInventoryService();
        if (soldCandy.weight() < AUTO_RESTOCK_LIMIT) {
            autoRestockCandy(soldCandy, inventoryService);
        }
    }

    private void autoRestockCandy(Candy candy, InventoryWithQtyService<Candy> inventoryService) {
        System.out.println("Candy " + candy.toString() + " has been automatically restocked");
        inventoryService.takeFromInventory(candy.id());
        int actualWeight = candy.weight();
        inventoryService.addToInventory(new Candy(candy.flavor(), actualWeight + AUTO_RESTOCK_WEIGHT, candy.id()));
    }
}
