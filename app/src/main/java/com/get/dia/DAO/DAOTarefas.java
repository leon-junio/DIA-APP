package com.get.dia.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.get.dia.objetos.Tarefas;

public class DAOTarefas {

    private SQLiteDatabase bd;
    public DAOTarefas(SQLiteDatabase conexao){
        bd = conexao;
    }

    public Tarefas getDados(){
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM Tarefas ;");
            Cursor resultado = bd.rawQuery(sql.toString(), null);
            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
            }
            Tarefas obj = new Tarefas();
            obj.setIdTarefa(resultado.getInt(resultado.getColumnIndexOrThrow("idTarefa")));
            obj.setAlarme(resultado.getInt(resultado.getColumnIndexOrThrow("alarme")));
            obj.setDataHora(resultado.getString(resultado.getColumnIndexOrThrow("datahora")));
            obj.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow("descricao")));
            obj.setIcone(resultado.getString(resultado.getColumnIndexOrThrow("icone")));
            obj.setNomeTarefa(resultado.getString(resultado.getColumnIndexOrThrow("nometarefa")));
            return obj;
        }catch(Exception e){
            System.out.println("Error BD "+e.getMessage());
            return null;
        }
    }
    public boolean incluir(Tarefas obj){

        ContentValues valores = new ContentValues();
        valores.put("idTarefa",obj.getIdTarefa());
        valores.put("alarme",obj.getAlarme());
        valores.put("datahora",obj.getDataHora());
        valores.put("descricao",obj.getDescricao());
        valores.put("icone",obj.getIcone());
        valores.put("nometarefa",obj.getNomeTarefa());
        try {
            bd.insertOrThrow("Tarefas", null, valores);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean atualizar(Tarefas obj){
        ContentValues valores = new ContentValues();
        valores.put("idTarefa",obj.getIdTarefa());
        valores.put("alarme",obj.getAlarme());
        valores.put("datahora",obj.getDataHora());
        valores.put("descricao",obj.getDescricao());
        valores.put("icone",obj.getIcone());
        valores.put("nometarefa",obj.getNomeTarefa());
        try {
            String[] parametros = new String[1];
            parametros[0] = ""+obj.getIdTarefa();
            bd.update("Tarefas",valores,"idTarefa = ?",parametros);
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
            bd.delete("Tarefas","IdTarefa = ?",parametros);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}


