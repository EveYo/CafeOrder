package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//Store each order's information, including total price, drinks, customer's information
public class Order {

    private static final int MIN_FOR_DELIVERY = 20;
    private static int trackID = 0;
    private String orderID; //Unique order ID for each order
    private String customerName; //Name of the customer
    private String customerAddress; //Customer's address
    private ArrayList<Drink> drinkList; //Drinks that's included in the order
    private boolean done;  //If the order is done - true; otherwise, false
    private double totalPrice; //Price of this order
    private boolean validForDelivery; //True if it's valid for delivery
    private boolean loading = false;
    
    // EFFECTS: create an order with customer's name, total price=0, empty drinkList
    public Order(String name) {
        customerName = name;
        orderID = Integer.toString(trackID);
        trackID++;
        customerAddress = "Not Valid";
        drinkList = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Order " + orderID + " is created"));
    }

    public Order(String orderID, String name, String address, boolean done) {
        this.orderID = orderID;
        customerName = name;
        customerAddress = address;
        drinkList = new ArrayList<>();
        this.done = done;
        trackID++;
        loading = true;
        EventLog.getInstance().logEvent(new Event("Order " + orderID + " is loaded from the JsonFile"));
    }

    public static void setTrackID(int max) {
        trackID = max;
    }

    public static int getTrackID() {
        return trackID;
    }

    // REQUIRES: drink != null
    // MODIFIES: this
    // EFFECTS: add Drinks into drinkList
    public void addDrinks(Drink drink) {
        drinkList.add(drink);
        if (!loading) {
            drink.createEvent(orderID);
        }
        calculateTotalPrice(true, drink.getPrice());
    }

    // REQUIRES: drink exists in the drinkList
    // MODIFIES: this
    // EFFECTS: remove the specific Drinks in the drinkList
    public void removeDrinks(Drink drink) {
        drinkList.remove(drink);
        calculateTotalPrice(false, drink.getPrice());
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: Update (add/minus the price) the total price
    private void calculateTotalPrice(boolean increase, double amount) {
        if (increase) {
            totalPrice += amount;
        } else {
            totalPrice -= amount;
        }
        if (!loading) {
            EventLog.getInstance().logEvent(new Event("The Price is updated to " + totalPrice));
        }
        validForDelivery();
    }

    // EFFECTS: Check if the totalPrice is greater than MIN_FOR_DELIVERY
    public void validForDelivery() {
        if (totalPrice >= MIN_FOR_DELIVERY) {
            validForDelivery = true;
            EventLog.getInstance().logEvent(new Event("Order " + orderID + " is now valid for delivery"));
        } else {
            validForDelivery = false;
        }
    }
    
    // MODIFIES: this
    // EFFECTS: TRUE: modify delivery address for this customer; FALSE: not valid
    public boolean modifyAddress(boolean valid, String address) {
        if (valid) {
            customerAddress = address;
            EventLog.getInstance().logEvent(new Event("Order " + orderID + " has modified its address."));
            return true;
        }
        return false;
    }

    // EFFECTS: return order information in a specific format.
    public String toString() {
        String drinkCollection = "\n";

        for (Drink d: drinkList) {
            drinkCollection += d.toString() + "\n";
        }

        return ("Customer Name: " + customerName + "\nCustomer ID: " + orderID + "\nTotal Price: " + totalPrice
                + "\nDrinks ordered: " + drinkCollection
                + "Valid for delivery: " + isValidForDelivery()
                + "\nAddress: " + customerAddress + "\n------------------------------");
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOrderID() {
        return orderID;
    }

    public static int getTotalOrder() {
        return trackID;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public ArrayList<Drink> getDrinkList() {
        return drinkList;
    }

    public boolean isValidForDelivery() {
        return validForDelivery;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setDone() {
        EventLog.getInstance().logEvent(new Event("Order " + orderID + " is done"));
        done = true;
    }

    public boolean isDone() {
        return done;
    }


    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("OrderID", orderID);
        json.put("Name", customerName);
        json.put("Address", customerAddress);
        json.put("Done", done);
        json.put("Drinks", drinksToJson());
        EventLog.getInstance().logEvent(new Event("Today's orders are saved as JsonFile"));
        return json;
    }

    // EFFECTS: returns things in this drinkList as a JSON array
    private JSONArray drinksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Drink drink : drinkList) {
            jsonArray.put(drink.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: add a log event that shows the order is added to list
    // which will be shown later
    public void added() {
        EventLog.getInstance().logEvent(new Event("Order " + orderID + " is added to the waitingList"));
    }

    // EFFECTS: add a log event that shows the order is added to list
    // which will be shown later
    public void removed() {
        EventLog.getInstance().logEvent(new Event("Order " + orderID + " is removed from the waitingList"));
    }

    public void setLoading() {
        loading = false;
    }
}

