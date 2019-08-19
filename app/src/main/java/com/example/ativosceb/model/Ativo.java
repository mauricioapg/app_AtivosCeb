package com.example.ativosceb.model;

public class Ativo {
    private int idAtivo;
    private String item;
    private int idLocal;
    private int idFabricante;
    private int idCategoria;
    private int idPiso;
    private String modelo;
    private String comentarios;
    private String dataRetirada;
    private String dataRegistro;
    private double valor;
    private String condicao;
    private String serviceTag;
    private int patrimonio;
    private String garantia;
    private String numeroSerie;
    private String notaFiscal;

    public Ativo(int id, String item){
        this.setIdAtivo(id);
        this.setItem(item);
    }

    public int getIdAtivo() {
        return idAtivo;
    }

    public void setIdAtivo(int idAtivo) {
        this.idAtivo = idAtivo;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(int idPiso) {
        this.idPiso = idPiso;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(String dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getServiceTag() {
        return serviceTag;
    }

    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    public int getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(int patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }


    /*
    private int idAtivo;
    private String item;
    private int idLocal;
    private int idFabricante;
    private int idCategoria;
    private int idPiso;
    private String modelo;
    private String comentarios;
    private String dataRetirada;
    private String dataRegistro;
    private double valor;
    private String condicao;
    private String serviceTag;
    private int patrimonio;
    private String garantia;
    private String numeroSerie;
    private String notaFiscal;

    public Ativo(int idAtivo, String item, int idLocal, int idCategoria, int idPiso, int idFabricante, String modelo, String comentarios,
                 String dataRetirada, String dataRegistro, String condicao, String serviceTag, String garantia, String numeroSerie, String notaFiscal,
                 int patrimonio, double valor){
        //this.setIdAtivo(idAtivo);
        this.setItem(item);
        this.setIdLocal(idLocal);
        this.setIdPiso(idPiso);
        this.setIdFabricante(idFabricante);
        this.setIdCategoria(idCategoria);
        this.setValor(valor);
        this.setCondicao(condicao);
        this.setDataRegistro(dataRegistro);
        this.setDataRetirada(dataRetirada);
        this.setPatrimonio(patrimonio);
        this.setServiceTag(serviceTag);
        this.setNumeroSerie(numeroSerie);
        this.setGarantia(garantia);
        this.setNotaFiscal(notaFiscal);
        this.setComentarios(comentarios);
        this.setModelo(modelo);
    }

    public int getIdAtivo() {
        return idAtivo;
    }

    public void setIdAtivo(int idAtivo) {
        this.idAtivo = idAtivo;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(int idPiso) {
        this.idPiso = idPiso;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(String dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getServiceTag() {
        return serviceTag;
    }

    public void setServiceTag(String serviceTag) {
        this.serviceTag = serviceTag;
    }

    public int getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(int patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }
    */
}
