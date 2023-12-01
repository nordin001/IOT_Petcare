package com.example.sprint_1_nuevo_15;
import android.util.Log;

import com.example.sprint_1_nuevo_15.Usuario;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
/*
public class Usuarios {

    public static void guardarUsuario(final FirebaseUser user) {
        Usuario usuario = new Usuario(user.getDisplayName(), user.getEmail());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(user.getUid()).set(usuario);
    }

    public static void actualizarUsuario(final FirebaseUser user, String nuevoNombre, String nuevoCorreo,String nuevotelefono) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(user.getUid()).update(
                "nombre", nuevoNombre,
                "correo", nuevoCorreo,
                "telefono",nuevotelefono
        );
    }

    public static void actualizarUsuarioNOCORREO(final FirebaseUser user, String nuevoNombre,String nuevotelefono, String nuevoCorreo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(user.getUid()).update(
                "nombre", nuevoNombre,
                "correo",nuevoCorreo,
                "telefono",nuevotelefono
        );
    }
}
 */




public class Usuarios {

    public static void guardarUsuario(final FirebaseUser user) {
        Usuario usuario = new Usuario(user.getDisplayName(), user.getEmail());
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Set the user's mascotaIds to an empty list initially
        usuario.setMascotaIds(Collections.emptyList());

        // Save the user document to the "usuarios" collection
        db.collection("usuarios").document(user.getUid()).set(usuario);
    }

    public static void agregarMascotaUsuario(String userId, String mascotaId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Add the mascotaId to the user's mascotaIds list
        db.collection("usuarios").document(userId)
                .update("mascotaIds", FieldValue.arrayUnion(mascotaId));
    }

    public static void actualizarUsuario(final FirebaseUser user, String nuevoNombre, String nuevoCorreo, String nuevoTelefono) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(user.getUid()).update(
                "nombre", nuevoNombre,
                "correo", nuevoCorreo,
                "telefono", nuevoTelefono
        );
    }

    public static void actualizarUsuarioNOCORREO(final FirebaseUser user, String nuevoNombre, String nuevoTelefono, String nuevoCorreo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(user.getUid()).update(
                "nombre", nuevoNombre,
                "correo", nuevoCorreo,
                "telefono", nuevoTelefono
        );
    }

    public static void asociarMascota(final FirebaseUser user, final Mascota mascota) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Añadir la mascota al documento del usuario
        db.collection("usuarios").document(user.getUid())
                .collection("mascotas")
                .add(mascota)
                .addOnSuccessListener(documentReference -> {
                    // Obtener el ID de la mascota recién añadida
                    String mascotaId = documentReference.getId();

                    // Actualizar el usuario con el ID de la mascota
                    Usuarios.agregarMascotaUsuario(user.getUid(), mascotaId);
                })
                .addOnFailureListener(e -> {
                    // Manejar el fallo al añadir la mascota
                    Log.e("Firebase", "Error al asociar mascota al usuario", e);
                });
    }

}

