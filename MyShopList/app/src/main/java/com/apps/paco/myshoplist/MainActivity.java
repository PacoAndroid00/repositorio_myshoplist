package com.apps.paco.myshoplist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txt_uno;
    private TextView txt_dos;
    private TextView txt_dosuno;
    private TextView txt_dosdos;
    private  Button bt_anadir_producto;


    private ImageView imagen1;
    private ImageButton    imb_anadir_producto;
    private ImageButton    bt_borrar_lista;
    private ImageButton    bt_crear_lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.nuevo_layout);

       // System.out.println("SALE comienza =====================================================================================================");

        txt_uno = (TextView) findViewById(R.id.opcion1 );
        txt_dos = (TextView) findViewById(R.id.opcion2 );
        txt_dosuno = (TextView) findViewById(R.id.opcion21);
        txt_dosdos = (TextView) findViewById(R.id.opcion22);
        imagen1 = (ImageView)   findViewById(R.id.imb_uno);


        imb_anadir_producto = (ImageButton) findViewById(R.id.imb_anadir_producto);
        bt_crear_lista     = (ImageButton) findViewById(R.id.imb_crear_lista);
        bt_borrar_lista     = (ImageButton) findViewById(R.id.imb_lista_menos);


        imb_anadir_producto.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent2 = new Intent(v.getContext(), seleccionaLista.class);
                                        startActivityForResult(intent2, 0);
                                    }
                                }
        );
        bt_crear_lista.setOnClickListener(new View.OnClickListener() {

                                                  @Override
                                                  public void onClick(View v) {
                                                      Intent intent2 = new Intent(v.getContext(), nuevaLista.class);
                                                      startActivityForResult(intent2, 0);
                                                  }
                                              }
        );

        bt_borrar_lista.setOnClickListener(new View.OnClickListener() {

                                              @Override
                                              public void onClick(View v) {
                                                  Intent intent2 = new Intent(v.getContext(), borraLista.class);
                                                  startActivityForResult(intent2, 0);
                                              }
                                          }
        );
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_opciones, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.opcion1:   //NUEVA LISTA:
            //    Toast.makeText(this, "Hola, opcion 1", Toast.LENGTH_LONG ).show();
            //    finish();
             //   System.out.println("SALE Entro en la opcion 1");
                Intent ii = new Intent(this, com.apps.paco.myshoplist.nuevaLista.class);
                startActivity(ii);
                return true;

       //     case R.id.opcion2 :
       //         Toast.makeText(this, "Hola, opción 2", Toast.LENGTH_LONG ).show();
       //         return true;

            case R.id.opcion21 :  // NUEVA LISTA
            ///    Toast.makeText(this, "Hola, opción 21", Toast.LENGTH_LONG ).show();
            //    System.out.println("SALE Entro en la opcion 21");
                Intent i = new Intent(this, com.apps.paco.myshoplist.segundaPantalla.class );
                startActivity(i);
                return true;

            case R.id.opcion22 : // BORRAR LISTAS

                //System.out.println("SALE Main:Entro en la opcion borraLista ");
                Intent iopcion22 = new Intent(this, com.apps.paco.myshoplist.borraLista.class );
                startActivity(iopcion22);
                return true;


            case R.id.opcion23 :   //   Una más

            //    System.out.println("SALE Entro en la opcion 23");
                Intent i23 = new Intent(this, com.apps.paco.myshoplist.muestraListas.class );
                startActivity(i23);
                return true;

            case R.id.opcionSelecLista :   // Selecciona una lista

            //    System.out.println("SALE Main:Entro en la opcion SelecLista ");
                Intent i234 = new Intent(this, com.apps.paco.myshoplist.seleccionaLista.class );
                startActivity(i234);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

