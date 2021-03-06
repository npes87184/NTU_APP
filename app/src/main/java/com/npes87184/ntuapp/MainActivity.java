package com.npes87184.ntuapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //in first execute need init
        DataClass.getInstance();
        Calendars.getInstance().init(this);

        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = CM.getActiveNetworkInfo();
        if((info != null) && info.isConnected()) {
            new DataFetch().execute(DataType.Notification);
            new DataFetch().execute(DataType.Activity);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        if(0==position) {
            mTitle = getString(R.string.title_section1);
            restoreActionBar();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance(position + 1))
                    .commit();
        } else if(1==position) {
            mTitle = getString(R.string.title_section2);
            restoreActionBar();

            ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = CM.getActiveNetworkInfo();
            if(!((info != null) && info.isConnected())) {
                Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
        } else if(2==position) {
            mTitle = getString(R.string.title_section3);
            restoreActionBar();

            CaldroidFragment caldroidFragment = new CaldroidFragment();
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            caldroidFragment.setArguments(args);
            final SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");
            final SimpleDateFormat MMFormatter = new SimpleDateFormat("MM");
            final SimpleDateFormat backgroundFormatter = new SimpleDateFormat("yyyy MM dd");

            // let the date of weekend be red
            for(int mm=1;mm<13;mm++) {
                for(int day=1;day<31;day++) {
                    try {
                        String target = "2015 " + String.valueOf(mm) + " " + String.valueOf(day);
                        Date redDate = backgroundFormatter.parse(target);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(redDate);
                        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
                        if(weekday==7 || weekday==1) {
                            caldroidFragment.setTextColorForDate(R.color.red, redDate);
                        }
                        if(!Calendars.getInstance().getEvents(mm, day).equals("0")) {
                            if(Calendars.getInstance().getType(mm, day).equals("假")) {
                                caldroidFragment.setTextColorForDate(R.color.red, redDate);
                            } else {
                                caldroidFragment.setTextColorForDate(R.color.green, redDate);
                            }
                        }
                    } catch (ParseException e) {

                    }
                }
            }

            final CaldroidListener listener = new CaldroidListener() {
                @Override
                public void onSelectDate(Date date, View view) {
                    int day = Integer.parseInt(dayFormatter.format(date));
                    int mm = Integer.parseInt(MMFormatter.format(date));
                    if(!Calendars.getInstance().getEvents(mm, day).equals("0")) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(getString(R.string.Detail))
                                .setMessage(Calendars.getInstance().getEvents(mm, day))
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                }
            };

            caldroidFragment.setCaldroidListener(listener);

            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(R.id.container, caldroidFragment);
            t.commit();

        } else if(3==position) {
            //activity
            mTitle = getString(R.string.title_section4);
            restoreActionBar();

            ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = CM.getActiveNetworkInfo();
            if(!((info != null) && info.isConnected())) {
                Toast.makeText(this, R.string.no_internet, Toast.LENGTH_LONG).show();
            }

            CaldroidFragment caldroidFragment = new CaldroidFragment();
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            caldroidFragment.setArguments(args);
            final SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");
            final SimpleDateFormat MMFormatter = new SimpleDateFormat("MM");
            final SimpleDateFormat backgroundFormatter = new SimpleDateFormat("yyyy MM dd");

            // let the date of night be red
            for(int mm=1;mm<13;mm++) {
                for(int day=1;day<31;day++) {
                    if(!DataClass.getInstance().events[mm][day].equals("0")) {
                        try {
                            String target = "2015 " + String.valueOf(mm) + " " + String.valueOf(day);
                            Date purpleDate = backgroundFormatter.parse(target);
                            caldroidFragment.setTextColorForDate(R.color.red, purpleDate);
                        } catch (ParseException e) {

                        }
                    }
                }
            }

            final CaldroidListener listener = new CaldroidListener() {
                @Override
                public void onSelectDate(Date date, View view) {
                    int day = Integer.parseInt(dayFormatter.format(date));
                    int mm = Integer.parseInt(MMFormatter.format(date));
                    if(!DataClass.getInstance().events[mm][day].equals("0")) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(getString(R.string.Detail))
                                .setMessage(DataClass.getInstance().events[mm][day])
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }
                }
            };

            caldroidFragment.setCaldroidListener(listener);

            FragmentTransaction t = getSupportFragmentManager().beginTransaction();
            t.replace(R.id.container, caldroidFragment);
            t.commit();
        } else if(4==position) {
            //share activity
            mTitle = getString(R.string.title_section5);
            restoreActionBar();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, ShareActivityFragment.newInstance(position + 1))
                    .commit();
        } else if(5==position) {
            //emergency
            mTitle = getString(R.string.title_section6);
            restoreActionBar();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, EmergencyFragment.newInstance(position + 1))
                    .commit();
        } else if(6==position) {
            //About
            mTitle = getString(R.string.title_section7);
            restoreActionBar();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, AboutFragment.newInstance(position + 1))
                    .commit();
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;

            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ListView listview = (ListView)rootView.findViewById(R.id.listView);
            ArrayList<String> arr = new ArrayList<String>();

            for(int i=0;i<DataClass.getInstance().notifi.size();i++) {
                arr.add(DataClass.getInstance().notifi.get(i));
            }

            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1,arr);
            listview.setAdapter(adapter);
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3) {
                    String url = DataClass.getInstance().link.get(arg2);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
             });

             return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
