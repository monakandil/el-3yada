package com.taekung.nady.el_3yada;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllPatientFrag extends Fragment {


    public AllPatientFrag() {
        // Required empty public constructor
    }


    private ListView all_patient_list;
    private AllPatientListAdapter adapter;
    private Database db;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_patient, container, false);
        db = new Database(getActivity());
        ArrayList<Patient> list = db.getALlPatient(ActivityViewer.main.getStoredUsername());
        adapter = new AllPatientListAdapter(getActivity(),list );
        all_patient_list = (ListView) view.findViewById(R.id.all_patient_list);
        all_patient_list.setAdapter(adapter);
        all_patient_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
               position  = p;
                showDialog();
          }
        });
        if(list.size() == 0){
            Toast.makeText(getActivity(), "No Patients found", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void showDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        AlertDialog alertDialog =  alert.setMessage("Do you want to close the app ?")
                .setCancelable(false)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //updating user data
                        Patient p = (Patient) adapter.getItem(position);
                        PatientFrag pf = new PatientFrag();
                        pf.setPatient(p);
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,pf).commit();
                        dialog.cancel();
                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Patient p = (Patient) adapter.getItem(position);
                        if(db.deletePatient(p.getId())){
                            Toast.makeText(getActivity(), "Patient Deleted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "Patient Not  Deleted", Toast.LENGTH_SHORT).show();
                        }
                        dialog.cancel();
                    }
                }).create();

        alertDialog.setTitle("Delete Or Update");
        alertDialog.show();
    }

}
