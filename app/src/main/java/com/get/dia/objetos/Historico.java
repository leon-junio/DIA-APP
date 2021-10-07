package com.get.dia.objetos;

public class Historico {
    private int idHistorico,usuario;
    private double nota;
    private String dataHora,materia,observacao;
    public Historico(){}
    public Historico(int idHistorico, int usuario, double nota, String dataHora, String materia, String observacao) {
        this.idHistorico = idHistorico;
        this.usuario = usuario;
        this.nota = nota;
        this.dataHora = dataHora;
        this.materia = materia;
        this.observacao = observacao;
    }

    public int getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
