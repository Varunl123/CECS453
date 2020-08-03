package com.example.fragment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OnButtonPressListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onButtonPressed(String msg){
            fragment2 frag2=(fragment2)getSupportFragmentManager().findFragmentById(R.id.fragment2);
            assert frag2!=null;
            frag2.onFragmentInteraction(msg);
    }

}
