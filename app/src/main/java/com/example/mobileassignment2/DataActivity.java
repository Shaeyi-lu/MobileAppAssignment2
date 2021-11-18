package com.example.mobileassignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DataActivity extends AppCompatActivity {

    EditText lati, longi, addr, id;
    Button delete, update, view;
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
        view = findViewById(R.id.view);

        DB = new DBHelper(this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting inputted values as string
                String idValue = id.getText().toString();
                String latitude = lati.getText().toString();
                String address = addr.getText().toString();
                String longitude = longi.getText().toString();

                //converting it to Double
                Double parseLati = Double.parseDouble(latitude);
                Double parseLongi = Double.parseDouble(longitude);

                Boolean checkUpdateData = DB.updateData(idValue, address, parseLati, parseLongi);//updates database with edited fields

                if(checkUpdateData==true){
                    Toast.makeText(DataActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();//successful
                }
                else{
                    Toast.makeText(DataActivity.this, "New Entry not Updated", Toast.LENGTH_SHORT).show();//not successful
                }
            }

        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idValue = id.getText().toString();

                Boolean checkDeleteData = DB.deleteData(idValue);//deletes data

                if(checkDeleteData==true){
                    Toast.makeText(DataActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show(); //successful
                }
                else{
                    Toast.makeText(DataActivity.this, "New Entry not Deleted", Toast.LENGTH_SHORT).show(); //not successful
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getData();
                if (res.getCount() == 0) {
                    Toast.makeText(DataActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    //displays results of view data as a pop up
                    buffer.append("Address :" + res.getString(1) + "\n");
                    buffer.append("Latitude :" + res.getString(2) + "\n");
                    buffer.append("Longitude :" + res.getString(3) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(DataActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
            });


    }
}