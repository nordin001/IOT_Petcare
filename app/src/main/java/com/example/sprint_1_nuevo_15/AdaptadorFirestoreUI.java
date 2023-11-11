package com.example.sprint_1_nuevo_15;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.sprint_1_nuevo_15.databinding.ElementoListaBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;



public class AdaptadorFirestoreUI extends
        FirestoreRecyclerAdapter<Mascota, MyAdapter.ViewHolder> {

    protected View.OnClickListener onClickListener;
    protected Context context;

    public AdaptadorFirestoreUI(
            @NonNull FirestoreRecyclerOptions<Mascota> options, Context context){
        super(options);
        this.context = context;
    }

@Override
public MyAdapter.ViewHolder onCreateViewHolder(
        ViewGroup parent, int viewType) {
    ElementoListaBinding v = ElementoListaBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false);
    v.getRoot().setOnClickListener(onClickListener);
    return new MyAdapter.ViewHolder(v);
}

    @Override
    protected void onBindViewHolder(@NonNull MyAdapter
            .ViewHolder holder, int position, @NonNull Mascota lugar) {
        holder.personaliza(lugar);
    }

    public void setOnItemClickListener(View.OnClickListener onClick) {
        onClickListener = onClick;
    }

    public String getKey(int pos) {
        return super.getSnapshots().getSnapshot(pos).getId();
    }

    public int getPos(String id) {
        int pos = 0;
        while (pos < getItemCount()) {
            if (getKey(pos).equals(id)) return pos;
            pos++;
        }
        return -1;
    }
}