package com.abcgeometrie.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.abcgeometrie.R;
import com.abcgeometrie.metier.Gagnant;

/**
 * Created by Yanick on 28/01/2015.
 */
public class ColorArrayAdapter extends ArrayAdapter<Gagnant> {
    private String[] list;
    private int[] colors = new int[] { 0x302970ff, 0x3018a9ff };


    public ColorArrayAdapter(Context context, int textViewResourceId,Gagnant[] gagnants) {
        super(context, textViewResourceId, gagnants);
        list = new String[gagnants.length];
        for (int i = 0; i < list.length; i++)
            list[i] = gagnants[i].toString();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);
        return view;
    }
}