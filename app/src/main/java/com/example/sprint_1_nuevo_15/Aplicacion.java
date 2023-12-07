package com.example.sprint_1_nuevo_15;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import android.app.Application;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.sprint_1_nuevo_15.Mascota;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import android.util.LruCache;

public class Aplicacion extends Application {
    public ImageLoader lectorImagenes;
    public MascotasAsinc mascotas;
    public AdaptadorFirestoreUI adaptador;
    private String userId2;
    @Override public void onCreate() {
        super.onCreate();
        mascotas = new MascotasFirestore();
        String userId = "11ck4jpBdvfrQKox6bYi2g7ws4H3";//pruebas
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        if (usuario != null) {
            userId2 = usuario.getUid();
        } else {
            // Handle the case when no user is logged in
            userId2 = "defaultUserId"; // Provide a default user ID or handle it accordingly
        }
        Query query = FirebaseFirestore.getInstance()
                .collection("mascotas")
                .whereEqualTo("userid",userId2)  // Replace "userId" with the actual field name in the users collection
                .limit(5);
        FirestoreRecyclerOptions<Mascota> opciones =new FirestoreRecyclerOptions
                .Builder<Mascota>().setQuery(query, Mascota.class).build();
        //adaptador = new AdaptadorFirestoreUI(opciones, this);
        adaptador = new AdaptadorFirestoreUI(getApplicationContext(), opciones);
        // Inicialización Volley (En Applicaction, para hacerlo solo una vez)
        RequestQueue colaPeticiones = Volley.newRequestQueue(this);
        lectorImagenes = new ImageLoader(colaPeticiones,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache =
                            new LruCache<String, Bitmap>(10);
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }
                });
    }
}
