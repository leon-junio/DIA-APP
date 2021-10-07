package com.get.dia.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.get.dia.objetos.Trabalhos;

public class DAOTrabalhos {

    private SQLiteDatabase bd;
    public DAOTrabalhos(SQLiteDatabase conexao){
        bd = conexao;
    }

    public Trabalhos getDados(){
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM Trabalhos ;");
            Cursor resultado = bd.rawQuery(sql.toString(), null);
            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
            }
            Trabalhos obj = new Trabalhos();
            obj.setIdTrabalho(resultado.getInt(resultado.getColumnIndexOrThrow("idTrabalho")));
            obj.setAlarme(resultado.getInt(resultado.getColumnIndexOrThrow("alarme")));
            obj.setDatahora(resultado.getString(resultado.getColumnIndexOrThrow("datahora")));
            obj.setDescricao(resultado.getString(resultado.getColumnIndexOrThrow("descricao")));
            obj.setIcone(resultado.getString(resultado.getColumnIndexOrThrow("icone")));
            obj.setMateria(resultado.getString(resultado.getColumnIndexOrThrow("materia")));
            obj.setNomeTrabalho(resultado.getString(resultado.getColumnIndexOrThrow("nometrabalho")));
            return obj;
        }catch(Exception e){
            System.out.println("Error BD "+e.getMessage());
            return null;
        }
    }
    public boolean incluir(Trabalhos obj){

        ContentValues valores = new ContentValues();
        valores.put("idTrabalho",obj.getIdTrabalho());
        valores.put("alarme",obj.getAlarme());
        valores.put("datahora",obj.getDatahora());
        valores.put("descricao",obj.getDescricao());
        valores.put("icone",obj.getIcone());
        valores.put("materia",obj.getMateria());
        valores.put("nometrabalho",obj.getNomeTrabalho());
        try {
            bd.insertOrThrow("Trabalhos", null, valores);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean atualizar(Trabalhos obj){
        ContentValues valores = new ContentValues();
        valores.put("idTrabalho",obj.getIdTrabalho());
        valores.put("alarme",obj.getAlarme());
        valores.put("datahora",obj.getDatahora());
        valores.put("descricao",obj.getDescricao());
        valores.put("icone",obj.getIcone());
        valores.put("materia",obj.getMateria());
        valores.put("nometrabalho",obj.getNomeTrabalho());
        try {
            String[] parametros = new String[1];
            parametros[0] = ""+obj.getIdTrabalho();
            bd.update("Trabalhos",valores,"idTrabalho = ?",parametros);
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
            bd.delete("Trabalhos","IdTrabalho = ?",parametros);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}

