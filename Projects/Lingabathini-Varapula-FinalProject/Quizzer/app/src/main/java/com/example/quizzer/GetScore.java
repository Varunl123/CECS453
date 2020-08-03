package com.example.quizzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class GetScore extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView scoreView,answerQuestionItem,answerListAnswer;
    Integer score;
    Integer testId;
    DataBaseHandler mydb;
    Button returnMenu;
    Button checkAnswers;
    Integer duplicatetestId;
    ArrayList<HashMap<String,String>> array;
    HashMap<String,String>  reportsArray;
    ListView listview;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_score);
        scoreView=findViewById(R.id.score);
        testId=getIntent().getIntExtra("testId",0);
        score=getIntent().getIntExtra("score",0);
        mydb=new DataBaseHandler(this);
        scoreView.setText("Total Score: "+score.toString());
        returnMenu=findViewById(R.id.returnMenu);
        array=mydb.getUserQuestionList(testId);
       listview=findViewById(R.id.answerslist);
       CustomAdapter customadapter=new CustomAdapter();
       listview.setAdapter(customadapter);
       returnMenu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent  intent=new Intent(getApplicationContext(),UserDashboard.class);
               startActivity(intent);
           }
       });
        reportsArray=mydb.getTestDetails(testId);
        String userid=reportsArray.get("Userid");
        String topicid=reportsArray.get("topicId");
        String testid=reportsArray.get("TestId");

        long l=mydb.addReports(userid,score,topicid,testid);
        Log.e("reports",Long.toString(l));
        mydb.deleteTest(testId);
    }

    @Override
    public void onBackPressed() {

    }
    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int position) {
            return array.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=getLayoutInflater().inflate(R.layout.answers_list_item,null);
            answerQuestionItem=view.findViewById(R.id.answerQuestionItem);
            answerListAnswer=view.findViewById(R.id.answerListAnswer);
            String question=array.get(position).get("questionDescription");
            String answer=array.get(position).get("correctAnswer");
            int p=position+1;
            answerQuestionItem.setText("Question "+p+": "+question);
            answerListAnswer.setText("Answer: "+answer);
            return view;
        }
    }

}