package com.jedp.entryeventapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.jedp.entryeventapp.DetallesEventos;
import com.jedp.entryeventapp.MisDetalles_eventos;
import com.jedp.entryeventapp.R;
import com.jedp.entryeventapp.model.EventosModel;

import java.io.Serializable;
import java.util.ArrayList;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.Viewholder> {

    ArrayList<EventosModel> items;

    Context context;

    public EventosAdapter(ArrayList<EventosModel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public EventosAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_eventos_list,parent,false);
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosAdapter.Viewholder holder, int position) {
        holder.txt_tipo_evet.setText(items.get(position).getTipo_evento());
        holder.txt_nombre_evt.setText(items.get(position).getNombre_evento());
        holder.txt_precio.setText("$"+items.get(position).getPrecio());
        holder.txt_descripcion.setText(items.get(position).getDescripcion());
        holder.txt_lugar.setText(items.get(position).getLugar());
        holder.txt_fecha.setText(items.get(position).getFecha());

        int drawableResourceId=holder.itemView.getResources().getIdentifier(items.get(position).getImg_url(),
                "drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .transform(new GranularRoundedCorners(30,30,30,30))
                .into(holder.img_evento);

        holder.itemView.setOnClickListener(v -> {
            Intent intent= new Intent(holder.itemView.getContext(), DetallesEventos.class);
            intent.putExtra("object",items.get(position));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView txt_nombre_evt,txt_precio,txt_tipo_evet,txt_descripcion,txt_lugar,txt_fecha;

        ImageView img_evento;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            txt_nombre_evt = itemView.findViewById(R.id.txt_nombre_evt);
            txt_precio = itemView.findViewById(R.id.txt_precio);
            txt_tipo_evet = itemView.findViewById(R.id.txt_tipo);
            img_evento = itemView.findViewById(R.id.img_evento);
            txt_descripcion = itemView.findViewById(R.id.txt_descripcion);
            txt_lugar = itemView.findViewById(R.id.txt_lugar);
            txt_fecha = itemView.findViewById(R.id.txt_fecha);
        }
    }
}
