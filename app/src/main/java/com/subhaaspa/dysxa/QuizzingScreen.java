package com.subhaaspa.dysxa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;

import java.util.Locale;

public class QuizzingScreen extends AppCompatActivity {

    TextToSpeech tts;
    ImageButton imageQuizButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizzing_screen);

        imageQuizButton = (ImageButton) findViewById(R.id.imageQuizButton);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.US);
                }
            }
        });


    }

    public void openImageQuiz(View v){
       Intent intent = new Intent(this, ImageQuiz.class);
       startActivity(intent);
       tts.speak("What is this ?",TextToSpeech.QUEUE_FLUSH, null);

    }

    public void openDictation(View v){
        Intent intent = new Intent(this,Dictation.class);
        startActivity(intent);
    }

    public void openWordJungle(View v){
        Intent intent = new Intent(this,WordJungle.class);
        startActivity(intent);
    }
}