package com.example.canberra.businesscardsaver;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mark on 1/04/2017.
 */

public class BusinessCardAdapter extends ArrayAdapter<BusinessCard> {

    ArrayList<BusinessCard> cards;

    public BusinessCardAdapter(Context context, int  resource, ArrayList<BusinessCard> objects) {
        super(context, resource, objects);
        cards = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_listview_item, parent, false);
        }

        BusinessCard card = cards.get(position);

        ImageView icon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
        icon.setImageResource(R.mipmap.ic_business_card);

        TextView t = (TextView) convertView.findViewById(R.id.textViewTitleView);
        t.setText(card.getName());

        if (position % 2 == 0) {
            convertView.setBackgroundColor(Color.parseColor("#ccffff"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#ffffe6"));
        }

        return convertView;
    }
}
