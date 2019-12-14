package io.github.oguzhancevik.quizzler;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import io.github.oguzhancevik.quizzler.model.TrueFalse;

public class MainActivity extends AppCompatActivity {;

  Button trueButton;
  Button falseButton;
  TextView questionTextView;
  TextView scoreTextView;
  ProgressBar progressBar;
  int index;
  int question;
  int score;

  // Question Bank
  private TrueFalse[] questionBank =
      new TrueFalse[] {
        new TrueFalse(R.string.question_1, true),
        new TrueFalse(R.string.question_2, true),
        new TrueFalse(R.string.question_3, true),
        new TrueFalse(R.string.question_4, true),
        new TrueFalse(R.string.question_5, true),
        new TrueFalse(R.string.question_6, false),
        new TrueFalse(R.string.question_7, true),
        new TrueFalse(R.string.question_8, false),
        new TrueFalse(R.string.question_9, true),
        new TrueFalse(R.string.question_10, true),
        new TrueFalse(R.string.question_11, false),
        new TrueFalse(R.string.question_12, false),
        new TrueFalse(R.string.question_13, true)
      };

  final int progressBarIncrement = (int) Math.ceil(100.0 / questionBank.length);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    trueButton = findViewById(R.id.true_button);
    falseButton = findViewById(R.id.false_button);
    questionTextView = findViewById(R.id.question_text_view);
    scoreTextView = findViewById(R.id.score);
    progressBar = findViewById(R.id.progress_bar);

    question = questionBank[index].getQuestionId();
    questionTextView.setText(question);

    View.OnClickListener trueButtonOnClickListener =
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            checkAnswer(true);
            updateQuestion();
          }
        };
    trueButton.setOnClickListener(trueButtonOnClickListener);

    falseButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            checkAnswer(false);
            updateQuestion();
          }
        });
  }

  private void updateQuestion() {
    index = ((index + 1) % 13);

    if (index == 0) {
      AlertDialog.Builder alert = new AlertDialog.Builder(this);
      alert.setTitle("Game Over");
      alert.setCancelable(false);
      alert.setMessage("You scored " + score + " points");
      alert.setPositiveButton(
          "Close Application",
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              finish();
            }
          });
      alert.show();
    }

    question = questionBank[index].getQuestionId();
    questionTextView.setText(question);
    progressBar.incrementProgressBy(progressBarIncrement);
    scoreTextView.setText("Score " + score + "/" + questionBank.length);
  }

  private void checkAnswer(boolean userSelection) {
    boolean correctAnswer = questionBank[index].isAnswer();
    if (userSelection == correctAnswer) {
      Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
      score++;
    } else {
      Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
    }
  }
}
