package com.subhaaspa.dysxa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LearnScreen extends AppCompatActivity {

TextView learningHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_screen);

        learningHeader = (TextView) findViewById(R.id.textView2);

    }

    public void openColorKB(View v){
        Intent intent = new Intent(this,ColorKB.class);
        startActivity(intent);
    }
    public void openAlphabetAnimation(View v){
        Intent intent = new Intent(this, AlphabetAnimation.class);
        startActivity(intent);
    }
    public void openColorChanger(View v){
        Intent intent = new Intent(this,Colours.class);
        startActivity(intent);
    }
    public void openColors(View v){
        Intent intent = new Intent(this, Colours.class);
        startActivity(intent);
    }
}