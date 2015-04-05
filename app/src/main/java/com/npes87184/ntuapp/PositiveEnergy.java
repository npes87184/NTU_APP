package com.npes87184.ntuapp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by npes87184 on 2015/4/5.
 */
public class PositiveEnergy {


    private static  PositiveEnergy positiveEnergy;
    private String [][] events = new String[13][32];

    public void init(Context c) {
        for(int i=1;i<13;i++) {
            for(int j=1;j<32;j++) {
                events[i][j] = "0";
            }
        }

        try {
            InputStream abpath = c.getAssets().open("positiveenergy.dat");
            InputStreamReader isr = new InputStreamReader(abpath);
            BufferedReader bReader = new BufferedReader(isr);
            String input;
            while ((input = bReader.readLine()) != null) {
                String[] line = input.split(" ");
                events[Integer.parseInt(line[1])][Integer.parseInt(line[2])] = line[3];
            }
            abpath.close();
        } catch(Exception e) {
            Log.i("error", "bye");
        }
    }

    private PositiveEnergy() {

    }

    public static PositiveEnergy getInstance() {
        if(null==positiveEnergy) {
            positiveEnergy = new PositiveEnergy();
        }
        return positiveEnergy;
    }

    public String getPositiveEnergy(int mm, int day) {
        return events[mm][day];
    }
}
