package com.npes87184.ntuapp;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by npes87184 on 2015/4/4.
 */

enum DataType {
    Notification, Activity
}

public class DataFetch extends AsyncTask<DataType, Void, Boolean> {

    @Override
    protected Boolean doInBackground(DataType[] params) {

        switch (params[0]) {
            case Notification:
                // clear old data
                DataClass.getInstance().notifi.clear();
                DataClass.getInstance().link.clear();
                try {
                    Document doc = Jsoup.connect("http://www.ntu.edu.tw/").get();
                    Elements tableTags = doc.getElementsByAttributeValue("id", "news").select("ul");
                    Elements aTags = tableTags.select("a");

                    for (int i = 0; i < aTags.size(); i++) {
                        DataClass.getInstance().notifi.add(aTags.get(i).text());
                        DataClass.getInstance().link.add((aTags.get(i).attr("href")));
                    }

                } catch (Exception e) {
                    return false;
                }
                break;
            case Activity:
                //clear old data
                DataClass.getInstance().activity.clear();
                try {
                    Document doc = Jsoup.connect("https://ntu-activities.herokuapp.com/activities").get();
                    Elements pTags = doc.select("p");

                    for (int i = 0; i < pTags.size(); i++) {
                        DataClass.getInstance().activity.add(pTags.get(i).text());
                    }

                } catch (Exception e) {
                    return false;
                }

                for(int i=1;i<13;i++) {
                    for(int j=1;j<32;j++) {
                        DataClass.getInstance().events[i][j] = "0";
                    }
                }

                try {
                    for(String input : DataClass.getInstance().activity) {
                        String[] line = input.split(" ")[0].split("-");

                        //fix activity has space
                        String [] temp_line = input.split(" ");
                        String output = "";
                        for(int i=1;i<temp_line.length;i++) {
                            output = output + " " + temp_line[i];
                        }

                        if(DataClass.getInstance().events[Integer.parseInt(line[1])][Integer.parseInt(line[2])].equals("0")) {
                            DataClass.getInstance().events[Integer.parseInt(line[1])][Integer.parseInt(line[2])] = output;
                        } else {
                            DataClass.getInstance().events[Integer.parseInt(line[1])][Integer.parseInt(line[2])] = DataClass.getInstance().events[Integer.parseInt(line[1])][Integer.parseInt(line[2])] + ", " + output;
                        }
                    }
                } catch(Exception e) {
                    Log.i("error", "bye");
                }

                break;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean bool) {

    }
}