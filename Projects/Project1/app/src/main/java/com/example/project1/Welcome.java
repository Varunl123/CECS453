package com.example.project1;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    //Naming widget references such as TextView
    private TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Mapping the actual activity_welcome.xml widget objects to the above mentioned references.
        welcome=findViewById(R.id.welcomeView);


        // Receiving the intent object from MainActivity and Retrieving the String associated with that intent.
        String str= getIntent().getStringExtra("message");
        String currentText=welcome.getText().toString();

        //Displaying welcome user text
        welcome.setText(currentText+" "+str+" !");


    }
}
