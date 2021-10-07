package com.get.dia.objetos;

public class Trabalhos {
    private int idTrabalho, alarme;
    private String materia, nomeTrabalho, descricao, datahora, icone;

    public Trabalhos() {
    }


    public Trabalhos(int idTrabalho, int alarme, String materia, String nomeTrabalho, String descricao, String datahora, String icone) {
        this.idTrabalho = idTrabalho;
        this.alarme = alarme;
        this.materia = materia;
        this.nomeTrabalho = nomeTrabalho;
        this.descricao = descricao;
        this.datahora = datahora;
        this.icone = icone;
    }

    public int getIdTrabalho() {
        return idTrabalho;
    }

    public void setIdTrabalho(int idTrabalho) {
        this.idTrabalho = idTrabalho;
    }

    public int getAlarme() {
        return alarme;
    }

    public void setAlarme(int alarme) {
        this.alarme = alarme;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getNomeTrabalho() {
        return nomeTrabalho;
    }

    public void setNomeTrabalho(String nomeTrabalho) {
        this.nomeTrabalho = nomeTrabalho;
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

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}
