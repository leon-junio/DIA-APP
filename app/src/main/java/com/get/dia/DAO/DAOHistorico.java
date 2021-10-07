package com.get.dia.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.get.dia.objetos.Historico;

public class DAOHistorico {

    private SQLiteDatabase bd;
    public DAOHistorico(SQLiteDatabase conexao){
        bd = conexao;
    }

    public Historico getDados(){
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM Historico;");
            Cursor resultado = bd.rawQuery(sql.toString(), null);
            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
            }
            Historico obj = new Historico();
            obj.setIdHistorico(resultado.getInt(resultado.getColumnIndexOrThrow("idHistorico")));
            obj.setMateria(resultado.getString(resultado.getColumnIndexOrThrow("materia")));
            obj.setDataHora(resultado.getString(resultado.getColumnIndexOrThrow("datahora")));
            obj.setNota(resultado.getDouble(resultado.getColumnIndexOrThrow("nota")));
            obj.setObservacao(resultado.getString(resultado.getColumnIndexOrThrow("observacao")));
            obj.setUsuario(resultado.getInt(resultado.getColumnIndexOrThrow("usuario")));
            return obj;
        }catch(Exception e){
            System.out.println("Error BD "+e.getMessage());
            return null;
        }
    }
    public boolean incluir(Historico obj){

        ContentValues valores = new ContentValues();
        valores.put("idHistorico",obj.getIdHistorico());
        valores.put("nota",obj.getNota());
        valores.put("datahora",obj.getDataHora());
        valores.put("materia",obj.getMateria());
        valores.put("observacao",obj.getObservacao());
        valores.put("usuario",obj.getUsuario());
        try {
            bd.insertOrThrow("Historico", null, valores);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean atualizar(Historico obj){
        ContentValues valores = new ContentValues();
        valores.put("idHistorico",obj.getIdHistorico());
        valores.put("nota",obj.getNota());
        valores.put("datahora",obj.getDataHora());
        valores.put("materia",obj.getMateria());
        valores.put("observacao",obj.getObservacao());
        valores.put("usuario",obj.getUsuario());
        try {
            String[] parametros = new String[1];
            parametros[0] = ""+obj.getIdHistorico();
            bd.update("Historico",valores,"idHistorico = ?",parametros);
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
            bd.delete("Historico","idHistorico = ?",parametros);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}


