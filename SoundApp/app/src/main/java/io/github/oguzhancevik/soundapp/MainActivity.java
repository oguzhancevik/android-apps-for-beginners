package io.github.oguzhancevik.soundapp;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  // Helpful Constants
  private final int NR_OF_SIMULTANEOUS_SOUNDS = 7;
  private final float LEFT_VOLUME = 1.0f;
  private final float RIGHT_VOLUME = 1.0f;
  private final int NO_LOOP = 0;
  private final int PRIORITY = 0;
  private final float NORMAL_PLAY_RATE = 1.0f;

  // TODO: Add member variables here
  private SoundPool soundPool;
  private int cSoundId;
  private int dSoundId;
  private int eSoundId;
  private int fSoundId;
  private int gSoundId;
  private int aSoundId;
  private int bSoundId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // TODO: Create a new SoundPool
    soundPool = new SoundPool(NR_OF_SIMULTANEOUS_SOUNDS, AudioManager.STREAM_MUSIC, 0);

    // TODO: Load and get the IDs to identify the sounds
    cSoundId = soundPool.load(getApplicationContext(), R.raw.note1_c, 1);
    dSoundId = soundPool.load(getApplicationContext(), R.raw.note2_d, 1);
    eSoundId = soundPool.load(getApplicationContext(), R.raw.note3_e, 1);
    fSoundId = soundPool.load(getApplicationContext(), R.raw.note4_f, 1);
    gSoundId = soundPool.load(getApplicationContext(), R.raw.note5_g, 1);
    aSoundId = soundPool.load(getApplicationContext(), R.raw.note6_a, 1);
    bSoundId = soundPool.load(getApplicationContext(), R.raw.note7_b, 1);
  }

  // TODO: Add the play methods triggered by the buttons

  public void playC(View view) {
    Log.d("playC", "Button clicked!");
    soundPool.play(cSoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
    Toast.makeText(getApplicationContext(), "Do", Toast.LENGTH_SHORT).show();
  }

  public void playD(View view) {
    Log.d("playD", "Button clicked!");
    soundPool.play(dSoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
    Toast.makeText(getApplicationContext(), "Re", Toast.LENGTH_SHORT).show();
  }

  public void playE(View view) {
    Log.d("playE", "Button clicked!");
    soundPool.play(eSoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
    Toast.makeText(getApplicationContext(), "Mi", Toast.LENGTH_SHORT).show();
  }

  public void playF(View view) {
    Log.d("playF", "Button clicked!");
    soundPool.play(fSoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
    Toast.makeText(getApplicationContext(), "Fa", Toast.LENGTH_SHORT).show();
  }

  public void playG(View view) {
    Log.d("playG", "Button clicked!");
    soundPool.play(gSoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
    Toast.makeText(getApplicationContext(), "Sol", Toast.LENGTH_SHORT).show();
  }

  public void playA(View view) {
    Log.d("playA", "Button clicked!");
    soundPool.play(aSoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
    Toast.makeText(getApplicationContext(), "La", Toast.LENGTH_SHORT).show();
  }

  public void playB(View view) {
    Log.d("playB", "Button clicked!");
    soundPool.play(bSoundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
    Toast.makeText(getApplicationContext(), "Si", Toast.LENGTH_SHORT).show();
  }
}
