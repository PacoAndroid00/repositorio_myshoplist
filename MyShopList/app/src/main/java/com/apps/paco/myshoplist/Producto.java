package com.apps.paco.myshoplist;

/**
 * Created by Paco on 19/05/2017.
 */

public class Producto {



    private  String nombreLista;
    private  String idProducto;
    private  int cantidad;
    private  String nombreproducto;
    private  String naturaleza; //Esto indicará si se trata de un producto de Fruta, Carne, Otros...para ponerle un color u otro.

    public Producto() {
    }

    public Producto(String nombreLista, String idProducto, int cantidad, String nombreproducto, String naturaleza) {
        this.nombreLista = nombreLista;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.nombreproducto = nombreproducto;
        this.naturaleza = naturaleza;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreproducto() {
        return nombreproducto;
    }

    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {         this.naturaleza = naturaleza;    }
}

