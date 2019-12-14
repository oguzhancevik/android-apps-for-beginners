package io.github.oguzhancevik.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.github.oguzhancevik.weatherapp.model.Weather;

public class WeatherController extends AppCompatActivity {

  // Constants:
  final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
  // App ID to use OpenWeather data
  final String APP_ID = "3c86d0b58b98f79ab5b45b0acaae0e66";
  // Time between location updates (5000 milliseconds or 5 seconds)
  final long MIN_TIME = 5000;
  // Distance between location updates (1000m or 1km)
  final float MIN_DISTANCE = 1000;

  // Request Code
  final int REQUEST_CODE = 123;

  // LogCat String
  final String LOGCAT_TAG = "WeatherApp";

  // TODO: Set LOCATION_PROVIDER here:
  String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

  // Member Variables:
  TextView cityLabel;
  ImageView weatherImage;
  TextView temperatureLabel;

  // TODO: Declare a LocationManager and a LocationListener here:
  LocationManager locationManager;
  LocationListener locationListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.weather_controller_layout);

    // Linking the elements in the layout to Java code
    cityLabel = (TextView) findViewById(R.id.locationTV);
    weatherImage = (ImageView) findViewById(R.id.weatherSymbolIV);
    temperatureLabel = (TextView) findViewById(R.id.tempTV);
    ImageButton changeCityButton = (ImageButton) findViewById(R.id.changeCityButton);

    // TODO: Add an OnClickListener to the changeCityButton here:
    changeCityButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ChangeCityController.class);
            startActivity(intent);
          }
        });
  }

  // TODO: Add onResume() here:

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(LOGCAT_TAG, "onResume() called");

    Intent intent = getIntent();
    String city = intent.getStringExtra("city");

    if (city != null) {
      getWeatherForNewCity(city);
    } else {
      Log.d(LOGCAT_TAG, "Getting weather for current location");
      getWeatherForCurrentLocation();
    }
  }

  // TODO: Add getWeatherForNewCity(String city) here:
  private void getWeatherForNewCity(String city) {
    RequestParams params = new RequestParams();
    params.put("q", city);
    params.put("appid", APP_ID);
    callWeatherService(params);
  }

  // TODO: Add getWeatherForCurrentLocation() here:
  private void getWeatherForCurrentLocation() {
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    locationListener =
        new LocationListener() {
          @Override
          public void onLocationChanged(Location location) {
            Log.d(LOGCAT_TAG, "onLocationChanged() callback received");
            Log.d(LOGCAT_TAG, "longtitude is: " + location.getLongitude());
            Log.d(LOGCAT_TAG, "latitude is: " + location.getLatitude());

            RequestParams params = new RequestParams();
            params.put("lat", location.getLatitude());
            params.put("lon", location.getLongitude());
            params.put("appid", APP_ID);
            callWeatherService(params);
          }

          @Override
          public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(LOGCAT_TAG, "onStatusChanged() callback received. Status: " + status);
            Log.d(LOGCAT_TAG, "2 means AVAILABLE, 1: TEMPORARILY_UNAVAILABLE, 0: OUT_OF_SERVICE");
          }

          @Override
          public void onProviderEnabled(String provider) {
            Log.d(LOGCAT_TAG, "onProviderEnabled() callback received. Provider: " + provider);
          }

          @Override
          public void onProviderDisabled(String provider) {
            Log.d(LOGCAT_TAG, "onProviderDisabled() callback received");
          }
        };
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    Activity#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for Activity#requestPermissions for more details.
      ActivityCompat.requestPermissions(
          this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
      return;
    }

    // Speed up update on screen by using last known location.
    Location lastLocation = locationManager.getLastKnownLocation(LOCATION_PROVIDER);

    Log.d(
        LOGCAT_TAG,
        "Location Provider used: " + locationManager.getProvider(LOCATION_PROVIDER).getName());
    Log.d(
        LOGCAT_TAG,
        "Location Provider is enabled: " + locationManager.isProviderEnabled(LOCATION_PROVIDER));
    Log.d(
        LOGCAT_TAG,
        "Last known location (if any): " + locationManager.getLastKnownLocation(LOCATION_PROVIDER));
    Log.d(LOGCAT_TAG, "Requesting location updates");

    locationManager.requestLocationUpdates(
        LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, locationListener);
  }

  @Override
  public void onRequestPermissionsResult(
      int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == REQUEST_CODE) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        Log.d(LOGCAT_TAG, "onRequestPermissionsResult(): Permission Granted!");
        getWeatherForCurrentLocation();
      } else {
        Log.d(LOGCAT_TAG, "onRequestPermissionsResult(): Permission Denied!");
      }
    }
  }

  // TODO: Add letsDoSomeNetworking(RequestParams params) here:
  private void callWeatherService(RequestParams params) {
    AsyncHttpClient client = new AsyncHttpClient();
    client.get(
        WEATHER_URL,
        params,
        new JsonHttpResponseHandler() {
          @Override
          public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            // super.onSuccess(statusCode, headers, responseString);
            Log.d(LOGCAT_TAG, "Success! JSON: " + response.toString());
            updateUI(Weather.fromJSON(response));
          }

          @Override
          public void onFailure(
              int statusCode, Header[] headers, Throwable throwable, JSONArray response) {
            // super.onFailure(statusCode, headers, throwable, errorResponse);
            Log.e(LOGCAT_TAG, "Fail " + throwable.toString());
            Log.d(LOGCAT_TAG, "Status Code " + statusCode);
            Toast.makeText(getApplicationContext(), "Request Failed", Toast.LENGTH_SHORT).show();
          }
        });
  }

  // TODO: Add updateUI() here:
  private void updateUI(Weather weather) {
    temperatureLabel.setText(weather.getTemperature());
    cityLabel.setText(weather.getCity());
    int resourceId =
        getResources().getIdentifier(weather.getIconName(), "drawable", getPackageName());
    weatherImage.setImageResource(resourceId);
  }

  // TODO: Add onPause() here:

  @Override
  protected void onPause() {
    super.onPause();
    if (locationManager != null) locationManager.removeUpdates(locationListener);
  }
}
