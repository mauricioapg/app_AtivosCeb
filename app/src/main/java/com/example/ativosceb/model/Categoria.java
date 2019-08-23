package com.example.ativosceb.model;

public class Categoria {

    private int idCategoria;
    private String descCategoria;

    public Categoria(int idCategoria, String descCategoria){
        this.setIdCategoria(idCategoria);
        this.setDescCategoria(descCategoria);
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescCategoria() {
        return descCategoria;
    }

    public void setDescCategoria(String descCategoria) {
        this.descCategoria = descCategoria;
    }

    @Override
    public String toString() {
        return getDescCategoria();
    }
}
