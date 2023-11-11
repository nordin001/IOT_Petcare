package com.example.sprint_1_nuevo_15;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeActivity extends AppCompatActivity {

    private AdaptadorFirestoreUI adaptador;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        recyclerView = findViewById(R.id.recycler_view);
        setupRecyclerView();
        loadDataFromFirestore();
    }

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
    }






    private void loadDataFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MascotasLista miLista = new MascotasLista();

        // Cambia el tipo de lugar solo para las mascotas y actualiza en Firestore
        for (Mascota mascota : miLista.listaLugares) {
            // Cambia el tipo de lugar según tus necesidades
          //  mascota.setTipoLugar("Nuevo Tipo de Lugar");


            // Actualiza en Firestore
            db.collection("mascotas").add(mascota);
        }

        // Inicia la escucha de cambios en Firestore
        adaptador.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adaptador.stopListening();
    }
}

/*
public class HomeActivity extends AppCompatActivity {

    private AdaptadorFirestoreUI adaptador;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        recyclerView = findViewById(R.id.recycler_view);
        setupRecyclerView();
        loadDataFromFirestore();
    }

    private void setupRecyclerView() {
        Query query = FirebaseFirestore.getInstance()
                .collection("lugares")
                .limit(50);

        FirestoreRecyclerOptions<Mascota> options = new FirestoreRecyclerOptions.Builder<Mascota>()
                .setQuery(query, Mascota.class)
                .build();

        adaptador = new AdaptadorFirestoreUI(options, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);
    }

    private void loadDataFromFirestore() {
        // Assuming you want to add data to Firestore, you can do it here.
        // Replace the following lines with your logic to add data to Firestore.
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MascotasLista miLista = new MascotasLista();
        for (Mascota lugar : miLista.listaLugares) {
            db.collection("lugares").add(lugar);
        }

        // Start listening to changes in Firestore
        adaptador.startListening();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adaptador.stopListening();
    }
}

 */

/*
public class HomeActivity extends AppCompatActivity {

    public static AdaptadorFirestoreUI adaptador;
      //  private RecyclerView mRecyclerView;

    private ActivityMainBinding binding;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.home);


            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            Query query = FirebaseFirestore.getInstance()
                    .collection("lugares")
                    .limit(50);
            FirestoreRecyclerOptions<Lugar> opciones = new FirestoreRecyclerOptions
                    .Builder<Lugar>().setQuery(query, Lugar.class).build();
            adaptador = new AdaptadorFirestoreUI(opciones, this);
            recyclerView.setAdapter(adaptador);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


            FirebaseFirestore db = FirebaseFirestore.getInstance();
            LugaresLista miLista = new LugaresLista();
            for (Lugar lugar: miLista.listaLugares) {
                db.collection("mascotas").add(lugar);

            }
            adaptador.startListening();
        }
    @Override protected void onDestroy() {
        super.onDestroy();
        adaptador.stopListening();
    }

}

 */
