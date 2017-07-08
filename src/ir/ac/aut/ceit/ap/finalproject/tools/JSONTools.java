package ir.ac.aut.ceit.ap.finalproject.tools;


import ir.ac.aut.ceit.ap.finalproject.model.InputFileReader;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JSONTools {

    public static void parseJSON(String json,JFrame chatHistoryTextFrame,JTextArea chatHistoryArea){
        JSONObject jsonObject = new JSONObject(json);
        String info = jsonObject.getString("Information");
        chatHistoryTextFrame.setTitle(info);
        JSONArray jsonArray = jsonObject.getJSONArray("chat messages");
        System.out.println("json array size >>>" + jsonArray.length());
        if(jsonArray.length() > 0){
            for(int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject messageJSONObject = jsonArray.getJSONObject(i);
                chatHistoryArea.append(messageJSONObject.getString("message"));
            }
        }
    }


}
