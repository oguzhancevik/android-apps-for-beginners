package io.github.oguzhancevik.weatherapp.model;

import org.json.JSONObject;

public class Weather {

  // TODO: Declare the member variables here
  private String temperature;
  private String city;
  private String iconName;
  private int condition;

  public Weather() {}

  // TODO: Create a Weather from a JSON:
  public static Weather fromJSON(JSONObject jsonObject) {
    Weather weather = new Weather();
    try {
      weather.city = jsonObject.getString("name");
      weather.condition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
      weather.iconName = updateWeatherIcon(weather.condition);
      double tempResult = (jsonObject.getJSONObject("main").getDouble("temp") - 273.15);
      int roundedValue = (int) Math.rint(tempResult);
      weather.temperature = Integer.toString(roundedValue);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return weather;
  }

  // TODO: Uncomment to this to get the weather image name from the condition:
  private static String updateWeatherIcon(int condition) {
    if (condition >= 0 && condition < 300) {
      return "tstorm1";
    } else if (condition >= 300 && condition < 500) {
      return "light_rain";
    } else if (condition >= 500 && condition < 600) {
      return "shower3";
    } else if (condition >= 600 && condition <= 700) {
      return "snow4";
    } else if (condition >= 701 && condition <= 771) {
      return "fog";
    } else if (condition >= 772 && condition < 800) {
      return "tstorm3";
    } else if (condition == 800) {
      return "sunny";
    } else if (condition >= 801 && condition <= 804) {
      return "cloudy2";
    } else if (condition >= 900 && condition <= 902) {
      return "tstorm3";
    } else if (condition == 903) {
      return "snow5";
    } else if (condition == 904) {
      return "sunny";
    } else if (condition >= 905 && condition <= 1000) {
      return "tstorm3";
    }

    return "dunno";
  }

  // TODO: Create getter methods for temperature, city, and icon name:

  public String getTemperature() {
    return temperature + "°";
  }

  public String getCity() {
    return city;
  }

  public String getIconName() {
    return iconName;
  }
}
