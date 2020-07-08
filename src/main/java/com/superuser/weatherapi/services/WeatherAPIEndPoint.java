package com.superuser.weatherapi.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class WeatherAPIEndPoint {
    String error;
    boolean isValid;
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private double pressure;
    private double humidity;
    private String location;
    private double longitude;
    private double latitude;
    private String weather;
    private String apiKey = "05421eb90beaae51378b287142ff8554";

    @RequestMapping("/weather/name/{place}")
    public String responseName(@PathVariable("place") final String place) {
        String request = getResponse(
                "http://api.openweathermap.org/data/2.5/weather?q=" + place + "&units=metric&appid=" + apiKey);
        if (!(request.startsWith("Error"))) {
            updateData(request);
            if (isValid) {
                return "Weather at: " + location + "<br>Coordinates are<br>Longitude: " + longitude + "<br>Latitude: "
                        + latitude + "<br>Weather: " + weather + "<br>Temperature: " + temp + "<br>Feels like: "
                        + feels_like + "<br>Maximum Temperature: " + temp_max + "<br>Minimum Temperature: " + temp_min
                        + "<br>Pressure: " + pressure + "<br>Humidity: " + humidity;
            } else {
                return error;
            }
        } else {
            return "There was an error processing the request<br>Invalid Place";
        }
    }
    @RequestMapping("/weather/name/json/{place}")
    public String responseNameJSON(@PathVariable("place") final String place) {
        String request = getResponse(
                "http://api.openweathermap.org/data/2.5/weather?q=" + place + "&units=metric&appid=" + apiKey);
        if (!(request.startsWith("Error"))) {
            updateData(request);
            if (isValid) {
                JSONObject result = new JSONObject();
                result.put("location", location);
                result.put("lon", longitude);
                result.put("lat", latitude);
                result.put("weather", weather);
                result.put("temp", temp);
                result.put("feels_like", feels_like);
                result.put("temp_max", temp_max);
                result.put("temp_min", temp_min);
                result.put("pressure", pressure);
                result.put("humidity", humidity);
                result.put("Status","OK");
                return result.toJSONString();
            } else {
                return error;
            }
        } else {
            JSONObject result = new JSONObject();
            result.put("Status","ERROR");
            result.put("Message","Invalid Place");
            return result.toJSONString();
        }
    }

    @RequestMapping("/weather/pin/{pinCode}")
    public String responsePin(@PathVariable("pinCode") final String pincode) {
        String request = getResponse(
                "http://api.openweathermap.org/data/2.5/weather?zip=" + pincode + ",in&units=metric&appid=" + apiKey);
        if (!(request.startsWith("Error"))) {
            updateData(request);
            if (isValid) {
                return "Weather at: " + location + "<br>Coordinates are<br>Longitude: " + longitude + "<br>Latitude: "
                        + latitude + "<br>Weather: " + weather + "<br>Temperature: " + temp + "<br>Feels like: "
                        + feels_like + "<br>Maximum Temperature: " + temp_max + "<br>Minimum Temperature: " + temp_min
                        + "<br>Pressure: " + pressure + "<br>Humidity: " + humidity;
            } else {
                return error;
            }
        } else {
            return "There was an error processing the request<br>Invalid Pin Code  (Indian Pincodes only)";
        }
    }
    @RequestMapping("/weather/pin/json/{pinCode}")
    public String responsePinJSON(@PathVariable("pinCode") final String pincode) {
        String request = getResponse(
                "http://api.openweathermap.org/data/2.5/weather?zip=" + pincode + ",in&units=metric&appid=" + apiKey);
        if (!(request.startsWith("Error"))) {
            updateData(request);
            if (isValid) {
                JSONObject result = new JSONObject();
                result.put("location", location);
                result.put("lon", longitude);
                result.put("lat", latitude);
                result.put("weather", weather);
                result.put("temp", temp);
                result.put("feels_like", feels_like);
                result.put("temp_max", temp_max);
                result.put("temp_min", temp_min);
                result.put("pressure", pressure);
                result.put("humidity", humidity);
                result.put("Status","OK");
                return result.toJSONString();
            } else {
                return error;
            }
        } else {
            JSONObject result = new JSONObject();
            result.put("Status","ERROR");
            result.put("Message","Invalid Pin Code (Indian Pincodes only)");
            return result.toJSONString();
        }
    }

    @RequestMapping("/weather/coord/json/lon={lon}&lat={lat}")
    public String responseCoordJSON(@PathVariable("lon") final String lon, @PathVariable("lat") final String lat) {
        String request = getResponse("http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon
                + "&units=metric&appid=" + apiKey);
        if (!(request.startsWith("Error"))) {
            updateData(request);
            if (isValid) {
                JSONObject result = new JSONObject();
                result.put("location", location);
                result.put("lon", longitude);
                result.put("lat", latitude);
                result.put("weather", weather);
                result.put("temp", temp);
                result.put("feels_like", feels_like);
                result.put("temp_max", temp_max);
                result.put("temp_min", temp_min);
                result.put("pressure", pressure);
                result.put("humidity", humidity);
                result.put("Status","OK");
                return result.toJSONString();
            } else {
                return error;
            }
        } else {
            JSONObject result = new JSONObject();
            result.put("Status","ERROR");
            result.put("Message","Invalid Coordinates");
            return result.toJSONString();
        }
    }

    @RequestMapping("/weather/coord/lon={lon}&lat={lat}")
    public String responseCoord(@PathVariable("lon") final String lon, @PathVariable("lat") final String lat) {
        String request = getResponse("http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon
                + "&units=metric&appid=" + apiKey);
        if (!(request.startsWith("Error"))) {
            updateData(request);
            if (isValid) {
                return "Weather at: " + location + "<br>Coordinates are<br>Longitude: " + longitude + "<br>Latitude: "
                        + latitude + "<br>Weather: " + weather + "<br>Temperature: " + temp + "<br>Feels like: "
                        + feels_like + "<br>Maximum Temperature: " + temp_max + "<br>Minimum Temperature: " + temp_min
                        + "<br>Pressure: " + pressure + "<br>Humidity: " + humidity;
            } else {
                return error;
            }
        } else {
            return "There was an error processing the request<br>Invalid Coordinates";
        }
    }

    private void updateData(String request) {
        isValid = false;
        error = "";
        JSONObject ob = getDataObject(request);
        System.out.println(isValid);
        if (isValid) {
            JSONObject main = (JSONObject) ob.get("main");
            JSONObject coord = (JSONObject) ob.get("coord");
            JSONArray weatherArray = (JSONArray) ob.get("weather");
            JSONObject weatherObj = (JSONObject) weatherArray.get(0);
            temp = Double.parseDouble(main.get("temp").toString());
            feels_like = Double.parseDouble(main.get("feels_like").toString());
            temp_min = Double.parseDouble(main.get("temp_min").toString());
            temp_max = Double.parseDouble(main.get("temp_max").toString());
            pressure = Double.parseDouble(main.get("pressure").toString());
            humidity = Double.parseDouble(main.get("humidity").toString());
            location = ob.get("name").toString();
            longitude = Double.parseDouble(coord.get("lon").toString());
            latitude = Double.parseDouble(coord.get("lat").toString());
            weather = weatherObj.get("main").toString();
        }
    }

    private JSONObject getDataObject(String response) {
        JSONObject ob = null;
        try {
            ob = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if (ob.get("cod").toString().equals("200")) {
                isValid = true;
            } else {
                System.out.println("COD is:" + ob.get("cod").toString());
                error = ob.get("message").toString();
            }
        } catch (Exception e) {
            error = "Error in Fetching the Request";
        }
        return ob;
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