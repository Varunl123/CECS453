package com.example.quizzer;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME="session";
    String SESSION_KEY ="session_user";

    public SessionManagement(Context context){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit() ;
    }
    //save logged in user
    public void saveSession(User user){
        int id=user.getId();
        editor.putInt(SESSION_KEY,id).commit();

            }
//return user id whose session is saved
    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY,-1);

    }
    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
        sharedPreferences.edit().clear().commit();
    }
    public void setName(User user){
        String name=user.getUserName();
        sharedPreferences.edit().putString("name",name).commit();
    }
    public String getName(){
        return sharedPreferences.getString("name","");
    }
}
