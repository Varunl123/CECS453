package com.example.lab8g_r1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> personNames = new ArrayList<>(Arrays.asList("animal 1", "animal 2", "animal 3",
            "animal 4", "animal 5", "animal 6",
            "animal 7","animal 8", "animal 9",
            "animal 10", "animal 11", "animal 12"));
    ArrayList<Integer> personImages = new ArrayList<>(Arrays.asList(R.drawable.animal13,
            R.drawable.animal14, R.drawable.animal15,
            R.drawable.animal16, R.drawable.animal17,
            R.drawable.animal18, R.drawable.animal13,
            R.drawable.animal14, R.drawable.animal15,
            R.drawable.animal16, R.drawable.animal17,
            R.drawable.animal18));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, personNames,personImages);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

}
