package model;

import org.json.JSONObject;

//Stores the information of the drink that is in the order
public class Drink {

    private int drinkCode; //Each drink code represent a specific drink on the menu
    private String drink; //Store the drink's name
    private int size; //Size of the drink
    private double price; //Price of this drink
    private static final double SIZE_PRICE = 1.0; //Increase of charge due to different size

    // REQUIRES: d != null, 0<=s<=2
    // EFFECTS: shows which drink did the customer ordered; in which size; add ice or not
    public Drink(int i, int s) {
        size = s;
        drinkCode = i;
        drink = Product.findName(i);
        price = Product.findBasePriceForDrinkCode(i);
    }

    // EFFECTS: calculate the price for this drink (depends on the drink and its size).
    public double getPrice() {
        return price + (size * SIZE_PRICE);
    }

    public int getSize() {
        return size;
    }

    public String getDrink() {
        return drink;
    }

    public int getDrinkCode() {
        return drinkCode;
    }

    // EFFECTS: return drink's information in a specific format
    public String toString() {
        return drink + " size: " + size + " price: " + getPrice();
    }


    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("DrinkCode", drinkCode);
        json.put("Size", size);
        return json;
    }

    public void createEvent(String orderID) {
        EventLog.getInstance().logEvent(new Event(drinkCode + drink + " - $" + price + " is added to Order "
                + orderID));
    }
}
