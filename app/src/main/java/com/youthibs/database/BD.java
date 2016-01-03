package com.youthibs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.youthibs.entidades.Usuario;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

//TODO Alterar os nomes dos métodos para manter o padrão do nome das classes e do BD. Ex: buscarInsumoProduto mudar para buscarProdutoInsumo
//TODO Alterar os nomes dos métodos de relações para o mesmo padrão, com relaçnao à plural e singular. Manter consistência de padrões de nomenclaturas
public class BD {
    private SQLiteDatabase bd;

    public BD(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }


    public void inserirUsuario(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("email", usuario.getEmail());
        valores.put("islog", usuario.isLog());

        bd.insert("usuario", null, valores);
    }


    public void atualizarUsuario(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("nome", usuario.getNome());
        valores.put("email", usuario.getEmail());
        valores.put("islog", usuario.isLog());

        bd.update("usuario", valores, "_id = ?", new String[]{"" + usuario.getId()});
    }


    public void deletarUsuario(Usuario usuario) {
        bd.delete("usuario", "_id = " + usuario.getId(), null);
    }


    public List<Usuario> buscarUsuario() {
        List<Usuario> list = new ArrayList<Usuario>();
        String[] colunas = new String[]{"_id", "nome","email","islog"};

        Cursor cursor = bd.query("usuario", colunas, null, null, null, null, "nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {

                Usuario u = new Usuario();
                u.setId(cursor.getInt(0));
                u.setNome(cursor.getString(1));
                u.setEmail(cursor.getString(2));
                u.setIsLog(cursor.getString(3));
                list.add(u);

            } while (cursor.moveToNext());
        }

        return (list);
    }

}