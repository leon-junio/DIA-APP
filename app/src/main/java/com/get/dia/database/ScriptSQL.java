package com.get.dia.database;

public class ScriptSQL {
    /**
     * Criar tabela Usuario do BD
     *
     * @return um script sql para executar no banco de dados
     */
    protected static String createTableUsuario() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table Usuario (idUser int not null default ('0'),");
        sql.append("nome varchar(150) not null default (''),");
        sql.append("inicio int not null default ('0'),");
        sql.append("escola varchar(150) not null default (''),");
        sql.append("idMateria int not null default ('0'),");
        sql.append("primary key (idUser));");
        return sql.toString();
    }

    /**
     * Adicionar um user padrão na tabela usuario do BD
     *
     * @return um script sql para executar no banco de dados
     */
    protected static String addUserPadrao() {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into Usuario (idUser,nome,escola,inicio,idMateria)");
        sql.append("values(1,'','',1,1);");
        return sql.toString();
    }

    /**
     * Criar tabela Alarme do BD
     *
     * @return um script sql para executar no banco de dados
     */
    protected static String createTableAlarme() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table Alarme(idAlarme int not null,");
        sql.append("descricao varchar(150) not null default(''),");
        sql.append("datahora varchar(150) not null default(''),");
        sql.append("usuario int not null,");
        sql.append("primary key (idAlarme),");
        sql.append("foreign key(usuario) references Usuario(idUser));");
        return sql.toString();
    }

    /**
     * Criar tabela Tarefas do BD
     *
     * @return um script sql para executar no banco de dados
     */
    protected static String createTableTarefas() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table Tarefas(idTarefa int not null ,");
        sql.append("nometarefa varchar(60) not null default(''),");
        sql.append("descricao varchar(150) not null default(''),");
        sql.append("alarme int, datahora varchar(150) not null default(''),");
        sql.append("icone varchar(50) not null,");
        sql.append("primary key(idTarefa),");
        sql.append("foreign key(alarme)references Alarme(idAlarme));");
        return sql.toString();
    }

    /**
     * Criar tabela Trabalhos do BD
     *
     * @return um script sql para executar no banco de dados
     */
    protected static String createTableTrabalhos() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table Trabalhos(idTrabalho int not null ,");
        sql.append("materia varchar(60) not null default(''),");
        sql.append("alarme int, nometrabalho varchar(80) not null default(''),");
        sql.append("descricao varchar(150) not null default(''),");
        sql.append("datahora varchar(150) not null default(''),");
        sql.append("icone varchar(50) not null, primary key(idTrabalho),");
        sql.append("foreign key (alarme)references Alarme(idAlarme));");
        return sql.toString();
    }

    /**
     * Criar tabela Horario do BD
     *
     * @return um script sql para executar no banco de dados
     */
    protected static String createTableHorario() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table Horario(idHorario int not null,");
        sql.append("dia varchar (20) not null default(''),");
        sql.append("materia varchar(60) not null default(''),");
        sql.append("professor varchar (150) default(''),");
        sql.append("hora varchar (150) not null default(''),");
        sql.append("primary key (idHorario));");
        return sql.toString();
    }

    /**
     * Criar tabela Agenda do BD
     *
     * @return um script sql para executar no banco de dados
     */
    protected static String createTableAgenda() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table Agenda(idAgenda int not null,");
        sql.append("horario int, notificacao varchar(100) not null default(''),");
        sql.append("alarme int, datahora varchar(150) not null, hora varchar(150) not null,");
        sql.append("primary key(idagenda),");
        sql.append("foreign key(alarme)references alarme(idalarme),");
        sql.append("foreign key(horario) references Horario(idHorario));");
        return sql.toString();
    }

    /**
     * Criar tabela Historico do BD
     *
     * @return um script sql para executar no banco de dados
     */
    protected static String createTableHistorico() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table Historico(idHistorico int not null,");
        sql.append("usuario int not null, materia varchar(60) not null default(''),");
        sql.append("nota double not null, datahora varchar(150) not null default(''),");
        sql.append("observacao varchar(500) default(''),");
        sql.append("primary key (idhistorico),");
        sql.append("foreign key (usuario) references Usuario(idUser));");
        return sql.toString();
    }

    /**
     * Criar tabela Questoes do BD
     *
     * @return um script sql para executar no banco de dados
     */
    protected static String createTableQuestoes() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table Questoes(idQuestoes int not null,");
        sql.append("questao varchar(500) not null default(''),");
        sql.append("dia varchar(100) not null default(''),");
        sql.append("usuario int not null,");
        sql.append("primary key(idQuestoes),");
        sql.append("foreign key (usuario)references Usuario(idUser));");
        return sql.toString();
    }

    /**
     * Criar tabela Feedback do BD
     *
     * @return um script sql para executar no banco de dados
     */
    protected static String createTableFeedback() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table feedback(idfeed int not null,");
        sql.append("comentario varchar(1000) not null default(''),");
        sql.append("usuario int not null, dia varchar (120) not null default(''),");
        sql.append("email varchar(200) not null default(''),");
        sql.append("primary key (idfeed),");
        sql.append("foreign key(usuario) references Usuario(idUser));");
        return sql.toString();
    }

    /**
     * Criar tabela Logs do BD
     *
     * @return um script sql para executar no banco de dados
     */
    protected static String createTableLogs() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table logregister(idlog int not null ,");
        sql.append("logtext varchar (500) not null default(''),");
        sql.append("dia varchar (120)not null default(''),");
        sql.append("primary key (idlog));");
        return sql.toString();
    }

}
