package com.example.vsaik.sjsumap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vsaik on 10/27/2016.
 */
public class AddressData {

    public List<String> addressList = new ArrayList<String>();

    public static String[] places= {"KING LIBRARY","ENGINEERING BUILDING","YOSHIHIRO UCHIDA HALL"
            ,"STUDENT UNION","BBC","South Parking Garage"};

    public static HashMap<String,Loc> hMap =
            new HashMap<String,Loc>();
    static{
        hMap.put("KING LIBRARY",new Loc(120,500));
        hMap.put("ENGINEERING BUILDING" , new Loc(560,500));
        hMap.put("YOSHIHIRO UCHIDA HALL" , new Loc(100,800));
        hMap.put("STUDENT UNION" , new Loc(560,620));
        hMap.put("BBC" , new Loc(860,730));
        hMap.put("SOUTH PARKING GARAGE" , new Loc(330,1060));
    }

    public AddressData(){
        addressList.add("Dr. Martin Luther King,Jr. Library,150 East San Fernando Street,San Jose, CA 95112");
        addressList.add("San José State University Charles W. Davidson College of Engineering, 1 Washington Square, San Jose, CA 95112");
        addressList.add("Yoshihiro Uchida Hall, San Jose, CA 95112");
        addressList.add("Student Union Building, San Jose, CA 95112");
        addressList.add("Boccardo Business Complex, San Jose, CA 95112");
        addressList.add("San Jose State University South Garage, 330 South 7th Street, San Jose, CA 95112");
    }

    public List<String> getAddressList(){
        return addressList;
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
