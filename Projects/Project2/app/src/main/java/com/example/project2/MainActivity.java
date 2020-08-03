package com.example.project2;

import android.media.Image;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements OnButtonPressListener{

    // Creating references for Image fragment, Button Fragment and gallery view fragment
    private ImageFragment firstFragment;
    private GallaryView gallaryView;
    private ButtonFragment buttonFragment;
    private ImageFragment fragmentB;

    // Creating a Fragment Manager Reference and Gridview reference.
    public static FragmentManager fragmentManager;
    private GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating objects for GallaryView fragment, Image fragment and Button fragment.
        gallaryView=new GallaryView();
        firstFragment=ImageFragment.newInstance("Image",0);
        buttonFragment=new ButtonFragment();

        //getting the fragmentManager instance.
        fragmentManager = getSupportFragmentManager();

        // If Current Framelayout container is not null then we return
        if(findViewById(R.id.container) != null) {
            if(savedInstanceState != null) {
                return;
            }
        // If Current Framelayout container is null then , we create a new fragment and add it to the container and also pass a bundle object to the fragment to display first image as default.
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container, firstFragment);
            fragmentTransaction.commit();
        }
    }

    // onButtonPressed is a callback method whenever user clicks on Previous or Next buttons.
    @Override
    public void onButtonPressed(Integer index){

        // We create a new ImageFragment instance with an index as argument and replace with the existing fragment in the container.
        ImageFragment fragmentB=ImageFragment.newInstance("Image",index);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentB).commit();

    }
    //onSlideShowCheck is a callback method whenever user checks on slide show.
    @Override
    public void onSlideShowCheck(Boolean check){

        // We get the current image fragment object from the container and call setSlideShow method of image fragment class.
        ImageFragment fragmentB;
        fragmentB=(ImageFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        assert fragmentB!=null;
        fragmentB.setSlideShow(check);

    }
    //onGalleryView is a callback method whenever user checks on gallery view
    @Override
    public void onGallaryViewCheck(Boolean check){
        // If user check is true then, we replace the current image fragment in the container with galleryview object with the help of fragment manager.
        if(check==true){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, gallaryView).addToBackStack("gallery fragment").commit();
        }
        // Else we pop back the last imagefragment we replaced with gallaryview fragment.
        else{
                getSupportFragmentManager().popBackStack();
        }
    }

}
