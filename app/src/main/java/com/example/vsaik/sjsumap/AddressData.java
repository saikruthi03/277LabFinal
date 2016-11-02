package com.example.vsaik.sjsumap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vsaik on 10/27/2016.
 */
public class AddressData {

    public List<String> addressList = new ArrayList<String>();
    public List<String> geoLat = new ArrayList<String>();
    public List<String> geoLong = new ArrayList<String>();

    public static String[] places= {"KING LIBRARY","ENGINEERING BUILDING","YOSHIHIRO UCHIDA HALL"
            ,"STUDENT UNION","BBC","South Parking Garage"};

    public static HashMap<String,Loc> hMap =
            new HashMap<String,Loc>();
    static{
        hMap.put("KING LIBRARY",new Loc(150,650));
        hMap.put("ENGINEERING BUILDING" , new Loc(750,720));
        hMap.put("YOSHIHIRO UCHIDA HALL" , new Loc(130,1170));
        hMap.put("STUDENT UNION" , new Loc(750,910));
        hMap.put("BBC" , new Loc(1130,1050));
        hMap.put("SOUTH PARKING GARAGE" , new Loc(500,1500));
    }

    public AddressData(){
        addressList.add("Dr. Martin Luther King,Jr. Library,150 East San Fernando Street,San Jose, CA 95112");
        addressList.add("San José State University Charles W. Davidson College of Engineering, 1 Washington Square, San Jose, CA 95112");
        addressList.add("Yoshihiro Uchida Hall, San Jose, CA 95112");
        addressList.add("Student Union Building, San Jose, CA 95112");
        addressList.add("Boccardo Business Complex, San Jose, CA 95112");
        addressList.add("San Jose State University South Garage, 330 South 7th Street, San Jose, CA 95112");
        geoLat.add("37.260282");
        geoLat.add("37.3351424");
        geoLat.add("37.33377");
        geoLat.add("37.4241967");
        geoLat.add("37.3365611");
        geoLat.add("37.3334738");
        geoLong.add("-121.884999");
        geoLong.add("-121.88127580000003");
        geoLong.add("-121.88338829999998");
        geoLong.add("-122.17093940000001");
        geoLong.add("-121.87872279999999");
        geoLong.add("-121.87991620000003");


    }

    public List<String> getAddressList(){
        return addressList;
    }
    public List<String> getGeoLat(){
        return geoLat;
    }
    public List<String> getGeoLong(){
        return geoLong;
    }
}

class Loc{
    public int x;
    public int y;

    public Loc(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
