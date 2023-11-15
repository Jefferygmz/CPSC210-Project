package persistence;

import org.json.JSONObject;

public interface Writable {

    // Method taken from Writable class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    //EFFECTS: return as a json object
    JSONObject toJson();
}
