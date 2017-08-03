package com.apps.paco.myshoplist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.paco.myshoplist.Objetos.AdaptadorListas;
import com.apps.paco.myshoplist.Objetos.Listas;
import com.apps.paco.myshoplist.Objetos.Referencias;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.apps.paco.myshoplist.R.layout.activity_borra_lista;
import static java.lang.String.valueOf;


public class borraLista extends AppCompatActivity {

    // Es una copia del seleccionaLista, pero en esta clase se modifica un poco para poder seleccionar una lista
    // y ser borrada.

    RecyclerView    rv_listas;
    Button          bt_volver;
    Button          bt_borrar_lista;
    List<Listas>    miLista;
    TextView        listaQueBorrare;
    List<Producto>  miListaDeProductos;
    int             positionLista;
    boolean         listaBorrable;



    RecyclerView.Adapter adaptador;

    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_borra_lista);

        //Mostrar las listas que hay para que pueda seleccionar alguna para borrar.

        miListaDeProductos = new ArrayList<>();

        listaQueBorrare = (TextView)  findViewById(R.id.tx_lista_a_borrar );
        bt_volver = (Button) findViewById(R.id.bt_volver);
        bt_borrar_lista = (Button) findViewById(R.id.bt_borrar_lista);

        rv_listas = (RecyclerView) findViewById(R.id.rec_listas);

        rv_listas.setLayoutManager(new GridLayoutManager(this,1));

     //   rv_listas.addItemDecoration(new DividerItemDecoration(this,1));


        miLista = new ArrayList<>();

        final DatabaseReference miDb = FirebaseDatabase.getInstance().getReference().child(Referencias.LISTAS);

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

                        positionLista = recyclerView.getChildAdapterPosition(child);
                        Listas listita = new Listas();  //listita usada para obtener el valor de la lista seleccionada.
                        listita = miLista.get(positionLista);
                        // Toast.makeText(seleccionaLista.this , "The Item Clicked is: "+ position + " " + listita.getNombreLista() , Toast.LENGTH_SHORT).show();
                        // ******************************************************************************************************
                        // LLAMO A muestraListas pasando como parámetro el nombre de la lista
                        // ******************************************************************************************************

                        /*
                        Intent intent = new Intent(borraLista.this, muestraListas.class);
                        intent.putExtra("listaSeleccionada", listita.getNombreLista() );
                        startActivity(intent);
                        */
                        Toast.makeText(borraLista.this , "Ha seleccionado la lista : "+  listita.getNombreLista().toUpperCase() , Toast.LENGTH_SHORT).show();
                        listaQueBorrare.setText(listita.getNombreLista()) ;

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
        // ===================================================================================================
        // BOTON eliminar un producto a la lista ya seleccionada.
        // ===================================================================================================
       // Button btn_eliminar = (Button) findViewById(R.id.bt_borrar_producto );

        bt_borrar_lista.setOnClickListener( new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                               if (listaQueBorrare.getText() =="") {
                                        Toast.makeText(borraLista.this, "Selecciona un producto para borrar. ", Toast.LENGTH_SHORT).show();
                               }
                               else {
                                    //COMPROBAR SI TIENE PRODUCTOS. SI ES ASÍ, NO SE PUEDE BORRAR.
                                    listaBorrable = true;
                                    final DatabaseReference misListasQueTienenProductos = FirebaseDatabase.getInstance().getReference().child(Referencias.PRODUCTOS);
                                    misListasQueTienenProductos.orderByChild("nombreLista").equalTo(valueOf(listaQueBorrare.getText())).addValueEventListener( new ValueEventListener() {

                                         @Override
                                         public void onDataChange(DataSnapshot dataSnapshot) {

                                             for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                                 Producto listaDeProd = snap.getValue(Producto.class);
                                                 if (snap.child("nombreLista").getValue().equals(listaQueBorrare.getText())) {
                                                     listaBorrable = false;
                                                 }
                                             } // fin del for
                                             // Toast.makeText(borraLista.this, "borrable es " + listaBorrable, Toast.LENGTH_SHORT).show();
                                             //Ahora, borrar de la bd, luego borrar de la lista y actualizarla.
                                             //
                                             if (listaBorrable) {
                                                 final DatabaseReference misListasQueNoTienenProductos = FirebaseDatabase.getInstance().getReference().child(Referencias.LISTAS);
                                                 misListasQueNoTienenProductos.orderByChild("nombreLista").equalTo(valueOf(listaQueBorrare.getText())).addValueEventListener(new ValueEventListener() {

                                                     @Override
                                                     public void onDataChange(DataSnapshot dataSnapshot) {
                                                         for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                                             Listas listaABorrar = snap.getValue(Listas.class);
                                                             if (snap.child("nombreLista").getValue().equals(listaQueBorrare.getText())) {
                                                                 snap.getRef().removeValue();
                                                                 listaQueBorrare.setText("");
                                                                 adaptador.notifyDataSetChanged();
                                                                 listaBorrable = true;
                                                             }
                                                         } // fin del for
                                                     }

                                                     @Override
                                                     public void onCancelled(DatabaseError databaseError) {

                                                     }
                                                 });
                                             }
                                             else
                                               Toast.makeText(borraLista.this, "La lista " + listaQueBorrare.getText() + " contiene productos. Bórralos antes de eliminarla.", Toast.LENGTH_SHORT).show();
                                         }
                                         @Override
                                         public void onCancelled(DatabaseError databaseError) {
                                                  Log.w("TodoApp", "onCancelled", databaseError.toException());
                                         }
                                         });


                               } //Fin del else
                        }
        }); //Fin del setOnClickListener del botón eliminar.


        // Hasta aquí lo correspondiente al botón Eliminar Lista.
        // ****************************************************************************************************************


        miDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                miLista.removeAll(miLista);
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Listas lista = snap.getValue(Listas.class);
                    miLista.add(lista);
                    //     System.out.println("SALE pongo.........." + snap.child("nombreLista").getValue()); //Cq de las dos vale.
                    //     System.out.println("SALE pongo2.........." + lista.getNombreLista());
                }
                adaptador.notifyDataSetChanged();
            }  //cierra el onDataChange

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("SALE ERROR EN ON CANCELLED");
            }
        });




        // ******************************************************************************************************************
        // ******************************************************************************************************************
        // Botón volver:
        // ******************************************************************************************************************

        bt_volver.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent2 = new Intent(v.getContext(), MainActivity.class);
                                        startActivityForResult(intent2, 0);
                                    }
                                }
        );
       // ******************************************************************************************************************



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
