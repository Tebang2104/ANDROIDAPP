package com.jedp.entryeventapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jedp.entryeventapp.Helper.ManagementCart;
import com.jedp.entryeventapp.model.EventosModel;

public class MisDetalles_eventos extends AppCompatActivity {


    private ImageButton btn_salir2;

    private TextView txt_descripcion,txt_lugar,txt_correo, txt_id;

    private ImageView codigoQR,img_evt;

    private EventosModel object;

    private int numeroOrden = 1;

    private ManagementCart managementCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_detalles_eventos);
    }

    public void irMisEventos(View view){
        startActivity(new Intent(this, MisEventos_activity.class));
    }

}