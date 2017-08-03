package com.apps.paco.myshoplist.Objetos;

/**
 * Created by Paco on 26/05/2017.
 */

public class ElementoDeLista {

    Listas nombrelista;
    String nombredeproducto;
    int cantidaddeproducto;


    //eSTE ES EL NUMERO DE ELEMENTO QUE SE INCLUYE EN CADA LISTA.
    //Cada número tiene asociado un par de datos: nombre y cantidad de producto.

    public ElementoDeLista() {
    }

    public ElementoDeLista(Listas nombrelista, String nombredeproducto, int cantidaddeproducto) {
        this.nombrelista = nombrelista;
        this.nombredeproducto = nombredeproducto;
        this.cantidaddeproducto = cantidaddeproducto;
    }


    public Listas getNombrelista() {
        return nombrelista;
    }

    public void setNombrelista(Listas nombrelista) {
        this.nombrelista = nombrelista;
    }

    public String getNombredeproducto() {
        return nombredeproducto;
    }

    public void setNombredeproducto(String nombredeproducto) {
        this.nombredeproducto = nombredeproducto;
    }

    public int getCantidaddeproducto() {
        return cantidaddeproducto;
    }

    public void setCantidaddeproducto(int cantidaddeproducto) {
        this.cantidaddeproducto = cantidaddeproducto;
    }
}
