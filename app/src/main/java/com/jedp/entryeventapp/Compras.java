package com.jedp.entryeventapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.jedp.entryeventapp.Helper.ManagementCart;
import com.jedp.entryeventapp.adapter.CarritoAdapter;
import com.jedp.entryeventapp.model.EventosModel;

public class Compras extends AppCompatActivity {

    private RecyclerView.Adapter adapter;

    private RecyclerView recyclerView;

    private ManagementCart managmentCart;
    private TextView txt_totalPrecio,txt_impuesto,txt_total,txt_vacio;

    private double impuesto;

    private ScrollView scrollCompras;
    private ImageView btn_volver3;
    private Button btnMisEvt;

    private EventosModel object;

    private static final int NOTIFICATION_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        managmentCart = new ManagementCart(this);

        
        initView();
        setVariable();
        calculateCart();
        initList();
    }




    private void initList() {
        if(managmentCart.getListCart().isEmpty()){
            txt_vacio.setVisibility(View.VISIBLE);
            scrollCompras.setVisibility(View.GONE);

        }else{
            txt_vacio.setVisibility(View.GONE);
            scrollCompras.setVisibility(View.VISIBLE);

        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter= new CarritoAdapter(managmentCart.getListCart(), this, () -> calculateCart());
        recyclerView.setAdapter(adapter);
    }

    private void calculateCart() {
        double porcienImpuesto = 0.02;

        impuesto = Math.round(managmentCart.getTotalPrecio()*porcienImpuesto*100.0)/100.0;

        double total = Math.round((managmentCart.getTotalPrecio()+impuesto)*100)/100;
        double itemTotal = Math.round(managmentCart.getTotalPrecio()*100)/100;

        txt_total.setText("$"+total);
        txt_impuesto.setText("$"+impuesto);
        txt_totalPrecio.setText("$"+itemTotal);

    }

    private void setVariable() {
        btnMisEvt.setOnClickListener(v -> {
            Toast.makeText(this,"Su evento ha sido agregado a mis eventos y se ha notificado correc",Toast.LENGTH_SHORT).show();
            mostrarNotificacion();
        });
    }



    private void initView() {
        txt_totalPrecio = findViewById(R.id.txt_totalPrecio);
        txt_impuesto = findViewById(R.id.txt_impuesto);
        txt_total = findViewById(R.id.txt_total);
        recyclerView = findViewById(R.id.recycler_compras);
        btn_volver3 = findViewById(R.id.btn_volver3);
        btnMisEvt = findViewById(R.id.btnOrdenar);
        txt_vacio = findViewById(R.id.txt_vacio);
        scrollCompras = findViewById(R.id.scrpll_compras);

    }

    public void volver(View view){
        startActivity(new Intent(this,Menu_activity.class));
    }

    private void mostrarNotificacion() {
        // Crear una notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, String.valueOf(NOTIFICATION_ID))
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("Notificación de pago exitoso")
                .setContentText("Recuerda que quedan 4 días para tu evento y tambien lo puedes ver en la sección de mis eventos")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
}