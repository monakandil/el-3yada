package com.taekung.nady.el_3yada;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Taekunger on 5/22/2016.
 */
public class AllPatientListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Patient> list;

    public AllPatientListAdapter(Context context, ArrayList<Patient> list) {
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

    private TextView patient_list_layout_name ,patient_list_layout_tel , patient_list_layout_date , patient_list_layout_time;
    @Override
    public View getView(int position, View v, ViewGroup parent) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        v = inflater.inflate(R.layout.all_patient_list_layout,null);

        patient_list_layout_name = (TextView) v.findViewById(R.id.patient_list_layout_name);
        patient_list_layout_tel = (TextView) v.findViewById(R.id.patient_list_layout_tel);
        patient_list_layout_date = (TextView) v.findViewById(R.id.patient_list_layout_date);
        patient_list_layout_time = (TextView) v.findViewById(R.id.patient_list_layout_time);

        patient_list_layout_name.setText(list.get(position).getName());
        patient_list_layout_tel.setText(list.get(position).getTel());
        patient_list_layout_date.setText(list.get(position).getDate_of_arrival());
        patient_list_layout_time.setText(list.get(position).getTime_of_arrival());

        return v;
    }
}
