package com.apps.paco.myshoplist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.apps.paco.myshoplist.Objetos.AdaptadorListas;
import com.apps.paco.myshoplist.Objetos.Listas;
import com.apps.paco.myshoplist.Objetos.Listas2;
import com.apps.paco.myshoplist.Objetos.Referencias;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;

import java.util.ArrayList;
import java.util.List;

import static com.apps.paco.myshoplist.R.layout.activity_selecciona_lista;
import static com.apps.paco.myshoplist.R.layout.selec_lista;


public class seleccionaLista extends AppCompatActivity {


    RecyclerView    rv_listas;
    Button          btn2;
    List<Listas>    miLista;

    RecyclerView.Adapter adaptador;

    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_selecciona_lista);

        //Mostrar las listas que hay para que pueda seleccionar alguna como LISTA ACTIVA.

     //   System.out.println("SALE INICIO  ANTES DE ASIGNAR en SeleccionaLista. ");



        btn2 = (Button) findViewById(R.id.bt_volver);


        rv_listas = (RecyclerView) findViewById(R.id.rec_listas);

        rv_listas.setLayoutManager(new GridLayoutManager(this,1));

     //   rv_listas.addItemDecoration(new DividerItemDecoration(this,1));


        miLista = new ArrayList<>();






        /* La línea         final DatabaseReference miDb = FirebaseDatabase.getInstance().getReference().child(Referencias.LISTAS);
          puede sustituir al siguiente bloque:
          */

        DatabaseReference miDb = FirebaseDatabase.getInstance().getReference();
        boolean espera = true;
        while ( espera )
        {
            Toast.makeText(seleccionaLista.this, "Esperando por la conexión a la Base de Datos.", Toast.LENGTH_SHORT).show();
            miDb = FirebaseDatabase.getInstance().getReference().child(Referencias.LISTAS);
            if (miDb != null)
                      espera = false;
        }

        // Hasta aquí el bloque sustituíble.

        adaptador = new AdaptadorListas(miLista);


        rv_listas.setAdapter(adaptador);




        // ****************************************************************************************************************
        // Tratamiento para seleccionar una lista:
        // ****************************************************************************************************************
        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        rv_listas.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {
            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                        int position = recyclerView.getChildAdapterPosition(child);
                        Listas listita = new Listas();  //listita usada para obtener el valor de la lista seleccionada.
                        listita = miLista.get(position);
                       // Toast.makeText(seleccionaLista.this , "The Item Clicked is: "+ position + " " + listita.getNombreLista() , Toast.LENGTH_SHORT).show();

                        // ******************************************************************************************************
                        // ******************************************************************************************************
                        // LLAMO A muestraListas pasando como parámetro el nombre de la lista
                        // ******************************************************************************************************
                        Intent intent = new Intent(seleccionaLista.this, muestraListas.class);
                        intent.putExtra("listaSeleccionada", listita.getNombreLista() );
                        startActivity(intent);
                        // ******************************************************************************************************
                        // ******************************************************************************************************
                        return true;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }

        });
        // ****************************************************************************************************************




        // ****************************************************************************************************************





      //  System.out.println("SALE 3  antes del AVEL");
        miDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             //   System.out.println("SALE 4  entro en el onDataChange del AVEL");
                miLista.removeAll(miLista);
                for (DataSnapshot snap : dataSnapshot.getChildren()) {

               //     System.out.println("SALE 5  dentro del for en el onDataChange del AVEL");
                    Listas lista = snap.getValue(Listas.class);
                    miLista.add(lista);
               //     System.out.println("SALE pongo.........." + snap.child("nombreLista").getValue()); //Cq de las dos vale.
               //     System.out.println("SALE pongo2.........." + lista.getNombreLista());
                }
               // System.out.println("SALE 6  salgo del for y antes del notify al adaptador");



                adaptador.notifyDataSetChanged();
               // System.out.println("SALE 6bis  después del notify al adaptador y cerrando el ondatachange del AVEL");


            }  //cierra el onDataChange

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("SALE ERROR EN ON CANCELLED");
            }
        });

        // System.out.println("SALE 7  fuera del addValueEventListener");

        btn2.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent2 = new Intent(v.getContext(), MainActivity.class);
                                        startActivityForResult(intent2, 0);
                                    }
                                }
        );



/* COMENTO TODO ESTO QUE SÍ FUNCIONA:***********************************************************************************


        final DatabaseReference misListasSeleccionadas = FirebaseDatabase.getInstance().getReference().child(Referencias.LISTAS);

        // misListasSeleccionadas.addListenerForSingleValueEvent(new ValueEventListener() {
        misListasSeleccionadas.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot xdataSnapshot) {

                System.out.println("SALE ................................................");

                Listas2 miLista = xdataSnapshot.getValue(Listas2.class);
                for (DataSnapshot projectSnapshot : xdataSnapshot.getChildren()) {

                    System.out.println("SALE EN EL FOR " + projectSnapshot.getKey());
                    System.out.println("SALE EN EL FOR con child y getvalue " + projectSnapshot.child("nombreLista").getValue());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error ", databaseError.getMessage());
            }
        });



*/
      //  System.out.println("SALE 16  FINAL del seleccionaLista");

    } // cierra el onCreate inicial



}
