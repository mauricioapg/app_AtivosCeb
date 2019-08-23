package com.example.ativosceb.model;

public class Local {

    private int idLocal;
    private String descLocal;

    public Local(int idLocal, String descLocal){
        this.setIdLocal(idLocal);
        this.setDescLocal(descLocal);
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public String getDescLocal() {
        return descLocal;
    }

    public void setDescLocal(String descLocal) {
        this.descLocal = descLocal;
    }

    @Override
    public String toString() {
        return getDescLocal();
    }
}
