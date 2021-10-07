package com.get.dia.objetos;

public class Questoes {
    private int idQuestao, usuario;
    private String questao, dia;

    public Questoes() {
    }

    public Questoes(int idQuestao, int usuario, String questao, String dia) {
        this.idQuestao = idQuestao;
        this.usuario = usuario;
        this.questao = questao;
        this.dia = dia;
    }

    public int getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(int idQuestao) {
        this.idQuestao = idQuestao;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getQuestao() {
        return questao;
    }

    public void setQuestao(String questao) {
        this.questao = questao;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
