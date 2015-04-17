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
public class AboutFragment extends Fragment {

    private View v;
    public static AboutFragment newInstance(int index) {
        AboutFragment aboutFragment = new AboutFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("home", index);
        aboutFragment.setArguments(args);

        return aboutFragment;
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
                if(view.getTag().toString().equals("contact")) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", "npes87184@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.subject));
                    emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.mail_body));
                    startActivity(emailIntent);
                }
            }

            @Override
            public void onItemLongClick(CardItemView view, int position) {

            }
        });

        SmallImageCard claim = new SmallImageCard(getActivity());
        claim.setDescription(getString(R.string.claim_detail));
        claim.setDrawable(R.drawable.law);
        claim.setTitle(getString(R.string.claim));
        claim.setTag("claim");
        mListView.add(claim);

        SmallImageCard library = new SmallImageCard(getActivity());
        library.setDescription("Jsoup, Caldroid, Materiallist and NumberProgressBar");
        library.setTitle("Library");
        library.setTag("library");
        mListView.add(library);

        SmallImageCard contact = new SmallImageCard(getActivity());
        contact.setDescription(getString(R.string.contact_detail));
        contact.setTitle(getString(R.string.contact));
        contact.setDrawable(R.drawable.npes);
        contact.setTag("contact");
        mListView.add(contact);

        return v;
    }
}
