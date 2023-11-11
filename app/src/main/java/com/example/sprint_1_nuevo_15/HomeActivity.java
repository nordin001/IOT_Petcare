package com.example.sprint_1_nuevo_15;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {


        private RecyclerView mRecyclerView;
        private MyAdapter mAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.home);

            mRecyclerView = findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            List<String> data = new ArrayList<>();
            data.add("Item 1");
            data.add("Item 2");

            mAdapter = new MyAdapter(this, data);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
