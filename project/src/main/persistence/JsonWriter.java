package persistence;

import model.Event;
import model.EventLog;
import model.MedalBoard;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of a medalBoard to file
public class JsonWriter {
    private static final int TAB = 5;
    private PrintWriter writer;
    private String destination;

    // Method taken from JSONWriter class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Method taken from JSONWriter class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // Method taken from JSONWriter class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes JSON representation of medalBoard to file
    public void write(MedalBoard md) {
        JSONObject json = md.toJson();
        EventLog.getInstance().logEvent(new Event("Saved the file to " + destination + "."));
        saveToFile(json.toString(TAB));
    }

    // Method taken from JSONWriter class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // Method taken from JSONWriter class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
