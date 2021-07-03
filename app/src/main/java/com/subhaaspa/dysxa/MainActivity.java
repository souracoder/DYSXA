package com.subhaaspa.dysxa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

   TextToSpeech tts;

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status != TextToSpeech.ERROR){
                tts.setLanguage(Locale.US);

            }
        }
    });

    tts.speak("Hello",TextToSpeech.QUEUE_ADD,null);


    }

    public void openLearning(View v){
        Intent intent = new Intent(this,LearnScreen.class);
        startActivity(intent);
    }

    public void openQuizzing(View v){
        Intent intent = new Intent(this,QuizzingScreen.class);
        startActivity(intent);
    }
    public void openInformation(View v){
        Intent intent = new Intent(this,FollowAlphabet.class);
        startActivity(intent);
    }

}
