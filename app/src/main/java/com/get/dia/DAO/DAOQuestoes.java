package com.get.dia.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.get.dia.objetos.Questoes;

public class DAOQuestoes {

    private SQLiteDatabase bd;
    public DAOQuestoes(SQLiteDatabase conexao){
        bd = conexao;
    }

    public Questoes getDados(){
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM Questoes ;");
            Cursor resultado = bd.rawQuery(sql.toString(), null);
            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
            }
            Questoes obj = new Questoes();
            obj.setIdQuestao(resultado.getInt(resultado.getColumnIndexOrThrow("idQuestoes")));
            obj.setDia(resultado.getString(resultado.getColumnIndexOrThrow("dia")));
            obj.setQuestao(resultado.getString(resultado.getColumnIndexOrThrow("questao")));
            obj.setUsuario(resultado.getInt(resultado.getColumnIndexOrThrow("usuario")));
            return obj;
        }catch(Exception e){
            System.out.println("Error BD "+e.getMessage());
            return null;
        }
    }
    public boolean incluir(Questoes obj){

        ContentValues valores = new ContentValues();
        valores.put("idQuestoes",obj.getIdQuestao());
        valores.put("dia",obj.getDia());
        valores.put("questao",obj.getIdQuestao());
        valores.put("usuario",obj.getUsuario());
        try {
            bd.insertOrThrow("Questoes", null, valores);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean atualizar(Questoes obj){
        ContentValues valores = new ContentValues();
        valores.put("idQuestoes",obj.getIdQuestao());
        valores.put("dia",obj.getDia());
        valores.put("questao",obj.getIdQuestao());
        valores.put("usuario",obj.getUsuario());
        try {
            String[] parametros = new String[1];
            parametros[0] = ""+obj.getIdQuestao();
            bd.update("Questoes",valores,"idQuestoes = ?",parametros);
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
            bd.delete("Questoes","IdQuestoes = ?",parametros);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}



