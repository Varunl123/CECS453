package com.example.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText username;
    private EditText pass;
    private EditText repass;
    DataBaseHandler mydb;
    private String usern;
    private String passw;
    private String repassw;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mydb = new DataBaseHandler(this);
        username = (EditText) findViewById(R.id.usernameforgotpassword);
        pass = (EditText) findViewById(R.id.passwordforgotpassword);
        repass = (EditText) findViewById(R.id.reenterforgotpassword);
        save = findViewById(R.id.buttonsaveForgorpassword);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean response = validate_insert(username.getText().toString(), pass.getText().toString(), repass.getText().toString());
                if (response) {
                    if (mydb.checkuser(username.getText().toString())) {
                        if (pass.getText().toString().equals(repass.getText().toString())) {
                            mydb.updatepassword(mydb.getID(username.getText().toString()), pass.getText().toString());
                            Notification("Password Updated Successfully");
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Notification("Passwords didn't match");
                        }
                    } else {
                        Notification("Username doesn't exist");
                    }

                }
            }
        });

    }

    private void Notification(String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }
    private boolean validate_insert(String username,String password,String retype)
    {
        if(username.isEmpty() || password.isEmpty() || retype.isEmpty() )
        {
            Notification("All fields are mandatory");
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


        return true;
    }
}