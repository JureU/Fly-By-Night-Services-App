package com.example.ProjektMarand.database;

import java.io.BufferedReader;
import java.io.FileReader;

public class DatabaseFillText {
    public static void main(String[] args) {
        try {
            // Definiraj shemo (FieldInfo array)
            FieldInfo[] fields = {
                    new FieldInfo("FlightNumber", 6),
                    new FieldInfo("OriginAirport", 3),
                    new FieldInfo("DestinationAirport", 3),
                    new FieldInfo("Carrier", 20),
                    new FieldInfo("Price", 5),
                    new FieldInfo("Day", 3),
                    new FieldInfo("Time", 5),
                    new FieldInfo("Duration", 5),
                    new FieldInfo("AvailableSeats", 3)
            };

            Data database = new Data("flights_database.db", fields);

            String inputFile = "output.txt";
            try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(", ");
                    database.add(parts);
                }
            }
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}