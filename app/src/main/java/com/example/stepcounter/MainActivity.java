package com.example.stepcounter;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.example.stepcounter.databinding.ActivityMainBinding;

//import com.example.stepcounter.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
        private SensorManager mSensorManager;
        private Sensor mSensor;
        private String HelloData;
        private TextView mTextView;
        private boolean isSensorPresent;

        private SensorEventListener mSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                mTextView.setText("Heart Rate:  " + String.valueOf(event.values[0]));
                HelloData = (String) String.valueOf(event.values[0]);
                if(!HelloData.contains("0.0")){
                    postDataUsingVolley(HelloData);
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                //Nothing to do
            }
        };
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mTextView = (TextView)findViewById(R.id.text);
            mSensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
            if(mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
                mSensor = mSensorManager.getDefaultSensor(69680);
                isSensorPresent = true;
            } else {
                isSensorPresent = false;
            }
        }
        private void postDataUsingVolley(String ranData){
              String url = "api usr";
//
             RequestQueue queue = Volley.newRequestQueue(this);
             JSONObject postData = new JSONObject();
            // double ranData = Math.random();
            //Toast.makeText(this, (int) ranData, Toast.LENGTH_SHORT).show();

             try {
                 postData.put("data",  ranData);

             }catch (JSONException e){
                 e.printStackTrace();
             }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            queue.add(jsonObjectRequest);

//            StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    // inside on response method we are
//                    // hiding our progress bar
//                    // and setting data to edit text as empty
//
//
//                    // on below line we are displaying a success toast message.
//                    Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
//                    try {
//                        // on below line we are passing our response
//                        // to json object to extract data from it.
//                        JSONObject respObj = new JSONObject(response);
//
//                        // below are the strings which we
//                        // extract from our json object.
//                        String name = respObj.getString("name");
//                        String job = respObj.getString("job");
//
//                        // on below line we are setting this string s to our text view.
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }, new com.android.volley.Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    // method to handle errors.
//                    Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() {
//                    // below line we are creating a map for
//                    // storing our values in key and value pair.
//                    Map<String, String> params = new HashMap<String, String>();
//
//
//
//                    // at last we are
//                    // returning our params.
//                    return params;
//                }
//            };
//            // below line is to make
//            // a json object request.
//            queue.add(request);
        }



        @Override
        protected void onResume() {
            super.onResume();
            mSensorManager.registerListener(mSensorListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        protected void onPause() {
            super.onPause();
            mSensorManager.unregisterListener(mSensorListener);
        }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}