package com.example.leedongho.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 김 형민 on 2018-09-07.
 */

public class NewAdapter extends ArrayAdapter<String> {

    NewAdapter(Context context, ArrayList item) {
        super(context, R.layout.newlayout, item);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.newlayout, parent, false);
        String item = getItem(position);
        TextView textView_title = (TextView) view.findViewById(R.id.textView_title);
        textView_title.setText(item);
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}
