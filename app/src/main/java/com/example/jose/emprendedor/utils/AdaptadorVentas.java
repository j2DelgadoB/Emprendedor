package com.example.jose.emprendedor.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jose.emprendedor.R;

import java.util.ArrayList;

/**
 * Created by jose on 09/10/2014.
 */
public class AdaptadorVentas extends ArrayAdapter {

    private ArrayList<VentasObj> ventas= new ArrayList<VentasObj>();
    Activity context;

    public AdaptadorVentas(Context context, ArrayList<VentasObj> ventasTotales) {
        super(context, R.layout.list_item_ventas,ventasTotales);
        this.context= (Activity) context;
        this.ventas= ventasTotales;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.list_item_ventas, null);

        TextView lblDescripcion = (TextView)item.findViewById(R.id.tvDescripcion);
        lblDescripcion.setText(ventas.get(position).getDescripcion());

        TextView lblCantSoles = (TextView)item.findViewById(R.id.tvSoles);
        lblCantSoles.setText(ventas.get(position).getCantSoles());

        TextView lblCantDolares = (TextView)item.findViewById(R.id.tvDolares);
        lblCantDolares.setText(ventas.get(position).getCantDolares());

        return(item);
    }
}
