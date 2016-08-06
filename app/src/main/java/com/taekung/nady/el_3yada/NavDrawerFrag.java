package com.taekung.nady.el_3yada;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavDrawerFrag extends Fragment {
    private static final String PREF_FILE_NAME = "Prefs";
    private static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout navDraw;
    private View containerView;
    private boolean userLearnedDrawer;
    private boolean fromSavedInstanceState;
    private TextView nav_email_vtxt , nav_name_vtxt;
    private ImageView nav_profile_image;
    private ListView drawer_list;
    private Database db;

    private ArrayList<ListRow> list;
    private NavDrawerListAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userLearnedDrawer = Boolean.parseBoolean(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "true"));
        if(savedInstanceState != null){
            fromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_nav_drawer, container, false);

        db = new Database(getContext());
        nav_email_vtxt = (TextView) layout.findViewById(R.id.nav_email_vtxt);
        nav_name_vtxt = (TextView) layout.findViewById(R.id.nav_name_vtxt);
        nav_profile_image = (ImageView) layout.findViewById(R.id.nav_profile_image);
        // filling the drawer header with the data of the currently signed in user
        loadUserData();
        // filling a list of option for the drawer list
        list = new ArrayList<>();
        list.add(new ListRow(R.drawable.account_settings , "Update Profile" , ListRow.DRAWER_PROFILE));
        list.add(new ListRow(R.drawable.patient_icom , "New Patient" ,ListRow.DRAWER_NEW_PATIENT));
        list.add(new ListRow(R.drawable.browse_all , "All Patients",ListRow.DRAWER_BROWSE_ALL));
        list.add(new ListRow(R.drawable.nav_search, "Search",ListRow.DRAWER_SEARCH));
        list.add(new ListRow(R.drawable.logout_icon , "Logout" ,ListRow.DRAWER_LOGOUT));
        // setting up the adapter
        adapter = new NavDrawerListAdapter(getContext(),list);
        //initializing the drawer list
        drawer_list = (ListView) layout.findViewById(R.id.nav_draw_list);
        drawer_list.setAdapter(adapter);
        drawer_list.setSelector(R.drawable.selector_list);
        drawer_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                        view.setBackgroundColor(Color.CYAN);
                        new CountDownTimer(50 , 1000){
                            @Override
                            public void onTick(long millisUntilFinished) {}
                            @Override
                            public void onFinish() {changeColor();}
                            public synchronized void changeColor(){view.setBackgroundColor(Color.WHITE);}
                        }.start();

                ListRow row = (ListRow) adapter.getItem(position);
                switch (row.getId()){
                    case ListRow.DRAWER_PROFILE:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFrag()).commit();
                        navDraw.closeDrawer(containerView);
                        break;
                    case ListRow.DRAWER_NEW_PATIENT:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,new PatientFrag()).commit();
                        navDraw.closeDrawer(containerView);
                        break;
                    case ListRow.DRAWER_BROWSE_ALL:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container,new AllPatientFrag()).commit();
                        navDraw.closeDrawer(containerView);
                        break;
                    case ListRow.DRAWER_SEARCH:
                        startActivity(new Intent(getActivity(),Main.class));
                        break;
                    case ListRow.DRAWER_LOGOUT:
                        ActivityViewer.main.storeUsername(null);
                        startActivity(new Intent(getActivity(),Login.class));
                        break;
                }
            }
        });

        return layout;
    }

    public void setUp(int nav_draw_frag, DrawerLayout navDraw, final Toolbar tootlbar) {
        containerView = getActivity().findViewById(nav_draw_frag);
        this.navDraw = navDraw;
        drawerToggle = new ActionBarDrawerToggle(getActivity(), navDraw, tootlbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               if(!userLearnedDrawer){
                   userLearnedDrawer = true;
                   saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,userLearnedDrawer + "");
               }

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }
        };

        if(!userLearnedDrawer && !fromSavedInstanceState){
            this.navDraw.openDrawer(containerView);
        }
        this.navDraw.setDrawerListener(drawerToggle);
        this.navDraw.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String key, String defaultValue){
       return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE).getString(key,defaultValue);
    }

    private void loadUserData() {
        User user = db.getUser(getStoredUsername());
        if(user != null) {

            nav_name_vtxt.setText(user.getName());
            nav_email_vtxt.setText(user.getUsername());
            nav_profile_image.setImageBitmap(user.getAvatar());
        }
    }

    public String getStoredUsername(){
        return getContext().getSharedPreferences(ActivityViewer.SHARED_PREF_FILE,Context.MODE_PRIVATE).getString("username","").trim();
    }
}
