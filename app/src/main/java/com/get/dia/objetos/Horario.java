package com.get.dia.objetos;

public class Horario {
    private int idHorario;
    private String dia,materia,professor,hora;
    public Horario(){}
    public Horario(int idHorario, String dia, String materia, String professor, String hora) {
        this.idHorario = idHorario;
        this.dia = dia;
        this.materia = materia;
        this.professor = professor;
        this.hora = hora;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
