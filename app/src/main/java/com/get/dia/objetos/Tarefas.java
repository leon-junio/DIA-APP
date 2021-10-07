package com.get.dia.objetos;

public class Tarefas {
    private int idTarefa, alarme;
    private String descricao, nomeTarefa, dataHora, icone;

    public Tarefas() {
    }


    public Tarefas(int idTarefa, int alarme, String descricao, String nomeTarefa, String dataHora, String icone) {
        this.idTarefa = idTarefa;
        this.alarme = alarme;
        this.descricao = descricao;
        this.nomeTarefa = nomeTarefa;
        this.dataHora = dataHora;
        this.icone = icone;
    }

    public int getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(int idTarefa) {
        this.idTarefa = idTarefa;
    }

    public int getAlarme() {
        return alarme;
    }

    public void setAlarme(int alarme) {
        this.alarme = alarme;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}
