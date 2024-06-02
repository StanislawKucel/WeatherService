/**
 *
 *  @author Kucel Stanislaw S24910
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Service {
	private final String country;

    public Service(String country) {
        this.country = country;
    }

    public String getWeather(String city) {
        String apiKey = "27b4501068438b425ba04c2a79d999f7";
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&appid=" + apiKey;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            
            JSONObject json = new JSONObject(content.toString());
            JSONArray weatherArray = json.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);
            String weatherDescription = weatherObject.getString("description");
            return weatherDescription;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Double getRateFor(String currencyCode) {
        String apiUrl = "https://api.exchangerate.host/latest?base=" + currencyCode;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            JSONObject json = new JSONObject(content.toString());
            String countryCode = getCountryCodeForCountryName(country);
            return json.getJSONObject("rates").getDouble(countryCode);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Double getNBPRate() {
        // Jeœli waluta docelowa to PLN, zwróæ 1.0.

        
        String countryCode = getCountryCodeForCountryName(country);
        String apiUrl = "http://api.nbp.pl/api/exchangerates/rates/A/" + countryCode + "/?format=json";
       
        if (countryCode.equals("PLN")) {
            return 1.0;
        }
        
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            JSONObject json = new JSONObject(content.toString());
            JSONArray ratesArray = json.getJSONArray("rates");
            JSONObject rateObject = ratesArray.getJSONObject(0);
            return rateObject.getDouble("mid");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private String getCountryCodeForCountryName(String countryName) {
        Map<String, String> countryCurrencyCodes = new HashMap<>();
        countryCurrencyCodes.put("Poland", "PLN");
        countryCurrencyCodes.put("United States", "USD");
        countryCurrencyCodes.put("Germany", "EUR");
        countryCurrencyCodes.put("France", "EUR");
        countryCurrencyCodes.put("Italy", "EUR");
        countryCurrencyCodes.put("Spain", "EUR");
        countryCurrencyCodes.put("Netherlands", "EUR");
        countryCurrencyCodes.put("Belgium", "EUR");
        countryCurrencyCodes.put("Austria", "EUR");
        countryCurrencyCodes.put("Greece", "EUR");
        countryCurrencyCodes.put("Portugal", "EUR");
        countryCurrencyCodes.put("United Kingdom", "GBP");
        countryCurrencyCodes.put("Switzerland", "CHF");
        countryCurrencyCodes.put("Sweden", "SEK");
        countryCurrencyCodes.put("Norway", "NOK");
        countryCurrencyCodes.put("Denmark", "DKK");
        countryCurrencyCodes.put("Finland", "EUR");
        countryCurrencyCodes.put("Ireland", "EUR");
        countryCurrencyCodes.put("Russia", "RUB");
        countryCurrencyCodes.put("Turkey", "TRY");
        countryCurrencyCodes.put("Australia", "AUD");
        countryCurrencyCodes.put("Canada", "CAD");
        countryCurrencyCodes.put("Japan", "JPY");
        countryCurrencyCodes.put("China", "CNY");
        countryCurrencyCodes.put("India", "INR");
        countryCurrencyCodes.put("Brazil", "BRL");
        countryCurrencyCodes.put("Mexico", "MXN");
        countryCurrencyCodes.put("South Africa", "ZAR");
        countryCurrencyCodes.put("South Korea", "KRW");
        countryCurrencyCodes.put("Singapore", "SGD");
        countryCurrencyCodes.put("New Zealand", "NZD");
        countryCurrencyCodes.put("Thailand", "THB");
        countryCurrencyCodes.put("Malaysia", "MYR");
        countryCurrencyCodes.put("Philippines", "PHP");
        countryCurrencyCodes.put("Indonesia", "IDR");
        countryCurrencyCodes.put("Israel", "ILS");
        return countryCurrencyCodes.getOrDefault(countryName, "USD");
    }
}  
