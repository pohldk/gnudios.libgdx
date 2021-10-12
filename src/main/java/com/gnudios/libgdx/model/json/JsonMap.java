package com.gnudios.libgdx.model.json;

import java.util.ArrayList;


public class JsonMap {

    private ArrayList<JsonBox2dAO> box2dJsonArray;

    public JsonMap() {
        box2dJsonArray = new ArrayList<>();
    }

    public ArrayList<JsonBox2dAO> getBox2dJsonArray() {
        return box2dJsonArray;
    }

    public void setBox2dJsonArray(ArrayList<JsonBox2dAO> box2dJsonArray) {
        this.box2dJsonArray = box2dJsonArray;
    }

}
