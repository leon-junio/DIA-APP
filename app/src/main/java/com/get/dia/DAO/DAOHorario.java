package com.get.dia.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.get.dia.objetos.Horario;

public class DAOHorario {

    private SQLiteDatabase bd;
    public DAOHorario(SQLiteDatabase conexao){
        bd = conexao;
    }

    public Horario getDados(){
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM Horario;");
            Cursor resultado = bd.rawQuery(sql.toString(), null);
            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
            }
            Horario obj = new Horario();
            obj.setIdHorario(resultado.getInt(resultado.getColumnIndexOrThrow("idHorario")));
            obj.setDia(resultado.getString(resultado.getColumnIndexOrThrow("dia")));
            obj.setHora(resultado.getString(resultado.getColumnIndexOrThrow("hora")));
            obj.setMateria(resultado.getString(resultado.getColumnIndexOrThrow("materia")));
            obj.setProfessor(resultado.getString(resultado.getColumnIndexOrThrow("professor")));
            return obj;
        }catch(Exception e){
            System.out.println("Error BD "+e.getMessage());
            return null;
        }
    }
    public boolean incluir(Horario obj){

        ContentValues valores = new ContentValues();
        valores.put("idTarefa",obj.getIdHorario());
        valores.put("dia",obj.getDia());
        valores.put("hora",obj.getHora());
        valores.put("materia",obj.getMateria());
        valores.put("professor",obj.getProfessor());
        try {
            bd.insertOrThrow("Horario", null, valores);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean atualizar(Horario obj){
        ContentValues valores = new ContentValues();
        valores.put("idTarefa",obj.getIdHorario());
        valores.put("dia",obj.getDia());
        valores.put("hora",obj.getHora());
        valores.put("materia",obj.getMateria());
        valores.put("professor",obj.getProfessor());
        try {
            String[] parametros = new String[1];
            parametros[0] = ""+obj.getIdHorario();
            bd.update("Horario",valores,"idHorario = ?",parametros);
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
            bd.delete("Horario","IdHorario = ?",parametros);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}


