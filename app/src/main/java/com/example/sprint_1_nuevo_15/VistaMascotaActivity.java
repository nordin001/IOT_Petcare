package com.example.sprint_1_nuevo_15;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
public class VistaMascotaActivity extends AppCompatActivity {
    private MascotasAsinc lugares;
    private int pos;
    private Mascota lugar;
    // private LugaresFirestore lugar;
    public CasosDeUsoMascota usoLugar;
    private Uri uriUltimaFoto;
    private AdaptadorFirestoreUI adaptador;
    public TextView peso,humedad,temperatura;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_mascota);
        peso=findViewById(R.id.peso);
        humedad=findViewById(R.id.humedad);
        temperatura=findViewById(R.id.temperatura);
        lugares = ((Aplicacion) getApplication()).mascotas;
        adaptador = ((Aplicacion) getApplication()).adaptador;
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("pos")) {
            pos = extras.getInt("pos", 0);
            if (adaptador != null) {
                int itemCount = adaptador.getItemCount();
                Log.d("VistaMascotaActivity", "Adapter item count: " + itemCount);
                if (itemCount > 0 && pos >= 0 && pos < itemCount) {
                    lugar = adaptador.getItem(pos);

                    if (lugar != null) {
                        actualizaVistas();
                    } else {
                        Log.e("VistaMascotaActivity", "Error: lugar is null");
                    }
                } else {
                    Log.e("VistaMascotaActivity", "Error: Invalid position or adapter is empty");
                }
            } else {
                Log.e("VistaMascotaActivity", "Error: Adapter is null");
            }
        } else {
            Log.e("VistaMascotaActivity", "Error: Intent extras do not contain 'pos'");
        }

        adaptador = ((Aplicacion) getApplication()).adaptador;
        lugar = adaptador.getItem(pos);
        actualizaVistas();
        //-------------toolbar-------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //------------------------------------------
        usoLugar = new CasosDeUsoMascota(this, lugares);



        ///-----------btootn gps-----------------------
        ImageView imageView4 = findViewById(R.id.imageView4);

        // Set an OnClickListener for the ImageView
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open MapsActivity when the ImageView is clicked
                abrirMapsActivity();
            }
        });


    }

    public void actualizaVistas() {
        TextView nombre = findViewById(R.id.nombre);
        ImageView logoTipo = findViewById(R.id.foto);
       // TextView tipo = findViewById(R.id.tipo);
        TextView direccion = findViewById(R.id.direccion);
        TextView telefono = findViewById(R.id.direccion2);

        nombre.setText(lugar.getNombre());
       // logoTipo.setImageResource(lugar.getTipo().getRecurso());

        direccion.setText(lugar.getDireccion());


        // Check and hide TextViews when information is empty
        checkAndHideEmptyViews(direccion, lugar.getDireccion());



     //   NetworkImageView imageView = findViewById(R.id.foto); // Reference to your ImageView
        String uri = lugar.getFoto(); // Replace with the actual URI source

    }

    // Metodo para ocultar las vistas si no hay informacion sobre ellas
    private void checkAndHideEmptyViews(TextView textView, String text) {
        ImageView imageView = null; // para las imagenes

        //identificar la imagen con la id del texto
        int textViewId = textView.getId();


        if (text == null || text.isEmpty()) {
            textView.setVisibility(View.GONE);
            if (imageView != null) {
                imageView.setVisibility(View.GONE);
            }
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
            if (imageView != null) {
                imageView.setVisibility(View.VISIBLE);
            }
        }
    }

    //---------------  MENU -----------------
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; /** true -> el menú ya está visible*/}

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
 //---------------METODOS MENU----------------------


    public void lanzarEditarPerfil(View view){
        Intent i = new Intent(this,EditarPerfil.class);
        startActivity(i);
    }
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
    //------------------ fin menu--------------------------------------
    // METODOS
    public void editarMascota(View view){
        Intent i = new Intent(this,EditarMascotaActivity.class);
        startActivity(i);
    }

    // INTENCIONES
    //---------------metodo gps abrir--------------------
    // Add a method to open MapsActivity
    private void abrirMapsActivity() {
        Intent intent = new Intent(this, mapaActivity.class);
        startActivity(intent);
    }
}


