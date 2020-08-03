package com.example.quizzer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.zip.Inflater;

public class SignupActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private EditText retype;
    private EditText email;
    private Button signup;
    DataBaseHandler mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);
        mydb=new DataBaseHandler(this);
        username=(EditText)findViewById(R.id.usernameid);
        password =(EditText)findViewById(R.id.passwordid);
        retype =(EditText)findViewById(R.id.retypeid);
        email =(EditText)findViewById(R.id.emailid);
        signup=(Button)findViewById(R.id.signup_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean response=validate_insert(username.getText().toString(),password.getText().toString(),retype.getText().toString(),email.getText().toString());
                if(response)
                {
                    Notification("Account Created!");
                    mydb.addUser(username.getText().toString(),password.getText().toString(),email.getText().toString());
                    Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
    private boolean validate_insert(String username,String password,String retype,String email)
    {
        if(username.isEmpty() || password.isEmpty() || retype.isEmpty() || email.isEmpty() )
        {
            Notification("All fields are mandatory");
            return false;
        }
        if(mydb.isDuplicate(username)){
            Notification("User Name already exists");
            return false;
        }
        if(!password.equals(retype))
        {
            Notification("Passwords didn't match");
            return false;
        }
        else
        {
            if(password.length()<4)
            {
                Notification("Password should be atleast 4 characters");
                return false;
            }
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Notification("Invalid email format");
            return false;
        }

        return true;
    }
    public void backToLogin(View view){
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }
    private void Notification(String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    }

