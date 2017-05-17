package com.collpoll.customers;

import org.codehaus.jackson.map.*;
import org.codehaus.jackson.*;
import org.codehaus.jackson.JsonFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by shashikant on १७-०५-२०१७.
 */
public class Invitation {

    public static final int RADIUS_OF_EARTH = 6371;
    public static final double OFFICE_LATITUDE = 12.935076;
    public static final double OFFICE_LONGITUDE = 77.614277;
    public static final int RANGE = 100;

/*  main method  */
    public static void main(String[] args) {

        String filename = System.getProperty("user.dir") + File.separator + "input" + File.separator + "customers.json";
        List<ShortListedCustomer> shortListedCustomers = null;
        try {
            shortListedCustomers = shortlistCustomers(filename);
        } catch (IOException e) {
            System.out.println("\nBAD file !! Please check the input file for error \n\n" + e.getMessage());
        }
        if (shortListedCustomers != null){
            shortListedCustomers.sort(new UserIdComparator());
            displayShortListedCustomers(shortListedCustomers);
        }


    }

/*  method: to display the sorted list of matched customers */
    private static void displayShortListedCustomers(List<ShortListedCustomer> shortListedCustomers) {
        System.out.println( " -----------------------------\n" +
                            "| Shortlisted Customers:\t |\n" +
                            "-----------------------------\n" +
                            " Name  \t\t\t|\t" + "User ID\n" +
                            "-----------------------------") ;
        for (ShortListedCustomer s : shortListedCustomers){
            System.out.println(s.toString());
        }
        System.out.println("-----------------------------");
    }

/*  method: to shortlist the customers based on distance criteria  */
    public static List<ShortListedCustomer> shortlistCustomers(String filename) throws IOException {
        List<ShortListedCustomer> shortListedCustomers = new LinkedList<ShortListedCustomer>();
        JsonFactory jsonFactory = new MappingJsonFactory();
        JsonParser jsonParser = null;
        System.out.println(System.getProperty("user.dir"));
        try {
            jsonParser = jsonFactory.createJsonParser(new File(filename));
        } catch (IOException e) {
            System.out.println("\n Incorrect FILE PATH !!\n"
                    + e.getMessage());
        }
        if (jsonParser != null){
            JsonToken current = jsonParser.nextToken();
            if(current != JsonToken.START_OBJECT){
                System.out.println("Error: root should be object: quiting.");
                return shortListedCustomers;
            }
            while (jsonParser.nextToken() != JsonToken.END_OBJECT){
                String fieldname = jsonParser.getCurrentName();
                current = jsonParser.nextToken();
                if (fieldname.equals("customers")){
                    if (current == JsonToken.START_ARRAY){
                        while (jsonParser.nextToken() != JsonToken.END_ARRAY){
                            JsonNode jsonNode = jsonParser.readValueAsTree();
                            String name = String.valueOf(jsonNode.get("name"));
                            String userId = String.valueOf(jsonNode.get("userId"));
                            double latitude = 999999, longitude = 999999;
                            if (name == "null" || userId == "null"){
                                System.out.println("Invalid entry: " + jsonNode.toString());
                            }else {
                                try {
                                    latitude = Double.valueOf(String.valueOf(jsonNode.get("latitude")));
                                    longitude = Double.valueOf(String.valueOf(jsonNode.get("longitude")));
                                } catch (NumberFormatException e){
                                    System.out.println("Invalid or missing location for user:" + userId);
                                }
                            }
                            if (latitude != 999999 && longitude != 999999){
                                double distance = calculateDistance(latitude, longitude);
                                if(distance < RANGE){
                                    shortListedCustomers.add(new ShortListedCustomer(name, userId));
                                }
                            }
                        }
                    }else {
                        System.out.println("Error: records should be an array: skipping.");
                        jsonParser.skipChildren();
                    }
                }else {
                    System.out.println("Unprocessed property: " + fieldname);
                    jsonParser.skipChildren();
                }
            }
        }
        return shortListedCustomers;
    }

/*  method: to calculate the distance between two coordinates  */
    public static double calculateDistance(double latitude, double longitude) {

        return Math.acos(Math.sin(Math.toRadians(latitude))
                            * Math.sin(Math.toRadians(OFFICE_LATITUDE))
                        + Math.cos(Math.toRadians(latitude))
                            * Math.cos(Math.toRadians(OFFICE_LATITUDE))
                            * Math.cos(Math.toRadians(OFFICE_LONGITUDE - longitude)))
                * RADIUS_OF_EARTH;
    }
}
