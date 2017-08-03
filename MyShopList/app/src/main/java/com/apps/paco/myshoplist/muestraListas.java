package com.apps.paco.myshoplist;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.apps.paco.myshoplist.Objetos.AdaptadorProductos;
import com.apps.paco.myshoplist.Objetos.ElementoDeLista;
import com.apps.paco.myshoplist.Objetos.Listas;
import com.apps.paco.myshoplist.Objetos.Referencias;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class muestraListas extends AppCompatActivity {



    RecyclerView.Adapter adaptadorDeProductos;

    RecyclerView    rv_productos;
    TextView        listaGlobal ;
    TextView        numProductos ;
    TextView        productoAborrar ;
    List<Producto>  miListaDeProductos;
    int             posicionAborrar;
    String          productoBorrado;
    private         RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestra_listas);

        listaGlobal = (TextView) findViewById(R.id.id_texto);
        numProductos = (TextView) findViewById(R.id.tx_tamanio_lista );
        productoAborrar = (TextView) findViewById(R.id.tx_borrar_producto);

        final String listaSelecta =getIntent().getExtras().getSerializable("listaSeleccionada").toString() ;

        listaGlobal.setText(listaSelecta);




        // ===================================================================================================
        // BOTON Añadir un producto a la lista ya seleccionada.
        // ===================================================================================================

        Button btn2 = (Button) findViewById(R.id.bt_anade_producto);

        btn2.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        //Anulo este campo para que no se mantenga nada.
                                        productoAborrar.setText("");

                                        Intent intent2 = new Intent(v.getContext(),  AltaDeProducto.class);
                                        intent2.putExtra("listaSeleccionada", listaSelecta );
                                        startActivityForResult(intent2, 0);
                                    }
                                }
        );
        // ===================================================================================================
        // ===================================================================================================
        // BOTON volver:
        // ===================================================================================================

        Button btn_volver = (Button) findViewById(R.id.bt_volver );
        btn_volver.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent2 = new Intent(v.getContext(), seleccionaLista.class);
                                        startActivityForResult(intent2, 0);
                                    }
                                }
        );
        // ===================================================================================================
        // Mostrar los elementos de esta lista.
        // ===================================================================================================


        final DatabaseReference misElementosDeLaLista = FirebaseDatabase.getInstance().getReference().child(Referencias.PRODUCTOS);


        rv_productos = (RecyclerView) findViewById(R.id.rec_v_productos);
        rv_productos.setLayoutManager(new GridLayoutManager(this,1));
    //    rv_productos.addItemDecoration(new DividerItemDecoration(this,1));

        miListaDeProductos = new ArrayList<>();

        adaptadorDeProductos = new AdaptadorProductos(miListaDeProductos);

        rv_productos.setAdapter(adaptadorDeProductos);
        //rv_productos.setBackgroundColor(Color.RED); // Esto cambia el fondo del color del recicler que contiene la lista de productos.

        // ****************************************************************************************************************
        // Tratamiento para seleccionar una lista:
        // ****************************************************************************************************************
        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        rv_productos.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {
            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                try {
                    View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

                        int position = recyclerView.getChildAdapterPosition(child);
                        posicionAborrar = position;
                        Producto listita = new Producto();
                        listita = miListaDeProductos.get(position);
                   //     Toast.makeText(muestraListas.this , "The Item Clicked is: "+ position + " " + listita.getNombreproducto() , Toast.LENGTH_SHORT).show();

                        productoAborrar.setText(listita.getNombreproducto());
                        productoBorrado = listita.getNombreproducto();
                    // ******************************************************************************************************
                    // LLAMO A muestraListas pasando como parámetro el nombre de la lista
                    //    Intent intent = new Intent(seleccionaLista.this, muestraListas.class);
                    //    intent.putExtra("listaSeleccionada", listita.getNombreLista() );
                    //    startActivity(intent);

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


        productoAborrar.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View arg0) {
                // Toast.makeText(getApplicationContext(), "Long Clicked " + posicionAborrar + ".",   Toast.LENGTH_SHORT).show();

                //Poner el elemento posicionAborrar subrayado.
                 //productoAborrar.setPaintFlags(productoAborrar.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


                return true;    // <- set to true
            }
        });

        // ===================================================================================================
        // BOTON eliminar un producto a la lista ya seleccionada.
        // ===================================================================================================
        Button btn_eliminar = (Button) findViewById(R.id.bt_borrar_producto );

        btn_eliminar.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {

                                                if (productoAborrar.getText() =="") {
                                                    Toast.makeText(muestraListas.this, "Selecciona un producto para borrar. ", Toast.LENGTH_SHORT).show();
                                                }
                                                else {

                                                // Toast.makeText(muestraListas.this , "Borraré: "+ posicionAborrar + " "  , Toast.LENGTH_SHORT).show();

                                                // FALTA BORRARLO TAMBIÉN DE LA BD.
                                                // misElementosDeLaLista.child(productoAborrar.toString());
                                                // hoy     adaptadorDeProductos.notifyDataSetChanged()
                                                // hoy     productoAborrar.setText("Borrado: "+     productoBorrado) ;


         //                                       System.out.println("SALE 3333333333333333 " + listaGlobal.getText() +
         //                                                " "+ productoAborrar.getText() + " --" +
         //                                                misElementosDeLaLista.orderByChild(valueOf(listaGlobal.getText())).startAt(valueOf(productoAborrar.getText())).endAt(valueOf(productoAborrar.getText()))
         //                                                       );
                                                /*  misElementosDeLaLista.orderByChild(listaGlobal.getText()).startAt( parametrito)
                                                + misElementosDeLaLista. startAt(valueOf(listaGlobal)).  child(valueOf(listaGlobal)) +"....."
                                                +   misElementosDeLaLista.orderByChild(valueOf()).startAt(parametrito).endAt(parametrito)

                                                */
                                                // (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((

                                            //     misElementosDeLaLista.child(valueOf(listaGlobal.getText())).startAt(valueOf(productoAborrar.getText())).equalTo(valueOf(productoAborrar.getText())).addListenerForSingleValueEvent(
                                              //  System.out.println("SALE......"+ misElementosDeLaLista.orderByChild("nombreLista").equalTo(listaGlobal.getText().toString()).
                                              //                                                         orderByChild("nombreproducto").equalTo(productoAborrar.getText().toString()). .getRef() );

                                                misElementosDeLaLista.orderByChild("nombreLista").equalTo(valueOf(listaGlobal.getText())).addValueEventListener(

                                                //        orderByChild("nombreproducto").equalTo(valueOf(productoAborrar.getText())).addValueEventListener(

                                                        new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                                // dataSnapshot.getRef().setValue(null);
                                                                for (DataSnapshot snap : dataSnapshot.getChildren()) {

                                                                    Producto listaDeProd = snap.getValue(Producto.class);
                                                                    //  System.out.println(("SALE 1 " +  snap.child("nombreproducto").getValue()));
                                                                    //  System.out.println("SALE A BORRAR.........." + snap.child("nombreLista").getValue()); //Cq de las dos vale.
                                                                    //  System.out.println("SALE A BORRAR.........." + listaDeProd.getNombreLista());
                                                                    //  System.out.println("SALE A BORRAR getvalue.........." + snap.child("nombreproducto").getValue());
                                                                    //  System.out.println("SALE A BORRAR getref.........." +   snap.child("nombreproducto").getRef()) ;
                                                                    if (snap.child("nombreproducto").getValue() == productoAborrar.getText())
                                                                      {
                                                                        // snap.child("nombreproducto").getRef().removeValue();
                                                                        //  System.out.println(("SALE if " +  snap.child("nombreproducto").getValue()));
                                                                        //  System.out.println(("SALE if " +  snap.getRef()));
                                                                          snap.getRef().removeValue();
                                                                          miListaDeProductos.remove(posicionAborrar);
                                                                      }
                                                                }

                                                                adaptadorDeProductos.notifyDataSetChanged();
                                                                productoAborrar.setText("");
                                                            }


                                                            @Override
                                                            public void onCancelled(DatabaseError databaseError) {
                                                                Log.w("TodoApp", "onCancelled", databaseError.toException());
                                                            }
                                                        });
                                            //    System.out.println("SALE 66666666666666666");
                                                // (((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((

                                            }
                                        }
                     }
        ); //Fin del setOnClickListener del botón eliminar.

        // *******************************************************************************************************************************
        // *******************************************************************************************************************************

        misElementosDeLaLista.orderByChild("nombreLista").startAt(listaSelecta).endAt(listaSelecta).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                miListaDeProductos.removeAll(miListaDeProductos);
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Producto listaDeProd = snap.getValue(Producto.class);
                    miListaDeProductos.add(listaDeProd );
                    //Aquí debo mirar la naturaleza del producto y pintarla de un color u otro.
                    //ojo.
                    System.out.println("SALE ahora" + listaDeProd.getNaturaleza() + " " + listaDeProd.getNombreproducto() );
                  //  System.out.println("SALE pongo.........." + snap.child("nombreLista").getValue()); //Cq de las dos vale.
                  //  System.out.println("SALE pongo2.........." + listaDeProd.getNombreLista());
                }

                adaptadorDeProductos.notifyDataSetChanged();
                // System.out.println("SALE numerando.........." + miListaDeProductos.size() ) ;

                numProductos.setText( valueOf(miListaDeProductos.size()) + " productos." );
            }  //cierra el onDataChange

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("SALE ERROR EN ON CANCELLED");
            }
        });


    }
}