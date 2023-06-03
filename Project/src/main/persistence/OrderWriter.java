package persistence;

import org.json.JSONObject;
import ui.TakeOrder;

import java.io.*;

// Represents a writer that writes JSON representation of TakeOrder to file
// Citation : https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Creator : Paul Carter
public class OrderWriter {
    private static final int TAB = 4;
    private PrintWriter writer; // Writer
    private String destination; //destination

    // EFFECTS: constructs writer to write to destination file
    public OrderWriter(String destination) throws FileNotFoundException {
        this.destination = "./data/" + destination + ".json";
        open();
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        File file = new File(destination);
        writer = new PrintWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of TakeOrder to file
    public void write(TakeOrder orderRecord) {
        JSONObject json = orderRecord.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
