package com.example.ProjektMarand.database;

import java.io.*;

public class KonverterBinary {

    public static void main(String[] args) {
        String inputFile = "Data.txt";
        String outputFile = "output.bin";

        convertData(inputFile, outputFile);

        System.out.println("Conversion complete.");
    }

    public static void convertData(String inputFile, String outputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\^");

                for (String part : parts) {
                    dos.writeUTF(part);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
