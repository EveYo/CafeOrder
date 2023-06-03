package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Drink;
import model.Order;
import org.json.*;
import ui.TakeOrderGUI;


// Represents a reader that reads TakeOrder from JSON data stored in file
// Citation : https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Creator : Paul Carter
public class OrderReaderGUI {
    private String source; //source

    // EFFECTS: constructs reader to read from source file
    public OrderReaderGUI(String source) {
        this.source = "./data/" + source + ".json";
    }

    // EFFECTS: reads TakeOrder from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TakeOrderGUI read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOrderList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: create two lists (order waiting/ order done)
    private TakeOrderGUI parseOrderList(JSONObject jsonObject) {
        String date = jsonObject.getString("Date");
        TakeOrderGUI ordersMadeInDate = new TakeOrderGUI(date);
        addOrderList(ordersMadeInDate, jsonObject);
        return ordersMadeInDate;
    }

    // EFFECTS: parses orders from JSON object and adds them to the order list
    private void addOrderList(TakeOrderGUI orderList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Orders");
        for (Object json : jsonArray) {
            JSONObject order = (JSONObject) json;
            addOrder(orderList, order);
        }
    }


    // MODIFIES: orderWaiting
    // EFFECTS: parses drink from JSON object and adds them to drink list in the order
    private void addOrder(TakeOrderGUI orderList, JSONObject jsonObject) {
        String orderID = jsonObject.getString("OrderID");
        String name = jsonObject.getString("Name");
        String address = jsonObject.getString("Address");
        boolean done = jsonObject.getBoolean("Done");
        Order order = new Order(orderID, name, address, done); // create a order
        orderList.addOrder(order); // add the order into TakeOrder's waitingList
        JSONArray drinkList = jsonObject.getJSONArray("Drinks");

        for (Object json : drinkList) {
            JSONObject drink = (JSONObject) json;
            int drinkCode = drink.getInt("DrinkCode");
            int size = drink.getInt("Size");
            order.addDrinks(new Drink(drinkCode, size)); // add drink to the order
        }
        order.setLoading();
    }

}
