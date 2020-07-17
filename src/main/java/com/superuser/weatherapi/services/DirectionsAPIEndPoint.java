package com.superuser.weatherapi.services;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class DirectionsAPIEndPoint {
    String distance;
    String duration;
    String startLoc;
    String endLoc;
    String error = "";

    private void updateData(final String point1, final String point2, boolean isPin) {
        String response = getResponse("https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=pincode+"+point1+"+india&destinations=pincode+"+point2+"+india&key=AIzaSyDXlLjUgUAC6GgdUZD4rfmlHKsZrISplgs");
        JSONObject ob = null;
        error = "";
        //String status ="";
        // Incase a error is returned by the getResponse() function
        if (response.startsWith("Error:")) {
            error = response;
        }
        // Everything goes well and a JSON String is returned
        else {
            try {
                ob = (JSONObject) new JSONParser().parse(response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            JSONArray rows = (JSONArray)ob.get("rows");
            JSONArray elements = (JSONArray)((JSONObject)rows.get(0)).get("elements");
            JSONObject status = (JSONObject)elements.get(0);
            // If valid Places are entered
            if (status.get("status").equals("OK")) {
                JSONArray destination_addresses = (JSONArray) ob.get("destination_addresses");
                JSONArray origin_addresses = (JSONArray)ob.get("origin_addresses");
                JSONObject distanceObj = (JSONObject)((JSONObject)elements.get(0)).get("distance");
                JSONObject durationObj = (JSONObject)((JSONObject)elements.get(0)).get("duration");
                distance = distanceObj.get("text").toString();
                duration = durationObj.get("text").toString();
                startLoc = origin_addresses.get(0).toString();
                endLoc = destination_addresses.get(0).toString();
            }
            // Making provisons for all error codes
            else if (ob.get("status").equals("NOT_FOUND") || status.get("status").equals("ZERO_RESULTS") || ob.get("status").equals("ZERO_RESULTS") || status.get("status").equals("NOT_FOUND")) {
                error = "Invalid Places Passed, Please Retry";
            }
            else if(ob.get("status").equals("INVALID_REQUEST")){
                System.out.println(ob.toJSONString());
                error = "The request provided was invalid";
            }
            else if(ob.get("status").equals("MAX_ELEMENTS_EXCEEDED")){
                System.out.println(ob.toJSONString());
                error = "The product of origins and destinations exceeds the per-query limit";
            }
            else if(ob.get("status").equals("OVER_DAILY_LIMIT")){
                error = "The API key is missing or invalid. or Billing has not been enabled on your account. or A self-imposed usage cap has been exceeded. or The provided method of payment is no longer valid (for example, a credit card has expired).";
            }
            else if(ob.get("status").equals("OVER_QUERY_LIMIT")){
                System.out.println(ob.toJSONString());
                error = "The service has received too many requests from your application within the allowed time period.";
            }
            else if(ob.get("status").equals("REQUEST_DENIED")){
                System.out.println(ob.toJSONString());
                error = "The service denied use of the Distance Matrix service by your application.";
            }
            else if(ob.get("status").equals("UNKNOWN_ERROR")){
                System.out.println(ob.toJSONString());
                error = "Distance Matrix request could not be processed due to a server error. The request may succeed if you try again.";
            }            
            else if(status.get("status").equals("MAX_ROUTE_LENGTH_EXCEEDED")){
                error = "The requested route is too long and cannot be processed.";
            }

            // In case of any other error
            else {
                error = response;
            }
        }

    }

    @RequestMapping("/distance/json/{point1}to{point2}")
    public String getDistance(@PathVariable("point1") final String point1,
            @PathVariable("point2") final String point2) {
        System.out.println("JSON Request recieved");
        updateData(point1, point2, false);
        if (error.isEmpty()) {
            JSONObject output = new JSONObject();
            output.put("Distance", distance);
            output.put("Duration", duration);
            output.put("Starting Location", startLoc);
            output.put("Ending Location", endLoc);
            return output.toJSONString();
        } else {
            JSONObject output = new JSONObject();
            output.put("error", error);
            return output.toJSONString();
        }
    }

    @RequestMapping("/distance/{point1}to{point2}")
    public String getDistanceString(@PathVariable("point1") final String point1,
            @PathVariable("point2") final String point2) {
        System.out.println("Plain Text Request recieved");
        updateData(point1, point2, false);
        if (error.isEmpty()) {
            return "Distance is: " + distance + "<br>Duration is: " + duration + "<br>Starting Location: " + startLoc
                    + "<br>Ending Location: " + endLoc;
        } else {
            return error;
        }
    }

    private String getResponse(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            return content.toString();
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
            return "Error: " + e.toString();
        }
    }
}