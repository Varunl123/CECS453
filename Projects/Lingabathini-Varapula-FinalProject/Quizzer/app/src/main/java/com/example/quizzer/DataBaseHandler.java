package com.example.quizzer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class DataBaseHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    //Database name
    private static final String DATABASE_NAME = "quizzer.db";
    //Table name
    private static final String USER_TABLE_NAME = "registeruser";
    private static final String TEST_TABLE="tests";
    private static final String TOPICS_TABLE="topics";
    private static final String QUESTIONS_TABLE="questions";
    private static final String ANSWERES_TABLE="answers";
    private static final String REPORTS_TABLE="reports";
    //Column names
    private static final String COL_1 = "userid";
    private static final String COL_2 = "username";
    private static final String COL_3 = "password";
    private static final String COL_4 = "email";

    public DataBaseHandler(Context context)
    {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String QUERY1="CREATE TABLE registeruser (userid INTEGER PRIMARY  KEY AUTOINCREMENT, username TEXT, password TEXT, email TEXT)";
        String Query2="CREATE TABLE topics (topicID INTEGER PRIMARY KEY AUTOINCREMENT, topicName TEXT)";
        String Query3="CREATE TABLE tests (testID INTEGER PRIMARY KEY AUTOINCREMENT, userID INTEGER, topicID INTEGER, questionsNumber INTEGER, timeDuration INTEGER, FOREIGN KEY (userID) REFERENCES registeruser (userid), FOREIGN KEY (topicID) REFERENCES topics (topicID))";
        String Query4="CREATE TABLE questions (questionID INTEGER PRIMARY KEY AUTOINCREMENT, questionDescription TEXT,choice1 TEXT, choice2 TEXT, choice3 TEXT, choice4 TEXT, correctAnswer TEXT, testID INTEGER, FOREIGN KEY (testID) REFERENCES tests (testID))";
        String Query5="CREATE TABLE reports (reportID INTEGER PRIMARY KEY AUTOINCREMENT, userid INTEGER, score INTEGER, topicID INTEGER, testID INTEGER)";
        db.execSQL(QUERY1);
        db.execSQL(Query2);
        db.execSQL(Query3);
        db.execSQL(Query4);
        db.execSQL(Query5);
        ContentValues contentvalues=new ContentValues();
        contentvalues.put("username","admin");
        contentvalues.put("password","admin");
        contentvalues.put("email","admin@gmail.com");
        long res=db.insert("registeruser",null,contentvalues);
        addTopics(db,"Mobile Application Development");
        addTopics(db,"Computer Networks");
        addTopics(db,"Operating Systems");
        addTopics(db,"Artificial Intelligence");
    }
    // onUpgrade is called when the table schema is changed i.e When the Version number is less than number provided in the super class constructor.


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TOPICS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+TEST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+QUESTIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ANSWERES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+REPORTS_TABLE);
        onCreate(db);
    }
    public long addUser(String user, String password, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",password);
        contentValues.put("email",email);
        long res = db.insert("registeruser",null,contentValues);
        db.close();
        return  res;
    }
    public void addTopics(SQLiteDatabase db,String topicname){
        ContentValues contentvalues=new ContentValues();
        contentvalues.put("topicName",topicname);
        db.insert("topics",null,contentvalues);
    }
    public long addTests(Integer uID,Integer tID,Integer qNumber,Integer time){
        SQLiteDatabase data=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("userID",uID);
        contentValues.put("topicID",tID);
        contentValues.put("questionsNumber",qNumber);
        contentValues.put("timeDuration",time);
        long generatedId=data.insert("tests",null,contentValues);
        return generatedId;
    }
    public String getQuestions(){
        SQLiteDatabase data=this.getReadableDatabase();
        ArrayList<String> array=new ArrayList<String>();
        Cursor cursor=data.rawQuery("SELECT * from questions",null);
        String total="";
        if(cursor.moveToFirst()){
            do{
                total=cursor.getString(0);
                total+=" "+cursor.getString(1);
                total+=" "+cursor.getString(2);
                total+=" "+cursor.getString(3);
                total+=" "+cursor.getString(4);
            }while(cursor.moveToNext());
        }
        cursor.close();
        data.close();
        return total;
    }
    public Integer getTestDuration(Integer testId){
        SQLiteDatabase data=this.getReadableDatabase();

        String[] selectionArgs={testId.toString()};
        Cursor cursor=data.rawQuery("SELECT timeDuration from tests WHERE testID=?",selectionArgs);
        if(cursor.moveToFirst()){
              return  cursor.getInt(0);
        }
        cursor.close();
        data.close();
        return null;
    }
    public long addQuestions(String question,String choice1,String choice2,String choice3,String choice4,String correctAnswer,Long testId){
        SQLiteDatabase data=this.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put("testID",testId);
        contentvalues.put("questionDescription",question);
        contentvalues.put("choice1",choice1);
        contentvalues.put("choice2",choice2);
        contentvalues.put("choice3",choice3);
        contentvalues.put("choice4",choice4);
        contentvalues.put("correctAnswer",correctAnswer);
        return data.insert("questions",null,contentvalues);
    }
    public String getTests(){
        SQLiteDatabase data=this.getReadableDatabase();
        ArrayList<String> array=new ArrayList<String>();
        Cursor cursor=data.rawQuery("SELECT * from tests",null);
        String total="";
        if(cursor.moveToFirst()){
            do{
                total=cursor.getString(0);
                total+=" "+cursor.getString(1);
                total+=" "+cursor.getString(2);
                total+=" "+cursor.getString(3);
                total+=" "+cursor.getString(4);
            }while(cursor.moveToNext());
        }
        cursor.close();
        data.close();
        return total;
    }
    public ArrayList<Integer> getTestBySubjectAndUser(Integer subjectId,Integer uId){
        ArrayList<Integer> array=new ArrayList<Integer>();
        SQLiteDatabase db=this.getReadableDatabase();
        String[] selectionArgs={subjectId.toString(),uId.toString()};
        Cursor cursor=db.rawQuery("SELECT testID FROM tests where topicID=? AND userID=?",selectionArgs);
        if(cursor.moveToFirst()){
            do{
                Integer testId=Integer.parseInt(cursor.getString(0));
                array.add(testId);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return array;
    }
    public String getTestName(Integer testId){
        return "Test "+testId;
    }
    public ArrayList<Integer> getTopicIds(){
        ArrayList<Integer> array=new ArrayList<Integer>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * from topics",null);
        if(cursor.moveToFirst()){
            do{
                Integer name=Integer.parseInt(cursor.getString(0));
                array.add(name);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return array;
    }
    public String getTopicName(Integer topicId){
        String[] selectArgs={topicId.toString()};
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * from topics where topicID=?",selectArgs);
        if(cursor.moveToFirst()){
            return cursor.getString(1);
        }
        cursor.close();
        db.close();
        return null;
    }
    public ArrayList<Integer> getTestIDs(Integer uid){
        SQLiteDatabase data=this.getReadableDatabase();
        ArrayList<Integer> array=new ArrayList<Integer>();
        String[] selectionArgs={uid.toString()};
        Cursor cursor=data.rawQuery("SELECT * from tests WHERE testID=?",selectionArgs);
        if(cursor.moveToFirst()){
            do{
                array.add(cursor.getInt(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        data.close();
        return array;
    }
    public ArrayList<String> getTopics(){
        ArrayList<String> array=new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * from topics",null);
        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(1);
                array.add(name);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return array;
    }
    public boolean checkCredentials(String username, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(USER_TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return  true;
        else
            return  false;
    }
    public boolean checkuser(String username){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection =COL_2 + "=?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query(USER_TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return  true;
        else
            return  false;
    }

    public boolean isDuplicate(String username){
        SQLiteDatabase data=this.getReadableDatabase();
        String[] selectionArgs={username};
        Cursor cursor=data.rawQuery("SELECT * FROM registeruser WHERE username=?",selectionArgs);
        Log.e("TAG",String.valueOf(cursor.getCount()));
        if(cursor.getCount()>=1)
            return true;
        return false;
    }
    /*<summary>to get the user id from the database</summary>*/
    public String getId()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String QUERY="SELECT userid FROM "+USER_TABLE_NAME;
        Cursor cursor=db.rawQuery(QUERY,null);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "Failed";
    }
    public ArrayList<String> getuserlist(){
        ArrayList<String> list= new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        try{
            String query="SELECT username FROM registeruser where userid!=1";
            Cursor cursor=db.rawQuery(query,null);
            while (cursor.moveToNext()){
                list.add(cursor.getString(cursor.getColumnIndex("username")));
            }
            cursor.close();
            db.close();
        }catch(Exception ex){
            Log.e(TAG,"Error in geting list "+ex.toString());
        }
        return list;
    }
    public Integer getTopicID(String topic){
        SQLiteDatabase data=this.getReadableDatabase();
        String[] selectionArgs={topic};
        Cursor cursor=data.rawQuery("SELECT * FROM topics WHERE topicName=?",selectionArgs);
        if(cursor.getCount()==1){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        cursor.close();
        data.close();
        return -1;
    }
    public ArrayList<Integer> getUserIds(){
        ArrayList<Integer> list=new ArrayList<Integer>();
        SQLiteDatabase data=getReadableDatabase();
        try{
            String query="SELECT userid FROM "+USER_TABLE_NAME;
            Cursor cursor=data.rawQuery(query,null);
            while(cursor.moveToNext()){
                list.add(cursor.getInt(0));
            }
            cursor.close();
            data.close();
        }
        catch(Exception ex){
            Log.e(TAG,"Error in getting list"+ex.toString());
        }
        return list;
    }

    public ArrayList<HashMap<String,String>> getUserQuestionList(Integer testId){
        SQLiteDatabase data=this.getReadableDatabase();
        ArrayList<HashMap<String,String>> array=new ArrayList<HashMap<String,String>>();

        String id=testId.toString();
        String[] selectionArgs={id};
        Cursor cursor=data.rawQuery("SELECT * FROM questions WHERE testID=?",selectionArgs);
        HashMap<String,String> hashmap;
        if(cursor.moveToFirst()){
            do{
                String description=cursor.getString(1);
                String choice1=cursor.getString(2);
                String choice2=cursor.getString(3);
                String choice3=cursor.getString(4);
                String choice4=cursor.getString(5);
                String correctAnswer=cursor.getString(6);
                hashmap=new HashMap<String,String>();
                Log.e("choice1",choice1);
                hashmap.put("questionDescription",description);
                hashmap.put("choice1",choice1);
                hashmap.put("choice2",choice2);
                hashmap.put("choice3",choice3);
                hashmap.put("choice4",choice4);
                hashmap.put("correctAnswer",correctAnswer);
                array.add(hashmap);
            }while(cursor.moveToNext());
            return array;
        }

        cursor.close();
        data.close();
        return null;

    }

    /*<summary>to get the username from the database</summary>*/
    public String getUsername(Integer id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] selectionsArgs={id.toString()};
        Cursor cursor=db.rawQuery("SELECT username FROM registeruser WHERE userid=?",selectionsArgs);
        cursor.moveToFirst();
        if(cursor.getCount()==1)
        {
            return cursor.getString(0);
        }
        cursor.close();
        db.close();
        return "Failed";
    }
    public Integer getID(String uname){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] selectionsArgs={uname};
        Cursor cursor=db.rawQuery("SELECT userid FROM registeruser WHERE username=?",selectionsArgs);
        if(cursor.getCount()==1){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return -1;
    }

    /*<summary>to get the email id from the database</summary>*/
    public String getEmail(String check)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] names={check};
        String QUERY="SELECT * FROM registeruser WHERE username=?" ;
        Cursor cursor=db.rawQuery(QUERY,names);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(3);
        }
        cursor.close();
        db.close();
        return "Failed";
    }
    public void updateEmail(Integer uid, String emailaddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("email", emailaddress);
        db.update("registeruser", newValues, "userid="+uid, null);

    }
    public void updatepassword(Integer uid,String newpass){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put("password", newpass);
        db.update("registeruser", newValues, "userid="+uid, null);
    }
    public String getPassword(String uname){
        SQLiteDatabase db=this.getReadableDatabase();
        String[] names={uname};
        String QUERY="SELECT * FROM registeruser WHERE username=?" ;
        Cursor cursor=db.rawQuery(QUERY,names);
        if(cursor.getCount()==1)
        {
            cursor.moveToFirst();
            return cursor.getString(2);
        }
        cursor.close();
        db.close();
        return "Failed";
    }
    public boolean deleteTest(Integer testId){
        SQLiteDatabase db=this.getReadableDatabase();
        String table = "tests";
        String whereClause = "testID=?";
        String[] whereArgs = new String[] { String.valueOf(testId) };
        if(db.delete(table, whereClause, whereArgs)>0)
                return true;
        return false;
    }
    public HashMap<String,String> getTestDetails(Integer testId){
        SQLiteDatabase data=this.getReadableDatabase();
        String id=testId.toString();
        String[] selectionArgs={id};
        Cursor cursor=data.rawQuery("SELECT * FROM tests WHERE testID=?",selectionArgs);
        HashMap<String,String> hashmap=new HashMap<String,String>();
        Integer i=cursor.getCount();
        Log.e("count",i.toString());
        cursor.moveToFirst();
        if(cursor.getCount()==1){
                Integer identity=cursor.getInt(0);
                Integer userid=cursor.getInt(1);
                Integer topicid=cursor.getInt(2);
                hashmap.put("TestId",identity.toString());
                hashmap.put("Userid",userid.toString());
                hashmap.put("topicId",topicid.toString());
                Log.e("hashmap",hashmap.toString());
            return hashmap;
        }
        cursor.close();
        data.close();
        return hashmap;

    }
    public long addReports(String userid,Integer score,String topicid,String testid){
        SQLiteDatabase data=this.getWritableDatabase();
        ContentValues contentvalues=new ContentValues();
        contentvalues.put("testID",Integer.parseInt(testid));
        contentvalues.put("userid",Integer.parseInt(userid));
        contentvalues.put("score",score);
        contentvalues.put("topicID",Integer.parseInt(topicid));

        long l= data.insert("reports",null,contentvalues);
        Log.e("reports",Long.toString(l));
        return l;
    }
    public ArrayList<HashMap<String,Integer>> getFinalReport(Integer topicId){
        SQLiteDatabase data=this.getReadableDatabase();
        String id=topicId.toString();
        String[] selectionArgs={id};
        Cursor cursor=data.rawQuery("SELECT * FROM reports WHERE topicID=?",selectionArgs);

        ArrayList<HashMap<String,Integer>> array=new ArrayList<HashMap<String, Integer>>();
        Integer i=cursor.getCount();
        Log.e("count",i.toString());
        if(cursor.moveToFirst()){
            do {
                Integer userid = cursor.getInt(1);
                Integer score = cursor.getInt(2);
                HashMap<String,Integer> hashmap=new HashMap<String,Integer>();
                hashmap.put("userid", userid);
                hashmap.put("score", score);
                Log.e("hashmap", hashmap.toString());
                array.add(hashmap);
            }while(cursor.moveToNext());
            return array;
    }
        cursor.close();
        data.close();
        return null;
    }

}

