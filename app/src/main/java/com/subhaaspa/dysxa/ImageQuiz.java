package com.subhaaspa.dysxa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Locale;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class ImageQuiz extends AppCompatActivity {

    Random rand;
    GifImageView imageView3;
    WebView webView;
    TextToSpeech tts ;
    TextView footNote ;
    //int correct_index;
    String text, correct_ans, option1,option2;
    String  imageName;
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    ImageButton btnSpeak;

    Dictionary dict;
    ArrayList<Button> optionButtons;

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
        setContentView(R.layout.activity_image_quiz);

        optionButtons = new ArrayList<Button>();
        //rand_gen = new ArrayList<Integer>();

        footNote = (TextView) findViewById(R.id.footNote) ;
        imageView3 = (GifImageView) findViewById(R.id.imageView3);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.US);
                }
            }
        });



        optionButtons.add((Button) findViewById(R.id.button27));
        optionButtons.add((Button) findViewById(R.id.button28));
        optionButtons.add((Button) findViewById(R.id.button29));
        btnSpeak = (ImageButton) findViewById(R.id.btn_speak2);
        webView = new WebView(this);
        webView = (WebView) findViewById(R.id.webView1) ;

        btnSpeak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startVoiceRecognitionActivity(v);
            }
        });


        final Activity activity = this;

        webView.setWebViewClient( new WebViewClient(){
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });




        rand = new Random();


        intitOptions();




        //textView3.setText(immg);

       // tts.speak(text,TextToSpeech.QUEUE_ADD, null  );

    }

    public  void intitOptions(){
        int correct_index;
        String text, option1,option2;

        Global.rand_gen.clear();
        for (int i = 0; i<Global.Options.length; i++){
            Global.rand_gen.add(i);
        }
        Collections.shuffle(Global.rand_gen);



        correct_index = Global.rand_gen.get(0);
        text = Global.Options[correct_index];
        option1  = Global.Options[(correct_index%23)+1].toUpperCase();
        option2 = Global.Options[(correct_index%23)+2].toUpperCase();

        String imageName = "@drawable/"+text;
        correct_ans = text.toUpperCase();
        int immg = getResources().getIdentifier(imageName,null, getPackageName());
        imageView3.setImageResource(immg);

        Global.rand_gen.clear();

        for (int i=0;i<3;i++){
            Global.rand_gen.add(i);

        }
        Collections.shuffle(Global.rand_gen);

        Global.enableDisableButton(optionButtons,true);
        btnSpeak.setEnabled(true);
        optionButtons.get(Global.rand_gen.get(0)).setText(correct_ans);
        optionButtons.get(Global.rand_gen.get(1)).setText(option1);
        optionButtons.get(Global.rand_gen.get(2)).setText(option2);

        tts.speak("What is this ? ", TextToSpeech.QUEUE_FLUSH, null   );
        //footNote.setText(correct_ans);

    }

    public void nextQuiz(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        try{
            intitOptions();
        }catch(ArrayIndexOutOfBoundsException e){
            //e.notifyAll();
            builder.setMessage(e.getMessage());
            builder.show();
            //JOptionPane.showMessageDialog(null,e.getMessage(),"xxx",JOptionPane.ERROR_MESSAGE);
        }

    }


    public void checkAnswer(View v){


        Button replied = (Button) v;
//        Drawable butDrawable = replied.getBackground();
//        butDrawable = DrawableCompat.wrap(butDrawable);
        String replytext = (String) replied.getText();
        tts.setSpeechRate(.75f);
        if(replytext == correct_ans){
            tts.speak(Global.responseOnReply(true), TextToSpeech.QUEUE_FLUSH, null);
            Global.enableDisableButton(optionButtons,false);
            btnSpeak.setEnabled(false);

        }
        else {
            tts.speak(Global.responseOnReply(false)+", this is a ," + correct_ans, TextToSpeech.QUEUE_FLUSH, null);

        }
    }



    public void openWiki(View v){
        for (int i =0; i<3 ; i++){
            optionButtons.get(i).setVisibility(v.INVISIBLE);

        }
       webView.setVisibility(v.VISIBLE);
//        if (18 < Build.VERSION.SDK_INT ){
//            //18 = JellyBean MR2, KITKAT=19
//            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        }
        webView.loadUrl("https://en.wikipedia.org/wiki/"+Global.toTitleCase(correct_ans.toLowerCase()));
       // webView.loadUrl("facebook.com");
        //setContentView(webView);

    }

    public void closeWebView(View v){
        for (int i =0; i<3 ; i++){
            optionButtons.get(i).setVisibility(v.VISIBLE);


        }

        webView.setVisibility(v.INVISIBLE);

    }

    public void startVoiceRecognitionActivity(View v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it
            // could have heard
            ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //mList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, matches));
            // matches is the result of voice input. It is a list of what the
            // user possibly said.
            // Using an if statement for the keyword you want to use allows the
            // use of any activity if keywords match
            // it is possible to set up multiple keywords to use the same
            // activity so more than one word will allow the user
            // to use the activity (makes it so the user doesn't have to
            // memorize words from a list)
            // to use an activity from the voice input information simply use
            // the following format;
            // if (matches.contains("keyword here") { startActivity(new
            // Intent("name.of.manifest.ACTIVITY")


           // String spoken_words = String.valueOf(matches.get(0));

           // String text = Global.parseSpokenWordsToWord(spoken_words,alphas);
           // textView.setText(textView.getText()+text);

            String spoken_words = matches.get(0).toString().toLowerCase();


            if(Global.voiceReplyChecker(correct_ans.toLowerCase(),spoken_words)){
                tts.speak(Global.responseOnReply(true), TextToSpeech.QUEUE_FLUSH, null);
            }
            else{
                tts.speak(Global.responseOnReply(false), TextToSpeech.QUEUE_FLUSH, null);
            }

        }
    }
}