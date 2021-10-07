package com.get.dia.objetos;

public class Alarme {

 private int idAlarme,usuario;
 private String descricao,datahora;
    public Alarme(){}
    public Alarme(int idAlarme, int usuario, String descricao, String datahora) {
        this.idAlarme = idAlarme;
        this.usuario = usuario;
        this.descricao = descricao;
        this.datahora = datahora;
    }

    public int getIdAlarme() {
        return idAlarme;
    }

    public void setIdAlarme(int idAlarme) {
        this.idAlarme = idAlarme;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }
}
