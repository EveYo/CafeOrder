package ui;

import model.Drink;
import model.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.OrderReaderGUI;
import persistence.OrderWriterGUI;

import java.util.ArrayList;
import java.util.Scanner;

//Keep taking orders and add them to waiting list
//Could also check both the done list and waiting list
//Could modify orders, add/ remove drinks in an order, or add customer's address
public class TakeOrderGUI {

    private ArrayList<Order> orderWaiting; //List of orders that has not been done
    private ArrayList<Order> orderDone; //List of orders that has been done
    private String date = "";
    private Scanner input = new Scanner(System.in);

    public TakeOrderGUI() {
        orderWaiting = new ArrayList<>();
        orderDone = new ArrayList<>();
    }

    public TakeOrderGUI(String date) {
        this.date = date;
        orderWaiting = new ArrayList<>();
        orderDone = new ArrayList<>();
        Order.setTrackID(findMaxCustomerId());
    }

    public int findMaxCustomerId() {
        int maxId = 0;
        for (Order order : orderWaiting) {
            if (Integer.parseInt(order.getOrderID()) > maxId) {
                maxId = Integer.parseInt(order.getOrderID());
            }
        }
        for (Order order : orderDone) {
            if (Integer.parseInt(order.getOrderID()) > maxId) {
                maxId = Integer.parseInt(order.getOrderID());
            }
        }
        maxId += 1;
        return maxId;
    }

    //GUI used
    public ArrayList<Order> readJson(String date) throws Exception {
        OrderReaderGUI reader = new OrderReaderGUI(date);
        TakeOrderGUI orders = reader.read();
        orderWaiting = orders.getOrderWaiting();
        orderDone = orders.getOrderDone();
        return orderWaiting;
    }

    //GUI used
    public void writeJson(String date) throws Exception {
        OrderWriterGUI writer = new OrderWriterGUI(date);
        TakeOrderGUI copy = new TakeOrderGUI(date);
        copy.setOrderWaiting(orderWaiting);
        copy.setOrderDone(orderDone);
        writer.write(copy);
        writer.close();

    }

    // for GUI
    public ArrayList<Order> createOrder(String name, ArrayList<Drink> drinks) {

        Order order;
        order = new Order(name);
        for (Drink orderedDrink : drinks) {
            order.addDrinks(orderedDrink);
        }
        orderWaiting.add(order);
        order.added();
        return orderWaiting;
    }

    //OrderReader is using!!
    public void addOrder(Order order) {
        orderWaiting.add(order);
        if (order.isDone()) {
            doneOrder(order);
        }
    }

    public Order findOrder(String customerID) throws Exception {

        for (Order order : orderWaiting) {
            if (customerID.equalsIgnoreCase(order.getOrderID())) {
                return order;
            }
        }
        throw new Exception("Doesn't exist");
    }

    public void removeOrder(Order order) {
        order.removed();
        orderWaiting.remove(order);
    }

    public ArrayList<Order> doneOrder(Order order) {

        orderWaiting.remove(order);
        orderDone.add(order);
        order.setDone();
        return orderWaiting;
    }

    public ArrayList<Order> getOrderWaiting() {
        return orderWaiting;
    }

    public void setOrderDone(ArrayList<Order> orderDone) {
        this.orderDone = orderDone;
    }

    public void setOrderWaiting(ArrayList<Order> orderWaiting) {
        this.orderWaiting = orderWaiting;
    }

    public ArrayList<Order> getOrderDone() {
        return orderDone;
    }

    public String getDate() {
        return date;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Date", date);
        json.put("Orders", ordersToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray ordersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Order t : orderWaiting) {
            jsonArray.put(t.toJson());
        }
        for (Order t : orderDone) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}

