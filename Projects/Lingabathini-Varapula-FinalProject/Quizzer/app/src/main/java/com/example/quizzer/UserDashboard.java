package com.example.quizzer;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DataBaseHandler mydb;
    ListView listview;
    ArrayList<Integer> arraylist;
    ArrayList<Integer> testlist;
    Integer subjectId,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        mydb=new DataBaseHandler(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.username);
        SessionManagement sessionManagement=new SessionManagement(UserDashboard.this);
        String s=sessionManagement.getName();
        uid=mydb.getID(s);
        username.setText("Welcome "+s);
        navigationView.setNavigationItemSelectedListener(this);
        listview=findViewById(R.id.listview);
        arraylist=new ArrayList<Integer>();
        arraylist=mydb.getTopicIds();
        CustomAdapter adapter=new CustomAdapter();

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 subjectId=(Integer)listview.getAdapter().getItem(position);
                testlist=mydb.getTestBySubjectAndUser(subjectId,uid);
                Log.e("subjectId",subjectId.toString());
                if(testlist.size()!=0) {
                    Intent intent = new Intent(getApplicationContext(), UserTestSelection.class);
                    intent.putExtra("Data", subjectId);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UserDashboard.this, "No Quiz available for this subject !", Toast.LENGTH_LONG).show();
                }
            }
        });
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
            SessionManagement sessionManagement=new SessionManagement(UserDashboard.this);
            sessionManagement.removeSession();
            intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    DrawerLayout drawer = findViewById(R.id.drawerlayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
}
    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return arraylist.size();
        }

        @Override
        public Object getItem(int position) {
            return arraylist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view= getLayoutInflater().inflate(R.layout.subject_view,null);
            TextView subject=view.findViewById(R.id.subjectName);
            subject.setText(mydb.getTopicName(arraylist.get(position)));
            return view;
        }
    }
}