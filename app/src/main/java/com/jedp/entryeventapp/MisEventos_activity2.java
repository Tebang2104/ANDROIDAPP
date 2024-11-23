package com.jedp.entryeventapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MisEventos_activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos2);
    }

    public  void irDetalleEventos(View view){
        startActivity(new Intent(this, MisDetalles_eventos.class));
    }

    public  void irDetalleEventos2(View view){
        startActivity(new Intent(this, MisDetalles_eventos2.class));
    }

    public void irEventos(View view){
        startActivity(new Intent(this,Menu_activity.class));
    }
}
