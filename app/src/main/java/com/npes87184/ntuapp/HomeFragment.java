package com.npes87184.ntuapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dexafree.materialList.cards.SmallImageCard;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.view.MaterialListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CancellationException;

/**
 * Created by npes87184 on 2015/4/5.
 */
public class HomeFragment extends android.support.v4.app.Fragment {

    private View v;
    public static HomeFragment newInstance(int index) {
        HomeFragment homeFragment = new HomeFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("home", index);
        homeFragment.setArguments(args);

        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        v = inflater.inflate(R.layout.fragment_home, container, false);
        MaterialListView mListView = (MaterialListView) v.findViewById(R.id.material_listview);
        mListView.setOnDismissCallback(new OnDismissCallback() {
            @Override
            public void onDismiss(Card card, int position) {
                // Do whatever you want here

            }
        });

        PositiveEnergy.getInstance().init(getActivity());

        final SimpleDateFormat dayFormatter = new SimpleDateFormat("dd");
        final SimpleDateFormat MMFormatter = new SimpleDateFormat("MM");
        final SimpleDateFormat HHFormatter = new SimpleDateFormat("HH");

        Date current = new Date();

        int day = Integer.parseInt(dayFormatter.format(current));
        int mm = Integer.parseInt(MMFormatter.format(current));
        int hh = Integer.parseInt(HHFormatter.format(current));

        SmallImageCard today = new SmallImageCard(getActivity());
        if(hh<=12) {
            today.setDescription(getResources().getString(R.string.today_detail) +" "+getResources().getString(R.string.morning)+" " + String.valueOf(mm) + "/" + String.valueOf(day));
        } else if(hh>12 && hh <=18) {
            today.setDescription(getResources().getString(R.string.today_detail)+" " + getResources().getString(R.string.afternoon) +" "+ String.valueOf(mm) + "/" + String.valueOf(day));
        } else {
            today.setDescription(getResources().getString(R.string.today_detail)+" " + getResources().getString(R.string.evening) +" "+ String.valueOf(mm) + "/" + String.valueOf(day));
        }
        today.setTitle(getResources().getString(R.string.today));
        today.setTag(CardType.Today);
        mListView.add(today);

        SmallImageCard positive_energy = new SmallImageCard(getActivity());
        positive_energy.setDismissible(true);
        positive_energy.setDescription(PositiveEnergy.getInstance().getPositiveEnergy(mm, day));
        positive_energy.setTitle(getResources().getString(R.string.positive_energy));
        positive_energy.setTag(CardType.Positive_Energy);
        mListView.add(positive_energy);

        if(!Nights.getInstance().getNights(mm, day).equals("0")) {
            SmallImageCard activity = new SmallImageCard(getActivity());
            activity.setDismissible(true);
            activity.setDescription(Nights.getInstance().getNights(mm, day));
            activity.setTitle(getResources().getString(R.string.activity));
            activity.setTag(CardType.Activity);
            mListView.add(activity);
        }

        return v;
    }
}
