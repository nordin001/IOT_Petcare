package com.example.sprint_1_nuevo_15;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditarMascotaActivity extends AppCompatActivity {

    private MascotasAsinc lugares;
    private AdaptadorFirestoreUI adaptador;
    private int pos;

    // Add other necessary fields for your UI elements (e.g., EditText fields)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_mascota);
        lugares = ((Aplicacion) getApplication()).mascotas;
        adaptador = ((Aplicacion) getApplication()).adaptador;


        // Retrieve position from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("pos")) {
            pos = extras.getInt("pos", 0);

            // Retrieve the data based on position
            Mascota selectedMascota = adaptador.getItem(pos);

            // For example, if you have an EditText for the name:
            EditText nombreEditText = findViewById(R.id.editTextNombre);
            nombreEditText.setText(selectedMascota.getNombre());

            // Repeat the above steps for other UI elements

        } else {
            Log.e("EditarMascotaActivity", "Error: Intent extras do not contain 'pos'");
        }


        //-------------toolbar-------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //------------------------------------------

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


    // Add methods to handle saving or updating the edited data

    // ...

}
