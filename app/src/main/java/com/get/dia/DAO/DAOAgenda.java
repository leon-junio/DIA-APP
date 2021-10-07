package com.get.dia.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.get.dia.objetos.Agenda;
import com.get.dia.objetos.Alarme;

import java.util.ArrayList;

public class DAOAgenda {

    private SQLiteDatabase bd;
    public DAOAgenda(SQLiteDatabase conexao){
        bd = conexao;
    }

    public ArrayList<Agenda> getDados(){
        try {
            ArrayList<Agenda> list = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM Agenda;");
            Cursor resultado = bd.rawQuery(sql.toString(), null);
            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
            }
            int i = 0;
            while(i<resultado.getCount()) {
                Agenda obj = new Agenda();
                obj.setIdAgenda(resultado.getInt(resultado.getColumnIndexOrThrow("idAgenda")));
                obj.setAlarme(resultado.getInt(resultado.getColumnIndexOrThrow("alarme")));
                obj.setData(resultado.getString(resultado.getColumnIndexOrThrow("datahora")));
                obj.setHora(resultado.getString(resultado.getColumnIndexOrThrow("hora")));
                obj.setHorario(resultado.getInt(resultado.getColumnIndexOrThrow("horario")));
                obj.setNotificacao(resultado.getString(resultado.getColumnIndexOrThrow("notificacao")));
                list.add(obj);
                resultado.moveToNext();
                i++;
            }
            return list;
        }catch(Exception e){
            System.out.println("Error BD "+e.getMessage());
            return null;
        }
    }

    public ArrayList<Agenda> getEventosDoDia(String dia){
        try {
            ArrayList<Agenda> list = new ArrayList<>();
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM Agenda " +
                    "where datahora like '"+dia+"';");
            Cursor resultado = bd.rawQuery(sql.toString(), null);
            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
            }
            int i = 0;
            while(i<resultado.getCount()) {
                Agenda obj = new Agenda();
                obj.setIdAgenda(resultado.getInt(resultado.getColumnIndexOrThrow("idAgenda")));
                obj.setAlarme(resultado.getInt(resultado.getColumnIndexOrThrow("alarme")));
                obj.setData(resultado.getString(resultado.getColumnIndexOrThrow("datahora")));
                obj.setHora(resultado.getString(resultado.getColumnIndexOrThrow("hora")));
                obj.setHorario(resultado.getInt(resultado.getColumnIndexOrThrow("horario")));
                obj.setNotificacao(resultado.getString(resultado.getColumnIndexOrThrow("notificacao")));
                list.add(obj);
                resultado.moveToNext();
                i++;
            }
            return list;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error BD "+e.getMessage());
            return null;
        }
    }

    public boolean incluir(Agenda obj){

        ContentValues valores = new ContentValues();
        valores.put("idAgenda",obj.getIdAgenda());
        valores.put("alarme",obj.getAlarme());
        valores.put("datahora",obj.getData());
        valores.put("hora",obj.getHora());
        valores.put("notificacao",obj.getNotificacao());
        valores.put("horario",obj.getHorario());
        try {
            bd.insertOrThrow("Agenda", null, valores);
            System.out.println("INCLUIU MENOR");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean atualizar(Agenda obj){
        ContentValues valores = new ContentValues();
        valores.put("idAgenda",obj.getIdAgenda());
        valores.put("alarme",obj.getAlarme());
        valores.put("datahora",obj.getData());
        valores.put("hora",obj.getHora());
        valores.put("notificacao",obj.getNotificacao());
        valores.put("horario",obj.getHorario());
        try {
            String[] parametros = new String[1];
            parametros[0] = ""+obj.getIdAgenda();
            bd.update("Agenda",valores,"idAgenda = ?",parametros);
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
            bd.delete("Agenda","idAgenda = ?",parametros);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}


