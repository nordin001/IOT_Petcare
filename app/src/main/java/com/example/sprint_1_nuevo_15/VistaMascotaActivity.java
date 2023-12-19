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
import static com.example.comun.Mqtt.*;

import com.example.comun.Mqtt;

public class VistaMascotaActivity extends AppCompatActivity {
    private MascotasAsinc lugares;
    private int pos;
    private Mascota mascota;
    // private LugaresFirestore lugar;
    public CasosDeUsoMascota usoMascota;
    private Uri uriUltimaFoto;
    private AdaptadorFirestoreUI adaptador;
    public TextView peso,humedad,temperatura;
    Mqtt mqtt;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_mascota);
        //Configuracion de mqtt

        Log.d(TAG, "conectado a " + mqtt.broker);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("pos")) {
            pos = extras.getInt("pos", 0);
            if (adaptador != null) {
                int itemCount = adaptador.getItemCount();
                Log.d("VistaMascotaActivity", "Adapter item count: " + itemCount);
                if (itemCount > 0 && pos >= 0 && pos < itemCount) {
                    mascota = adaptador.getItem(pos);
                    if (mascota != null) {
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
        mascota = adaptador.getItem(pos);
        actualizaVistas();
        //-------------toolbar-------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //------------------------------------------
        usoMascota = new CasosDeUsoMascota(this, lugares);
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
        TextView direccion = findViewById(R.id.direccion);
        TextView edad =findViewById(R.id.edad);
        peso=findViewById(R.id.peso);
        humedad=findViewById(R.id.humedad);
        temperatura=findViewById(R.id.temperatura);
        lugares = ((Aplicacion) getApplication()).mascotas;
        adaptador = ((Aplicacion) getApplication()).adaptador;

       // logoTipo.setImageResource(lugar.getTipo().getRecurso());
        nombre.setText(mascota.getNombre());
        direccion.setText(mascota.getDireccion());
        peso.setText(String.valueOf(mascota.getPeso()));
        edad.setText(String.valueOf(mascota.getEdad()));
        humedad.setText(String.valueOf(mascota.getHumedad()));
        temperatura.setText(String.valueOf(mascota.getTemperatura()));
        // Check and hide TextViews when information is empty
        checkAndHideEmptyViews(direccion, mascota.getDireccion());



     //   NetworkImageView imageView = findViewById(R.id.foto); // Reference to your ImageView
        String uri = mascota.getFoto(); // Replace with the actual URI source

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
        i.putExtra("pos", pos);
        startActivity(i);
    }

    // INTENCIONES
    //---------------metodo gps abrir--------------------
    // Add a method to open MapsActivity
    private void abrirMapsActivity() {
        Intent intent = new Intent(this, mapaActivity.class);
        intent.putExtra("pos", pos);
        startActivity(intent);
    }
    //---------------------------------------
    //--------mandar mensaje mqtt------------
    //--------------------------------------
    public void encenderLuz(View v){
        mqtt = new Mqtt();
        String s = mqtt.publicar("a", "luz");
        Log.d(TAG, s);
        mqtt.desconectar();
    }

    public void capturarImagen(View v){
        mqtt = new Mqtt();
        String s = mqtt.publicar("a", "foto");
        Log.d(TAG, s);
        mqtt.desconectar();
    }

    //cuando salimos de la actividad se disconecta de la app

    @Override
    public void onDestroy() {
        String s = mqtt.desconectar();
        Log.d(TAG, s);
        super.onDestroy();
    }
}


