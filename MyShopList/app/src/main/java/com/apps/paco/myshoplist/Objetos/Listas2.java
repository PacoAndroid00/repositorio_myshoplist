package com.apps.paco.myshoplist.Objetos;

/**
 * Created by Paco on 05/06/2017.
 */

public class Listas2 {

    String idLista;
    String nombreLista;


    public Listas2() {
    }

    public Listas2(String idLista, String nombreLista) {
        this.idLista = idLista;
        this.nombreLista = nombreLista;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public String getIdLista() {
        return idLista;
    }

    public void setIdLista(String idLista) {
        this.idLista = idLista;
    }
}
