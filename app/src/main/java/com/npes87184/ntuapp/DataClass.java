package com.npes87184.ntuapp;

import java.util.ArrayList;

/**
 * Created by npes87184 on 2015/4/4.
 */
public class DataClass {
    private static  DataClass mDataClass;
    public ArrayList<String> notifi = new ArrayList<String>(3);
    public ArrayList<String> news = new ArrayList<String>(3);

    private DataClass() {

    }

    public static  DataClass getInstance() {
        if(null==mDataClass) {
            mDataClass = new DataClass();
        }
        return mDataClass;
    }
}
