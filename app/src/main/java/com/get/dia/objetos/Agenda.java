package com.get.dia.objetos;

public class Agenda {
    private int idAgenda;
    private int horario,alarme;
    private String notificacao,data,hora;
    public Agenda(){}

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Agenda(int idAgenda, int horario, int alarme, String notificacao, String data, String hora) {
        this.idAgenda = idAgenda;
        this.horario = horario;
        this.alarme = alarme;
        this.notificacao = notificacao;
        this.data = data;
        this.hora = hora;
    }

    public int getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public int getAlarme() {
        return alarme;
    }

    public void setAlarme(int alarme) {
        this.alarme = alarme;
    }

    public String getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(String notificacao) {
        this.notificacao = notificacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String datahora) {
        this.data = datahora;
    }

}
