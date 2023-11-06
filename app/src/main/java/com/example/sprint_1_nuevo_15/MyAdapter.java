package com.example.sprint_1_nuevo_15;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Context;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<String> mData;
        private LayoutInflater mInflater;

        public MyAdapter(Context context, List<String> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.elemento_lista, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String item = mData.get(position);
            holder.myTextView.setText(item);

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView myTextView;
            public TextView nombre, direccion;
            public ImageView foto;
            ViewHolder(View itemView) {
                super(itemView);
                 myTextView = itemView.findViewById(R.id.direccion2);
                 myTextView = itemView.findViewById(R.id.direccion);
                 foto = itemView.findViewById(R.id.foto);
            }


        }
    }
