package com.get.dia.objetos;

public class Usuario {

    public Usuario(int idUser,String nome,String escola, int idMateria,int inicio){
        this.idUser = idUser;
        this.nome = nome;
        this.escola = escola;
        this.idMateria = idMateria;
        this.inicio = inicio;
    }

    private int idUser;
    private String nome;
    private String escola;
    private int idMateria;

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    private int inicio;

    public Usuario() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

}
