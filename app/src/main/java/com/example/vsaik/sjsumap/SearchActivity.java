package com.example.vsaik.sjsumap;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Loc buildingCoordinates = null;
        Intent received = getIntent();
        String name = received.getStringExtra("name");
        TextView searchBar = (TextView)findViewById(R.id.buildingName);
        searchBar.setText(name);
        searchBar.setTextSize(35);
        if(AddressData.hMap.containsKey(name)){
            buildingCoordinates = AddressData.hMap.get(name);
        }

        if(buildingCoordinates != null) {
            ImageView pin = (ImageView) findViewById(R.id.pin);
            pin.setX(buildingCoordinates.x);
            pin.setY(buildingCoordinates.y);
            // To do download a pin
            pin.setBackgroundResource(R.drawable.pin);
        }


    }

}
