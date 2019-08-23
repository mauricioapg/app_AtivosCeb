package com.example.ativosceb.model;

public class Piso {

    private int idPiso;
    private String descPiso;

    public Piso(int idPiso, String descPiso){
        this.setIdPiso(idPiso);
        this.setDescPiso(descPiso);
    }

    public int getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(int idPiso) {
        this.idPiso = idPiso;
    }

    public String getDescPiso() {
        return descPiso;
    }

    public void setDescPiso(String descPiso) {
        this.descPiso = descPiso;
    }

    @Override
    public String toString() {
        return getDescPiso();
    }
}
