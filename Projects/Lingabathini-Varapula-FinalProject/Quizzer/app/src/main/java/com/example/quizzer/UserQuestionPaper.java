package com.example.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class UserQuestionPaper extends AppCompatActivity {
    DataBaseHandler mydb;
    Integer testId;
    HashMap<String,String> hashmap;
    ArrayList<HashMap<String,String>> array;
    TextView question,choice1,choice2,choice3,choice4;
    TextView questionText;
    RadioGroup radiogroup;
    RadioButton selectedradiobutton;
    RadioButton choice1Button;
    RadioButton choice2Button;
    RadioButton choice3Button;
    String correctAnswer;
    RadioButton choice4Button;
    Integer score;
    Button nextButton;
    Integer count;
    TextView countdown;
    Integer duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_question_paper);
        mydb = new DataBaseHandler(this);
        testId = getIntent().getIntExtra("Data", 0);
        Log.e("userSelectedId", testId.toString());
        array=new  ArrayList<HashMap<String,String>>();
        array = mydb.getUserQuestionList(testId);
        Log.e("array",array.toString());
        question = findViewById(R.id.userquestion);
        radiogroup = findViewById(R.id.radiogroup);
        questionText = findViewById(R.id.userquestionText);
        choice1Button = findViewById(R.id.radiochoice1);
        choice2Button = findViewById(R.id.radiochoice2);
        choice3Button = findViewById(R.id.radiochoice3);
        choice4Button = findViewById(R.id.radiochoice4);
        nextButton = findViewById(R.id.user_next_button);
        //displayQuestions(hashmap);

        count=0;
        score=0;
        hashmap=array.get(count);
        int  number=count+1;
        questionText.setText("Question "+number);
        String description = hashmap.get("questionDescription");
        String choice1 = hashmap.get("choice1");
        // Log.e("TAG",choice1);
        String choice2 = hashmap.get("choice2");
        Log.e("choice2",choice2);
        String choice3 = hashmap.get("choice3");
        String choice4 = hashmap.get("choice4");
        correctAnswer=hashmap.get("correctAnswer");
        question.setText(description);
        choice1Button.setText(choice1);
        choice2Button.setText(choice2);
        choice3Button.setText(choice3);
        choice4Button.setText(choice4);

        countdown=findViewById(R.id.countdown);
        duration=mydb.getTestDuration(testId);
        startTimer(duration);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int radioId=radiogroup.getCheckedRadioButtonId();
                if(radioId==-1){
                    Toast.makeText(getApplicationContext(),"Select an option !",Toast.LENGTH_LONG).show();
                }
                else {
                    selectedradiobutton = findViewById(radioId);
                    Log.e("correctAnswer", correctAnswer);
                    Log.e("selectedAnswer", selectedradiobutton.getText().toString());
                    if (correctAnswer.equals(selectedradiobutton.getText().toString())) {
                        score++;
                    }

                    count++;
                    if (count < array.size()) {
                        int number = count + 1;
                        questionText.setText("Question " + number);
                        if (count == array.size() - 1) {
                            nextButton.setText("Finish");
                        }
                        hashmap = array.get(count);
                        display(hashmap);

                    } else {
                        Intent intent = new Intent(getApplicationContext(), GetScore.class);
                        intent.putExtra("Score", score);
                        intent.putExtra("testId", testId);
                        startActivity(intent);
                    }
                }

            }
        });


    }
    public void display(HashMap<String,String> hashmap){
        String description = hashmap.get("questionDescription");
        String choice1 = hashmap.get("choice1");
        String choice2 = hashmap.get("choice2");
        String choice3 = hashmap.get("choice3");
        String choice4 = hashmap.get("choice4");
        correctAnswer=hashmap.get("correctAnswer");
        question.setText(description);
        choice1Button.setText(choice1);
        choice2Button.setText(choice2);
        choice3Button.setText(choice3);
        choice4Button.setText(choice4);
    }

    private void startTimer(int noOfMinutes) {
        CountDownTimer  countDownTimer = new CountDownTimer(60000*noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format(Locale.getDefault(), "Time Remaining %02d min: %02d sec",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                countdown.setText(hms);//set text
            }
            public void onFinish() {
                countdown.setText("TIME'S UP!!");
                //On finish change timer text
                int radioId=radiogroup.getCheckedRadioButtonId();
                if(radioId==-1){
                    Toast.makeText(getApplicationContext(),"Select an option !",Toast.LENGTH_LONG).show();
                }
                else {
                    selectedradiobutton = findViewById(radioId);
                    Log.e("correctAnswer", correctAnswer);
                    Log.e("selectedAnswer", selectedradiobutton.getText().toString());
                    if (correctAnswer.equals(selectedradiobutton.getText().toString())) {
                        score++;
                    }
                }
                    Intent intent=new Intent(getApplicationContext(),GetScore.class);
                Log.e("Score",score.toString());
                intent.putExtra("score",score);
                intent.putExtra("testId",testId);
                startActivity(intent);

            }
        }.start();

    }


}