package com.jedp.entryeventapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jedp.entryeventapp.adapter.EventosAdapter;
import com.jedp.entryeventapp.model.EventosModel;

import java.util.ArrayList;

public class MisEventos_activity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;

    private RecyclerView recycler_misEvt;

    private ImageView Img_evt;

    private TextView txt_empty;

    private EventosAdapter evtAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);




    }
    public void irMisEventos(View view){
        startActivity(new Intent(this,Menu_activity.class));
    }

    public  void irDetalleEventos(View view){
        startActivity(new Intent(this, MisDetalles_eventos.class));
    }

}