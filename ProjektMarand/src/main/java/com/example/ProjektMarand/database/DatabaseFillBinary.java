package com.example.ProjektMarand.database;

import java.io.DataInputStream;
import java.io.FileInputStream;

public class DatabaseFillBinary {
    public static void main(String[] args) {
        try {
            // Define schema (FieldInfo array)
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

            Data database = new Data("flights_database_bin.db", fields);

            String inputFile = "output.bin";
            try (DataInputStream dis = new DataInputStream(new FileInputStream(inputFile))) {
                while (dis.available() > 0) {
                    String[] parts = new String[fields.length];
                    for (int i = 0; i < fields.length; i++) {
                        parts[i] = dis.readUTF().trim();
                    }
                    database.add(parts);
                }
            }
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}