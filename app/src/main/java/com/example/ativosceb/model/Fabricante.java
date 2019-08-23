package com.example.ativosceb.model;

public class Fabricante {

    private int idFabricante;
    private String descFabricante;

    public Fabricante(int idFabricante, String descFabricante){
        this.setIdFabricante(idFabricante);
        this.setDescFabricante(descFabricante);
    }

    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getDescFabricante() {
        return descFabricante;
    }

    public void setDescFabricante(String descFabricante) {
        this.descFabricante = descFabricante;
    }

    @Override
    public String toString() {
        return getDescFabricante();
    }
}
