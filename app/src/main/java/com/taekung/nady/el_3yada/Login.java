package com.taekung.nady.el_3yada;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private FloatingActionButton fab;
    public static Login main ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        main = this;
        if(!getStoredUsername().isEmpty()){
            startActivity(new Intent(this,ActivityViewer.class));
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container,new LoginFrag()).commit();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar
                        .make(view, "You are about to create new user,confirm!", Snackbar.LENGTH_LONG)
                        .setAction("CREATE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // code for creating new user
                                fab.setVisibility(View.INVISIBLE);
                                getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container,new ProfileFrag()).commit();
                            }
                        });

                // Changing message text color
                snackbar.setActionTextColor(Color.GREEN);

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.CYAN);

                snackbar.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        android.support.v4.app.Fragment f = getSupportFragmentManager().findFragmentById(R.id.login_fragment_container);
        if(f instanceof LoginFrag){
            super.onBackPressed();
        }else{
            fab.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.login_fragment_container,new LoginFrag()).commit();
        }
    }

    public void storeUsername(String username){
        getSharedPreferences(ActivityViewer.SHARED_PREF_FILE,MODE_PRIVATE).edit().putString("username",username.trim()).commit();
    }

    public String getStoredUsername(){
        return getSharedPreferences(ActivityViewer.SHARED_PREF_FILE,MODE_PRIVATE).getString("username","").trim();
    }
}
