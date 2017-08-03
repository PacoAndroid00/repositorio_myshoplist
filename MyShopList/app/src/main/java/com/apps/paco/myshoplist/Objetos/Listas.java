package com.apps.paco.myshoplist.Objetos;

/**
 * Created by Paco on 01/06/2017.
 */

public class Listas {

    //Contiene las posibles listas a definir.

    String nombreLista;

    public Listas() {
    }

    public Listas(String nombreLista) {
        this.nombreLista = nombreLista;
    }




    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }
}
