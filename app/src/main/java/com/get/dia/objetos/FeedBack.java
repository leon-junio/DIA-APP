package com.get.dia.objetos;

public class FeedBack {
    private int idFeed,usuario;
    private String comentario,dia,email;
    public FeedBack(){}
    public FeedBack(int idFeed, int usuario, String comentario, String dia, String email) {
        this.idFeed = idFeed;
        this.usuario = usuario;
        this.comentario = comentario;
        this.dia = dia;
        this.email = email;
    }

    public int getIdFeed() {
        return idFeed;
    }

    public void setIdFeed(int idFeed) {
        this.idFeed = idFeed;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
