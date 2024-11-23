package com.jedp.entryeventapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jedp.entryeventapp.adapter.EventosAdapter;
import com.jedp.entryeventapp.model.EventosModel;

import java.util.ArrayList;

public class Menu_activity extends AppCompatActivity {

    private RecyclerView.Adapter eventosAdapter;
    private RecyclerView recyclerEventos;

    FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<EventosModel> items = new ArrayList<>();

        items.add(new EventosModel("Fiestas","Megaland","megaland",50000,"uno de los mejores conciertos de musica urbana con artistas de talla mundial" ,"Parque simón bólivar , bogotá" , "25 de noviembre de 2023","jduitamap@ucentral.edu.co","JdWiEDvMWMfWPoIyThewFYhFgJ63","qrnavidad"));
        items.add(new EventosModel("Teatros","Cisne Negro","cisnenegro",30000,"La hermosisma obra del cisne negro pero mas colombianizada un pato negro" ,"teatro la castellana, Bogota" , "30 de noviembre de 2023","jduitamap@ucentral.edu.co","JdWiEDvMWMfWPoIyThewFYhFgJ63","qrnavidad"));
        items.add(new EventosModel("Galerias","Festival de luces","festival",10000,"Siente la magia de la navidad haciendo la ruta navideña del festival de luces","Bogotá D.C." ,"16 de diciembre de 2023","jduitamap@ucentral.edu.co","JdWiEDvMWMfWPoIyThewFYhFgJ63","qrnavidad"));
        items.add(new EventosModel("Prueba","prueba","festival",10000,"Siente la magia de la navidad haciendo la ruta navideña del festival de luces","Bogotá D.C." ,"16 de diciembre de 2023","jduitamap@ucentral.edu.co","JdWiEDvMWMfWPoIyThewFYhFgJ63","qrnavidad"));

        recyclerEventos= findViewById(R.id.recycle_events);
        recyclerEventos.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        eventosAdapter = new EventosAdapter(items);
        recyclerEventos.setAdapter(eventosAdapter);

    }


    public void irCompras(View view){
        startActivity(new Intent(this,Compras.class));
    }

    public void irMisEventos(View view){
        startActivity(new Intent(this,MisEventos_activity.class));
    }
    public void irMisEventos2(View view){
        startActivity(new Intent(this,MisEventos_activity2.class));
    }


    public void salir(View view){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this,"Sessión Cerrada",Toast.LENGTH_SHORT).show();
        gologging();
    }

    private void gologging(){
        Intent salir = new Intent(this,Login_activity.class);
        salir.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(salir);
    }
}