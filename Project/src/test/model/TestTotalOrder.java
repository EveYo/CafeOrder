package model;

import org.junit.jupiter.api.Test;
import ui.TakeOrderGUI;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTotalOrder {
    @Test
    void testTotalOrder() {
        TakeOrderGUI orders = new TakeOrderGUI("2022");
        orders.addOrder(new Order("Eve"));
        orders.addOrder(new Order("Karina"));
        orders.addOrder(new Order("Hannah"));
        orders.addOrder(new Order("Liu"));
        orders.addOrder(new Order("Irene"));
        assertEquals(5,Order.getTotalOrder());
    }
}
