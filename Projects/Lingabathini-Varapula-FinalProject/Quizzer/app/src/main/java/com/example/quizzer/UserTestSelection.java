package com.example.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class UserTestSelection extends AppCompatActivity {
    DataBaseHandler mydb;
    Spinner testSpinner;
    ArrayList<Integer> testIds;
    ArrayList<String> testNames;
    Integer selectedTestId;
    Button startbutton;
    Integer uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_test_selection);
        Integer subjectId=getIntent().getIntExtra("Data",0);
        Log.e("Hello",subjectId.toString());
        mydb=new DataBaseHandler(this);
        SessionManagement sessionManagement=new SessionManagement(UserTestSelection.this);
        String uname=sessionManagement.getName();
        uid=mydb.getID(uname);
        testIds=new ArrayList<Integer>();
        testIds=mydb.getTestBySubjectAndUser(subjectId,uid);
        testNames=new ArrayList<String>();
        startbutton=findViewById(R.id.startQuiz);
        for(int i=0;i<testIds.size();i++){
            String s=mydb.getTestName(testIds.get(i));
            Log.e("Hello",s);
            testNames.add(s);
        }
        testSpinner=findViewById(R.id.testSpinner);
        ArrayAdapter<Integer> customeAdapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,testIds);
        customeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testSpinner.setAdapter(customeAdapter);
        testSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        selectedTestId=(Integer)testSpinner.getSelectedItem();
                Log.e("selectedId",selectedTestId.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        startbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UserQuizStart.class);
                intent.putExtra("Data",selectedTestId);
                startActivity(intent);
            }
        });
    }
}