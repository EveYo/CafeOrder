package PersistenceTest;

import model.Drink;
import model.Order;
import org.junit.jupiter.api.Test;
import persistence.OrderReader;
import persistence.OrderWriter;
import ui.TakeOrder;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class OrderReaderTest {

    OrderReader reader;
    Order newOrder;

    void runBefore() {
        TakeOrder ordersTest = new TakeOrder("20220101");
        try {
            OrderWriter writerOne = new OrderWriter("20220101");
            writerOne.write(ordersTest);
            writerOne.close();
            TakeOrder orders = new TakeOrder("20220210");
            newOrder = new Order("0", "Eve", "UBC", false);
            for (int i = 0; i<14; i++) {
                newOrder.addDrinks(new Drink(i,0));
            }
            orders.addOrder(newOrder);
            OrderWriter writerTwo = new OrderWriter("20220210");
            writerTwo.write(orders);
            writerTwo.close();
        } catch (Exception e) {
            System.out.println("At orderReaderTest, cannot write to Json file");
        }
    }

    @Test
    void testReaderDoesNotExistFile() {
        runBefore();
        reader = new OrderReader("20220000");
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
        reader = new OrderReader("20220101");
        try {
            TakeOrder testOrders = reader.read();
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
        OrderReader testReader = new OrderReader("20220210");
        try {
            TakeOrder testOrders = testReader.read();
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
