package com.example.sprint_1_nuevo_15;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sprint_1_nuevo_15.databinding.ElementoListaBinding;



public class MyAdapter extends
        RecyclerView.Adapter<MyAdapter.ViewHolder> {
    protected RepositorioLugares lugares; // Lista de lugares a mostrar


    protected View.OnClickListener onClickListener;

    public MyAdapter(RepositorioLugares lugares) {
        this.lugares = lugares;
    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, direccion;
        public ImageView fotoMacotas;
        public ImageView fotoSexo;

        // public RatingBar valoracion;
        public ViewHolder(ElementoListaBinding itemView) {
            super(itemView.getRoot());
            nombre = itemView.nombre;
            direccion = itemView.direccion;
            fotoMacotas = itemView.fotoMascota;
            fotoSexo= itemView.foto2;
        }

        // Personalizamos un ViewHolder a partir de un lugar
        public void personaliza(Mascota lugar) {
            nombre.setText(lugar.getNombre());
            direccion.setText(lugar.getDireccion());
            int id = R.drawable.perro1;
            switch (lugar.getTipo()) {
                case OTROS:
                    id = R.drawable.perro1;
                    break;
                case AñadirMascota:
                    id = R.drawable.logo_mascota;
                    break;
            }

            fotoMacotas.setImageResource(id);
            fotoMacotas.setScaleType(ImageView.ScaleType.FIT_END);
            // valoracion.setRating(lugar.getValoracion());
        }

    }

    // Creamos el ViewHolder con la vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ElementoListaBinding binding = ElementoListaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setOnClickListener(onClickListener); // Set the click listener on the root view

        return new ViewHolder(binding);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Mascota lugar = lugares.elemento(posicion);
        holder.personaliza(lugar);
    }

    // Indicamos el número de elementos de la lista
    @Override
    public int getItemCount() {
        return lugares.tamaño();
    }


    // Para poder modificar el protected View.OnClickListener onClickListener
    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}




