package com.example.vsaik.sjsumap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;


public class MapActivity extends AppCompatActivity implements LocationListener {

    static double a = 360 - 53.35;
    static double cos = Math.cos(a);
    static double sin = Math.sin(a);
    public Location location = null;

    public Button button1;
    public Button button2;
    public Button button3;
    public Button button4;
    public Button button5;
    public Button button6;
    public AddressData addressData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Set<String> places = AddressData.hMap.keySet();
        String[] array = places.toArray(new String[0]);

        AutoCompleteTextView actv;
        actv = (AutoCompleteTextView) findViewById(R.id.searchEditText);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        actv.setAdapter(adapter);

        addressData = new AddressData();
        showMyLocation();


        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BuildingActivity.class);
                i.putExtra("address", addressData.getAddressList().get(0));
                i.putExtra("building", "1");
                i.putExtra("buildingName", "King Library");
                startActivity(i);
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BuildingActivity.class);
                i.putExtra("address", addressData.getAddressList().get(1));
                i.putExtra("building", "2");
                i.putExtra("buildingName", "Engineering Building");
                startActivity(i);

            }
        });
        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BuildingActivity.class);
                i.putExtra("address", addressData.getAddressList().get(2));
                i.putExtra("building", "3");
                i.putExtra("buildingName", "Yoshihiro Uchida Hall");
                startActivity(i);
            }
        });
        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BuildingActivity.class);
                i.putExtra("address", addressData.getAddressList().get(3));
                i.putExtra("building", "4");
                i.putExtra("buildingName", "Student Union");
                startActivity(i);
            }
        });
        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BuildingActivity.class);
                i.putExtra("address", addressData.getAddressList().get(4));
                i.putExtra("building", "5");
                i.putExtra("buildingName", "Boccardo Business Complex");
                startActivity(i);
            }
        });
        button6 = (Button) findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BuildingActivity.class);
                i.putExtra("address", addressData.getAddressList().get(5));
                i.putExtra("building", "6");
                i.putExtra("buildingName", "South Parking Garage");
                startActivity(i);
            }
        });

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView name1 = (AutoCompleteTextView) findViewById(R.id.searchEditText);
                String name = name1.getText().toString();
                Loc loc = null;
                if (AddressData.hMap.containsKey(name.toUpperCase()))
                    loc = AddressData.hMap.get(name.toUpperCase());
                //if (loc != null)
                    //showPin(loc.x, loc.y);
            }
        });

       // ImageView pin = (ImageView) findViewById(R.id.pin);
       /* pin.setX(330);
        pin.setY(1060);*/
       // pin.setX(500);
       // pin.setY(1400);
      //  pin.setBackgroundResource(R.drawable.pin);

    }



    private void showPin(int x,int y){
      //  ImageView pin = (ImageView) findViewById(R.id.pin);
        //pin.setX(x);
        //pin.setY(y);
      //  pin.setBackgroundResource(R.drawable.pin);

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
             int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMyLocation(){

        LocationManager locationmanager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        String provider=LocationManager.GPS_PROVIDER;

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
                    if((checkInCollege(location)))
                        transformCoordinates(location.getLatitude(),location.getLongitude());
                    else
                        Toast.makeText(getApplicationContext(),"out of college",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"location not found",Toast.LENGTH_LONG ).show();
                }
            }

        }
        else {
            Toast.makeText(getApplicationContext(), "Provider is null", Toast.LENGTH_LONG).show();
        }

    }
    //transformCoordinates(37.334359, -121.882999);

    private boolean checkInCollege(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        if( lat > 37.331319 && lat < 37.340363) {
            if(lon > -121.886259 && lon < -121.876238)
                return true;
        }
        return false;

    }



    private void transformCoordinates(double y,double x){
        double tranfX = ( x*cos + y*sin) ;
        double tranfY = (-x*sin + y*cos) ;

        if( x > -121.87785){
            tranfX += 76.32320;
        }
        else if( x > -121.87980){
            tranfX += 76.32320;
        }
        else{
            tranfX += 76.32320;
        }

        //if(y > 37.33550) {
        tranfY += 102.09841;

        //}
        /*else if( y > 37.33250) {
            tranfY += 102.09751;
        }
        else{
            tranfY += 102.09708;
        }*/

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;



        System.out.println("transX : "+round(tranfX,4) +"::transY : "+
                round(tranfY,4) );

        float xi = (float)(tranfX*1000000/8462)*1200;
        float yi = (float)(tranfY*1000000/6043)*900;


        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.RED);
        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        drawable.setCornerRadius(50);
        drawable.setSize(50,50);
        GradientDrawable drawable2 = new GradientDrawable();
        drawable2.setColor(Color.parseColor("#30ff0000"));
        drawable2.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        drawable2.setCornerRadius(300);
        drawable2.setSize(300,300);

        ImageView locationTrans = (ImageView) findViewById(R.id.locationtrans);
        ImageView locationPoint = (ImageView) findViewById(R.id.location);
        if(xi < 0)
            xi *= -1;
        if(yi < 0)
            yi*= -1;
        float xCoord = yi + 80   ;
        float yCoord = 1580 - xi ;
        locationPoint.setX(xCoord );
        locationPoint.setY(yCoord);
        locationTrans.setX(xCoord-125);
        locationTrans.setY(yCoord-125);
        locationTrans.setBackground(drawable2);
        locationPoint.setBackground(drawable);
    }

    public static double round(double value, int places) {
        if(value < 0)
            value = value*-1;
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);


        return bd.doubleValue();
    }
    @Override
    public void onProviderEnabled(String s) {
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        if(location!=null)
        {

        }
        else{
            Toast.makeText(getApplicationContext(),"location not found", Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Latitude",""+location.getLatitude());
        Log.d("Longitude",""+location.getLongitude());
        if((checkInCollege(location)))
            transformCoordinates(location.getLatitude(),location.getLongitude());

    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }
    @Override
    public void onProviderDisabled(String s) {
    }

}
