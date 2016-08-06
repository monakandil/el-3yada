package com.taekung.nady.el_3yada;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFrag extends Fragment {

    private EditText login_username , login_password;
    private Button btnLogin , btnCancel;
    Database db;
    public LoginFrag() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        db = new Database(getActivity());
        login_username = (EditText) v.findViewById(R.id.login_username);
        login_password = (EditText) v.findViewById(R.id.login_password);
        btnLogin = (Button) v.findViewById(R.id.btnLogin);
        btnCancel = (Button) v.findViewById(R.id.btnCancel);

        checkLoginForUser();
        return v;
    }

    private void checkLoginForUser() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = login_username.getText().toString();
                String password = login_password.getText().toString();

                if(!username.isEmpty() && !password.isEmpty()){
                    if(db.checkUserLogin(username,password)){
                        Login.main.storeUsername(username);
                        startActivity(new Intent(getActivity(),ActivityViewer.class));
                        Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Login failed, try again!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Complete missing data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().moveTaskToBack(true);
            }
        });
    }

}
