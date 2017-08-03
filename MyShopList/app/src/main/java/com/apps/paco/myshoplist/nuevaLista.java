package com.apps.paco.myshoplist;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.paco.myshoplist.Objetos.Listas;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class nuevaLista extends AppCompatActivity {


    private TextView txt_nueva_lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_lista);

        System.out.println("SALE Entro en el onCreate de nuevaLista");


        txt_nueva_lista = (TextView) findViewById(R.id.tx_nueva_lista);

        // ===================================================================================================
        // BOTON CANCELAR:
        // ===================================================================================================
        Button btn2 = (Button) findViewById(R.id.bt_cancelar_nueva_lista);
        btn2.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent2 = new Intent(v.getContext(), MainActivity.class);
                                        System.out.println("SALE CANCELAR: ");
                                        startActivityForResult(intent2, 0);
                                    }
                                }
        );
        // ===================================================================================================
        // BOTON ACEPTAR:
        // ===================================================================================================
        Button bt_aceptar = (Button) findViewById(R.id.bt_aceptar_nueva_lista);
        bt_aceptar.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View v) {
                                              // *****************************************************************************
                                          //    System.out.println("SALE entra en el View");

                                              final DatabaseReference miNuevaLista = FirebaseDatabase.getInstance().getReference().child("LISTAS");

                                              String nombre_lista = txt_nueva_lista.getText().toString();
                                          //    System.out.println("SALE 1 " + nombre_lista);
                                              Listas nuevalista = new Listas();
                                              nuevalista.setNombreLista(nombre_lista);

                                              miNuevaLista.push().setValue(nuevalista);
                                          //    System.out.println("SALE 2 " + nombre_lista);

                                              finish();

                                              // ***********************************************************************************


                                          }
                                      }
        );
        // ===================================================================================================


        // ===================================================================================================
    } // Fin del método onCreate.
}
