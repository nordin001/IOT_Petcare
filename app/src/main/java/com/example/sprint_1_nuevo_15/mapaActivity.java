package com.example.sprint_1_nuevo_15;

import android.annotation.SuppressLint;
import android.content.Intent;
import  android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class mapaActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap mapa;
    private MascotasAsinc lugares;
    private int pos;
    private Mascota mascota;
    private AdaptadorFirestoreUI adaptador;
    public CasosDeUsoMascota usoMascota;
    private double latitude ,longitude;
    private  LatLng UPV ;//=new LatLng(38.995656,-0.166093);
    private GeoPunto geopunto;
    Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);
        Bundle extras = getIntent().getExtras();
        usoMascota = new CasosDeUsoMascota(this, lugares);
        adaptador = ((Aplicacion) getApplication()).adaptador;
        mascota = adaptador.getItem(pos);
        if (extras != null && extras.containsKey("pos")) {
            pos = extras.getInt("pos", 0);
            if (adaptador != null) {
                int itemCount = adaptador.getItemCount();
                Log.d("VistaMascotaActivity", "Adapter item count: " + itemCount);
                if (itemCount > 0 && pos >= 0 && pos < itemCount) {
                    mascota = adaptador.getItem(pos);
                    if (mascota != null) {
                        //UPV = new LatLng(mascota.getEdad(),);
                        latitude = mascota.getPosicion().getLatitud();
                        longitude = mascota.getPosicion().getLongitud();

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
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        //------------------------------------------


        //-------------toolbar-------------------------
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //------------------------------------------
        usoMascota = new CasosDeUsoMascota(this, lugares);
    }
    @Override public void onMapReady(GoogleMap googleMap) {

        UPV=new LatLng(latitude,longitude);
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mapa.getUiSettings().setZoomControlsEnabled(false);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(UPV, 15));
        mapa.addMarker(new MarkerOptions()
                .position(UPV)
                .title("La ultima ubicacio de su mascota :")
                .snippet(mascota.getNombre())
                .icon(BitmapDescriptorFactory
                        .fromResource(android.R.drawable.ic_menu_compass))
                .anchor(0.5f, 0.5f));
        mapa.setOnMapClickListener(this);
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
            mapa.getUiSettings().setCompassEnabled(true);
        }
    }
    public void moveCamera(View view) {
        mapa.moveCamera(CameraUpdateFactory.newLatLng(UPV));
    }
    public void animateCamera(View view) {
        mapa.animateCamera(CameraUpdateFactory.newLatLng(UPV));
    }
    public void addMarker(View view) {
        mapa.addMarker(new MarkerOptions().position(
                mapa.getCameraPosition().target));
    }
    @Override public void onMapClick(LatLng puntoPulsado) {
        mapa.addMarker(new MarkerOptions().position(puntoPulsado)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
    }

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
    public void lanzarDescubrir(View view){
        Intent i = new Intent(this,DescubrirActivity.class);
        startActivity(i);
    }
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
        if (id == R.id.descubrir) {

            lanzarDescubrir(null);
            //  getMenuInflater().inflate(R.menu.menu_main, item.getSubMenu());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
