package com.get.dia.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.get.dia.objetos.Log;

public class DAOLog {

    private SQLiteDatabase bd;
    public DAOLog(SQLiteDatabase conexao){
        bd = conexao;
    }

    public Log getDados(){
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM logregister;");
            Cursor resultado = bd.rawQuery(sql.toString(), null);
            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
            }
            Log obj = new Log();
            obj.setIdlog(resultado.getInt(resultado.getColumnIndexOrThrow("idlog")));
            obj.setDia(resultado.getString(resultado.getColumnIndexOrThrow("dia")));
            obj.setLogText(resultado.getString(resultado.getColumnIndexOrThrow("logtext")));
            return obj;
        }catch(Exception e){
            System.out.println("Error BD "+e.getMessage());
            return null;
        }
    }
    public boolean incluir(Log obj){

        ContentValues valores = new ContentValues();
        valores.put("idlog",obj.getIdlog());
        valores.put("dia",obj.getDia());
        valores.put("logtext",obj.getLogText());
        try {
            bd.insertOrThrow("logregister", null, valores);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean atualizar(Log obj){
        ContentValues valores = new ContentValues();
        valores.put("idlog",obj.getIdlog());
        valores.put("dia",obj.getDia());
        valores.put("logtext",obj.getLogText());
        try {
            String[] parametros = new String[1];
            parametros[0] = ""+obj.getIdlog();
            bd.update("logregister",valores,"idlog = ?",parametros);
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
            bd.delete("logregister","Idlog = ?",parametros);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}



