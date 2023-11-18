package com.example.sprint_1_nuevo_15;
import com.example.sprint_1_nuevo_15.Usuario;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

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
