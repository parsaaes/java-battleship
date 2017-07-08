package ir.ac.aut.ceit.ap.finalproject.model;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class OutputFileWriter {
    public static void writeJsonIntoFile(String text,String name,String with) {
        File file = new File("chathistory/" + name + " with " + with + ".json");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
