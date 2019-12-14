package io.github.oguzhancevik.magic8ball;

import android.os.Bundle;
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

    final ImageView ballImage = findViewById(R.id.ballImage);
    Button askButton = findViewById(R.id.askButton);

    final int[] ballArray = {
      R.drawable.ball1, R.drawable.ball2, R.drawable.ball3, R.drawable.ball4, R.drawable.ball5
    };

    askButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Random randomNumberGenerator = new Random();
            int number = randomNumberGenerator.nextInt(ballArray.length - 1);
            ballImage.setImageResource(ballArray[number]);
          }
        });
  }
}
