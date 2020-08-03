package com.example.quizzer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class SetupQuizActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DataBaseHandler mydb;
    Button mStudents;
    TextView mSubject;
    TextView mSelected;
    Button mSetupQuetions;
    String[] availableusers;
    boolean[] checkedUsers;
    Spinner subjectSpinner;
    EditText questionNumbers;
    EditText timeduration;
    TextView spinnerTextView;
    ListView listview;
    ArrayList<String> usersList;
    ArrayList<Integer> selectedUsers;
    String topicName;
    String numberofquestions;
    String time;
    ArrayList<Long> testIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_quiz);
        mydb= new DataBaseHandler(this);
        availableusers=mydb.getuserlist().toArray(new String[mydb.getuserlist().size()]);
        //mStudents= (Button) findViewById(R.id.btnselect);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawerlayout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav1_view);
        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.username);
        SessionManagement sessionManagement=new SessionManagement(SetupQuizActivity.this);
        String s=sessionManagement.getName();
        username.setText("Welcome "+s);
        navigationView.setNavigationItemSelectedListener(this);

        //mSelected=(TextView) findViewById(R.id.Selected);
        subjectSpinner=findViewById(R.id.subjectSpinner);
        mSetupQuetions=(Button) findViewById(R.id.squestion);
        checkedUsers=new boolean[availableusers.length];
        listview=findViewById(R.id.userlistView);
        questionNumbers=(EditText)findViewById(R.id.numberofquestions);
        timeduration=(EditText)findViewById(R.id.timeduration);
       numberofquestions=questionNumbers.getText().toString();
       Log.e("numberofquestions"," "+numberofquestions);
        time=timeduration.getText().toString();
        usersList=mydb.getuserlist();
        CustomAdapter adapter=new CustomAdapter();
        listview.setAdapter(adapter);
        selectedUsers=new ArrayList<Integer>();
        ArrayList<String> topicString=new ArrayList<String>();
        topicString=mydb.getTopics();
        ArrayAdapter<String> customeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,topicString);
        customeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSpinner.setAdapter(customeAdapter);
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                topicName=(String)subjectSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        testIds=new ArrayList<Long>();
        mSetupQuetions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!questionNumbers.getText().toString().isEmpty() && !timeduration.getText().toString().isEmpty() ) {
                    Log.e("TAG", selectedUsers.toString());
                    // Toast.makeText(getApplicationContext(),""+mydb.getTests(),Toast.LENGTH_LONG).show();
                    //  Toast.makeText(getApplicationContext(),""+selectedUsers.toString(),Toast.LENGTH_LONG).show();
                    String a = questionNumbers.getText().toString();
                    String b = timeduration.getText().toString();
                    for (int i = 0; i < selectedUsers.size(); i++) {
                        Long l = mydb.addTests(selectedUsers.get(i), mydb.getTopicID(topicName), Integer.parseInt(a), Integer.parseInt(b));
                        testIds.add(l);
                    }
                   // Toast.makeText(getApplicationContext(), mydb.getTests(), Toast.LENGTH_LONG).show();
                    Intent setupq = new Intent(getApplicationContext(), SetupQuestionPaper.class);
                    setupq.putExtra("data", testIds);
                    setupq.putExtra("questionsNumber", Integer.parseInt(a));
                    startActivity(setupq);
                }
                else{
                    Notification("All feilds are mandatory");
                }
            }
        });
    }
    public class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return usersList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=getLayoutInflater().inflate(R.layout.user_checkbox,null);
            final CheckBox checkview=view.findViewById(R.id.userCheckbox);
            checkview.setText(usersList.get(position));
            checkview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        selectedUsers.add(mydb.getID(checkview.getText().toString()));
                    }
                    else{
                        selectedUsers.remove(mydb.getID(checkview.getText().toString()));
                    }
                }
            });
            return view;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        if (id == R.id.home) {
            intent =new Intent(this,AdminDashboard.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.view_reports) {
            intent =new Intent(this,AdminSelectReport.class);
            startActivity(intent);
        }
        else if(id==R.id.logout){
            SessionManagement sessionManagement=new SessionManagement(SetupQuizActivity.this);
            sessionManagement.removeSession();
            intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawerlayout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void Notification(String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

}
