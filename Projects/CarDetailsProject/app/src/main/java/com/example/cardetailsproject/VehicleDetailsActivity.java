package com.example.cardetailsproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class VehicleDetailsActivity extends AppCompatActivity {
    // Widget references for imageview and Textviews
    ImageView imageview;
    TextView displayPrice;
    TextView displayDescritpion;
    TextView displayModel;
    TextView displayCreated_at;
    Button mprevious;


    // Method to check if the URL of the image specified is valid or not.
    public boolean isValid(String str){
        if(URLUtil.isValidUrl(str))
            return true;
        else
            return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_details);

        // Getting the view objects of imageviews and textboxes.
        imageview=findViewById(R.id.displayCar);
        displayPrice=findViewById(R.id.displayPrice);
        displayDescritpion=findViewById(R.id.descriptionDisplay);
        displayModel=findViewById(R.id.modelDisplay);
        displayCreated_at=findViewById(R.id.created_at_display);
        Intent intent=getIntent();

        // Receiving the hashmap using getSerializableExtra method
        HashMap<String,String> map=(HashMap<String,String>)intent.getSerializableExtra("Data");

        String imageurl=map.get("image_url");
        String price=map.get("price");
        String description=map.get("vehicle_description");
        String model=map.get("model");
        String created_at=map.get("created_at");
        String make=map.get("vehicle_make");
        Log.e("TAG",price);
        Log.e("TAG",model);
        displayPrice.setText("PRICE:"+price);
        //Picasso.get().load(imageurl).into(imageview);
        displayModel.setText("Make-MODEL: "+make+" "+model);
        displayDescritpion.setText(description);
        displayCreated_at.setText("LAST UPDATED ON: "+created_at);

        // Checking if the image url is valid and if not valid setting a default image
        if(isValid(imageurl)) {
            Picasso.get().load(imageurl).into(imageview, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    imageview.setImageResource(R.drawable.car4);
                }

            });
        }
        else
            imageview.setImageResource(R.drawable.car4);


    }
}