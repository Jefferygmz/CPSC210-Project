package ui;

import java.io.FileNotFoundException;
// run VisualBoard()

public class Main {
    // EFFECTS: run CreateBoard(), catch the FileNotFoundException
//    public static void main(String[] args) {
//        try {
//            new TextMedalBoard();
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to run application: file not found");
//        }
//    }

    // EFFECTS: run VisualBoard(), GUI of medal board
    public static void main(String[] args) {
        new VisualBoard();
    }
}
