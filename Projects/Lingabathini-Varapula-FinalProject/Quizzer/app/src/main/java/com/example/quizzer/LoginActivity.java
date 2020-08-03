package com.example.quizzer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

DataBaseHandler mydb;
EditText username;
private EditText password;
private Button signup;
private Button login;
public static String checkusername;
String user;
@Override
protected void onCreate(Bundle SavedInstanceState) {
    super.onCreate(SavedInstanceState);
    mydb= new DataBaseHandler(this);
    setContentView(R.layout.layout_login);
    username=findViewById(R.id.log_usernameid);
    password=findViewById(R.id.log_passwordid);
    signup=findViewById(R.id.loginsignup_btn);
    login=findViewById(R.id.next_button);

    signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
            startActivity(intent);
        }
    });

    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
             checkusername = username.getText().toString();
            String pass = password.getText().toString();
            int id= mydb.getID(checkusername);
            Boolean check = mydb.checkCredentials(checkusername, pass);
            if (check) {
                if (checkusername.equals("admin") && pass.equals("admin")) {
                    Intent intentadmin = new Intent(getApplicationContext(), AdminDashboard.class);
                    intentadmin.putExtra("data",checkusername);
                    startActivity(intentadmin);
                    Notification("Logging in as Admin");
                    User user=new User(id,checkusername);
                    SessionManagement sessionManagement=new SessionManagement(LoginActivity.this);
                    sessionManagement.saveSession(user);
                    sessionManagement.setName(user);
                } else {
                    Notification("Login Successful");
                    Intent intentuser = new Intent(getApplicationContext(), UserDashboard.class);
                    startActivity(intentuser);
                    User user=new User(id,checkusername);
                    SessionManagement sessionManagement=new SessionManagement(LoginActivity.this);
                    sessionManagement.saveSession(user);
                    sessionManagement.setName(user);
                }
            }
            else{
                Boolean ch = mydb.checkuser(checkusername);
                if (ch) {
                    Notification("username or password incorrect, Please try again");
                } else
                    Notification("username doesn't exist, please sign-in to login");
            }
        }
    });

}
    @Override
    public void onStart() {
        super.onStart();
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userid = sessionManagement.getSession();
        user = sessionManagement.getName();
        if (userid != -1) {
            if (user.equals("admin")) {
                Intent intentuser = new Intent(getApplicationContext(), AdminDashboard.class);
                startActivity(intentuser);
            } else {
                Intent intentuser = new Intent(getApplicationContext(), UserDashboard.class);
                startActivity(intentuser);
            }
        } else {
            //do nothing
        }

    }
    private void Notification(String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    public void forgotPassword(View view){
    Intent intent=new Intent(getApplicationContext(),ForgotPasswordActivity.class);
    startActivity(intent);
    }

}
