package com.apps.paco.myshoplist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Paco on 23/05/2017.
 */

public class primeraOpcion  extends Activity {

    private TextView txt_uno;
    private TextView txt_dos;
    private TextView txt_dosuno;
    private TextView txt_dosdos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_layout);

//        txt_uno = (TextView)    findViewById(R.id.opcion1 );
//        txt_dos = (TextView)    findViewById(R.id.opcion2 );
//        txt_dosuno = (TextView) findViewById(R.id.opcion21);
//        txt_dosdos = (TextView) findViewById(R.id.opcion22);

//        Toast.makeText(this, "eSTO ES OTRA ACTIVITY", Toast.LENGTH_LONG ).show();

    }

    public void cerrar(View view) {
        finish();
    }
}
