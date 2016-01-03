package com.youthibs.Control;

import android.content.Context;

import com.youthibs.database.BD;
import com.youthibs.entidades.Aviso;
import com.youthibs.entidades.Usuario;

import java.util.LinkedList;
import java.util.List;

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
        avisos.add(a);
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
}
