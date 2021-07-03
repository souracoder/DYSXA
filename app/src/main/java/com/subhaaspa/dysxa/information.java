package com.subhaaspa.dysxa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class information extends AppCompatActivity {

    TextToSpeech tts;

    ImageButton btnVoice;

    TextView t1,t2,t3, hyperLink;

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
        setContentView(R.layout.activity_information);

        btnVoice = (ImageButton) findViewById(R.id.btn_speak2);
        t1 = (TextView) findViewById(R.id.textView6);
        t2 = (TextView) findViewById(R.id.textView7);
        t3 = (TextView) findViewById(R.id.textView8);

        hyperLink = (TextView) findViewById(R.id.textView9);


        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.US);
                }
            }
        });



    }

    public void speak(View v){
        if(!tts.isSpeaking()){
            tts.speak(t1.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            tts.speak(t2.getText().toString(),TextToSpeech.QUEUE_ADD,null);
            tts.speak(t3.getText().toString().replaceFirst("DYSXA","diskha"),TextToSpeech.QUEUE_ADD,null);



        }
        else{
            tts.stop();
        }





    }

    public void resolveHyperLink(View v){
    hyperLink.setMovementMethod(LinkMovementMethod.getInstance());
    }
}