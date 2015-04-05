package com.npes87184.ntuapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dexafree.materialList.cards.SmallImageCard;
import com.dexafree.materialList.controller.OnDismissCallback;
import com.dexafree.materialList.controller.RecyclerItemClickListener;
import com.dexafree.materialList.model.Card;
import com.dexafree.materialList.model.CardItemView;
import com.dexafree.materialList.view.MaterialListView;

/**
 * Created by npes87184 on 2015/4/5.
 */
public class EmergencyFragment extends Fragment {

    private View v;
    public static EmergencyFragment newInstance(int index) {
        EmergencyFragment emergencyFragment = new EmergencyFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("home", index);
        emergencyFragment.setArguments(args);

        return emergencyFragment;
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
        mListView.addOnItemTouchListener(new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(CardItemView view, int position) {
                if(!view.getTag().toString().equals("0")) {
                    String uri = "tel:" + view.getTag().toString().trim();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {

            }
        });

        SmallImageCard card0 = new SmallImageCard(getActivity());
        card0.setDescription(getString(R.string.information_detail));
        //card1.setDrawable(R.drawable.law);
        card0.setTitle(getString(R.string.information));
        card0.setTag("0");
        mListView.add(card0);

        SmallImageCard card1 = new SmallImageCard(getActivity());
        card1.setDescription("(02)3366-2054~60 \n (02)3366-9119");
        //card1.setDrawable(R.drawable.law);
        card1.setTitle(getString(R.string.student_Safety_Division));
        card1.setTag("(02)3366-2054");
        mListView.add(card1);

        SmallImageCard card2 = new SmallImageCard(getActivity());
        card2.setDescription("(02)3366-9110");
        //card2.setDrawable(R.drawable.law);
        card2.setTitle(getString(R.string.campus_Security));
        card2.setTag("(02)3366-9110");
        mListView.add(card2);

        SmallImageCard card3 = new SmallImageCard(getActivity());
        card3.setDescription("(02)2312-3456 ext. 2459(ambulance)\n" +
                "ext. 2264");
        //card3.setDrawable(R.drawable.law);
        card3.setTitle(getString(R.string.NTU_Hospital));
        card3.setTag("(02)2312-3456");
        mListView.add(card3);

        SmallImageCard card4 = new SmallImageCard(getActivity());
        card4.setDescription("(02)2365-9055 ext. 11306~8");
        //card4.setDrawable(R.drawable.law);
        card4.setTitle(getString(R.string.TSGH_Hospital));
        card4.setTag("(02)2365-9055");
        mListView.add(card4);

        SmallImageCard card5 = new SmallImageCard(getActivity());
        card5.setDescription("(02)27-007-995\n(02)2709-3600");
        //card5.setDrawable(R.drawable.law);
        card5.setTitle(getString(R.string.TC_Hospital));
        card5.setTag("(02)27-007-995");
        mListView.add(card5);

        SmallImageCard card6 = new SmallImageCard(getActivity());
        card6.setDescription("(02)2388-9595");
        //card6.setDrawable(R.drawable.law);
        card6.setTitle(getString(R.string.TC_Hospital_He_Ping));
        card6.setTag("(02)2388-9595");
        mListView.add(card6);

        SmallImageCard card7 = new SmallImageCard(getActivity());
        card7.setDescription("(02)3366-9595");
        //card7.setDrawable(R.drawable.law);
        card7.setTitle(getString(R.string.Health_Center));
        card7.setTag("(02)3366-9595");
        mListView.add(card7);

        SmallImageCard card8 = new SmallImageCard(getActivity());
        card8.setDescription("(02)3366-2048~53");
        //card8.setDrawable(R.drawable.law);
        card8.setTitle(getString(R.string.Student_Assistance_Division));
        card8.setTag("(02)3366-2048");
        mListView.add(card8);

        SmallImageCard card9 = new SmallImageCard(getActivity());
        card9.setDescription("(02)3366-2264~8");
        //card9.setDrawable(R.drawable.law);
        card9.setTitle(getString(R.string.Student_Housing_Service_Division));
        card9.setTag("(02)3366-2264");
        mListView.add(card9);

        SmallImageCard card10 = new SmallImageCard(getActivity());
        card10.setDescription("(02)3366-2063~6");
        //card10.setDrawable(R.drawable.law);
        card10.setTitle(getString(R.string.Student_Activity_Division));
        card10.setTag("(02)3366-2063");
        mListView.add(card10);

        SmallImageCard card11 = new SmallImageCard(getActivity());
        card11.setDescription("(02)3366-2181~2");
        //card11.setDrawable(R.drawable.law);
        card11.setTitle(getString(R.string.Student_Counseling_Center));
        card11.setTag("(02)3366-2181");
        mListView.add(card11);

        return v;
    }
}
