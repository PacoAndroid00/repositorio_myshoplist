package com.apps.paco.myshoplist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class segundaPantalla extends AppCompatActivity {

    private TextView txt_uno;
    private TextView txt_dos;
    private TextView txt_dosuno;
    private TextView txt_dosdos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevo_layout);


        Button btn2 = (Button) findViewById(R.id.bt_volver);
        btn2.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent2 = new Intent(v.getContext(), MainActivity.class);
                                        startActivityForResult(intent2, 0);
                                    }
                                }
        );
    }
}
