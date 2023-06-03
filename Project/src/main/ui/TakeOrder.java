package ui;

import model.Drink;
import model.Order;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.OrderReader;
import persistence.OrderWriter;

import java.util.ArrayList;
import java.util.Scanner;

//Keep taking orders and add them to waiting list
//Could also check both the done list and waiting list
//Could modify orders, add/ remove drinks in an order, or add customer's address
public class TakeOrder {

    private ArrayList<Order> orderWaiting; //List of orders that has not been done
    private ArrayList<Order> orderDone; //List of orders that has been done
    private String date = "";
    private Scanner input = new Scanner(System.in);

    public TakeOrder() {
        orderWaiting = new ArrayList<>();
        orderDone = new ArrayList<>();
        boolean keepOrdering = true;
        System.out.println("Want to load other date's orders? y-yes n-no");
        String response = input.next();
        if (response.equalsIgnoreCase("y")) {
            readJson();
        }

        while (keepOrdering) {
            keepOrdering = control();
        }
        System.out.println("There's total " + Order.getTotalOrder() + " order(s) been made today.");
        System.out.println("Do you want to save? y-yes n-no");
        response = input.next();
        if (response.equalsIgnoreCase("y")) {
            writeJson();
        }
        System.out.println("~Ending Order System~");
    }

    public TakeOrder(String date) {
        this.date = date;
        orderWaiting = new ArrayList<>();
        orderDone = new ArrayList<>();
    }

    public boolean control() {
        displayMenu();
        String response = input.next().toLowerCase();
        switch (response) {
            case "a":
                addOrder(createOrder()); //add order
                return true;
            case "r":
                removeOrder(findOrder()); //remove order
                return true;
            case "f":
                doneOrder(findOrder()); //done order
                return true;
            case "c":
                checkList(); //check list
                return true;
            case "m":
                modify(findOrder()); //modify order
                return true;
            case "q":
                return false; //quit
        }
        System.out.println("INVALID COMMAND");
        return true;
    }

    // read TakeOrder from jsonFile
    public void readJson() {
        System.out.println("Date? yyymmdd");
        String date = input.next();
        try {
            OrderReader reader = new OrderReader(date);
            TakeOrder orders = reader.read();
            orderWaiting = orders.getOrderWaiting();
            orderDone = orders.getOrderDone();

        } catch (Exception e) {
            System.out.println("Cannot find date... Try again");
            readJson();
        }
        System.out.println("DONE READING! :)");
    }

    // Write TakeOrder to json file
    public void writeJson() {
        System.out.println("Date? yyymmdd");
        String date = input.next();
        try {
            OrderWriter writer = new OrderWriter(date);
            TakeOrder copy = new TakeOrder(date);
            copy.setOrderWaiting(orderWaiting);
            copy.setOrderDone(orderDone);
            writer.write(copy);
            writer.close();
        } catch (Exception e) {
            System.out.println("Cannot write to file...");
        }

    }

    public void displayMenu() {

        System.out.println("\nOrder system:");
        System.out.println(" A - add order\n R - remove order\n F - order done\n C - check list");
        System.out.println(" M - modify order\n Q - quit");

    }

    public void drinksMenu() {
        System.out.println("\nDrinks:\n 1 - Americano\n 2 - Apple milk\n 3 - Cappuccino");
        System.out.println(" 4 - Caramel Latte\n 5 - Cold Brew\n 6 - Chocolate Milk\n 7 - Iced Tea");
        System.out.println(" 8 - Iced Coffee\n 9 - Latte\n 10 - Macchiato \n 11 - Milk tea");
        System.out.println(" 12 - Mocha\n 13 - Shakes\n 14 - Done");
    }

    public Order createOrder() {

        Order order;
        System.out.print("Customer Name: ");
        String answer = input.next();
        order = new Order(answer);
        int orderResponse;
        drinksMenu();
        orderResponse = input.nextInt();
        do {
            System.out.println("Size? \n0 - Small\n1 - Medium\n2 - Large");
            int size = input.nextInt();
            Drink orderDrink = new Drink(orderResponse, size);
            order.addDrinks(orderDrink);
            drinksMenu();
            orderResponse = input.nextInt();
        } while (orderResponse != 14);
        return order;
    }

    public void addOrder(Order order) {
        orderWaiting.add(order);
        if (order.isDone()) {
            doneOrder(order);
        } else {
            System.out.println("Order " + order.getCustomerName() + " was added in waiting list.");
        }
    }

    public Order findOrder() {

        System.out.println("Order ID?");
        String customerID = input.next();
        for (Order order : orderWaiting) {
            if (customerID.equalsIgnoreCase(order.getOrderID())) {
                System.out.println("Order: " + order.getCustomerName());
                return order;
            }
        }
        System.out.println("Order does not exist in the waiting list. Try again");
        return findOrder();
    }

    public Drink findDrink(Order order) {
        drinksMenu();
        int drinkCode = input.nextInt();
        System.out.println("Size? \n0 - Small\n1 - Medium\n2 - Large");
        int size = input.nextInt();
        Drink target = null;
        for (Drink drink : order.getDrinkList()) {
            if (drinkCode == drink.getDrinkCode() && size == drink.getSize()) {
                target = drink;
            }
        }
        if (target == null) {
            System.out.println("ERROR: NO SUCH DRINK IN THE ORDER");
            target = findDrink(order);
        }
        return target;
    }

    public void modify(Order order) {
        int command;
        do {
            System.out.println("MODIFY MENU:\nModify drinks? - 1  Modify Address? - 2  Quit - 3 ");
            command = input.nextInt();
            if (command == 1) {
                modifyDrinksInOrder(order);
            } else if (command == 2) {
                System.out.println("Total Price: " + order.getTotalPrice());
                System.out.println("Valid for delivery: " + order.isValidForDelivery());
                if (order.isValidForDelivery()) {
                    System.out.print("Address: ");
                    String address = input.next();
                    order.modifyAddress(true, address);
                    System.out.println("Address updated.\n");
                } else {
                    System.out.println("Haven't meet minimum order, cannot be delivered.");
                }
            }
        } while (command <= 3);
    }

    public void modifyDrinksInOrder(Order order) {
        System.out.println("1-Add or 2-remove drinks from order?");
        int ans = input.nextInt();
        int add;
        if (ans == 1) {
            drinksMenu();
            add = input.nextInt();
            do {
                System.out.println("Size? \n0 - Small\n1 - Medium\n2 - Large");
                int size = input.nextInt();
                Drink orderDrink = new Drink(add, size);
                order.addDrinks(orderDrink);
                drinksMenu();
                add = input.nextInt();
            } while (add != 14);
        } else if (ans == 2) {
            for (Drink d : order.getDrinkList()) {
                System.out.println(d.toString());
            }
            order.removeDrinks(findDrink(order));
        }
    }

    public void removeOrder(Order order) {
        orderWaiting.remove(order);
        System.out.println("Order " + order.getCustomerName() + " was removed in waiting list.");
    }

    public void doneOrder(Order order) {

        orderWaiting.remove(order);
        orderDone.add(order);
        order.setDone();
        System.out.println("Order " + order.getCustomerName() + " has been done.");
    }

    public void checkList() {
        System.out.println("Check waiting list - 1\nCheck list of done orders - 2");
        int answer = input.nextInt();
        if (answer == 1) {
            System.out.println("Waiting List:");
            System.out.println("There is " + orderWaiting.size() + " orders waiting to be done.");
            print(getOrderWaiting());
        } else if (answer == 2) {
            System.out.println("Order Done");
            System.out.println(orderDone.size() + " order(s) has been done.");
            print(getOrderDone());
        } else {
            System.out.println("Invalid Command");
            checkList();
        }

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

    public void print(ArrayList<Order> list) {
        for (Order order : list) {
            System.out.println(order.toString());
        }
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