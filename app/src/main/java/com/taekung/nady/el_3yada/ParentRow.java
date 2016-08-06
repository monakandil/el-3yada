package com.taekung.nady.el_3yada;

import java.util.ArrayList;

/**
 * Created by Taekunger on 5/21/2016.
 */
public class ParentRow {
    private String name;
    private ArrayList<ListRow> childList;

    public ParentRow(String name, ArrayList<ListRow> childList) {
        this.name = name;
        this.childList = childList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ListRow> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<ListRow> childList) {
        this.childList = childList;
    }
}