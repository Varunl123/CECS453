package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Patterns;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import java.util.HashMap;

public class Registration extends AppCompatActivity implements View.OnClickListener{

    //Naming widget references such as EditText and Button
    private EditText register_username;
    private EditText register_password;
    private EditText register_phone;
    private EditText register_email;
    private EditText register_retypepassword;
    private Button register_signup_button;

    Context context;

    private AwesomeValidation awesomeValidation;

    //A Hashmap reference is created and is set as null currently
    HashMap<String,String> newMember=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //getApplicationContext() renders the current context of the Application which can be used in various ways.
        context=getApplicationContext();

        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);

        // Mapping the actual widget objects in the activity_registration.xml to the corresponding mentioned references
        register_username=findViewById(R.id.register_uname);
        register_password=findViewById(R.id.register_password);
        register_phone=findViewById(R.id.register_phone);
        register_email=findViewById(R.id.register_email);
        register_retypepassword=findViewById(R.id.register_retype_password);
        register_signup_button=findViewById(R.id.signup_button);

        //Adding validation to the Registration fields like username , password , email and phone
        awesomeValidation.addValidation(this, R.id.register_uname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.username_error);
        awesomeValidation.addValidation(this, R.id.register_email, Patterns.EMAIL_ADDRESS, R.string.email_error);
        awesomeValidation.addValidation(this, R.id.register_phone, "^[2-9]{2}[0-9]{8}$", R.string.phone_error);

        // Regular Expression to put a constraint on the password entered.This Regular Expression states that the password entered should
        // A digit must appear at least once, An lowercase letter,uppercase letter and a special character should appear at least once.
        String regexPassword="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        awesomeValidation.addValidation(this, R.id.register_password, regexPassword, R.string.password_error);

        // to validate a confirmation field (don't validate any rule other than confirmation on confirmation field)
        awesomeValidation.addValidation(this, R.id.register_retype_password, R.id.register_password, R.string.password_mismatch);
        register_signup_button.setOnClickListener(this);

        //Preserving the Hashmap that we get from the login page irrespective of contents in the hashmap.If the hashmap is empty we can still add a new user to this hashmap
        newMember=(HashMap<String,String>)getIntent().getSerializableExtra("data");



    }
    // This method returns true if the Entered user already exists in the hashmap and false otherwise
    private boolean isUserAlreadyExists(String user){
        if(newMember.containsKey(user)){
            return true;
        }
        return false;
    }

    private void submitForm() {
        //first validate the form then move ahead
        //if this becomes true that means validation is successful
        if (awesomeValidation.validate() ) {

            //Check for existing user with the given username and move further
            if(isUserAlreadyExists(register_username.getText().toString()))
                    Toast.makeText(getApplicationContext(),"User Already Exists !",Toast.LENGTH_LONG).show();
            else {

                Toast.makeText(this, "Registration Successful", Toast.LENGTH_LONG).show();

                //If Registration is successful then put the entered username and password in the hashmap and send back the hashmap reference to the MainActivity using intent object.
                String current_username = register_username.getText().toString();
                String current_password = register_password.getText().toString();
                newMember.put(current_username, current_password);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("data", newMember);
                startActivity(intent);
            }

            //process the data further
        }
    }

    @Override
    public void onClick(View view) {
        if (view == register_signup_button) {
            submitForm();
        }
    }
}
