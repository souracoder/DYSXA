package com.subhaaspa.dysxa;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.vision.digitalink.DigitalInkRecognition;
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModel;
import com.google.mlkit.vision.digitalink.DigitalInkRecognitionModelIdentifier;
import com.google.mlkit.vision.digitalink.DigitalInkRecognizer;
import com.google.mlkit.vision.digitalink.DigitalInkRecognizerOptions;
import com.google.mlkit.vision.digitalink.Ink;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class FollowAlphabet extends AppCompatActivity {

    DigitalInkRecognitionModelIdentifier modelIdentifier = null;
    DigitalInkRecognitionModel model;
    String assignedLetter = "A";
    String letterSet = "abcdefghijklmnopqrstuvwxyz";
    InkView inkView;
    GifImageView gifView;
    EditText textInput;
    
    int index=0;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_alphabet);
        String[] lang_model ={"en-US","zxx-Zsym-x-autodraw"};

        gifView = (GifImageView) findViewById(R.id.gifView);
        inkView = (InkView) findViewById(R.id.inkView);
        textInput = (EditText) findViewById(R.id.inputText);

        showAnimatedLetter();
        inkView.drawBoundRectangle();

        inkView.initInkRecognition(lang_model[0],getApplicationContext());
        
        //inkView.getParent().

    }







    public void showAnimatedLetter(){

        String letter = String.valueOf(letterSet.charAt(index));

        Global.showLetterAnimation(letter,gifView,this);



    }


    public void checkLetterDrawn(View v){
       inkView.recognizeObject(getApplicationContext());

        String letter = inkView.recognizedObject;
        if (letter.compareTo(assignedLetter)==0){
            Toast.makeText(getApplicationContext(),"Good",Toast.LENGTH_LONG).show();
        }
      // Toast.makeText(getApplicationContext(),inkView.getRecognizedObject(),Toast.LENGTH_LONG).show();


//        Global.recognizeObject(inkView.getInkInstance(), getApplicationContext());
//        Toast.makeText(getApplicationContext(),Global.recognizedObject,Toast.LENGTH_LONG).show();
    }

    public void clearCanvas(View v){
        inkView.clear();
    }

    public void nextLetter(View v ){
        Global.showLetterAnimation(assignedLetter,gifView,this);
    }
    
    public void assignLetter(View v){
        assignedLetter = textInput.getText().toString().substring(0,1);

    }
}