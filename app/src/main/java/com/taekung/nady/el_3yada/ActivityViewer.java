package com.taekung.nady.el_3yada;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

public class ActivityViewer extends AppCompatActivity {

    private Toolbar bar;
    public static RelativeLayout activity_viewer_background;
    public final static String SHARED_PREF_FILE = "myPres";
    public static ActivityViewer main ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_viewer);
        main = this;
        bar = (Toolbar) findViewById(R.id.app_bar);
        bar.setAlpha(.3f);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity_viewer_background = (RelativeLayout)findViewById(R.id.activity_viewer);
        NavDrawerFrag navDraw = (NavDrawerFrag) getSupportFragmentManager().findFragmentById(R.id.nav_draw_frag);
        navDraw.setUp(R.id.nav_draw_frag,(DrawerLayout) findViewById(R.id.drawer_layout), bar);
        
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container ,new PatientFrag()).commit();
    }

    public void storeUsername(String username){
        getSharedPreferences(SHARED_PREF_FILE,MODE_PRIVATE).edit().putString("username",username).commit();
    }

    public String getStoredUsername(){
        return getSharedPreferences(SHARED_PREF_FILE,MODE_PRIVATE).getString("username","");
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if(fm.getBackStackEntryCount() > 0){
            fm.popBackStack();
        }else{
        moveTaskToBack(true);
        }
    }
}
