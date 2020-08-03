package com.example.cardetailsproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity {

    // Creating references for Imageviews,Textviews,Listviews and spinners
    private DisplayFragment displayfragment;
    private Spinner spinner1, spinner2;
    private boolean dualPane = false;
    TextView spinnerTextview;
    private TextView textview;
    private String spinner1SelectID;
    private String spinner1SelectName;
    private String spinner2SelectID;
    private String spinner2SelectName;
    private ListView listview;
    String[] cars;
    private ArrayList<HashMap<String,String>> completevehicleDetails;
    private ArrayList<HashMap<String, String>> vehiclemakelist;
    private ArrayList<HashMap<String,String>> vehicle_model_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Making sure if the layout inflated is in the tablet mode or phone mode.
        if (findViewById(R.id.container) != null) {
            dualPane = true;
        } else
            dualPane = false;
        // Getting the view objects of respective spinners and listview
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        textview = findViewById(R.id.spinner1text);
        listview=findViewById(R.id.customlistview);

        // Executing the GetCarsInfo inner class
            new GetCarsInfo().execute();

    }

    class GetCarsInfo extends AsyncTask<Void, Void, Void> {
        private String TAG = MainActivity.class.getSimpleName();
        private String url = "https://thawing-beach-68207.herokuapp.com/carmakes";
        HashMap<String, String> car;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            vehiclemakelist = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler http = new HttpHandler();
            String jsonStr = http.makeServiceCall(url);

            // Appending the REST API url to json string object
            jsonStr = "{\"vehicles_list\":" + jsonStr + "}";
            Log.d(TAG, "Reespnse from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonobject = new JSONObject(jsonStr);
                    // Receiving the JSON Array with the json array named "vehicle_list"
                    JSONArray cars = jsonobject.getJSONArray("vehicles_list");
                    // Storing the Json data into the hashmap object using a loop
                    for (int i = 0; i < cars.length(); i++) {
                        JSONObject c = cars.getJSONObject(i);
                        String id = c.getString("id");
                        String make = c.getString("vehicle_make");
                        car = new HashMap<>();
                        car.put("id", id);
                        car.put("vehicle_make", make);
                        vehiclemakelist.add(car);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "Cannot connect to the server");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            String[] from={"vehicle_make"};
            int[] to={R.id.spinner1text};
            // Initializing an adapter for spinner to store the data into the textview of the spinner
            final SpinnerAdapter adapter = new SimpleAdapter(MainActivity.this, vehiclemakelist, R.layout.spinner_text_view,from,to);
            spinner1.setAdapter(adapter);

            // Intializing an listener whenever user performs an action on the vehicle_make spinner
            spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Receiving the hashmap object from the selected item in the vehicle make spinner.
                    HashMap<String,String> object=(HashMap<String,String>)spinner1.getSelectedItem();
                    try {
                        // Storing the id and name of the the vehicle into string objects for future reference
                        spinner1SelectID= object.get("id");
                        spinner1SelectName=object.get("vehicle_make");
                        Log.d(TAG,spinner1SelectID);
                        Log.d(TAG,spinner1SelectID);
                        Log.d(TAG,spinner1SelectName);
                        new GetCarModel().execute();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
    class GetCarModel extends AsyncTask<Void,Void,Void>{
        private String TAG= MainActivity.class.getSimpleName();

        private String url="https://thawing-beach-68207.herokuapp.com/carmodelmakes/"+spinner1SelectID;
        private HashMap<String,String> carModel;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            vehicle_model_list=new ArrayList<>();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);

            // Appending the REST API url to json string object
            jsonStr = "{\"vehicle_model_list\":" + jsonStr + "}";

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    // Storing the Json data into the hashmap object using a loop
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray car_models = jsonObj.getJSONArray("vehicle_model_list");
                    for (int i = 0; i < car_models.length(); i++) {
                        JSONObject c = car_models.getJSONObject(i);
                        String model_id = c.getString("id");
                        String model = c.getString("model");
                        String vehicle_make_id = c.getString("vehicle_make_id");
                        carModel = new HashMap<>();
                        carModel.put("model_id", model_id);
                        carModel.put("model",model);
                        carModel.put("vehicle_make_id", vehicle_make_id);
                        vehicle_model_list.add(carModel);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());

                }
            } else {
                Log.e(TAG, "Cannot connect to the server");
            }

            return null;
        }
        @Override
        public void onPostExecute(Void result){
            String[] from={"model"};
            int[] to={R.id.spinner1text};
            // Initializing an adapter for spinner to store the data into the textview of the spinner
            final SpinnerAdapter adapter = new SimpleAdapter(MainActivity.this, vehicle_model_list, R.layout.spinner_text_view,from,to);
            spinner2.setAdapter(adapter);
            spinner2.setOnItemSelectedListener(new OnItemSelectedListener(){
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    // Receiving the hashmap object from the selected item in the vehicle model spinner.
                    HashMap<String,String> object=(HashMap<String,String>)spinner2.getSelectedItem();
                    try {
                        // Storing the model_id and model_name of the the vehicle into string objects for future reference
                        String str=spinner2.getSelectedItem().toString();
                        spinner2SelectID=object.get("model_id");
                        spinner2SelectName=object.get("model");
                        Log.d(TAG,str);
                        // Executing the GetCompleVehiclDetails inner class
                        new GetCompleteVehicleDetails().execute();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
    class GetCompleteVehicleDetails extends AsyncTask<Void,Void,Void>{
        private String TAG= MainActivity.class.getSimpleName();
        // Appending the REST API url to json string object
        private String url="https://thawing-beach-68207.herokuapp.com/cars/"+spinner1SelectID+"/"+spinner2SelectID+"/92603";
        private HashMap<String,String> vehicleDescription;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            completevehicleDetails=new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    // Getting the vehicle complete details using selected vehicle make and vehicle model and storing in the hashmap
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray car_models = jsonObj.getJSONArray("lists");
                    for (int i = 0; i < car_models.length(); i++) {
                        JSONObject c = car_models.getJSONObject(i);
                        String vehicle_make = c.getString("vehicle_make");
                        String model = c.getString("model");
                        String price = c.getString("price");
                        String veh_description = c.getString("veh_description");
                        String image_url = c.getString("image_url");
                        String created_at = c.getString("created_at");
                        vehicleDescription = new HashMap<>();
                        vehicleDescription.put("vehicle_make", vehicle_make);
                        vehicleDescription.put("model",model);
                        vehicleDescription.put("price", price);
                        vehicleDescription.put("vehicle_description", veh_description);
                        vehicleDescription.put("image_url", image_url);
                        vehicleDescription.put("created_at", created_at);
                        completevehicleDetails.add(vehicleDescription);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());


                }
            } else {
                Log.e(TAG, "Couldn't connect Json server.");
            }

            return null;
        }
        @Override
        public void onPostExecute(Void result){

            //  Storeing the details obtained from the spinner1 and spinner2 and storing the textview of listviw using CustomList view adapter
          cars=new String[completevehicleDetails.size()];
          HashMap<String,String> hs;
          for(int i=0;i<completevehicleDetails.size();i++){
              hs=(HashMap<String,String>)completevehicleDetails.get(i);
              cars[i]=hs.get("vehicle_make") + " "+hs.get("model")+" Price: "+hs.get("price");
          }
          //Log.e(TAG,"hi");
          CustomAdapter customadapter=new CustomAdapter();
          listview.setAdapter(customadapter);
         listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> car=(HashMap<String,String>)listview.getAdapter().getItem(position);
                String str=car.get("price");
                Log.e(TAG,str);

                // Executing the intent if the layout inflated is in phone mode and passing the hashmap using putExtra method
                if(dualPane!=true){
                    Intent intent=new Intent(getApplicationContext(),VehicleDetailsActivity.class);
                    intent.putExtra("Data",car);
                    startActivity(intent);
                }
                else{
                    // Executing fragment replacement into the container if the layout inflated is in tablet mode and passing the hashmap using bundle object
                   Bundle bundle=new Bundle();
                   bundle.putSerializable("Data",car);
                   DisplayFragment displayfragment=new DisplayFragment();
                   displayfragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,displayfragment).commit();
                }

             }
         });
        }

    }
    // A custom adapter which extends the baseadapter for the listview
    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return cars.length;
        }

        @Override
        public Object getItem(int position) {
            return completevehicleDetails.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=getLayoutInflater().inflate(R.layout.custom_list_view,null);
            TextView textview=view.findViewById(R.id.custom1);
            textview.setText(cars[position]);
            return view;
        }
    }

}