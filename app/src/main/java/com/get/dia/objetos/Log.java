package com.get.dia.objetos;

public class Log {
    private int idlog;
    private String logText, dia;

    public Log() {
    }


    public Log(int idlog, String logText, String dia) {
        this.idlog = idlog;
        this.logText = logText;
        this.dia = dia;
    }

    public int getIdlog() {
        return idlog;
    }

    public void setIdlog(int idlog) {
        this.idlog = idlog;
    }

    public String getLogText() {
        return logText;
    }

    public void setLogText(String logText) {
        this.logText = logText;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
