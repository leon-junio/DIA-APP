package com.get.dia.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.get.dia.objetos.Usuario;

public class DAOUser {

    private SQLiteDatabase bd;
    public DAOUser(SQLiteDatabase conexao){
        bd = conexao;
    }

    public Usuario getDados(){
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM Usuario ;");
            Cursor resultado = bd.rawQuery(sql.toString(), null);
            if (resultado.getCount() > 0) {
                resultado.moveToFirst();
            }
            Usuario user = new Usuario();
            user.setIdUser(resultado.getInt(resultado.getColumnIndexOrThrow("idUser")));
            user.setEscola(resultado.getString(resultado.getColumnIndexOrThrow("escola")));
            user.setInicio(resultado.getInt(resultado.getColumnIndexOrThrow("inicio")));
            user.setIdMateria(resultado.getInt(resultado.getColumnIndexOrThrow("idMateria")));
            user.setNome(resultado.getString(resultado.getColumnIndexOrThrow("nome")));
            return user;
        }catch(Exception e){
            System.out.println("Error BD "+e.getMessage());
            return null;
        }
    }
    public boolean incluir(Usuario user){

        ContentValues valores = new ContentValues();
        valores.put("nome",user.getNome());
        valores.put("idUser",user.getIdUser());
        valores.put("idMateria",user.getIdMateria());
        valores.put("escola",user.getEscola());
        valores.put("inicio",user.getInicio());
        try {
            bd.insertOrThrow("Usuario", null, valores);
            System.out.println("ADICIONADO");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean atualizar(Usuario user){
        ContentValues valores = new ContentValues();
        valores.put("nome",user.getNome());
        valores.put("idUser",user.getIdUser());
        valores.put("idMateria",user.getIdMateria());
        valores.put("escola",user.getEscola());
        valores.put("inicio",user.getInicio());
        try {
            String[] parametros = new String[1];
            parametros[0] = ""+user.getIdUser();
            bd.update("Usuario",valores,"idUser = ?",parametros);
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
            bd.delete("Usuario","IdUser = ?",parametros);
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
