package com.jedp.entryeventapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jedp.entryeventapp.Helper.ManagementCart;
import com.jedp.entryeventapp.model.EventosModel;

public class DetallesEventos extends AppCompatActivity {

    private Button btn_comprar;
    private ImageButton btn_volver3;

    private TextView txt_nombre_evt,txt_precio,txt_descripcion,txt_fecha,txt_lugar;

    private ImageView itemImg;

    private EventosModel object;

    private int numeroOrden = 1;

    private ManagementCart managementCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_eventos);
        managementCart = new ManagementCart(this);
        initView();
        getBundle();
    }

    private void getBundle() {
        object= (EventosModel) this.getIntent().getSerializableExtra("object");
        int drawableResourceId=this.getResources().getIdentifier(object.getImg_url(),"drawable",this.getPackageName());

        Glide.with(this)
                .load(drawableResourceId)
                .into(itemImg);
        txt_nombre_evt.setText(object.getNombre_evento());
        txt_precio.setText("$ "+ object.getPrecio());
        txt_descripcion.setText(object.getDescripcion());
        txt_fecha.setText(object.getFecha());
        txt_lugar.setText(object.getLugar());

        btn_comprar.setOnClickListener(v -> {
            object.setNumero_carrito(numeroOrden);
            managementCart.insertEvent(object);
        });
        btn_volver3.setOnClickListener(v -> finish());
    }

    private void initView(){
        btn_comprar=findViewById(R.id.btn_comprar);
        btn_volver3=findViewById(R.id.btn_volver3);
        txt_nombre_evt=findViewById(R.id.txt_nombre_evt);
        txt_precio=findViewById(R.id.txt_precio);
        txt_descripcion=findViewById(R.id.txt_descripcion);
        txt_fecha=findViewById(R.id.txt_fecha);
        txt_lugar=findViewById(R.id.txt_lugar);
        itemImg=findViewById(R.id.itemImg);
    }
}