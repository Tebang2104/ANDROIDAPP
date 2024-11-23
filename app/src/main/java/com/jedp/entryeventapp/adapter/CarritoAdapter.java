package com.jedp.entryeventapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.jedp.entryeventapp.Helper.ChangeNumberItemsListener;
import com.jedp.entryeventapp.Helper.ManagementCart;
import com.jedp.entryeventapp.R;
import com.jedp.entryeventapp.model.EventosModel;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.Viewholder> {

    ArrayList<EventosModel> listItemSelected;
    private ManagementCart managementCart;

    ChangeNumberItemsListener changeNumberItemsListener;

    public CarritoAdapter(ArrayList<EventosModel> listItemSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listItemSelected = listItemSelected;
        managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CarritoAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate  = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdapter.Viewholder holder, int position) {
        holder.txt_nombre_evt.setText(listItemSelected.get(position).getNombre_evento());
        holder.txt_precio_x_evt.setText("$"+listItemSelected.get(position).getPrecio());
        holder.txt_total_x_evt.setText("$"+Math.round((listItemSelected.get(position).getNumero_carrito() * listItemSelected.get(position).getPrecio())));
        holder.txt_num_item.setText(String.valueOf(listItemSelected.get(position).getNumero_carrito()));

        int drawableResourceId=holder.itemView.getContext().getResources().getIdentifier(listItemSelected.get(position).getImg_url(),"drawable",
                holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .transform(new GranularRoundedCorners(30,30,30,30))
                .into(holder.itemPic);

        holder.sumaCarritoBtn.setOnClickListener(v -> managementCart.masNumItem(listItemSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));

        holder.restaCarritoBtn.setOnClickListener(v -> managementCart.menorNumItem(listItemSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));
    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView txt_nombre_evt, txt_precio_x_evt,txt_total_x_evt,sumaCarritoBtn,restaCarritoBtn,txt_num_item;
        ImageView itemPic;



        public Viewholder(@NonNull View itemView) {
            super(itemView);

            txt_nombre_evt=itemView.findViewById(R.id.txt_nombre_evt);
            itemPic=itemView.findViewById(R.id.itemPic);
            txt_precio_x_evt=itemView.findViewById(R.id.txt_precio_x_evt);
            txt_total_x_evt=itemView.findViewById(R.id.txt_total_x_evt);
            sumaCarritoBtn=itemView.findViewById(R.id.sumaCarritoBtn);
            restaCarritoBtn=itemView.findViewById(R.id.menosCarritoBtn);
            txt_num_item=itemView.findViewById(R.id.txt_numero_item);
        }
    }

}
