package com.apps.paco.myshoplist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.paco.myshoplist.Objetos.Listas;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AltaDeProducto extends AppCompatActivity {

    private TextView tx_nuevo_producto ;
    private TextView lb_lista_sel;

    private int natu = 9;

    TextView textoGrabado;

    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_de_producto);


        tx_nuevo_producto = (TextView) findViewById(R.id.tx_nuevo_producto);

        lb_lista_sel = (TextView) findViewById(R.id.lb_lista_sel );

        final String parametrito =getIntent().getExtras().getSerializable("listaSeleccionada").toString() ;

        lb_lista_sel.setText(parametrito);

        // ===================================================================================================
        // BOTON cancelar:
        // ===================================================================================================

        Button btn_volver = (Button) findViewById(R.id.bt_cancelar_nuevo_producto );
        btn_volver.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View v) {
                                           //   Intent intent2 = new Intent(v.getContext(), seleccionaLista.class);
                                              Intent intent2 = new Intent(v.getContext(), muestraListas.class);
                                              startActivityForResult(intent2, 0);
                                          }
                                      }
        );
        // ===================================================================================================

        Button bt_aceptar = (Button) findViewById(R.id.bt_aceptar_nuevo_producto);
        final String natu = "92";
        bt_aceptar.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 if ((TextUtils.isEmpty(tx_nuevo_producto.getText().toString())  )
                    ) {
                     Toast.makeText(AltaDeProducto.this, "Escribe un nombre de producto.", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     Toast.makeText(AltaDeProducto.this, " + "+ tx_nuevo_producto.getText().toString() + " + " , Toast.LENGTH_SHORT).show();
                     final DatabaseReference miDB_Producto = FirebaseDatabase.getInstance().getReference().child("PRODUCTOS");
                     Producto nuevoProducto = new Producto();
                     nuevoProducto.setNombreLista(parametrito);
                     nuevoProducto.setNaturaleza(natu);
                     System.out.println("SALE recién " + nuevoProducto.getNaturaleza() + " " + nuevoProducto.getNombreLista() );
                     nuevoProducto.setNombreproducto("-" + tx_nuevo_producto.getText().toString());
                     miDB_Producto.push().setValue(nuevoProducto);
                     finish();
                 }

             }
        }
        );
        // Fin del botón aceptar. ******************************************************************************************




        // ******************************************************************************************************************
        Button bt_voz = (Button) findViewById(R.id.bt_voz);
        bt_voz.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                // Configura el Lenguaje (Español-México)
                intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
                try {
                    startActivityForResult(intentActionRecognizeSpeech, RECOGNIZE_SPEECH_ACTIVITY);
                    System.out.println("SALE 4");
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getApplicationContext(), "Tú dispositivo no soporta el reconocimiento por voz", Toast.LENGTH_SHORT).show();
                }

            }

        });
        // **********************************************************************************************

        Button bt_voz2 = (Button) findViewById(R.id.bt_voz2);
        bt_voz2.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(v.getContext(), analizaVoz.class);
                startActivityForResult(intent2, 0);

                }
        });


        } // fin del onCreate ================================================================================

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:
                System.out.println("SALE 1");
                if (resultCode == RESULT_OK && null != data) {
                    System.out.println("SALE 2");
                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);

                    textoGrabado.setText(strSpeech2Text);
                    System.out.println("SALE "+ textoGrabado.getText());
                }
                break;
            default:
                System.out.println("SALE 3");
                 break;
        }
    }


}
