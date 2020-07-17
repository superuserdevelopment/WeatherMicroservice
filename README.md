# WeatherMicroservice
A Spring Boot project for Open Weather Map API to find weather of a location based on location name or pin code or geographic co-ordinates.<br>Made with ♥ in Java
## Steps to excution:
<ol>
<li>Execute the DemoApplication.java file
<li>An Tomcat server instance will be started at <b>port: 8080</b>.
<li>Access this server through your browser using localhost:8080.
<li>To use the API enter the following in the url:
<ol type='a'>
<li>Location Name based(Plain String Output): <a href="">http://localhost:8080/weather/name/{place}</a><br><i>Replace {place} with the location name you want the weather of</i></li>
<br>
<li>Location Name based(JSON Output): <a href="">http://localhost:8080/weather/name/json/{place}</a><br><i>Replace {place} with the location name you want the weather of</i></li>
<br>
<li>Pin-code based(Plain String Output): <a href="">http://localhost:8080/weather/pin/{pincode}</a><br><i>Replace {pincode} with the Pin-code you want the weather of(Only Indian Pin-codes)</i></li>
<br>
<li>Pin-code based(JSON Output): <a href="">http://localhost:8080/weather/pin/json/{pincode}</a><br><i>Replace {pincode} with the Pin-code you want the weather of(Only Indian Pin-codes)</i></li>
<br>
<li>Geographic Co-ordinates based(Plain String Output): <a href="">http://localhost:8080/weather/coord/lon={lon}&lat={lat}</a><br><i>Replace {lon} and {lat} with the respective longitude and latitude co-ordinates of the location you want to find the weather of</i></li>
<br>
<li>Geographic Co-ordinates based(JSON Output): <a href="">http://localhost:8080/weather/coord/json/lon={lon}&lat={lat}</a><br><i>Replace {lon} and {lat} with the respective longitude and latitude co-ordinates of the location you want to find the weather of</i></li>
</ol>
<li>Get output.
</ol>

## Output Format
<ol>
 <li><b>JSON String</b><br>Sample input: <a href="">http://localhost:8080/weather/name/json/thane</a><br>
   
   ```json
   {
    "Status": "OK",
    "temp": 31.0,
    "temp_min": 31.0,
    "weather": "Haze",
    "humidity": 79.0,
    "location": "Thāne",
    "lon": 72.97,
    "pressure": 1005.0,
    "feels_like": 35.45,
    "temp_max": 31.0,
    "lat": 19.2
   }
   ```
  </li>
  <li><b>Plain String</b><br>Sample input: <a href="">http://localhost:8080/weather/name/thane</a><br>
  
  ```
 Weather at: Thāne
 Coordinates are
 Longitude: 72.97
 Latitude: 19.2
 Weather: Haze
 Temperature: 31.0
 Feels like: 35.45
 Maximum Temperature: 31.0
 Minimum Temperature: 31.0
 Pressure: 1005.0
 Humidity: 79.0
  ```
  </li>
</ol>
