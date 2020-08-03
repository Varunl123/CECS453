package com.example.quizzer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Button homebutton;
    TextView showEmail;
    TextView showUsername;
    TextView changePassword;
    TextView updateEmail;
    DataBaseHandler mydb;
    public String user;
    public String userEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mydb= new DataBaseHandler(this);
        showEmail=findViewById(R.id.semailid);
        showUsername=findViewById(R.id.susername);
        changePassword=findViewById(R.id.change_Password);
        updateEmail=findViewById(R.id.update_Email);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.username);
        SessionManagement sessionManagement=new SessionManagement(ProfileActivity.this);
        user=sessionManagement.getName();
        username.setText("Welcome "+user);
        navigationView.setNavigationItemSelectedListener(this);
        showUsername.setText("User Name: "+user);
        userEmail=mydb.getEmail(user);
        showEmail.setText("Email Address: "+userEmail);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerlayout);
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
            intent =new Intent(this,UserDashboard.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.edit_profile) {
            intent =new Intent(this,ProfileActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.help) {
            intent=new Intent(this,HelpActivity.class);
            startActivity(intent);
        } else if (id == R.id.about) {
            intent=new Intent(this,AboutActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.logout){
            SessionManagement sessionManagement=new SessionManagement(ProfileActivity.this);
            sessionManagement.removeSession();
            intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawerlayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void updateEmail(View view){
            Intent intent = new Intent(getApplicationContext(),EmailUpdateActivity.class);
            startActivity(intent);
    }
    public void changePassword(View view){
            Intent intent = new Intent(getApplicationContext(),UpdatepasswordActivity.class);
            startActivity(intent);
    }
}