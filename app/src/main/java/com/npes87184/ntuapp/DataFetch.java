package com.npes87184.ntuapp;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by npes87184 on 2015/4/4.
 */

enum DataType {
    Notification, News
}

public class DataFetch extends AsyncTask<DataType, Void, Boolean> {

    @Override
    protected Boolean doInBackground(DataType[] parms) {

        // clear old data
        if(parms[0] == DataType.Notification) {
            DataClass.getInstance().notifi.clear();
        } else {
            DataClass.getInstance().news.clear();
        }

        try {
            Document doc = Jsoup.connect("http://www.ntu.edu.tw/").get();
            Elements tableTags = doc.getElementsByAttributeValue("id", "news").select("ul");
            Elements aTags = tableTags.select("a");

            for (int i = 0; i < aTags.size(); i++) {
                if(parms[0] == DataType.Notification) {
                    DataClass.getInstance().notifi.add(aTags.get(i).text());
                } else {
                    DataClass.getInstance().news.add(aTags.get(i+1).text());
                }
            }

        } catch(Exception e) {
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean bool) {

    }
}