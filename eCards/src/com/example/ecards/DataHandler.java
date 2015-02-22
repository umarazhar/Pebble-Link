package com.example.ecards;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;


public class DataHandler {

    public static void writeLine(OutputStream os, String text){
        PrintWriter pw = new PrintWriter(os, true);
        pw.println(text);
    }
    
    public static void write(OutputStream os, byte[] data) throws IOException{
        os.write(data);
    }
        
    public static void writeBytes(OutputStream os, byte[] data) throws IOException {
        for (int i = 0; i < data.length; i++) {
            os.write(data[i]);
        }
    }

    public static String readLine(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            if (br.ready()){
                String toReturn = br.readLine();

                return toReturn;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public static byte[] readBytes(InputStream is, int num){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            if (br.ready()){
                byte[] bytes = new byte[num];
                for (int i = 0; i < num; i++) {
                    bytes[i] = (byte) br.read();
                    
                }

                return bytes;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}


