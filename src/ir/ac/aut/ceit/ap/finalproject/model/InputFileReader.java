package ir.ac.aut.ceit.ap.finalproject.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputFileReader {
    public static String getJSON(String fileAddress){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileAddress));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String nowLine;
        String str="";
        while(scanner.hasNextLine()){
            nowLine = scanner.nextLine();
            str = str.concat(nowLine + "\n");
        }
        scanner.close();
        str = str.substring(0,str.length()-1);
        System.out.println(str);
        return str;

    }
}
