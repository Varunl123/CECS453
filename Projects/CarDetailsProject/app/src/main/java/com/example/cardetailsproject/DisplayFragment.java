package com.example.cardetailsproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class DisplayFragment extends Fragment {

private HashMap<String,String> hashmap;
    public DisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Getting the bundle object from main activity and retrieving the hashmap from it.
        Bundle bundle=getArguments();
        if(bundle.getSerializable("Data")!=null){
            hashmap=(HashMap<String,String>)bundle.getSerializable("Data");
        }
    }
    //method to see if image url is valid or not
    public boolean isValid(String str){
        if(URLUtil.isValidUrl(str))
            return true;
        else
            return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_display, container, false);
        // Getting the view objects of imageviews and textboxes.
        final ImageView imageview;
        TextView displayPrice;
        TextView displayDescritpion;
        TextView displayModel;
        TextView displayCreated_at;
        String imageurl=hashmap.get("image_url");
        String price=hashmap.get("price");
        String make=hashmap.get("vehicle_make");
        String description=hashmap.get("vehicle_description");
        String model=hashmap.get("model");
        imageview=view.findViewById(R.id.imageDisplay);
        displayPrice=view.findViewById(R.id.priceDisplay);
        displayCreated_at=view.findViewById(R.id.created_at_display);
        displayDescritpion=view.findViewById(R.id.descriptionDisplay);
        displayModel=view.findViewById(R.id.modelDisplay);
        String created_at=hashmap.get("created_at");
        //Picasso.get().load(imageurl).into(imageview);
        displayPrice.setText("PRICE:"+price);
        displayModel.setText("Make-MODEL: "+make+" "+model);
        displayDescritpion.setText(description);
        displayCreated_at.setText(created_at);


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


        return view;
    }
}