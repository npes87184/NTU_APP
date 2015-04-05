package com.npes87184.ntuapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dexafree.materialList.cards.SmallImageCard;
import com.dexafree.materialList.view.MaterialListView;

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

        SmallImageCard card = new SmallImageCard(getActivity());
        card.setDescription("hello");
        card.setTitle("world");
        card.setDrawable(R.drawable.ic_launcher);

        mListView.add(card);
        return v;
    }
}
