package io.github.oguzhancevik.dice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final Button rollButton = findViewById(R.id.rollButton);
    final ImageView leftDiceImage = findViewById(R.id.leftDiceImage);
    final ImageView rightDiceImage = findViewById(R.id.rightDiceImage);

    final int[] diceArray = {
      R.drawable.dice1,
      R.drawable.dice2,
      R.drawable.dice3,
      R.drawable.dice4,
      R.drawable.dice5,
      R.drawable.dice6
    };

    rollButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Log.d("Dicee", "Button has been pressed!");
            Random randomNumberGenerator = new Random();
            int number = randomNumberGenerator.nextInt(6);
            Log.d("Dicee", "The random number is: " + number);
            leftDiceImage.setImageResource(diceArray[number]);
            number = randomNumberGenerator.nextInt(6);
            Log.d("Dicee", "The random number is: " + number);
            rightDiceImage.setImageResource(diceArray[number]);
          }
        });
  }
}
