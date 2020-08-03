package com.example.project2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class GallaryView extends Fragment {
    GridView gridView;
    private int[] animals={R.drawable.animal13,R.drawable.animal14,R.drawable.animal15,R.drawable.animal16,R.drawable.animal17,R.drawable.animal18};
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment_gallary_view layout for this fragment
        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.fragment_gallary_view, container, false);

        // rendering the grid view reference
         gridView=view.findViewById(R.id.simpleGridView);

         // Creating a custom adapter with current context and passing it as an argument to setAdapter method of gridview.
         CustomAdapter adapter=new CustomAdapter(getContext(),animals);
         gridView.setAdapter(adapter);
        return view;
    }


}
