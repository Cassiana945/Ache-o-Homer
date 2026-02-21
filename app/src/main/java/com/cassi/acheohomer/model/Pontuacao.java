package com.cassi.acheohomer.model;

public class Pontuacao {

    private int id;
    private String data;
    private int pontos;
    private boolean recorde;

    public Pontuacao() {
    }

    public Pontuacao(int id, String data, int pontos, boolean recorde) {
        this.id = id;
        this.data = data;
        this.pontos = pontos;
        this.recorde = recorde;
    }

    public Pontuacao(String data, int pontos, boolean recorde) {
        this.data = data;
        this.pontos = pontos;
        this.recorde = recorde;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public boolean isRecorde() {
        return recorde;
    }

    public void setRecorde(boolean recorde) {
        this.recorde = recorde;
    }
}
