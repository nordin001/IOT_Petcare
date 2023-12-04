package com.example.sprint_1_nuevo_15;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
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

/*

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


 */
/*
public abstract class MascotasFirestore implements MascotasAsinc {
    private CollectionReference mascotas;

    public MascotasFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mascotas = db.collection("mascotas");
    }

    // Add a new parameter to associate the pet with a user
    //-------------------------MASCOTAS DE USUARIO ---------------------
    public void añade(Mascota mascota, String userId) {
        // Set the ownerID field in the mascota document to associate it with the user
        mascota.setOwnerID(userId);

        // Add the mascota document to the "mascotas" collection
        mascotas.add(mascota);
    }

    public Query getQuery() {
        // Provide a default query here if needed
        return mascotas;
    }

    public Query getQueryForUser(String userId) {
        return mascotas.whereEqualTo("ownerID", userId);
    }


    //-----------------------FIN DE MASCOTAS DE USUARIO--------------

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
                });
    }


}

 */

public class MascotasFirestore implements MascotasAsinc {
    private CollectionReference mascotas;


    public MascotasFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mascotas = db.collection("mascotas");
    }

    @Override
    public void añade(Mascota mascota, String userId) {
        mascota.setUserid(userId);
        mascotas.add(mascota);
    }

    @Override
    public Query getQuery() {
        return mascotas;
    }

    @Override
    public Query getQueryForUser(String userId) {
        return mascotas.whereEqualTo("ownerID", userId);
    }

    @Override
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
                });
    }

    @Override
    public String nuevo() {
        return mascotas.document().getId();
    }

    @Override
    public void borrar(String id) {
        mascotas.document(id).delete();
    }

    @Override
    public void actualiza(String id, Mascota mascota) {
        mascotas.document(id).set(mascota);
    }

    @Override
    public void tamaño(EscuchadorTamaño escuchador) {
        mascotas.get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        escuchador.onRespuesta(task.getResult().size());
                    } else {
                        Log.e("Firebase", "Error al obtener el tamaño", task.getException());
                        escuchador.onRespuesta(0);
                    }
                });
    }
}

