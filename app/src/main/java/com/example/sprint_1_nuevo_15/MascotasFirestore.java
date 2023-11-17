package com.example.sprint_1_nuevo_15;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import android.util.Log;

import com.example.sprint_1_nuevo_15.Mascota;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import androidx.annotation.NonNull;

public class MascotasFirestore implements MascotasAsinc {
    private CollectionReference mascotas;


    public MascotasFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mascotas = db.collection("mascotas");
    }

    public Query getQuery() {
        return mascotas.limit(2); // Adjust this query as needed
    }

    public void elemento(String id, final EscuchadorElemento escuchador) {
        mascotas.document(id).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Mascota mascota = task.getResult().toObject(Mascota.class);
                            escuchador.onRespuesta(mascota);
                        } else {
                            Log.e("Firebase", "Error al leer", task.getException());
                            escuchador.onRespuesta(null);
                        }
                    }
                });}
    public void añade(Mascota mascota) {
        mascotas.document().set(mascota); //o lugares.add(lugar);
    }
    public String nuevo() {
        return mascotas.document().getId();
    }
    public void borrar(String id) {
        mascotas.document(id).delete();
    }
    public void actualiza(String id, Mascota mascota) {
        mascotas.document(id).set(mascota);
    }
    public void tamaño(final EscuchadorTamaño escuchador) {
        mascotas.get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            escuchador.onRespuesta(task.getResult().size());
                        } else {
                            Log.e("Firebase","Error en tamaño",task.getException());
                            escuchador.onRespuesta(-1);
                        }
                    }
                });
    }
}
