package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Naming widget references such as EditText and Button
    private EditText login_username;
    private EditText login_password;
    private Button login_button;
    private Button signup_button;

    //A Hashmap reference is created and is currently set to null.
    HashMap<String,String> credentials=null;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getApplicationContext() renders the current context of the Application which can be used in various ways.
        context=getApplicationContext();

        // Mapping the actual widget objects in the activity_registration.xml to the corresponding mentioned references
        login_username=findViewById(R.id.login_uname);
        login_password=findViewById(R.id.login_pass);
        login_button=findViewById(R.id.login_button);
        signup_button=findViewById(R.id.singup_btn);

        //Creating an HashMap object and assigning to the above mentioned 'credentials' hashmap reference.
        credentials=new HashMap<String,String>();

        // We receive a hashmap object from Registration page through intent object and  check if the hashmap has data in it , then we restore . Otherwise the hashmap will be empty.
        if((HashMap<String,String>)getIntent().getSerializableExtra("data")!=null){
            credentials=(HashMap<String,String>)getIntent().getSerializableExtra("data");
        }

        // Setting an onClick listener to Login button
        login_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast toast;

                // If the 'credentials' hashmap has no data in it and login button is clicked,then a toast message saying 'please sign up' will be displayed.
                if(credentials==null) {
                    toast = Toast.makeText(context, "Please Sign up ", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    // Retrieving the data associated with the login and password fields
                    String current_username=login_username.getText().toString();
                    String current_password=login_password.getText().toString();

                    //Checking if entered username is present in the hashmap.If Yes then, then further validation is processed. Else a toast message displayed saying the user to sign up
                    if(credentials.get(current_username)!=null){

                        // Checking if the current user credentials are correct. If yes, then He is redirected to welcome page. Else a toast message is displayed saying password incorrect.
                        if(current_password.equals(credentials.get(current_username))){
                            Intent intent=new Intent(getApplicationContext(),Welcome.class);
                            intent.putExtra("message",current_username);
                            startActivity(intent);
                        }
                        else{
                            toast=Toast.makeText(context,"Password incorrect "+current_username,Toast.LENGTH_LONG);
                            toast.show();
                            login_password.getText().clear();
                        }
                    }
                    else{
                        toast=Toast.makeText(context,"Please Sign Up "+current_username,Toast.LENGTH_LONG);
                        toast.show();
                        login_password.getText().clear();
                    }
                }

            }
        });

        // setting an onclick event listener to Signup button. Once the clicks on this button, He is redirected to Registration page
        signup_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(),Registration.class);
                String current_username=login_username.getText().toString();
                String current_password=login_password.getText().toString();
                intent.putExtra("data",credentials);
                startActivity(intent);
            }

        });



    }
}
