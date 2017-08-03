package com.apps.paco.myshoplist.Objetos;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.paco.myshoplist.R;

import java.util.List;

/**
 * Created by Paco on 06/06/2017.
 */

//Adaptador para mostrar las listas que se pueden seleccionar.

public class AdaptadorListas  extends  RecyclerView.Adapter <AdaptadorListas.ListasViewHolder> {

    List<Listas> listaSel;

    public AdaptadorListas(List<Listas> listaSel) {
        // System.out.println("SALE 8  ADAPTADORLISTAS Comienza su método");
        this.listaSel = listaSel;
        // System.out.println("SALE 9  ADAPTADORLISTAS Fin de su método");
    }

    @Override
    public ListasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // System.out.println("SALE 10  ADAPTADORLISTAS Comienza onCreateViewHolder de ListasViewHolder");
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listas, parent , false);
        ListasViewHolder  holderListas = new ListasViewHolder(v) ;
        // System.out.println("SALE 11  ADAPTADORLISTAS Fin de onCreateViewHolder de ListasViewHolder");
        return holderListas;
    }

    @Override
    public void onBindViewHolder(ListasViewHolder holder, int position) {
   //Todo lo que se hace con el recicler:
        // System.out.println("SALE 12  ADAPTADORLISTAS Comienza onBindViewHolder");
        Listas lista = listaSel.get(position);
        // System.out.println("SALE 12  ADAPTADORLISTAS Comienza onBindViewHolder y ahora getposition vale " + listaSel.get(position)  );
        holder.tx_lis_selec.setText(lista.getNombreLista());

        holder.tx_lis_selec.setTextColor(Color.BLACK); //Esto coloca el color del texto de la lista de listas.


        //System.out.println("SALE 13 ADAPTADORLISTAS Fin onBindViewHolder y elnombre es " + lista.getNombreLista()     );
    }

    @Override
    public int getItemCount()
    {
        // System.out.println("SALE 14  ADAPTADORLISTAS Comienza getItemCount dando.." + listaSel.size() );
        return listaSel.size();
    }

    public static class ListasViewHolder extends RecyclerView.ViewHolder {

        TextView tx_lis_selec;

        public ListasViewHolder(View itemView) {

            super(itemView);
            // System.out.println("SALE 15  ADAPTADORLISTAS Comienza ListasViewHolder");
            tx_lis_selec = (TextView) itemView.findViewById(R.id.tv_listas_creadas );
        }

    } //Cierra la clase ListasViewHolder.

}


