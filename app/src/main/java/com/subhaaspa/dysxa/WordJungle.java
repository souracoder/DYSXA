package com.subhaaspa.dysxa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class WordJungle extends AppCompatActivity {

    TextView descriptionView;
    GifImageView gifImageView;
    ArrayList<Button> wordsInJungle;
    String correct_answer;
    TextToSpeech tts;
    int index = 0;

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
        setContentView(R.layout.activity_word_jungle);

        descriptionView = (TextView) findViewById(R.id.textView5);

        gifImageView = (GifImageView)findViewById(R.id.imageView3);

        wordsInJungle = new  ArrayList<Button>();

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.US);
                }
            }
        });

        
        wordsInJungle.add((Button) findViewById(R.id.button31));
        wordsInJungle.add((Button)findViewById(R.id.button36));
        wordsInJungle.add((Button) findViewById(R.id.button37));
        wordsInJungle.add((Button) findViewById(R.id.button38));
        wordsInJungle.add((Button) findViewById(R.id.button39));
        wordsInJungle.add((Button) findViewById(R.id.button40));
        wordsInJungle.add((Button) findViewById(R.id.button41));
        wordsInJungle.add((Button) findViewById(R.id.button42));
        wordsInJungle.add((Button) findViewById(R.id.button43));
        wordsInJungle.add((Button) findViewById(R.id.button44));
        wordsInJungle.add((Button) findViewById(R.id.button45));
        wordsInJungle.add((Button) findViewById(R.id.button46));
        wordsInJungle.add((Button) findViewById(R.id.button47));

        placeButton();
        setCorrectAns();


    }

    public void placeButton(){

        ArrayList<Integer> text_size = new ArrayList<Integer>();
        Random rand = new Random();

        for(int i = 14;i<30;i+=4){
            text_size.add(i);
        }

        ArrayList<Integer> rand_ints = new ArrayList<Integer>();
        String correct_ans = new String();
        Global.enableDisableButton(wordsInJungle,true);

        for (int i = 0; i<wordsInJungle.size(); i++){
            wordsInJungle.get(i).setText(genGibber());
           wordsInJungle.get(i).setTextSize(rand.nextInt(8)+16);
            rand_ints.add(i);


        }
        Collections.shuffle(rand_ints);


       // wordsInJungle.get(0).
    }

    public String genGibber(){

        Random rand = new Random();
        int length = rand.nextInt(13);
        String text = "abcdefgjijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        int position = 0;

        for (int i = 0;i<length+3; i++){
            position = rand.nextInt(text.length()-1);
            sb.append(text.charAt(position));



        }

        return Global.toTitleCase(sb.toString());


    }

    public void setCorrectAns(){
        Random rand = new Random();
        int maxlength = Global.rand_gen.size()+1;
        if (index % maxlength == 0) {
            Global.rand_gen.clear();

            for (int i = 0 ; i<Global.Options.length; i++){
                Global.rand_gen.add(i);
            }
            Collections.shuffle(Global.rand_gen);
        }




        correct_answer = Global.Options[Global.rand_gen.get(index % maxlength)];
        index++;

        String imageName = "@drawable/"+correct_answer;
        int immg = getResources().getIdentifier(imageName,null, getPackageName());
        gifImageView.setImageResource(immg);

        wordsInJungle.get(rand.nextInt(wordsInJungle.size())).setText(Global.toTitleCase(correct_answer));


    }

    public void frameNextQuestion(){
        placeButton();
        setCorrectAns();
    }
    public void checkAnswer(View v){
        Button replied = (Button) v;

        tts.setSpeechRate(.75f);
        String replytext = (String) replied.getText();
        if(replytext.compareToIgnoreCase(correct_answer) == 0){
            tts.speak(Global.responseOnReply(true), TextToSpeech.QUEUE_FLUSH, null);


            frameNextQuestion();
        }
        else {
            tts.speak(Global.responseOnReply(false), TextToSpeech.QUEUE_FLUSH, null);

        }
    }

}