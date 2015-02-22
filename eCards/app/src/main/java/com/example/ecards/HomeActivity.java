package com.example.ecards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Lenovo-USER on 2/22/2015.
 */
public class HomeActivity extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null)
            return null;

        RelativeLayout rl = (RelativeLayout)inflater.inflate(R.layout.activity_main, container, false);
        LinearLayout linearLayout = (LinearLayout)rl.findViewById(R.id.contact_card_container);
        LinearLayout contactCard = (LinearLayout)inflater.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard2 = (LinearLayout)inflater.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard3 = (LinearLayout)inflater.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard4 = (LinearLayout)inflater.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard5 = (LinearLayout)inflater.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard6 = (LinearLayout)inflater.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard7 = (LinearLayout)inflater.inflate(R.layout.contact_card_layout, null);
        LinearLayout contactCard8 = (LinearLayout)inflater.inflate(R.layout.contact_card_layout, null);

        linearLayout.addView(contactCard);
        linearLayout.addView(contactCard2);
        linearLayout.addView(contactCard3);
        linearLayout.addView(contactCard4);
        linearLayout.addView(contactCard5);
        linearLayout.addView(contactCard6);
        linearLayout.addView(contactCard7);
        linearLayout.addView(contactCard8);

        return rl;

    }
}
