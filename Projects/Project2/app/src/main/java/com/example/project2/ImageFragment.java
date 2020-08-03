package com.example.project2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Timer;
import java.util.TimerTask;

public class ImageFragment extends Fragment{


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private Integer mParam2;

    // Creating an imagelist array holding the images
    private Integer[] images_list={R.drawable.animal13,R.drawable.animal14,R.drawable.animal15,R.drawable.animal16,R.drawable.animal17,R.drawable.animal18};
    // Creating an ImageView reference.
    private ImageView imageView;
    private Integer timerIndex;
    private Timer timer;

// newInstance method creates a new ImageFragment object and initializes the object with appropriate parameters and returns the instance.
    public static ImageFragment newInstance(String param1, Integer param2) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment_image layout for this fragment
        View rootView =inflater.inflate(R.layout.fragment_image, container, false);
        imageView=(ImageView) rootView.findViewById(R.id.imagefragment);

        // setImageView is a method to set the image resource to an image with index as mparam2.
        setImageView(mParam2);

        //Initializing the timer object
        timer=new Timer();
        timerIndex=0;
        return rootView;
    }
    // setImageView method is used to set the image resource with the correct index based on user actions on next_button or previous_button.
    public void setImageView(Integer index){
        imageView.setImageResource(images_list[index]);
    }

    // setSlideShow method is used to schedule a timer task
    public void setSlideShow(Boolean check){
        //If user checks the slideshow checkbox the run() method is executed multiple times after a certain delay as specified
        if(check==true){
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setImageView(timerIndex%6);
                    timerIndex++;
                }
            },800,800);
        }
        else{
            // Terminating the current timer task when slideshow is unchecked.
            timer.cancel();
            // Assigning a new timer task which can be used later when the user checks the slideshow box again
            timer=new Timer();

        }
    }

}

