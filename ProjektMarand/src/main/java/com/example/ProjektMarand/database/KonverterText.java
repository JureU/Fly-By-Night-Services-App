package com.example.ProjektMarand.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class KonverterText {

    public static void main(String[] args) {
        String inputFile = "Data.txt";
        String outputFile = "output.txt";

        convertData(inputFile, outputFile);

        System.out.println("Conversion complete.");
    }

    public static void convertData(String inputFile, String outputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
    
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\^");
                StringBuilder convertedLine = new StringBuilder();
    
                for (String part : parts) {
                    convertedLine.append(part.replaceAll("\"", "")).append(", ");
                }
    
                // Remove the trailing comma and space
                convertedLine.setLength(convertedLine.length() - 2);
    
                bw.write(convertedLine.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}