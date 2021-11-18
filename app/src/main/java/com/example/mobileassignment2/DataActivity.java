package com.example.mobileassignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DataActivity extends AppCompatActivity {

    EditText lati, longi, addr, id;
    Button delete, update;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        lati = findViewById(R.id.latiData);
        longi = findViewById(R.id.longiData);
        addr = findViewById(R.id.addressData);
        id = findViewById(R.id.Id);

        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);

        DB = new DBHelper(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idValue = id.getText().toString();
                String latitude = lati.getText().toString();
                String address = addr.getText().toString();
                String longitude = longi.getText().toString();

                Double parseLati = Double.parseDouble(latitude);
                Double parseLongi = Double.parseDouble(longitude);

                Boolean checkUpdateData = DB.updateData(idValue, address, parseLati, parseLongi);

                if(checkUpdateData==true){
                    Toast.makeText(DataActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(DataActivity.this, "New Entry not Updated", Toast.LENGTH_SHORT).show();
                }
            }

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idValue = id.getText().toString();

                Boolean checkDeleteData = DB.deleteData(idValue);

                if(checkDeleteData==true){
                    Toast.makeText(DataActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(DataActivity.this, "New Entry not Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}