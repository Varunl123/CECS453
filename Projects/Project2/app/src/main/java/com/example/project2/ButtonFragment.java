package com.example.project2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class ButtonFragment extends Fragment {
    //Naming widget references such as Buttons and checkboxes
    private Button button_prev;
    private Button button_next;
    private CheckBox slide_show;
    private CheckBox gallary_view;

    // Count variable which stores the current index of the images_list array.
    private Integer count;

    // Array of images present in the drawable folder
    private Integer[] images_list={R.drawable.animal13,R.drawable.animal14,R.drawable.animal15,R.drawable.animal16,R.drawable.animal17,R.drawable.animal18};

    //Reference variable for OnButtonPressListener interface
    OnButtonPressListener buttonListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

   // Method to disable the previous or next buttons when the user reaches the start or end of index in images_list.
    public void checkCount(){
        if(count==0)
                button_prev.setEnabled(false);
        else
                button_prev.setEnabled(true);
        if(count==images_list.length-1)
                button_next.setEnabled(false);
        else
                button_next.setEnabled(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the fragment_button layout for this fragment
        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.fragment_button, container, false);

        //Naming widget references such as Buttons and checkboxes
        button_prev =  root.findViewById(R.id.button_prevoius);
        button_next = root.findViewById(R.id.button_next);
        slide_show= root.findViewById(R.id.slideshow);
        gallary_view=root.findViewById(R.id.gallaryview);
        count=0;

        checkCount();

        //Setting an onClick listener for button_previous button and displaying previous image when user clicks on it.
        button_prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Decrementing the index of images_list and checking the edge cases by calling checkCount()
                        count--;
                        checkCount();
                        // Passing the decremented index to the onButtonPressed method of OnButtonPressListener.
                        buttonListener.onButtonPressed(count);
                    }
                });

        //Setting an onClick listener for button_next button and displaying previous image when user clicks on it.
        button_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Incrementing the index of images_list and checking the edge cases by calling checkCount()
                        count++;
                        checkCount();
                        // Passing the decremented index to the onButtonPressed method of OnButtonPressListener.
                        buttonListener.onButtonPressed(count);
                    }
                });

        //Setting an onCheckChangeListener for the slide_show checkbox
        slide_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        // If the slide_show is checked, then we pass the boolean value as true to the onCheckAction method of ButtonListener interface. Else we pass false as an argument.
                        // We also check the edge cases when an action takes place on checkboxes.
                        if(isChecked){
                            buttonListener.onSlideShowCheck(true);
                            gallary_view.setEnabled(false);
                            button_prev.setEnabled(false);
                            button_next.setEnabled(false);}
                        else{
                            buttonListener.onSlideShowCheck(false);
                            gallary_view.setEnabled(true);
                            button_prev.setEnabled(true);
                            if(count==images_list.length-1){
                                button_next.setEnabled(false);}
                            else
                                button_next.setEnabled(true);
                            if(count==0){
                                button_prev.setEnabled(false);
                            }
                            else
                                button_prev.setEnabled(true);
                        }
                        }

                });
        //Setting an onCheckChangeListener for the gallery_view checkbox
        gallary_view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        // If the slide_show is checked, then we pass the boolean value as true to the onCheckAction method of ButtonListener interface. Else we pass false as an argument.
                        // We also check the edge cases when an action takes place on checkboxes.
                        if(isChecked){
                            buttonListener.onGallaryViewCheck(true);
                            slide_show.setEnabled(false);
                            button_prev.setEnabled(false);
                            button_next.setEnabled(false);}

                        else{
                            buttonListener.onGallaryViewCheck(false);
                            slide_show.setEnabled(true);
                            button_prev.setEnabled(true);
                            if(count==images_list.length-1){
                                button_next.setEnabled(false);}
                            else
                                button_next.setEnabled(true);
                            if(count==0){
                                button_prev.setEnabled(false);
                            }
                            else
                                button_prev.setEnabled(true);

                        }

                    }
                });
        return root;
    }
    // onAttach called when it is attached to MainActivity and to check if the MainActivity has implemented the required listener callback for the Fragment (if a listener interface was defined in the Fragment )
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            buttonListener = (OnButtonPressListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onButtonPressed");
        }
    }
    @Override
    public void onDetach(){
            super.onDetach();
            buttonListener=null;
    }
}
