package com.example.sprint_1_nuevo_15;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditarPerfil extends AppCompatActivity {
    EditText editTextPhone;
    EditText editTextNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        //---------------------------Mostrar datos-------------------------------------
        //conectar los layout con us funciones
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        TextView nombre = findViewById(R.id.nombre);
        TextView correo = findViewById(R.id.correo);
        TextView telefono=findViewById(R.id.telefonoUser);
        editTextPhone=findViewById(R.id.editTextPhone);
        editTextNombre=findViewById(R.id.editTextNombre);
        Button cerrarSesion=findViewById(R.id.btn_cerrar_sesion);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nombre.setText(usuario.getDisplayName());
        correo.setText(usuario.getEmail());
        editTextNombre.setText(usuario.getDisplayName());
        editTextPhone.setText(usuario.getPhoneNumber());
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creamos un intento de entrar en "login.xml" layout
                // la parte de codigo mainactivity.this, LoginActivity.class indica que quiero navegar de mainActivity en loginactivity
                cerrarSesion(v);
            }
        });
        //foto usuario
        RequestQueue colaPeticiones = Volley.newRequestQueue(this);
        ImageLoader lectorImagenes = new ImageLoader(colaPeticiones,
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

        //------------------foto del usuario-----------
        Uri urlImagen = usuario.getPhotoUrl();
        if (urlImagen != null) {
            NetworkImageView foto = (NetworkImageView) findViewById(R.id.imagen);
            foto.setImageUrl(urlImagen.toString(), lectorImagenes);}
    }
    //------------------fin foto de usuario
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; /** true -> el menú ya está visible*/}
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
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.acercaDe) {
            lanzarAcercaDe(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void lanzarHome(View view){
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);
    }
    public void cerrarSesion(View view) {
        AuthUI.getInstance().signOut(getApplicationContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(
                                getApplicationContext (),LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                });
    }

    public void ajustarCambios(View view){

        String nombre=editTextNombre.getText().toString();
        String telefono =editTextPhone.getText().toString();
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest perfil = new UserProfileChangeRequest.Builder()
                .setDisplayName(nombre)
                .build();
        usuario.updateProfile(perfil).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(EditarPerfil.this, "", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(EditarPerfil.this, "Cambiado con exito", Toast.LENGTH_LONG).show();
                }
            }
        });
        if (!TextUtils.isEmpty(telefono)) {
            // Actualizar el número de teléfono
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(usuario.getPhoneNumber(), "Código_de_verificación");
            usuario.updatePhoneNumber(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditarPerfil.this, "Número de teléfono actualizado con éxito", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EditarPerfil.this, "Error al actualizar el número de teléfono", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        //usuario.updatePhoneNumber(888888888);

        //------------abrir Main Activity despues de ajustar los cambios asi se refresca tambien
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);


    }
}