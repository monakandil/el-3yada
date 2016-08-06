package com.taekung.nady.el_3yada;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFrag extends Fragment {

    public PatientFrag() {
        // Required empty public constructor
        if( ActivityViewer.activity_viewer_background != null){
            ActivityViewer.activity_viewer_background.setBackgroundResource(R.drawable.patient_background);
        }
    }

    private EditText patient_name_txt;
    private EditText patient_email_txt;
    private EditText patient_tel_txt;
    private EditText patient_disease_txt;
    private EditText patient_medication_txt;
    private TextView patient_date_txt;
    private TextView patient_time_txt;
    private EditText patient_cost_txt;
    private Button patient_create_btn;
    private Database db;
    private int patientId = -1;
    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        this.patientId = patient.getId();
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_patient, container, false);

        patient_name_txt = (EditText) v.findViewById(R.id.patient_name_txt);
        patient_email_txt = (EditText) v.findViewById(R.id.patient_email_txt);
        patient_tel_txt = (EditText) v.findViewById(R.id.patient_tel_txt);
        patient_disease_txt = (EditText) v.findViewById(R.id.patient_disease_txt);
        patient_medication_txt = (EditText) v.findViewById(R.id.patient_medication_txt);
        patient_date_txt = (TextView) v.findViewById(R.id.patient_date_txt);
        patient_time_txt = (TextView) v.findViewById(R.id.patient_time_txt);
        patient_cost_txt = (EditText) v.findViewById(R.id.patient_cost_txt);
        patient_create_btn = (Button) v.findViewById(R.id.patient_create_btn);
        db = new Database(getActivity());

        addListenerToEditTexts();
        loadPatientData();
        createNewPatient();

        return v;
    }

    private void loadPatientData() {
        if(getPatient() != null){
            patient_name_txt.setText(getPatient().getName());
            patient_tel_txt.setText(getPatient().getTel());
            patient_email_txt.setText(getPatient().getEmail());
            patient_disease_txt.setText(getPatient().getDisease());
            patient_time_txt.setText(getPatient().getTime_of_arrival());
            patient_date_txt.setText(getPatient().getDate_of_arrival());
            patient_medication_txt.setText(getPatient().getMedication());
            patient_cost_txt.setText(getPatient().getCost()+"");
        }
    }

    private void addListenerToEditTexts() {
        patient_date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment d = new DatePickerFragment();
                d.setDateTextView(patient_date_txt);
                d.show(getFragmentManager(), "Date");
            }
        });

        patient_time_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment t = new TimePickerFragment();
                t.setTimeTextView(patient_time_txt);
                t.show(getFragmentManager(), "Time");
            }
        });
    }

    private void createNewPatient() {


        patient_create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = patient_name_txt.getText().toString();
                String email = patient_email_txt.getText().toString();
                String tel = patient_tel_txt.getText().toString();
                String disease = patient_disease_txt.getText().toString();
                String medication = patient_medication_txt.getText().toString();
                String date = patient_date_txt.getText().toString();
                String time = patient_time_txt.getText().toString();
                String cost = patient_cost_txt.getText().toString();
                if (
                        !name.isEmpty() &&
                                !tel.isEmpty() &&
                                !email.isEmpty() &&
                                !date.isEmpty() &&
                                !disease.isEmpty() &&
                                !time.isEmpty() &&
                                !medication.isEmpty() &&
                                !cost.isEmpty()
                        ) {
                    if(!db.checkPatientExists(ActivityViewer.main.getStoredUsername(),email)){
                        if (db.insertPatient(ActivityViewer.main.getStoredUsername(), name, email, tel, date, time, disease, medication, cost)) {
                            Toast.makeText(getActivity(), "Patient Created Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Error Creating Patient", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if(getPatientId() > -1){
                            if(db.updatePatient(ActivityViewer.main.getStoredUsername(),getPatientId(), name, email, tel, date, time, disease, medication, cost)){
                                Toast.makeText(getActivity(), "Patient Updated Successfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(), "Error Updating Patient", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Complete missing data!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
