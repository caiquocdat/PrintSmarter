package com.example.printmaster.model;

import java.util.ArrayList;
import java.util.List;

public class CompatibleModel {
    private int id;
    private String type;
    private ArrayList<NameCompatibleModel> listName;

    public CompatibleModel(int id, String type, ArrayList<NameCompatibleModel> listName) {
        this.id = id;
        this.type = type;
        this.listName = listName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<NameCompatibleModel> getListName() {
        return listName;
    }

    public void setListName(ArrayList<NameCompatibleModel> listName) {
        this.listName = listName;
    }
}
