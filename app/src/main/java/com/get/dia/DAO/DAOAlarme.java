package com.get.dia.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.get.dia.MainActivity;
import com.get.dia.objetos.Alarme;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

public class DAOAlarme {

    private SQLiteDatabase bd;
    public DAOAlarme(SQLiteDatabase conexao){
        bd = conexao;
    }

    public ArrayList<Alarme> getDados(Context c){
        try {
            ArrayList<Alarme> alarmes = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM alarme");
            Cursor resultado = bd.rawQuery(sql.toString(), null);
            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
            }
            int i = 0;
            while(i<resultado.getCount()){
                Alarme obj = new Alarme();
                obj.setIdAlarme(resultado.getInt(resultado.getColumnIndexOrThrow("idAlarme")));
                obj.setDatahora(resultado.getString(resultado.getColumnIndexOrThrow("datahora")));
                obj.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow("descricao")));
                obj.setUsuario(resultado.getInt(resultado.getColumnIndexOrThrow("usuario")));
                alarmes.add(obj);
                resultado.moveToNext();
                i++;
                System.out.println("BD: "+obj.getDatahora());
            }
            return alarmes;
        }catch(Exception e){
            System.out.println("Error BD "+e.getMessage());
            return null;
        }
    }
    public boolean incluir(Alarme obj){

        ContentValues valores = new ContentValues();
        valores.put("idAlarme",obj.getIdAlarme());
        valores.put("datahora",obj.getDatahora());
        valores.put("descricao",obj.getDescricao());
        valores.put("usuario",obj.getUsuario());
        try {
            bd.insertOrThrow("Alarme", null, valores);
            System.out.println("INCLUIDO");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean atualizar(Alarme obj){
        ContentValues valores = new ContentValues();
        valores.put("idAlarme",obj.getIdAlarme());
        valores.put("datahora",obj.getDatahora());
        valores.put("descricao",obj.getDescricao());
        valores.put("usuario",obj.getUsuario());
        try {
            String[] parametros = new String[1];
            parametros[0] = ""+obj.getIdAlarme();
            bd.update("Alarme",valores,"idAlarme = ?",parametros);
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
            bd.delete("Alarme","IdAlarme = ?",parametros);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}


