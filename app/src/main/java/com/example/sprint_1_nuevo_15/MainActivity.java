package com.example.sprint_1_nuevo_15;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //primer comentario de mimi
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //conectar
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        TextView nombre = findViewById(R.id.nombre);
        TextView correo = findViewById(R.id.correo);
        TextView provedor = findViewById(R.id.proveedor);
        TextView uid = findViewById(R.id.uid);
        TextView telefono=findViewById(R.id.telefono);
        Button cerrarSesion=findViewById(R.id.btn_cerrar_sesion);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nombre.setText(usuario.getDisplayName());
        correo.setText(usuario.getEmail());
        provedor.setText(usuario.getProviderId());
        uid.setText(usuario.getUid());
        telefono.setText(usuario.getPhoneNumber());
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creamos un intento de entrar en "login.xml" layout
                // la parte de codigo mainactivity.this, LoginActivity.class indica que quiero navegar de mainActivity en loginactivity
               cerrarSesion(v);
            }

            // FireStore bd

        });
        //--------------firestore-basedatos-----------
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> datos = new HashMap<>();
        datos.put("dato_1", "hola mundo");
        datos.put("dato_2", 35);
        db.collection("coleccion").document("documento").set(datos);


        //----mostrar la foto---------------
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



        Button home2Button = findViewById(R.id.home2);

        home2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMimacotaLayout();
            }
        });

        }


    //------------------cerrar sesion metodo-------------------------
    public void cerrarSesion(View view) {
        AuthUI.getInstance().signOut(getApplicationContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent i = new Intent(
                                getApplicationContext (), CustomLogInActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();
                    }
                });
    }
    //---------------------------------------------------------------------

    //El menu arriba para el acercade
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
        Intent i = new Intent(this, AcercaDeActivity.class);
        startActivity(i);
    }
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


    public void lanzarEditarPerfil(View view){
        Intent i = new Intent(this,EditarPerfil.class);
        startActivity(i);
    }

    public void lanzarHome(View view){
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);
    }

    private void openMimacotaLayout() {
        // Inflate the mimacota.xml layout and add it to the current view
        LayoutInflater inflater = getLayoutInflater();
        View mimacotaLayout = inflater.inflate(R.layout.editar_mascota, null);

        // Replace the current content view with the mimacotaLayout
        setContentView(mimacotaLayout);
    }
}
