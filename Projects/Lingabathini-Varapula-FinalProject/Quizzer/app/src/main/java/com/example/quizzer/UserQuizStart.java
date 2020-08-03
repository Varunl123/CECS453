package com.example.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserQuizStart extends AppCompatActivity {

    private Button return_home;
    private Button start_quiz;
    private TextView duration;
    Integer selectedTestId;
    DataBaseHandler mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_quiz_start);
        selectedTestId=getIntent().getIntExtra("Data",0);
        return_home=findViewById(R.id.return_home_button);
        start_quiz=findViewById(R.id.start_quiz_button);
        duration=findViewById(R.id.duration_display);
        mydb=new DataBaseHandler(this);

        duration.setText("Duration: "+mydb.getTestDuration(selectedTestId).toString()+" min");
        return_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),UserDashboard.class);
                startActivity(intent);
            }
        });
        start_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), UserQuestionPaper.class);
                intent.putExtra("Data",selectedTestId);
                startActivity(intent);
            }
        });

    }


}