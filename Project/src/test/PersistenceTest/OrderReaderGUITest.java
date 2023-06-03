package PersistenceTest;

import model.Drink;
import model.Order;
import org.junit.jupiter.api.Test;
import persistence.OrderReader;
import persistence.OrderReaderGUI;
import persistence.OrderWriterGUI;
import ui.TakeOrderGUI;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class OrderReaderGUITest {
    OrderReaderGUI reader;
    Order newOrder;

    void runBefore() {
        TakeOrderGUI ordersTest = new TakeOrderGUI("20220101");
        try {
            OrderWriterGUI writerOne = new OrderWriterGUI("20220101");
            writerOne.write(ordersTest);
            writerOne.close();
            TakeOrderGUI orders = new TakeOrderGUI("20220210");
            newOrder = new Order("0", "Eve", "UBC", false);
            for (int i = 0; i < 14; i++) {
                newOrder.addDrinks(new Drink(i, 0));
            }
            orders.addOrder(newOrder);
            OrderWriterGUI writerTwo = new OrderWriterGUI("20220210");
            writerTwo.write(orders);
            writerTwo.close();
        } catch (Exception e) {
            System.out.println("At orderReaderTest, cannot write to Json file");
        }
    }

    @Test
    void testReaderDoesNotExistFile() {
        runBefore();
        reader = new OrderReaderGUI("20220000");
        try {
            reader.read();
            fail("Exception expected");
        } catch (Exception e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyOrderList() {
        runBefore();
        reader = new OrderReaderGUI("20220101");
        try {
            TakeOrderGUI testOrders = reader.read();
            assertEquals("20220101", testOrders.getDate());
            assertEquals(0, testOrders.getOrderDone().size());
            assertEquals(0, testOrders.getOrderWaiting().size());
        } catch (Exception e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralOrderList() {
        runBefore();
        OrderReaderGUI testReader = new OrderReaderGUI("20220210");
        try {
            TakeOrderGUI testOrders = testReader.read();
            assertEquals("20220210", testOrders.getDate());
            assertEquals(0, testOrders.getOrderDone().size());
            assertEquals(1, testOrders.getOrderWaiting().size());
            Order orderEVE = testOrders.getOrderWaiting().get(0);

            JsonTest.compareTwo(newOrder, orderEVE);

        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (Exception e) {
            System.out.println("Other exception");
            fail();
        }
    }

}

