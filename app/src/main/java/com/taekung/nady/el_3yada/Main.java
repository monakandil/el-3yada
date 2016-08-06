package com.taekung.nady.el_3yada;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity implements SearchView.OnQueryTextListener , SearchView.OnCloseListener{

    private Toolbar bar;
    private SearchManager searchManager;
    private SearchView searchView;
    private ExpandableListView myList;
    private MyCustomExpandableListAdapter listAdapter;
    private ArrayList<ParentRow> parentList = new ArrayList<>();
    private ArrayList<ParentRow> showTheseParentList = new ArrayList<>();
    private Spinner spinner;
    Database db;
    public static Main main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this);
        main = this;
        bar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        spinner = (Spinner) findViewById(R.id.spinner);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        parentList = new ArrayList<>();
        showTheseParentList = new ArrayList<>();

        List<String> list = new ArrayList<String>();
        list.add("Name");
        list.add("Email");
        list.add("Tel");
        list.add("Cost");
        list.add("Date");
        list.add("Time");
        list.add("Disease");
        list.add("Medication");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :
                        MyCustomExpandableListAdapter.SEARCH_TYPE = Database.PATIENT_COL_NAME;
                        break;
                    case 1 :
                        MyCustomExpandableListAdapter.SEARCH_TYPE = Database.PATIENT_COL_EMAIL;
                        break;
                    case 2 :
                        MyCustomExpandableListAdapter.SEARCH_TYPE = Database.PATIENT_COL_TEL;
                        break;
                    case 3 :
                        MyCustomExpandableListAdapter.SEARCH_TYPE = Database.PATIENT_COL_COST;
                        break;
                    case 4 :
                        MyCustomExpandableListAdapter.SEARCH_TYPE = Database.PATIENT_COL_DATE_OF_ARRIVAL;
                        break;
                    case 5 :
                        MyCustomExpandableListAdapter.SEARCH_TYPE = Database.PATIENT_COL_TIME_OF_ARRIVAL;
                        break;
                    case 6 :
                        MyCustomExpandableListAdapter.SEARCH_TYPE = Database.PATIENT_COL_DISEASE;
                        break;
                    case 7 :
                        MyCustomExpandableListAdapter.SEARCH_TYPE = Database.PATIENT_COL_MEDICATION;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        displayList();

        expandAll();
    }


    private void loadData(String query){
        ArrayList<ListRow> childRows = new ArrayList<>();
        ParentRow parentRow = null;
        ArrayList<Patient> patients = db.searchALlPatientBy(getStoredUsername(),Database.PATIENT_COL_NAME,query);
        for(Patient p : patients){
            childRows.add(new ListRow(R.drawable.patient_icom , p.getName() ,0));
        }

        parentRow = new ParentRow("Name Filter" , childRows);
        parentList.add(parentRow);
    }

    private void expandAll (){
        int count = listAdapter.getGroupCount();
        for (int i  = 0; i < count ; i++){
            myList.expandGroup(i);
        }
    }

    private void displayList(){
        loadData("");
        listAdapter = new MyCustomExpandableListAdapter(this , parentList);
        myList = (ExpandableListView) findViewById(R.id.exband_list_search);
        myList.setAdapter(listAdapter);
     }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.app_bar_menu,menu);

        searchView = (SearchView)menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnCloseListener(this);
        searchView.setOnQueryTextListener(this);
        searchView.requestFocus();

        return true;
    }

    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.filterData(newText);
        expandAll();
        return false;
    }

    public String getStoredUsername(){
        return getSharedPreferences(ActivityViewer.SHARED_PREF_FILE,MODE_PRIVATE).getString("username","");
    }
}
