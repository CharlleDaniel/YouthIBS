package com.youthibs.entidades;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Charlle Daniel on 29/12/2015.
 */
public class Aviso implements Comparable<Aviso> {
    private String title;
    private String text;
    private List<Long> idParticipantes;
    private byte[] foto;
    private long id;
    private Date data;

    public Aviso(){
        data= new Date();
        idParticipantes= new LinkedList<>();
    }

    public Date getData() {
        return data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isParticipante(long id) {
        for(Long i :idParticipantes){
            if(i.longValue()==id){
                return true;
            }
        }

        return false;
    }

    public void setParticipante(long id) {
        if(isParticipante(id)){
            this.idParticipantes.remove(id);
        }else{
            this.idParticipantes.add(id);
        }

    }
    public int getQtdParticipantes(){
        return idParticipantes.size();
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[]foto) {

        this.foto=foto;


    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int compareTo(Aviso another) {
        if(another.getId()==id){
            return 1;
        }
        return -1;
    }
}
