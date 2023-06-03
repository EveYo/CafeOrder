package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class DrinkTest {

    Drink milkTea;
    Drink latte;
    Drink mocha;
    Drink shakes;

    @BeforeEach
    void runBefore(){
        milkTea = new Drink(11, 0);
        latte = new Drink(9, 1);
        mocha = new Drink(12, 1);
        shakes = new Drink(13, 2);

    }

    @Test
    void testConstructor(){

        assertEquals("MILK_TEA", milkTea.getDrink());
        assertEquals(0, milkTea.getSize());

        assertEquals("LATTE", latte.getDrink());
        assertEquals(1, latte.getSize());

        assertEquals("MOCHA", mocha.getDrink());
        assertEquals(1, mocha.getSize());

        assertEquals("SHAKES", shakes.getDrink());
        assertEquals(2, shakes.getSize());

    }

    @Test
    void testGetPrice(){

        assertEquals( 3.25, milkTea.getPrice());
        assertEquals(5.5 , latte.getPrice());
        assertEquals( 6.0, mocha.getPrice());
        assertEquals( 5.5, shakes.getPrice());

    }

    @Test
    void testToString() {
        assertEquals("MILK_TEA size: 0 price: 3.25", milkTea.toString());
    }

}

