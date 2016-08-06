package com.taekung.nady.el_3yada;

/**
 * Created by Taekunger on 5/20/2016.
 */
public class ListRow {
    private int icon;
    private String label;
    private int id;
    public final static int DRAWER_PROFILE = 1;
    public final static int DRAWER_BROWSE_ALL = 2;
    public final static int DRAWER_NEW_PATIENT = 3;
    public final static int DRAWER_LOGOUT = 4;
    public final static int DRAWER_SEARCH = 5;

    public ListRow(int icon, String label, int id) {
        this.icon = icon;
        this.label = label;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
