package com.get.dia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DadosSQL extends SQLiteOpenHelper {
    /**
     * Construtor para iniciar o banco de dados
     * @param context Contexto do aplicativo
     */
    public DadosSQL(Context context){
        super(context,"DadosDia",null,11);
    }

    /**
     * Método para criar o banco de dados do app
     * @param db Um parametro para executar os comandos SQL
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            System.out.println("CREATED AND LIONEL");
            db.execSQL(ScriptSQL.createTableUsuario());
            db.execSQL(ScriptSQL.addUserPadrao());
            db.execSQL(ScriptSQL.createTableAlarme());
            db.execSQL(ScriptSQL.createTableHorario());
            db.execSQL(ScriptSQL.createTableAgenda());
            db.execSQL(ScriptSQL.createTableTarefas());
            db.execSQL(ScriptSQL.createTableTrabalhos());
            db.execSQL(ScriptSQL.createTableHistorico());
            db.execSQL(ScriptSQL.createTableFeedback());
            db.execSQL(ScriptSQL.createTableLogs());
            db.execSQL(ScriptSQL.createTableQuestoes());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Atualizar o banco de dados em tempo de execução ou por outros metódos
     * @param db Parametro para executar codigos SQL
     * @param oldVersion Versão antiga do BD
     * @param newVersion Versão nova do BD
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Usado para atualizar o BD em determinados casos
    }
}
