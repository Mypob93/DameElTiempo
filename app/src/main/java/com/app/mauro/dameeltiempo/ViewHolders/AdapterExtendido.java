package com.app.mauro.dameeltiempo.ViewHolders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mauro.dameeltiempo.ModelsPronosticos.Extendido.Forecastday;
import com.app.mauro.dameeltiempo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mauro on 2/3/2018.
 */

public class AdapterExtendido extends RecyclerView.Adapter<AdapterExtendido.ViewHolder> {

    private ArrayList<Forecastday> arrayList;
    private Context context;

    public AdapterExtendido (ArrayList<Forecastday> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_extendido, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tMax.setText("Temperatura maxima: " + arrayList.get(position).getDay().getMaxtemp_c()  + " C°");
        holder.tMin.setText("Temperatura minima: " +  arrayList.get(position).getDay().getMintemp_c() + " C°");
        holder.humedad.setText("Humedad: " + arrayList.get(position).getDay().getAvghumidity() + "%");
        holder.dia.setText("Fecha: " + arrayList.get(position).getDate());

        String urlhttp = "http:" + arrayList.get(position).getDay().getCondition().getIcon();
        Picasso.with(context).load(urlhttp).error(R.mipmap.ic_launcher).fit().centerInside().into(holder.imagen);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tMax, tMin, humedad, dia;
        public ImageView imagen;

        public ViewHolder(View itemView) {
            super(itemView);

            tMax = (TextView) itemView.findViewById(R.id.temp_max_holder);
            tMin = (TextView) itemView.findViewById(R.id.temp_min_holder);
            humedad = (TextView) itemView.findViewById(R.id.humedad_holder);
            dia = (TextView) itemView.findViewById(R.id.fecha_holder);
            imagen = (ImageView) itemView.findViewById(R.id.imagen_holder);


        }
    }
}
