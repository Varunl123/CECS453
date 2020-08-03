package com.example.quizzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatepasswordActivity extends AppCompatActivity {
    DataBaseHandler mydb;
    EditText newPass;
    EditText oldPass;
    Button back;
    Button save;
    String oldSavedPassword;
    String oldEnteredPassword;
    String newEnteredPassword;
    String user;
    Integer uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepassword);
        mydb = new DataBaseHandler(this);
        SessionManagement sessionManagement=new SessionManagement(UpdatepasswordActivity.this);
        user=sessionManagement.getName();
        uid=mydb.getID(user);
        oldPass=findViewById(R.id.editTextOldpassword);
        newPass=findViewById(R.id.editTextNewPassword);
        back=findViewById(R.id.buttonBackpage);
        save=findViewById(R.id.buttonSavepage);
        newEnteredPassword=newPass.getText().toString();
        oldSavedPassword=mydb.getPassword(user);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((oldPass.getText().toString()).equals(oldSavedPassword)){
                    mydb.updatepassword(uid,newPass.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    Toast.makeText(getApplicationContext(), "Password updated Successfully ", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Your old password is incorrect", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}