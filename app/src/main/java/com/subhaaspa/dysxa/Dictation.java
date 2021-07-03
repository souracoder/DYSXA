package com.subhaaspa.dysxa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class Dictation extends AppCompatActivity {

    String text;
    ArrayList<Integer> rand_int, indices;
    String correct_letter;
    String option1,option2,option3;
    TextToSpeech tts;

    ArrayList<Button> optionsButton;
    ImageButton nextButton, playButton;
    GifImageView imageView;

    @Override
    protected void onPause() {

        super.onPause();
        if (tts.isSpeaking()){
            tts.stop();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictation);

        text = "abcdefghijklmnopqrstuvwxyz0123456789";

        rand_int = new ArrayList<Integer>();
        indices = new ArrayList<Integer>();
        optionsButton = new ArrayList<Button>();
        optionsButton.add((Button) findViewById(R.id.button32));
        optionsButton.add((Button) findViewById(R.id.button33));
        optionsButton.add((Button) findViewById(R.id.button34));
        optionsButton.add((Button) findViewById(R.id.button35));
        nextButton = findViewById(R.id.imageButton6);
        playButton = findViewById(R.id.imageButton5);
        imageView = (GifImageView)findViewById(R.id.imageView4);

        for (int i = 0;i<36;i++){
            rand_int.add(i);

        }

        for (int i= 0;i<4;i++){
            indices.add(i);
        }

            initAfresh(findViewById(R.id.screen2));

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.US);
                }
            }
        });

    }

    public void dictate(View v){

        Collections.shuffle(rand_int);
        Collections.shuffle(indices);
        int correct_pos = rand_int.get(0);

        for(int i = 0; i<4; i++){
            optionsButton.get(i).setVisibility(View.VISIBLE);
        }

        nextButton.setVisibility(View.VISIBLE);

        correct_letter = String.valueOf(text.charAt(correct_pos)) ;
        option1 = String.valueOf(text.charAt((correct_pos%32)+1)) ;
        option2 = String.valueOf(text.charAt((correct_pos%32)+2)) ;
        option3 = String.valueOf(text.charAt((correct_pos%32)+3)) ;

        optionsButton.get(indices.get(0)).setText((correct_letter));
        optionsButton.get(indices.get(1)).setText((option1));
        optionsButton.get(indices.get(2)).setText((option2));
        optionsButton.get(indices.get(3)).setText((option3));



        tts.speak(String.valueOf(correct_letter),TextToSpeech.QUEUE_FLUSH,null);
        playButton.setEnabled(false);
        nextButton.setEnabled(false);

    }

    public void checkAnswer(View v){
        Button but = (Button) v;
        String reply = (String) but.getText();
        tts.setSpeechRate(.75f);
        if(reply == correct_letter){
            tts.speak(Global.responseOnReply(true), TextToSpeech.QUEUE_FLUSH,null);
            String imageName = "@drawable/correct";
            int immg = getResources().getIdentifier(imageName,null, getPackageName());
            imageView.setImageResource(immg);


            }
        else{
            tts.speak( Global.responseOnReply(false)+",    this is,   " +correct_letter, TextToSpeech.QUEUE_FLUSH,null);
            String imageName = "@drawable/wrong";
            int immg = getResources().getIdentifier(imageName,null, getPackageName());
            imageView.setImageResource(immg);
        }
        nextButton.setEnabled(true);
    }

    public void initAfresh(View v){
        for(int i = 0; i<4; i++){
            optionsButton.get(i).setVisibility(View.INVISIBLE);
        }
        nextButton.setVisibility(View.INVISIBLE);
        playButton.setEnabled(true);
        imageView.setImageResource(0);

    }
}