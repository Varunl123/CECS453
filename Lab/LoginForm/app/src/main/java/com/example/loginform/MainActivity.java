package com.example.loginform;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText name;
    private EditText pass;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getApplicationContext();
        login=findViewById(R.id.loginButton);
        name=findViewById(R.id.editUsername);
        pass=findViewById(R.id.editPassword);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String username="admin";
                String passWord="admin";
                String usernameText = name.getText().toString();
                String  passwordText=pass.getText().toString();
                Toast toast;
                if(username.equals(usernameText) && passWord.equals(passwordText)){
                            toast=Toast.makeText(context,"Login Successful "+username,Toast.LENGTH_LONG);

                }
                else
                    toast=Toast.makeText(context,"Username or Password is incorrect "+usernameText,Toast.LENGTH_LONG);

                toast.show();
            }
        });
    }
}
