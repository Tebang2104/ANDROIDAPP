package com.jedp.entryeventapp.Helper;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.core.view.Change;
import com.jedp.entryeventapp.model.EventosModel;

import java.util.ArrayList;

public class ManagementCart {

    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertEvent(EventosModel item){
        ArrayList<EventosModel> listPop = getListCart();
        int versionSDK = android.os.Build.VERSION.SDK_INT;
        boolean existAlready = false;
        int n = 0;
        for(int i = 0; i < listPop.size(); i++){
            if(listPop.get(i).getNombre_evento().equals(item.getNombre_evento())){
                existAlready=true;
                n=i;
                break;
            }
        }

        if(existAlready){
            listPop.get(n).setNumero_carrito(item.getNumero_carrito());
        }else{
            listPop.add(item);
        }
        tinyDB.putListObject("CartList",listPop);
        Toast.makeText(context,"Añadido a tu carrito",Toast.LENGTH_SHORT).show();

    }

    public ArrayList<EventosModel>getListCart(){

        return tinyDB.getListObject("CartList");
    }

    public Double getTotalPrecio(){
        ArrayList<EventosModel> listItem = getListCart();
        double precio=0;
        for(int i= 0; i <listItem.size(); i++){
            precio = precio+(listItem.get(i).getPrecio()*listItem.get(i).getNumero_carrito());
        }
        return precio;
    }

    public void menorNumItem(ArrayList<EventosModel> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener){
        if(listItem.get(position).getNumero_carrito()==1){
            listItem.remove(position);
        }else{
            listItem.get(position).setNumero_carrito(listItem.get(position).getNumero_carrito()-1);
        }
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }

    public void masNumItem(ArrayList<EventosModel> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setNumero_carrito(listItem.get(position).getNumero_carrito()+1);
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }
}
