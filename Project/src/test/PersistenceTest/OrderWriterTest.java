package PersistenceTest;

import model.Drink;
import model.Order;
import org.junit.jupiter.api.Test;
import persistence.OrderReader;
import persistence.OrderWriter;
import ui.TakeOrder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class OrderWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            TakeOrder orders = new TakeOrder("00000000");
            OrderWriter writer = new OrderWriter("my\0illegal:fileName");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyOrder() {
        try {
            TakeOrder orders = new TakeOrder("20220101");
            OrderWriter writer = new OrderWriter("20220101");
            writer.write(orders);
            writer.close();

            OrderReader reader = new OrderReader("20220101");
            TakeOrder testOrders = reader.read();
            assertEquals("20220101", testOrders.getDate());
            assertEquals(0, testOrders.getOrderDone().size());
            assertEquals(0, testOrders.getOrderWaiting().size());

        } catch (IOException e) {
            fail("Exception found :(");
        }
    }

    @Test
    void testWriteGeneralOrders() {
        try {
            TakeOrder orders = new TakeOrder("20220210");
            Order newOrder = new Order("0", "Eve", "UBC", false);
            for (int i = 0; i < 14; i++) {
                newOrder.addDrinks(new Drink(i, 0));
            }
            orders.addOrder(newOrder);
            OrderWriter writer = new OrderWriter("20220210");
            writer.write(orders);
            writer.close();

            OrderReader testReader = new OrderReader("20220210");
            TakeOrder testOrders = testReader.read();
            assertEquals("20220210", testOrders.getDate());
            assertEquals(0, testOrders.getOrderDone().size());
            assertEquals(1, testOrders.getOrderWaiting().size());
            Order orderEVE = testOrders.getOrderWaiting().get(0);

            JsonTest.compareTwo(newOrder, orderEVE);
        } catch (IOException e) {
            fail("IOException :(");
        }
    }

}
