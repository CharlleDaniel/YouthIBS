package com.youthibs.Control;

import android.content.Context;

import com.youthibs.entidades.Aviso;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Charlle Daniel on 29/12/2015.
 */
public class YouthControl {
    private Context context;
    private List<Aviso> avisos;

    public YouthControl(Context context) {
        this.context = context;
        avisos= new LinkedList<>();
    }

    public void addAviso(Aviso a){
        avisos.add(a);
    }
    public List<Aviso> getAvisos(){
        return avisos;
    }
}
