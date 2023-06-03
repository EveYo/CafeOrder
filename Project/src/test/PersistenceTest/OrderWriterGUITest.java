package PersistenceTest;

import model.Drink;
import model.Order;
import org.junit.jupiter.api.Test;
import persistence.OrderReaderGUI;
import persistence.OrderWriterGUI;
import ui.TakeOrderGUI;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class OrderWriterGUITest {

    @Test
    void testWriterInvalidFile() {
        try {
            TakeOrderGUI orders = new TakeOrderGUI("00000000");
            OrderWriterGUI writer = new OrderWriterGUI("my\0illegal:fileName");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyOrder() {
        try {
            TakeOrderGUI orders = new TakeOrderGUI("20220101");
            OrderWriterGUI writer = new OrderWriterGUI("20220101");
            writer.write(orders);
            writer.close();

            OrderReaderGUI reader = new OrderReaderGUI("20220101");
            TakeOrderGUI testOrders = reader.read();
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
            TakeOrderGUI orders = new TakeOrderGUI("20220210");
            Order newOrder = new Order("0", "Eve", "UBC", false);
            for (int i = 0; i < 14; i++) {
                newOrder.addDrinks(new Drink(i, 0));
            }
            orders.addOrder(newOrder);
            OrderWriterGUI writer = new OrderWriterGUI("20220210");
            writer.write(orders);
            writer.close();

            OrderReaderGUI testReader = new OrderReaderGUI("20220210");
            TakeOrderGUI testOrders = testReader.read();
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

