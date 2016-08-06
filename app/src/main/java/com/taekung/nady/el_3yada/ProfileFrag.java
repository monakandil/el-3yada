package com.taekung.nady.el_3yada;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFrag extends Fragment {


    public ProfileFrag() {
        // Required empty public constructor
        if( ActivityViewer.activity_viewer_background != null){
            ActivityViewer.activity_viewer_background.setBackgroundResource(R.drawable.bg1);
        }
    }


    private EditText profile_name_txt , profile_email_txt, profile_password_txt;
    private TextView name_vtxt,email_vtxt;
    private Button profile_update_btn;
    private ImageView profile_image;
    private Switch show_hide_password;
    private Database db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        db = new Database(getActivity());

        profile_name_txt = (EditText) v.findViewById(R.id.profile_name_txt);
        profile_email_txt = (EditText) v.findViewById(R.id.profile_email_txt);
        profile_password_txt = (EditText) v.findViewById(R.id.profile_password_txt);
        profile_image = (ImageView) v.findViewById(R.id.nav_profile_image);
        name_vtxt = (TextView) v.findViewById(R.id.name_vtxt);
        email_vtxt = (TextView) v.findViewById(R.id.email_vtxt);
        show_hide_password = (Switch) v.findViewById(R.id.show_hide_password);
        profile_update_btn = (Button) v.findViewById(R.id.profile_update_btn);

        addListenersForEditTexts();
        if(getActivity() instanceof ActivityViewer){
            loadUserData();
        }
        doButtonAction();
        return v;
    }

    private void doButtonAction() {
        profile_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof Login){
                    doRegisterUser();
                }else{
                    doUpdateUser();
                }
            }
        });
    }

    private void loadUserData() {
        User user = db.getUser(ActivityViewer.main.getStoredUsername());
        if(user != null) {
            profile_email_txt.setText(user.getUsername());
            profile_name_txt.setText(user.getName());
            profile_password_txt.setText(user.getPass());
            profile_image.setImageBitmap(user.getAvatar());
            name_vtxt.setText(user.getName());
            email_vtxt.setText(user.getUsername());
        }
    }

    private void doRegisterUser() {
        String email = profile_email_txt.getText().toString();
        String name = profile_name_txt.getText().toString();
        String password = profile_password_txt.getText().toString();
        Bitmap image =  ((BitmapDrawable) profile_image.getDrawable()).getBitmap();

        if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
            if(!db.checkUserExists(profile_email_txt.getText().toString())){
                if(db.insertUser(email, password ,name ,image)){
                    Login.main.storeUsername(email);
                    startActivity(new Intent(getActivity(),ActivityViewer.class));
                    Toast.makeText(getActivity(), "User Created Successfully", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity(), "User already exists", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(), "Complete missing data!", Toast.LENGTH_SHORT).show();
        }

    }
     private void doUpdateUser() {
         String email = profile_email_txt.getText().toString();
         String name = profile_name_txt.getText().toString();
         String password = profile_password_txt.getText().toString();
         Bitmap image =  ((BitmapDrawable) profile_image.getDrawable()).getBitmap();
         if(db.updateUser(ActivityViewer.main.getStoredUsername(),email,password ,name, image)){
             ActivityViewer.main.storeUsername(email);
             Toast.makeText(getActivity(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
             getActivity().finish();
             startActivity(getActivity().getIntent());
         }else{
             Toast.makeText(getActivity(), "Profile Not Updated", Toast.LENGTH_SHORT).show();
         }
    }

    private void addListenersForEditTexts(){
        profile_name_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name_vtxt.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        profile_email_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email_vtxt.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    profile_password_txt.setTransformationMethod(null);
                }else{
                    profile_password_txt.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }


}
