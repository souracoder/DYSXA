package com.subhaaspa.dysxa;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Colours extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    Button[] colorButtons;
    TextToSpeech tts;

    Map<String, String> map = new HashMap<String,String>();

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
        setContentView(R.layout.activity_colours);

        imageView = (ImageView) findViewById(R.id.imageView5);
        textView = (TextView) findViewById(R.id.textView7);

//        colorButtons[0] = findViewById(R.id.color49);
//        colorButtons[1] = findViewById(R.id.color50);
//        colorButtons[2] = findViewById(R.id.color51);
//        colorButtons[3] = findViewById(R.id.color52);
//        colorButtons[4] = findViewById(R.id.color53);
//        colorButtons[5] = findViewById(R.id.color55);
//        colorButtons[6] = findViewById(R.id.color56);
//        colorButtons[7] = findViewById(R.id.color57);
//        colorButtons[8] = findViewById(R.id.color58);
//        colorButtons[9] = findViewById(R.id.color59);
//        colorButtons[10] = findViewById(R.id.color60);
//        colorButtons[11] = findViewById(R.id.color61);
//        colorButtons[12] = findViewById(R.id.color62);
//        colorButtons[13] = findViewById(R.id.color63);
//        colorButtons[14] = findViewById(R.id.color64);



        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        map.put("orange","Orange is found in veggies like carrot and orange fruit");
        map.put("sky blue","Your art teacher may tell you to paint the the sky with this colour");
        map.put("red","red is quite common, when sun sets it is red , apple is red , so are gulmohar, tomato , rose");
        map.put("purple","purple is a color of royalty , bouganvilla flower is purple ");
        map.put("violet", "Brinjal is violet, well dont know brinjal? , it's egg plant in America. , India has many violet flowers");
        map.put("blue", "blue is every where , most cooling color , look above the sky is blue.");
        map.put("indigo","indigo is darker of blue, , , ,indigo looks great on white background.");
        map.put("green", "Green is life, the leaves that you see is green, the forest is green, the spinach , kale , lady's fingure all are green");
        map.put("leaf green","The new born leaves , the sapling all are this shade of green" );
        map.put("grass green", "Ever seen cricketers hitting the balls on the ground? that field is grass green");
        map.put("yellow", "look your house hold and see how much yellow is there. Indian dishes can't be made without yellow.");
        map.put("ochre", "well ochre is not in nature but in art, paint your grounds ochre");
        map.put("brown","tree trunk is brown , so some wood paints , ask your parent you may have brown in your kitchen" );
        map.put("cyan","when you mix green with blue you get cyan, Copper Sulphate is cyan, ask your mom about it.");




    }

    public void putColour(View v){
        Button button = (Button) v;
       int color = ((ColorDrawable) button.getBackground()).getColor();
        imageView.setBackgroundColor(color);
        textView.setText(String.valueOf(v.getTag()).toUpperCase());
        textView.setVisibility(View.VISIBLE);
        String text = String.valueOf(v.getTag());
//        if(tts.isSpeaking()){
//            tts.stop();
//        }

        tts.setPitch(0);
        tts.setSpeechRate(0.75f);
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null   );
       tts.speak(map.get(text), TextToSpeech.QUEUE_ADD,null);




      //  ColorSpace sRGB = ColorSpace.get(ColorSpace.Named.SRGB);

    }


}