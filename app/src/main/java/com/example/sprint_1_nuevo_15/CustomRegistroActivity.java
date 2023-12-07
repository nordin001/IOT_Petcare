package com.example.sprint_1_nuevo_15;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.annotations.NonNull;

public class CustomRegistroActivity extends AppCompatActivity {

        private FirebaseAuth auth = FirebaseAuth.getInstance();
        private String correo = "";
        private String contraseña = "";
        private ViewGroup contenedor;
        private EditText etCorreo, etContraseña;
        private ProgressDialog dialogo;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.registro);

            etCorreo = findViewById(R.id.correElec); // Replace with your actual email EditText ID
            etContraseña = findViewById(R.id.contrasena); // Replace with your actual password EditText ID
            contenedor = findViewById(R.id.contenedor); // Replace with your actual container ViewGroup ID
            dialogo = new ProgressDialog(this);
            dialogo.setTitle("Registrando usuario");
            dialogo.setMessage("Por favor espere...");
        }

        public void registroCorreo(View v) {
            if (verificaCampos()) {
                dialogo.show();
                auth.createUserWithEmailAndPassword(correo, contraseña)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                dialogo.dismiss();
                                if (task.isSuccessful()) {
                                    // Registration successful
                                    Usuarios.guardarUsuario(auth.getCurrentUser());
                                    Intent i = new Intent(CustomRegistroActivity.this, MainActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                            | Intent.FLAG_ACTIVITY_NEW_TASK
                                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                    finish();
                                } else {
                                    // Registration failed
                                    mensaje(task.getException().getLocalizedMessage());
                                }
                            }
                        });
            }
        }

        private boolean verificaCampos() {
            correo = etCorreo.getText().toString();
            contraseña = etContraseña.getText().toString();

            if (correo.isEmpty() || !correo.matches(".+@.+[.].+")) {
                mensaje("Correo no válido !");
                return false;
            } else if (contraseña.isEmpty() || contraseña.length() < 6 ||
                    !contraseña.matches(".*[0-9].*") ||
                    !contraseña.matches(".*[A-Z].*")) {
                mensaje("Contraseña no válida. Debe contener al menos 6 caracteres, una letra mayúscula y un número.");
                return false;
            }
            return true;
        }

        private void mensaje(String mensaje) {
            Snackbar.make(contenedor, mensaje, Snackbar.LENGTH_LONG).show();
        }
    }

