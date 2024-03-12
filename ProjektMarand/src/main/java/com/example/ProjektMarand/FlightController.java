package com.example.ProjektMarand;

import java.io.IOException;
import java.util.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ProjektMarand.database.Data;
import com.example.ProjektMarand.database.DataInfo;
import com.example.ProjektMarand.database.DatabaseException;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FlightController {

// Prikaz vseh možnih poletov z filtriranjem:
@GetMapping("/flights")
public String showFilteredData(Model model,
                               @RequestParam(required = false, defaultValue = "any") String origin,
                               @RequestParam(required = false, defaultValue = "any") String destination) throws DatabaseException {
    try {
        // Odpremo našo bazo
        ClassPathResource resource = new ClassPathResource("flights_database.db");
        Data data = new Data(resource.getFile().getAbsolutePath());

        // Dobi vse unique odhode in destinacije
        Set<String> uniqueOrigins = new HashSet<>();
        Set<String> uniqueDestinations = new HashSet<>();
        for (int i = 1; i <= data.getRecordCount(); i++) {
            DataInfo record = data.getRecord(i);
            if (record != null) {
                uniqueOrigins.add(record.getValues()[1]);
                uniqueDestinations.add(record.getValues()[2]);
            }
        }

        // Pošlji na HTML
        model.addAttribute("uniqueOrigins", uniqueOrigins);
        model.addAttribute("uniqueDestinations", uniqueDestinations);
        model.addAttribute("origin", origin);
        model.addAttribute("destination", destination);

        // Glede na izbran filter razberi le tiste...
        List<DataInfo> records = new ArrayList<>();
        for (int i = 1; i <= data.getRecordCount(); i++) {
            DataInfo record = data.getRecord(i);
            if (record != null && (origin.equals("any") || record.getValues()[1].equals(origin))
                && (destination.equals("any") || record.getValues()[2].equals(destination))) {
                records.add(record);
            }
        }

        // Pošlji le kar ustreza filtru
        model.addAttribute("records", records);

        // Zapri povezavo!
        data.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "flights";
    }

// Prikaz vseh podatkov za izbran polet:
@GetMapping("/reserve/{flightNumber}")
public String reserveFlight(Model model, @PathVariable String flightNumber) throws DatabaseException {
    try {
        ClassPathResource resource = new ClassPathResource("flights_database.db");
        Data data = new Data(resource.getFile().getAbsolutePath());

        DataInfo flight = null;
        for (int i = 1; i <= data.getRecordCount(); i++) {
            DataInfo record = data.getRecord(i);
            if (record.getValues()[0].trim().equals(flightNumber.trim())) {
                flight = record;
                break;
            }
        }
        
        model.addAttribute("flight", flight);

        data.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "seat_reservation"; 
    }

// Rezervacija sedežev:
@PostMapping("/reserve")
public String reserveSeats(Model model, @RequestParam(required = true) String flightNumber, @RequestParam(required = true) String seats) throws DatabaseException {
    try {
        ClassPathResource resource = new ClassPathResource("flights_database.db");
        Data data = new Data(resource.getFile().getAbsolutePath());

        System.out.println("Seats requested: " + seats);

        DataInfo flight = null;
        for (int i = 1; i <= data.getRecordCount(); i++) {
            DataInfo record = data.getRecord(i);
            if (record.getValues()[0].trim().equals(flightNumber.trim())) {
                flight = record;
                break;
            }
        }

        if (flight == null) {
            model.addAttribute("message", "Flight not found.");
            return "error";
        }

        String availableSeatsString = flight.getValues()[8].trim();

        int availableSeats = Integer.parseInt(availableSeatsString);
        int requestedSeats = Integer.parseInt(seats);
        
        if (requestedSeats <= availableSeats) {
            // Update koliko je prostih sedežev:
            String novo = Integer.toString(availableSeats-requestedSeats);
            data.updateAvailableSeats(flightNumber, novo);

            model.addAttribute("message", "Seats booked successfully!");
        } else {

            model.addAttribute("message", "Requested seats are unavailable.");
        }
              
        data.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "reserve"; 
    }

}