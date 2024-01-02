package com.example.sprint_1_nuevo_15;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class HomeActivity extends AppCompatActivity {

    private MascotasAsinc mascotas;

    private AdaptadorFirestoreUI adaptador;
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout ;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        //-------------toolbar----------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //------------------------------------------
        //--------------refresh recycle view--------
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        recyclerView = findViewById(R.id.recycler_view);
        //--------------defining  the recycle view -----------------
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adaptador = ((Aplicacion) getApplicationContext()).adaptador;
        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = recyclerView.getChildAdapterPosition(v);

                // Check if the clicked item is the last one

                if (pos == adaptador.getItemCount() - 1) {
                    // Launch EditarMascotaActivity for the last item

                    editarMascota(v);
                }

                else {
                    // If not the last item, launch VistaMascotaActivity
                    mostrarLugar(pos);
                }

            }
        });
        mascotas = ((Aplicacion) getApplication()).mascotas;
        recyclerView.setAdapter(adaptador);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptador.startListening();
        //---------------------------------------
        //------refresh the recycle view---------
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Implement the code to refresh your data
                // For example, you may want to fetch new data from a server or reload from a local source
                ((Aplicacion) getApplicationContext()).refreshData();
                // After refreshing, call setRefreshing(false) to indicate that the refresh operation is complete
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    void mostrarMascota(int pos) {
        Intent i = new Intent(this, VistaMascotaActivity.class);
        i.putExtra("pos", pos);
        startActivity(i);
    }

/*
    private void setupRecyclerView() {
        Query query = FirebaseFirestore.getInstance()
                .collection("mascotas")
                .limit(2);

        FirestoreRecyclerOptions<Mascota> options = new FirestoreRecyclerOptions.Builder<Mascota>()
                .setQuery(query, Mascota.class)
                .build();

        adaptador = new AdaptadorFirestoreUI(options, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);

        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = recyclerView.getChildAdapterPosition(v);
                mostrarLugar(pos);
            }
        });


        adaptador.startListening();
    }



    private void loadDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MascotasLista miLista = new MascotasLista();

        // Log the size of listaLugares before adding data to Firestore
        Log.d("HomeActivity", "Size of listaLugares before adding to Firestore: " + miLista.listaLugares.size());

        for (Mascota mascota : miLista.listaLugares) {
            db.collection("mascotas").add(mascota);
        }

        // Log the size of listaLugares after adding data to Firestore
        Log.d("HomeActivity", "Size of listaLugares after adding to Firestore: " + miLista.listaLugares.size());

        // Start listening for changes in Firestore
        adaptador.startListening();

        // Log the size of listaLugares after starting to listen
        Log.d("HomeActivity", "Size of listaLugares after starting to listen: " + miLista.listaLugares.size());
    }

 */
    void mostrarLugar(int pos) {
        Intent i = new Intent(this, VistaMascotaActivity.class);
        i.putExtra("pos", pos);
        startActivity(i);
    }

    public void editarMascota(View view) {
        Intent i = new Intent(this, AniadirMascotaActivity.class);
        startActivity(i);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        adaptador.stopListening();
    }


    //---------------  MENU -----------------
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; /** true -> el menú ya está visible*/}

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.acercaDe) {
            lanzarAcercaDe(null);
            return true;
        }
        if (id == R.id.menu_perfil) {
            lanzarEditarPerfil(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //---------------METODOS MENU----------------------


    public void lanzarEditarPerfil(View view){
        Intent i = new Intent(this,EditarPerfil.class);
        startActivity(i);
    }
    public static class AcercaDeActivity extends AppCompatActivity {
        @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.acercade);
        }
    }
    public void lanzarAcercaDe(View view){
        Intent i = new Intent(this, MainActivity.AcercaDeActivity.class);
        startActivity(i);
    }

    //------------------ fin menu--------------------------------------
}
