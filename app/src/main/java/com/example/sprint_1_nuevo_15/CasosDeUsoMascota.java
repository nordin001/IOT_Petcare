package com.example.sprint_1_nuevo_15;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;


import com.example.sprint_1_nuevo_15.MascotasAsinc;
import com.example.sprint_1_nuevo_15.Mascota;
//import com.example.sprint_1_nuevo_15.RepositorioLugares;
//import com.example.sprint_1_nuevo_15.EdicionLugarActivity;
import com.example.sprint_1_nuevo_15.VistaMascotaActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CasosDeUsoMascota {
    private Activity actividad;
    private MascotasAsinc mascotas;

    public CasosDeUsoMascota(Activity actividad, MascotasAsinc mascota) {
        this.actividad = actividad;
        this.mascotas = mascota;
    }

    // OPERACIONES BÁSICAS
    public void mostrar(int pos) {
        Intent i = new Intent(actividad, VistaMascotaActivity.class);
        i.putExtra("pos", pos);
        actividad.startActivity(i);
    }
/*
    public void editar(int pos, ActivityResultLauncher<Intent> launcher) {
        Intent i = new Intent(actividad, EdicionLugarActivity.class);
        i.putExtra("pos", pos);
        launcher.launch(i);
    }

 */

    //-------------------------

    public void guardar(String id, Mascota  nuevaMascota) {
        mascotas.actualiza(id, nuevaMascota);
    }

    public void borrar(final String id) {
        new AlertDialog.Builder(actividad)
                .setTitle("Borrado de mascota")
                .setMessage("¿Estás seguro que quieres eliminar esta mascota?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mascotas.borrar(id);
                        actividad.finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

 //-----------------update data------------------------
        private FirebaseFirestore db;

        public CasosDeUsoMascota() {
            this.db = FirebaseFirestore.getInstance();
        }

        public void actualizarDatosMascota(String userId, String nombre, String direccion, double peso, int edad) {
            // Assuming you have a "mascotas" collection in Firestore
            DocumentReference mascotaRef = db.collection("mascotas").document(userId);

            // Update the fields in the document
            mascotaRef.update(
                    "nombre", nombre,
                    "direccion", direccion,
                    "peso", peso,
                    "edad", edad
            );
        }
    }


