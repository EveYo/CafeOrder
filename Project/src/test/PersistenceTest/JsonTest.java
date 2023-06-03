package PersistenceTest;

import model.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
    static void compareTwo(Order newOrder, Order testOrder) {
        assertEquals(newOrder.getOrderID(), testOrder.getOrderID());
        assertEquals(newOrder.getCustomerName(), testOrder.getCustomerName());
        assertEquals(newOrder.getTotalPrice(), testOrder.getTotalPrice());
        assertEquals(newOrder.getCustomerAddress(), testOrder.getCustomerAddress());
        for (int i = 0; i<newOrder.getDrinkList().size(); i++) {
            int testDrinkCode = testOrder.getDrinkList().get(i).getDrinkCode();
            int newDrinkCode = newOrder.getDrinkList().get(i).getDrinkCode();
            assertTrue(testDrinkCode == newDrinkCode);
        }
    }
}
