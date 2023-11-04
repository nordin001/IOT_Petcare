package com.example.sprint_1_nuevo_15;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<DataModel> data;

    public MyAdapter(Context context, ArrayList<DataModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.elemento_lista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel item = data.get(position);

        // Bind data to the views in elemento_lista layout
        holder.nombreTextView.setText(item.getNombre());
        holder.direccionTextView.setText(item.getDireccion());
        holder.direccion2TextView.setText(item.getDireccion2());
        holder.direccion3TextView.setText(item.getDireccion3());

        // You can load images using a library like Picasso or Glide
        // For example, if you're using Picasso:
        // Picasso.get().load(item.getImagenUrl()).into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreTextView;
        TextView direccionTextView;
        TextView direccion2TextView;
        TextView direccion3TextView;
        ImageView fotoImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombre);
            direccionTextView = itemView.findViewById(R.id.direccion);
            direccion2TextView = itemView.findViewById(R.id.direccion2);
            direccion3TextView = itemView.findViewById(R.id.direccion3);
            fotoImageView = itemView.findViewById(R.id.foto);
        }
    }
}
