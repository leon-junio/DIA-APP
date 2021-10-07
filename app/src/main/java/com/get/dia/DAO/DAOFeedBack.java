package com.get.dia.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.get.dia.objetos.FeedBack;

public class DAOFeedBack {

    private SQLiteDatabase bd;
    public DAOFeedBack(SQLiteDatabase conexao){
        bd = conexao;
    }

    public FeedBack getDados(){
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM feedback;");
            Cursor resultado = bd.rawQuery(sql.toString(), null);
            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
            }
            FeedBack obj = new FeedBack();
            obj.setIdFeed(resultado.getInt(resultado.getColumnIndexOrThrow("idfeed")));
            obj.setDia(resultado.getString(resultado.getColumnIndexOrThrow("dia")));
            obj.setComentario(resultado.getString(resultado.getColumnIndexOrThrow("comentario")));
            obj.setUsuario(resultado.getInt(resultado.getColumnIndexOrThrow("usuario")));
            obj.setEmail(resultado.getString(resultado.getColumnIndexOrThrow("email")));
            return obj;
        }catch(Exception e){
            System.out.println("Error BD "+e.getMessage());
            return null;
        }
    }
    public boolean incluir(FeedBack obj){

        ContentValues valores = new ContentValues();
        valores.put("idfeed",obj.getIdFeed());
        valores.put("dia",obj.getDia());
        valores.put("email",obj.getEmail());
        valores.put("usuario",obj.getUsuario());
        valores.put("comentario",obj.getComentario());
        try {
            bd.insertOrThrow("feedback", null, valores);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean atualizar(FeedBack obj){
        ContentValues valores = new ContentValues();
        valores.put("idfeed",obj.getIdFeed());
        valores.put("dia",obj.getDia());
        valores.put("email",obj.getEmail());
        valores.put("usuario",obj.getUsuario());
        valores.put("comentario",obj.getComentario());
        try {
            String[] parametros = new String[1];
            parametros[0] = ""+obj.getIdFeed();
            bd.update("feedback",valores,"idfeed = ?",parametros);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    public boolean excluir(int id){
        try {
            String[] parametros = new String[1];
            parametros[0] = ""+id;
            bd.delete("feedback","Idfeed = ?",parametros);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}



