package com.youthibs.Control;

import android.content.Context;

import com.youthibs.database.BD;
import com.youthibs.entidades.Aviso;
import com.youthibs.entidades.Usuario;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Charlle Daniel on 29/12/2015.
 */
public class YouthControl {
    private BD bd;
    private Context context;
    private List<Aviso> avisos;
    private Usuario usuarioLogado;

    public YouthControl(Context context) {
        this.bd= new BD(context);
        this.context = context;
        avisos= new LinkedList<>();
    }

    public void addAviso(Aviso a){
        List<Aviso>temp= new LinkedList<>();
        String txtTitle=a.getTitle();
        a.setTitle(upCaseAllFirstChar(txtTitle));
        temp.add(a);
        temp.addAll(avisos);
        avisos=temp;
    }
    public List<Aviso> getAvisos(){
        return avisos;
    }

    public void removeAviso(Aviso a ){
        avisos.remove(a);
    }
    public void login(Usuario usuarioLogado){
        boolean test= false;
        List<Usuario>temp= bd.buscarUsuario();
        if (temp.size() > 0) {
            for(Usuario u : temp) {
                if (u.getNome().equalsIgnoreCase(usuarioLogado.getNome())) {
                    this.usuarioLogado = u;
                    u.setIsLog("true");
                    bd.atualizarUsuario(u);
                    test = true;
                }
            }
        }
        if(test!=true) {
            usuarioLogado.setIsLog("true");
            bd.inserirUsuario(usuarioLogado);
            temp = bd.buscarUsuario();
            for (Usuario u : temp) {
                if (u.getNome().equalsIgnoreCase(usuarioLogado.getNome())) {
                    this.usuarioLogado = u;
                }
            }
        }


    }

    public Usuario getUsuarioLogado(){
        List<Usuario>temp= bd.buscarUsuario();
        if (temp.size() > 0) {
            for(Usuario u : temp) {
                if(u.isLog().equalsIgnoreCase("true")){
                    this.usuarioLogado = u;
                }
            }
        }
        return this.usuarioLogado;
    }
    //Text
    public String upCaseAllFirstChar(String txt){
        String temp="";
        Pattern p = Pattern.compile("[a-zA-Zà-úÀ-Ú]+");
        Matcher m = p.matcher(txt);
        boolean test = m.find();
        while (test==true){
            String parte = m.group();
            temp=temp+(parte.substring(0,1).toUpperCase())+(parte.substring(1, parte.length()))+" ";
            test = m.find();
        }
        return temp;
    }
}
