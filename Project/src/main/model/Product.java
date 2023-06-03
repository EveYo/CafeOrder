package model;

//Find the drink's name and price by drinking code
public class Product {

    public static String findName(int drinkCode) {

        switch (drinkCode) {
            case(1): return "AMERICANO";
            case(2): return "APPLE_MILK";
            case(3): return "CAPPUCCINO";
            case(4): return "CARAMEL_LATTE";
            case(5): return "COLD_BREW";
            case(6): return "CHOCOLATE_MILK";
            case(7): return "ICED_TEA";
            case(8): return "ICED_COFFEE";
            case(9): return "LATTE";
            case(10): return "MACCHIATO";
            case(11): return "MILK_TEA";
            case(12): return  "MOCHA";
            case(13): return "SHAKES";
            default: return "drink doesn't exist";
        }
    }

    public static double findBasePriceForDrinkCode(int drinkCode) {
        switch (drinkCode) {
            case (1): return 4.0;
            case (2): return 2.5;
            case (3): return 5.25;
            case (4): return 4.75;
            case (5): return 5.75;
            case (6): return 4.25;
            case (7): return 3.0;
            case (8): return 2.5;
            case (9): return 4.5;
            case (10): return 4.75;
            case (11): return 3.25;
            case (12): return 5.0;
            case (13): return 3.5;
            default: return -1;
        }
    }

}
