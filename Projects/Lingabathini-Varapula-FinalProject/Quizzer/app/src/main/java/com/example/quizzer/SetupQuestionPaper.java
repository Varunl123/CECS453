package com.example.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SetupQuestionPaper extends AppCompatActivity {

    EditText question;
    TextView questionText;
    EditText option1,option2,option3,option4,correctAnswer;
    Button next;
    Button exit;
    String getQuestion,getOption1,getOption2,getOption3,getOption4,getCorrectAnswer;
    DataBaseHandler mydb;
    ArrayList<Long> arraylist;
    Integer questionsNumber;
    Integer count=1;
    Button exitToMainMenu;
    public void increaseCount(){
        count++;
        if(count<=questionsNumber){
            questionText.setText("Question "+count);
            if(count==questionsNumber){
                next.setText("Finish");}

        }
        else{
            Toast.makeText(getApplicationContext(),"Question Paper Uploaded Successfully !",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),AdminDashboard.class);
            startActivity(intent);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_question_paper);
        questionText=findViewById(R.id.questionText);
        question=findViewById(R.id.question);
        option1=findViewById(R.id.choice1);
        option2=findViewById(R.id.choice2);
        option3=findViewById(R.id.choice3);
        option4=findViewById(R.id.choice4);
        correctAnswer=findViewById(R.id.correctAnswer);
        next=findViewById(R.id.next_button);
        exit=findViewById(R.id.exit_button);
        mydb=new DataBaseHandler(this);
        arraylist=(ArrayList<Long>)getIntent().getSerializableExtra("data");
        questionsNumber=getIntent().getIntExtra("questionsNumber",0);
        Log.e("QuestionsNumber",questionsNumber.toString());
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getQuestion=question.getText().toString();
                getOption1=option1.getText().toString();
                getOption2=option2.getText().toString();
                getOption3=option3.getText().toString();
                getOption4=option4.getText().toString();
                getCorrectAnswer=correctAnswer.getText().toString();
                if(validate_insert(getQuestion,getOption1,getOption2,getOption3,getOption4,getCorrectAnswer)) {
                    for (int i = 0; i < arraylist.size(); i++) {
                        Long l = mydb.addQuestions(getQuestion, getOption1, getOption2, getOption3, getOption4, getCorrectAnswer, arraylist.get(i));
                    }
                    Notification("Saved  !");
                    question.getText().clear();
                    option1.getText().clear();
                    option2.getText().clear();
                    option3.getText().clear();
                    option4.getText().clear();
                    correctAnswer.getText().clear();
                    increaseCount();

                }

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),AdminDashboard.class);
                startActivity(intent);
            }
        });

    }
    private boolean validate_insert(String question,String option1,String option2,String option3, String option4, String correctAnswer)
    {
        if(question.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty() ||correctAnswer.isEmpty() )
        {
            Notification("All fields are mandatory");
            return false;
        }

        return true;
    }
    private void Notification(String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
}