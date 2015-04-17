package com.npes87184.ntuapp;

import java.util.ArrayList;

/**
 * Created by npes87184 on 2015/4/4.
 */
public class DataClass {
    private static  DataClass mDataClass;
    public ArrayList<String> notifi = new ArrayList<String>(3);
    public ArrayList<String> link = new ArrayList<String>(3);
    public ArrayList<String> activity = new ArrayList<String>(3);
    public String [][] events = new String[13][32];

    private DataClass() {
        for(int i=1;i<13;i++) {
            for(int j=1;j<32;j++) {
                events[i][j] = "0";
            }
        }
    }

    public static  DataClass getInstance() {
        if(null==mDataClass) {
            mDataClass = new DataClass();
        }
        return mDataClass;
    }
}
