package com.taekung.nady.el_3yada;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Taekunger on 5/20/2016.
 */

public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ListRow> list;
    private ImageView icon;
    private TextView label;

    public NavDrawerListAdapter(Context context, ArrayList<ListRow> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        v = inflater.inflate(R.layout.nav_list_row,null);
        icon = (ImageView) v.findViewById(R.id.list_icon);
        label = (TextView) v.findViewById(R.id.list_label);

        icon.setImageResource(list.get(position).getIcon());
        label.setText(list.get(position).getLabel());
        return v;
    }
}
