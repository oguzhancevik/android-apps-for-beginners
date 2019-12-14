package io.github.oguzhancevik.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeCityController extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.change_city_layout);

    final EditText editText = findViewById(R.id.queryET);
    ImageButton backButton = findViewById(R.id.backButton);

    editText.setOnEditorActionListener(
        new TextView.OnEditorActionListener() {
          @Override
          public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            String newCity = editText.getText().toString();
            Intent intent = new Intent(getApplicationContext(), WeatherController.class);
            intent.putExtra("city", newCity);
            startActivity(intent);
            //setResult(Activity.RESULT_OK, intent);
            //finish();
            return false;
          }
        });

    backButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            finish();
          }
        });
  }
}
