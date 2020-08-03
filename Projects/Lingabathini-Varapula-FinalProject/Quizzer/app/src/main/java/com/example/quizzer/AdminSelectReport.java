package com.example.quizzer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminSelectReport extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DataBaseHandler mydb;
    Spinner selectQuizSpinner;
    Button generateReportButton;
    ArrayList<String> topicString;
    ArrayList<HashMap<String,Integer>> hashmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_select_report);
        mydb = new DataBaseHandler(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawerlayout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav1_view);
        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.username);
        TextView email = (TextView) headerView.findViewById(R.id.emailid);
        SessionManagement sessionManagement=new SessionManagement(AdminSelectReport.this);
        String s=sessionManagement.getName();
        username.setText("Welcome "+s);
        navigationView.setNavigationItemSelectedListener(this);
        generateReportButton= findViewById(R.id.generatebutton);
        selectQuizSpinner=findViewById(R.id.selectQuizSpinner);
        topicString=mydb.getTopics();
        ArrayAdapter<String> customeAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,topicString);
        customeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectQuizSpinner.setAdapter(customeAdapter);

        selectQuizSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String topicName=(String)selectQuizSpinner.getSelectedItem();
                Integer topicId=mydb.getTopicID(topicName);
                hashmap=mydb.getFinalReport(topicId);
                //Log.e("God",hashmap.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        generateReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hashmap!=null) {
                    Intent intent = new Intent(getApplicationContext(), AdminReportActivity.class);
                    intent.putExtra("Data", hashmap);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"No Reports Available !",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerlayout1);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            intent = new Intent(this, AdminDashboard.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.view_reports) {
            intent = new Intent(this, AdminSelectReport.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            SessionManagement sessionManagement=new SessionManagement(AdminSelectReport.this);
            sessionManagement.removeSession();
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawerlayout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}