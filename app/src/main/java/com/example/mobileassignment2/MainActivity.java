package com.example.mobileassignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DBHelper database;
    EditText longitude;
    EditText latitude;
    EditText id;
    Button button;
    Button viewData;
    TextView result;
    Geocoder geocoder;


    String addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.lat);
        longitude = findViewById(R.id.longitude);
        button = findViewById(R.id.button);
        viewData = findViewById(R.id.data);
        result = findViewById(R.id.result);
        id = findViewById(R.id.idMain);

        result.setVisibility(View.GONE);


        geocoder = new Geocoder(this);
        boolean parse = true;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turns input views to strings
                String latitudeVal = latitude.getText().toString();
                String longitudeVal = longitude.getText().toString();
                String idVal = id.getText().toString();

                //converts it to Double
                Double lati = Double.parseDouble(latitudeVal);
                Double longi = Double.parseDouble(longitudeVal);

                List<Address> geoResult = findGeocoder(lati, longi); //calls method created to initialize results
                if(geoResult != null){
                    DBHelper DB = new DBHelper(MainActivity.this);//object of database

                   addr = geoResult.get(0).getAddressLine(0); //gets the address as  string
                   DB.addData(idVal, addr, lati, longi);//adds data to the database


                }

                result.setVisibility(View.VISIBLE); //displays result once button clicked
                result.setText("Address: " + addr );

            }
        });
        
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DataActivity.class); //goes to next page
                startActivity(intent);
                finish();
            }
        });



    }

    private List<Address> findGeocoder(Double latitude, Double longitude){


        List<Address> address = null;
        try{
            address = geocoder.getFromLocation(latitude, longitude, 1); //created object of geocode and obtained latitude and longitude using location services
        }
        catch (IOException e){
            e.printStackTrace();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return address;
    }
}