package Weathers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/***
 * 
 * learning API, HTTP, and JSON
 * 
 * api.openweathermap.org/data/2.5/weather?zip={zip code},{country
 * code}&appid={your api key} key: fcc09851b4b997771590a96ef181f737
 *
 */

public class WeatherMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Scanner in = new Scanner(System.in);
		// System.out.println("Please Enter a Zip Code: ");
		// String zip = in.nextLine();
		// System.out.println("Please Enter a Country Code: ");
		// String country = in.nextLine();
		// in.close();
		String urlStr = String.format(
				"https://api.openweathermap.org/data/2.5/weather?zip=%1$s,%2$s&appid=fcc09851b4b997771590a96ef181f737",
				"90703", "US");

		HttpClient client = HttpClient.newHttpClient();
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(urlStr)).timeout(Duration.ofMinutes(1))
					.header("Content-Type", "application/json").build();
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(response.body());

			printWeatherReport(obj);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void printWeatherReport(JSONObject json) {
		String city = (String) json.get("name");
		System.out.println("Current Weather Report for the city of " + city);

		System.out.println(city + " coordinates:");
		JSONObject coords = (JSONObject) json.get("coord");

		System.out.println("    lon: " + coords.get("lon"));
		System.out.println("    lat: " + coords.get("lat"));
		System.out.println();

		JSONObject main = (JSONObject) json.get("main");
		DecimalFormat formatter = new DecimalFormat("#0.00");
		double temp = (Double) main.get("temp") * (9.0 / 5.0) - 459.67;
		System.out.println(formatter.format(temp) + " degrees F");

		System.out.println("humidity of " + main.get("humidity"));

		JSONArray weatherArr = (JSONArray) json.get("weather");
		Iterator<JSONObject> iterator = weatherArr.iterator();
		if (iterator.hasNext())
			System.out.println(iterator.next().get("description"));
	}
}
// {"visibility":16093,"timezone":-25200,"main":{"temp":291.87,"temp_min":289.82,"humidity":60,
//     "pressure":1012,"feels_like":291.08,"temp_max":294.15},"clouds":{"all":1},
//      "sys":{"country":"US","sunrise":1590410700,"sunset":1590461635,"id":4154,"type":1},
//      "dt":1590471553,"coord":{"lon":-118.07,"lat":33.87},"weather":[{"icon":"01n","description":"clear sky",
//      "main":"Clear","id":800}],"name":"Cerritos","cod":200,"id":0,"base":"stations","wind":{"deg":140,"speed":1.5}}
