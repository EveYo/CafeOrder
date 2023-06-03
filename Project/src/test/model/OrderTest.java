package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    Order orderOne;
    Order orderTwo;
    Order orderThree;
    Order orderFour;
    Order orderFive;
    Drink milkTea;
    Drink latte;
    Drink mocha;
    Drink shakes;
    Drink americano;
    Drink caramelLatte;
    double price;

    @BeforeEach
    void runBefore(){
        orderOne = new Order("Eve");
        orderTwo = new Order("Karina");
        orderThree = new Order("Hannah");
        orderFour = new Order("Liu");
        orderFive = new Order("Irene");
        milkTea = new Drink(11, 0);
        latte = new Drink(9, 1);
        mocha = new Drink(12, 1);
        shakes = new Drink(13, 2);
        americano = new Drink(1, 2);
        caramelLatte = new Drink(4, 2);
    }

    void addDrinks(){
        orderOne.addDrinks(milkTea);
        orderOne.addDrinks(latte);
        orderOne.addDrinks(mocha);
        orderOne.addDrinks(shakes);
        orderOne.addDrinks(americano);
        orderOne.addDrinks(caramelLatte);

        orderTwo.addDrinks(latte);
        orderTwo.addDrinks(shakes);

        orderThree.addDrinks(milkTea);

        price = 0;
        for(int i = 1; i<14 ; i++) {
            Drink drink = new Drink(i,0);
            orderFive.addDrinks(drink);
            price += drink.getPrice();
        }
    }
    
    void removeDrinks(){
        orderOne.removeDrinks(milkTea);
        orderTwo.removeDrinks(shakes);
        orderThree.removeDrinks(milkTea);
    }
    
    @Test
    void testOrderConstructor(){

        assertEquals("Eve", orderOne.getCustomerName());
        assertEquals("Karina", orderTwo.getCustomerName());
        assertEquals(0, orderOne.getTotalPrice());
        assertEquals(0, orderTwo.getTotalPrice());
        assertEquals(0, orderOne.getDrinkList().size());
        assertEquals(0, orderTwo.getDrinkList().size());

    }

    @Test
    void testSetAndGetTrackId() {
        Order.setTrackID(2);
        assertEquals(2,Order.getTrackID());
    }

    @Test
    void testAddDrinks() {

        addDrinks();
        assertEquals(6, orderOne.getDrinkList().size());
        assertEquals(2, orderTwo.getDrinkList().size());
        assertEquals(1, orderThree.getDrinkList().size());
        assertEquals(0, orderFour.getDrinkList().size());
        assertEquals(13, orderFive.getDrinkList().size());

    }

    @Test
    void testRemoveDrinks(){

        addDrinks();
        removeDrinks();
        assertEquals(5, orderOne.getDrinkList().size());
        assertEquals(1, orderTwo.getDrinkList().size());
        assertEquals(0, orderThree.getDrinkList().size());
        assertEquals(0, orderFour.getDrinkList().size());

    }

    @Test
    void testCalculatePrice(){

        addDrinks();
        assertEquals(33, orderOne.getTotalPrice());
        assertEquals(11, orderTwo.getTotalPrice());
        assertEquals(3.25, orderThree.getTotalPrice());
        assertEquals(price, orderFive.getTotalPrice());

    }

    @Test
    void testValidForDelivery(){
        addDrinks();
        assertTrue(orderOne.isValidForDelivery());
        assertFalse(orderTwo.isValidForDelivery());
        assertFalse(orderThree.isValidForDelivery());
    }

    @Test
    void testModifyAddress(){

        addDrinks();
        boolean modifySuccess = orderOne.modifyAddress(orderOne.isValidForDelivery(), "UBC");
        boolean modifySuccess2 = orderTwo.modifyAddress(orderTwo.isValidForDelivery(), "downtown");
        boolean modifySuccess3 = orderThree.modifyAddress(orderThree.isValidForDelivery(), "richmond");
        assertTrue(modifySuccess);
        assertFalse(modifySuccess2);
        assertFalse(modifySuccess3);

        assertEquals("UBC", orderOne.getCustomerAddress());
        assertEquals("Not Valid", orderTwo.getCustomerAddress());
        assertEquals("Not Valid", orderThree.getCustomerAddress());

    }

    @Test
    void testSetOrderDone() {
        assertEquals(false, orderOne.isDone());
        assertEquals(false, orderTwo.isDone());
        assertEquals(false, orderThree.isDone());
        orderTwo.setDone();
        orderThree.setDone();
        assertEquals(false, orderOne.isDone());
        assertEquals(true, orderTwo.isDone());
        assertEquals(true, orderThree.isDone());

    }

    @Test
    void testToString() {

        addDrinks();
        String expectedDrinkListToString = "\n";

        for(int i = 1; i<14 ; i++) {
            Drink drink = new Drink(i,0);
            expectedDrinkListToString += drink.toString() + "\n";
        }

        String expectedString = "Customer Name: Irene" + "\nCustomer ID: "+ orderFive.getOrderID() +
                "\nTotal Price: " + orderFive.getTotalPrice()
                + "\nDrinks ordered: " + expectedDrinkListToString
                + "Valid for delivery: true"
                + "\nAddress: Not Valid" + "\n------------------------------";
        assertEquals(expectedString, orderFive.toString());
    }

}