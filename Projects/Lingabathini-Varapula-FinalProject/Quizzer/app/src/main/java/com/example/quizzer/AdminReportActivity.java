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
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.google.android.material.navigation.NavigationView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminReportActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DataBaseHandler mydb;
    ArrayList<HashMap<String,Integer>> array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);
        mydb=new DataBaseHandler(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawerlayout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav1_view);
        View headerView = navigationView.getHeaderView(0);
        TextView username = (TextView) headerView.findViewById(R.id.username);
        SessionManagement sessionManagement=new SessionManagement(AdminReportActivity.this);
        String s=sessionManagement.getName();
        username.setText("Welcome "+s);
        navigationView.setNavigationItemSelectedListener(this);
    // To display the graph
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progressBar));
        Cartesian cartesian = AnyChart.column();

        array=(ArrayList<HashMap<String,Integer>>)getIntent().getSerializableExtra("Data");
        List<DataEntry> data = new ArrayList<>();
       // String p=""+array.size();
        //Log.e("size",p);
        if(array.size()<=0){
            Toast.makeText(getApplicationContext(),"No Reports Available",Toast.LENGTH_LONG).show();
        }
        else {
            for (int i = 0; i < array.size(); i++) {
                Integer id = array.get(i).get("userid");
                String user = mydb.getUsername(id);
                Integer score = array.get(i).get("score");
                // Log.e("username",user);
                //Log.e("score",score.toString());
                data.add(new ValueDataEntry(user, score));
            }

       /* List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("user1", 10));
        data.add(new ValueDataEntry("user2", 9));
        data.add(new ValueDataEntry("user3", 10));
        data.add(new ValueDataEntry("user4", 8));
        data.add(new ValueDataEntry("user5", 7));*/
            //data.add(new ValueDataEntry("Nail polish", 143760));
            //data.add(new ValueDataEntry("Eyebrow pencil", 170670));
            //data.add(new ValueDataEntry("Eyeliner", 213210));
            //data.add(new ValueDataEntry("Eyeshadows", 249980));

            Column column = cartesian.column(data);

            column.tooltip()
                    .titleFormat("{%X}")
                    .position(Position.CENTER_BOTTOM)
                    .anchor(Anchor.CENTER_BOTTOM)
                    .offsetX(0d)
                    .offsetY(2d)
                    .format("{%Value}{groupsSeparator: }");

            cartesian.animation(true);
            cartesian.title("Graph Report");

            cartesian.yScale().minimum(0d);

            cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

            cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
            cartesian.interactivity().hoverMode(HoverMode.BY_X);

            cartesian.xAxis(0).title("Students");
            cartesian.yAxis(0).title("Marks");

            anyChartView.setChart(cartesian);
        }
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
            intent =new Intent(this,AdminDashboard.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.view_reports) {
            intent =new Intent(this,AdminSelectReport.class);
            startActivity(intent);
        }
        else if(id==R.id.logout){
            SessionManagement sessionManagement=new SessionManagement(AdminReportActivity.this);
            sessionManagement.removeSession();
            intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawerlayout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}