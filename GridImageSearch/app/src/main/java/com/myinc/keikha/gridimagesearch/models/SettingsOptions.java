package com.myinc.keikha.gridimagesearch.models;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by keikha on 2/1/15.
 */
public class SettingsOptions implements Serializable{

    public String size ="";
    public String color ="";
    public String type="";
    public String site="";

    @Override
    public String toString() {
        return "size: "+size+", color: "+color+", type: "+type+", site:"+site;
    }
}
