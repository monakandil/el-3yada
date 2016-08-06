package com.taekung.nady.el_3yada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Taekunger on 5/21/2016.
 */
public class MyCustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ParentRow> parentRowList;
    private ArrayList<ParentRow> originalList;
    public static String SEARCH_TYPE =  Database.PATIENT_COL_NAME;
    Database db;

    public MyCustomExpandableListAdapter(Context context, ArrayList<ParentRow> originalList) {
        this.context = context;
        this.parentRowList = new ArrayList<>();
        this.parentRowList.addAll(originalList);
        this.originalList = new ArrayList<>();
        this.originalList.addAll(originalList);
        db = new Database(context);
    }

    @Override
    public int getGroupCount() {
        return this.parentRowList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.parentRowList.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.parentRowList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.parentRowList.get(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentRow parentRow = (ParentRow) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_row,null);
        }
        TextView heading = (TextView) convertView.findViewById(R.id.parent_text);
        heading.setText(parentRow.getName().trim());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ListRow childRow = (ListRow) getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_row,null);
        }

        final TextView childText = (TextView) convertView.findViewById(R.id.child_text);
        childText.setText(childRow.getLabel().trim());

        final View finalConvertView = convertView;
        childText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // you can do any thing here (eg. navigate to another activity , change the icon , do whwt you want)
                Toast.makeText(finalConvertView.getContext(), childText.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // helper method for doing search
    public void filterData(String query){
        parentRowList.clear();
        loadData(query);
//        if(query.isEmpty()){
//            parentRowList.addAll(originalList);
//        }else {
//            for (ParentRow parentRow : originalList){
//                ArrayList<ListRow> childList  = parentRow.getChildList();
//                ArrayList<ListRow> newList = new ArrayList<>();
//
//                for (ListRow listRow : childList){
//                    if (listRow.getLabel().toLowerCase().contains(query)) {
//                        newList.add(listRow);
//                    }
//                }
//
//                if(newList.size() > 0){
//                    ParentRow nParentRow  = new ParentRow(parentRow.getName() , newList);
//                    parentRowList.add(nParentRow);
//                }
//            }
//        }
        notifyDataSetChanged();
    }

    private void loadData(String query){
        ArrayList<ListRow> childRows = new ArrayList<>();
        ParentRow parentRow = null;
        ArrayList<Patient> patients;
        switch(SEARCH_TYPE){
            case Database.PATIENT_COL_NAME:
                patients = db.searchALlPatientBy(Main.main.getStoredUsername(),Database.PATIENT_COL_NAME,query);
                for(Patient p : patients){
                    childRows.add(new ListRow(R.drawable.patient_icom , p.getName() ,0));
                }

                parentRow = new ParentRow("Name Filter" , childRows);
                parentRowList.add(parentRow);
                break;
            case Database.PATIENT_COL_EMAIL:
                patients = db.searchALlPatientBy(Main.main.getStoredUsername(),Database.PATIENT_COL_EMAIL,query);
                for(Patient p : patients){
                    childRows.add(new ListRow(R.drawable.patient_icom , p.getName() ,0));
                }

                parentRow = new ParentRow("Email Filter" , childRows);
                parentRowList.add(parentRow);
                break;
            case Database.PATIENT_COL_TEL:
                patients = db.searchALlPatientBy(Main.main.getStoredUsername(),Database.PATIENT_COL_TEL,query);
                for(Patient p : patients){
                    childRows.add(new ListRow(R.drawable.patient_icom , p.getName() ,0));
                }

                parentRow = new ParentRow("Telephone Filter" , childRows);
                parentRowList.add(parentRow);
                break;
            case Database.PATIENT_COL_DATE_OF_ARRIVAL:
                patients = db.searchALlPatientBy(Main.main.getStoredUsername(),Database.PATIENT_COL_DATE_OF_ARRIVAL,query);
                for(Patient p : patients){
                    childRows.add(new ListRow(R.drawable.patient_icom , p.getName() ,0));
                }

                parentRow = new ParentRow("Date Filter" , childRows);
                parentRowList.add(parentRow);
                break;
            case Database.PATIENT_COL_TIME_OF_ARRIVAL:
               patients = db.searchALlPatientBy(Main.main.getStoredUsername(),Database.PATIENT_COL_TIME_OF_ARRIVAL,query);
                for(Patient p : patients){
                    childRows.add(new ListRow(R.drawable.patient_icom , p.getName() ,0));
                }

                parentRow = new ParentRow("Time Filter" , childRows);
                parentRowList.add(parentRow);
                break;
            case Database.PATIENT_COL_DISEASE:
                patients = db.searchALlPatientBy(Main.main.getStoredUsername(),Database.PATIENT_COL_DISEASE,query);
                for(Patient p : patients){
                    childRows.add(new ListRow(R.drawable.patient_icom , p.getName() ,0));
                }

                parentRow = new ParentRow("Disease Filter" , childRows);
                parentRowList.add(parentRow);
                break;
            case Database.PATIENT_COL_MEDICATION:
                 patients = db.searchALlPatientBy(Main.main.getStoredUsername(),Database.PATIENT_COL_MEDICATION,query);
                for(Patient p : patients){
                    childRows.add(new ListRow(R.drawable.patient_icom , p.getName() ,0));
                }

                parentRow = new ParentRow("Medication Filter" , childRows);
                parentRowList.add(parentRow);
                break;
            case Database.PATIENT_COL_COST:
                patients = db.searchALlPatientBy(Main.main.getStoredUsername(),Database.PATIENT_COL_COST,query);
                for(Patient p : patients){
                    childRows.add(new ListRow(R.drawable.patient_icom , p.getName() ,0));
                }

                parentRow = new ParentRow("Cost Filter" , childRows);
                parentRowList.add(parentRow);
                break;
        }
    }
}
