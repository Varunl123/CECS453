package com.example.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
   // DataBaseHandler mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mydb=new DataBaseHandler(this);
        Intent intent,intent1;
        intent= new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
}