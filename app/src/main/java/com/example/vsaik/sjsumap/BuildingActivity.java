package com.example.vsaik.sjsumap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class BuildingActivity extends AppCompatActivity  implements Result,LocationListener{

    protected LocationManager locationmanager;
    protected Location location = null;
    TextView textview1;
    TextView textview2;
    TextView textview3;
    TextView textview4;
    TextView textview5;
    ImageView image;
    String address = null;
    Button button;
    Button button1;
    String building = null;
    String buildingName = null;
    String latitude = null;
    String longitude = null;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);
        overridePendingTransition(0,0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        address  = getIntent().getStringExtra("address");
        building = getIntent().getStringExtra("building");
        buildingName = getIntent().getStringExtra("buildingName");
        latitude= getIntent().getStringExtra("latitude");
        longitude=getIntent().getStringExtra("longitude");
        locationmanager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        String provider=LocationManager.GPS_PROVIDER;
        textview2 = (TextView) findViewById(R.id.buildingName);
        textview3 =(TextView) findViewById(R.id.walking_value);
        textview4 =(TextView) findViewById(R.id.walking_dur_value);
        textview5 =(TextView) findViewById(R.id.DestAddressValue);
        image = (ImageView) findViewById(R.id.buildingImage);
        if(provider!=null & !provider.equals(""))
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET},10);
                return;
            }else{
                location=locationmanager.getLastKnownLocation(provider);
                locationmanager.requestLocationUpdates(provider,0,0,this);
                if(location!=null)
                {
                    onLocationChanged(location);
                }

            }

        }
        button1=(Button) findViewById(R.id.mapButton);
        button = (Button) findViewById(R.id.streetViewButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                    final Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("google.streetview:cbll="+latitude+","+longitude+"&cbp=1,99.56,,1,-5.27&mz=21"));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
                    final Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr=" + address));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        if(location!=null)
        {
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+location.getLatitude()+","+location.getLongitude()+"&destinations="+address+"&mode=driving&language=fr-FR&avoid=tolls";
            new DistanceResponse(BuildingActivity.this).execute(url);
            String url1 = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+location.getLatitude()+","+location.getLongitude()+"&destinations="+address+"&mode=walking&language=fr-FR&avoid=tolls";
            new DistanceResponse(BuildingActivity.this).execute(url1);
            onLocationChanged(location);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Latitude",""+location.getLatitude());
        Log.d("Longitude",""+location.getLongitude());
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+location.getLatitude()+","+location.getLongitude()+"&destinations="+address+"&mode=driving&language=fr-FR&avoid=tolls";
        new DistanceResponse(BuildingActivity.this).execute(url);
        String url1 = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+location.getLatitude()+","+location.getLongitude()+"&destinations="+address+"&mode=walking&language=fr-FR&avoid=tolls";
        new DistanceResponse(BuildingActivity.this).execute(url1);
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }
    @Override
    public void onProviderEnabled(String s) {
    }
    @Override
    public void onProviderDisabled(String s) {
    }
    Double durDriv = 0.0;
    int disDriv=0;
    @Override
    public void setResult(List<String> result) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        String res[]=result.get(0).split(",");
        if(!res[2].equals("") && res[2] != null) {

            textview2.setText(buildingName);
            textview3.setText(Integer.parseInt(res[1])/1000 + " km");
            Double durWalk = Double.parseDouble(res[0]) / 60;
            textview4.setText(df.format(durWalk) + "");
            textview5.setText(address);
            if(building != null && building.equals("1")){
                image.setImageResource(R.drawable.sjsulib);
            }else if(building != null && building.equals("2")) {
                image.setImageResource(R.drawable.sjsueng);
            }else if(building != null && building.equals("3")) {
                image.setImageResource(R.drawable.sjsuyhall);
            }else if(building != null && building.equals("4")) {
                image.setImageResource(R.drawable.sjsusu);
            }else if(building != null && building.equals("5")) {
                image.setImageResource(R.drawable.sjsubbbc);
            }else if(building != null && building.equals("6")) {
                image.setImageResource(R.drawable.sjsusouthgarage);
                //}
            }
        }

    }

}
