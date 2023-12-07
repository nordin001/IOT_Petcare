package com.example.sprint_1_nuevo_15;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.sprint_1_nuevo_15.databinding.ElementoListaBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorFirestoreUI extends
        FirestoreRecyclerAdapter<Mascota, AdaptadorFirestoreUI.ViewHolder> {
    private Context context;
    protected View.OnClickListener onClickListener;
    public AdaptadorFirestoreUI(Context context,
                     @NonNull FirestoreRecyclerOptions<Mascota> options) {
        super(options);
        this.context = context.getApplicationContext();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nombre, direccion ,peso,edad;

        public  final ImageView fotoMacotas;
        public final ImageView fotoSexo;
        public ViewHolder(View itemView) {
            super(itemView);
            this.nombre = (TextView) itemView.findViewById(R.id.nombre);
            this.direccion = (TextView) itemView.findViewById(R.id.direccion);
            this.fotoMacotas = (ImageView) itemView.findViewById(R.id.fotoMascota);
            this.fotoSexo = (ImageView) itemView.findViewById(R.id.foto2);
            this.peso = (TextView) itemView.findViewById(R.id.peso);
            this.edad = (TextView) itemView.findViewById(R.id.edad);
        }
    }



    @Override public AdaptadorFirestoreUI.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.elemento_lista, parent, false);
        return new AdaptadorFirestoreUI.ViewHolder(view);
    }
    @Override
    protected void onBindViewHolder(@NonNull AdaptadorFirestoreUI.ViewHolder holder, int position, @NonNull Mascota mascota) {
        holder.nombre.setText(mascota.getNombre());
        holder.direccion.setText(mascota.getDireccion());
        holder.peso.setText(Double.toString(mascota.getPeso()));
        holder.edad.setText(Integer.toString(mascota.getEdad()));
        if(mascota.isGenero()==true){//cambiar el foto del sexo depende de la bs
            holder.fotoSexo.setImageResource(R.drawable.img_5);
        }
        else {  holder.fotoSexo.setImageResource(R.drawable.img_6);}
        // Assuming getFoto() returns a valid URL or file path
        String fotoUrl = mascota.getFoto();
        if (fotoUrl != null && !fotoUrl.isEmpty()) {

        } else {
            // Set a placeholder if no valid URL or file path is available
            holder.fotoMacotas.setImageResource(R.drawable.logo_mascota);
        }

        holder.itemView.setOnClickListener(onClickListener);
    }
    public void setOnItemClickListener(View.OnClickListener onClick) {
        onClickListener = onClick;
    }
}