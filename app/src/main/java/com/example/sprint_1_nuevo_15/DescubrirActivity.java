package com.example.sprint_1_nuevo_15;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DescubrirActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descubrir_mascota);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    //------------------menu--------------
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
        if (id == R.id.descubrir) {

            lanzarDescubrir(null);
            //  getMenuInflater().inflate(R.menu.menu_main, item.getSubMenu());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void lanzarEditarPerfil(View view){
        Intent i = new Intent(this,EditarPerfil.class);
        startActivity(i);
    }

    public void lanzarDescubrir(View view){
        Intent i = new Intent(this,DescubrirActivity.class);
        startActivity(i);
    }
    public void lanzarAcercaDe(View view){
        Intent i = new Intent(this, MainActivity.AcercaDeActivity.class);
        startActivity(i);
    }


    public void abrirPrimerosPasosDescubrirMas(View view){
        Intent i = new Intent(this, PrimerosPasosDescubrirMasActivity.class);
        startActivity(i);
    }
    public void abrirIluminacionDescubrirMas(View view){
        Intent i = new Intent(this, PrimerosPasosDescubrirMasActivity.iluminacionDescubrirActivity.class);
        startActivity(i);
    }
    public void abrirSeguridadDescubrirMas(View view){
        Intent i = new Intent(this, PrimerosPasosDescubrirMasActivity.SeguridadDescubrirActivity.class);
        startActivity(i);
    }
}
