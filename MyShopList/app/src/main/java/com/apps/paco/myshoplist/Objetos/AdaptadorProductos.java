package com.apps.paco.myshoplist.Objetos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.paco.myshoplist.Producto;
import com.apps.paco.myshoplist.R;

import java.util.List;

/**
 * Created by Paco on 16/06/2017.
 */



public class AdaptadorProductos     extends  RecyclerView.Adapter <AdaptadorProductos.ProductosViewHolder> {

        List<Producto> listaSel;

    public AdaptadorProductos(List<Producto> listaSel) {
                       this.listaSel = listaSel;
          }

        @Override
        public AdaptadorProductos.ProductosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_productos, parent , false);
            AdaptadorProductos.ProductosViewHolder holderProductos = new AdaptadorProductos.ProductosViewHolder(v) ;
            return holderProductos;
        }

    @Override
    public void onBindViewHolder(ProductosViewHolder holder, int position) {
        Producto producto = listaSel.get(position);
        holder.tx_prod_selec.setText(producto.getNombreproducto() );

    }


        @Override
        public int getItemCount()
        {
          // System.out.println("SALE 14  ADAPTADORLISTAS Comienza getItemCount dando.." + listaSel.size() );
            return listaSel.size();
        }

        public static class ProductosViewHolder extends RecyclerView.ViewHolder {

            TextView tx_prod_selec;

            public ProductosViewHolder(View itemView) {

                super(itemView);

                tx_prod_selec = (TextView) itemView.findViewById(R.id.tv_productos_creados );
            }

        } //Cierra la clase ListasViewHolder.

    }







