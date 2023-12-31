package com.example.sprint_1_nuevo_15;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AniadirMascotaActivity  extends AppCompatActivity {
    //--------editar mascota ---------------
    EditText editarNombre;
    EditText editarDierrecion;
    EditText editarPesp;
    EditText editarEdad;
    private Mascota mascota;
    private MascotasAsinc lugares;
    private AdaptadorFirestoreUI adaptador;
    private int pos;

    public CasosDeUsoMascota usoMascota;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.aniadir_mascota);

        lugares = ((Aplicacion) getApplication()).mascotas;
        adaptador = ((Aplicacion) getApplication()).adaptador;
        // Retrieve position from the intent
        Bundle extras = getIntent().getExtras();
        adaptador = ((Aplicacion) getApplication()).adaptador;
        mascota = adaptador.getItem(pos);


        if (adaptador != null && adaptador.getItemCount() > 0 && extras != null && extras.containsKey("pos")) {
            pos = extras.getInt("pos", 0);
            // Initialize EditText fields
            editarNombre = findViewById(R.id.editarNombre);
            editarDierrecion = findViewById(R.id.editarDierrecion);
            editarPesp = findViewById(R.id.editarPesp);
            editarEdad = findViewById(R.id.editarEdad);
        }
        //-------------toolbar-------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //------------------------------------------
    }

    //---------------  MENU -----------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
        */


 /*
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public void lanzarEditarPerfil(View view) {
        Intent i = new Intent(this, EditarPerfil.class);
        startActivity(i);
    }

    public static class AcercaDeActivity extends AppCompatActivity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.acercade);
        }
    }

    public void lanzarAcercaDe(View view) {
        Intent i = new Intent(this, MainActivity.AcercaDeActivity.class);
        startActivity(i);
    }

    //----- ------------- fin menu--------------------------------------




  */

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.aniadir_mascota);
            lugares = ((Aplicacion) getApplication()).mascotas;
            adaptador = ((Aplicacion) getApplication()).adaptador;

            // Retrieve position from the intent
            Bundle extras = getIntent().getExtras();
            adaptador = ((Aplicacion) getApplication()).adaptador;
            mascota = adaptador.getItem(pos);
            usoMascota = new CasosDeUsoMascota(this, lugares);
            if (adaptador != null && adaptador.getItemCount() > 0 && extras != null && extras.containsKey("pos")) {
                pos = extras.getInt("pos", 0);
                // Initialize EditText fields


                mascota = adaptador.getItem(pos); // Retrieve the Mascota object at position pos


            } else {
                Log.e("aniadir", "Error: Intent extras do not contain 'pos' or adaptador is empty");
            }
            editarNombre = findViewById(R.id.editarNombre);
            editarDierrecion = findViewById(R.id.editarDierrecion);
            editarPesp = findViewById(R.id.editarPesp);
            editarEdad = findViewById(R.id.editarEdad);


            //-------------toolbar-------------------------
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            //------------------------------------------


            findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aniadirmascota();
                }
            });
        }


    //---------------  MENU -----------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; /** true -> el menú ya está visible*/}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public void lanzarEditarPerfil(View view) {
        Intent i = new Intent(this, EditarPerfil.class);
        startActivity(i);
    }

    public static class AcercaDeActivity extends AppCompatActivity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.acercade);
        }
    }

    public void lanzarAcercaDe(View view) {
        Intent i = new Intent(this, MainActivity.AcercaDeActivity.class);
        startActivity(i);
    }


    //------------------------------------------------------------------------------------
    /*
    public void  aniadirmascota(View view) {
        // Get the updated values from EditText fields
        String nombre = editarNombre.getText().toString();
        String direccion = editarDierrecion.getText().toString();
        double peso = Double.parseDouble(editarPesp.getText().toString());
        int edad = Integer.parseInt(editarEdad.getText().toString());

        // Assuming 'mascota' is an instance of your Mascota class
        mascota.setNombre(nombre);
        mascota.setDireccion(direccion);
        mascota.setPeso(peso);
        mascota.setEdad(edad);

        // Get the current user
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        // Get the reference to the Firestore collection and document
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference mascotaRef = db.collection("mascotas").document(usuario.getUid());

        // Update the data in Firestore
        mascotaRef
                .set(mascota)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Redirect to another activity or perform any other necessary actions
                        Intent i = new Intent(AniadirMascotaActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(AniadirMascotaActivity.this, "Error updating data", Toast.LENGTH_SHORT).show();
                    }
                });
    }



     */
    /*
    private void aniadirmascota() {
        String nombre = editarNombre.getText().toString();
        String direccion = editarDierrecion.getText().toString();
        double peso = Double.parseDouble(editarPesp.getText().toString());
        int edad = Integer.parseInt(editarEdad.getText().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        // Referencia al documento del usuario específico
        DocumentReference userDocRef =  db.collection("mascotas").document(user.getUid());


// Crea un mapa con los datos de la mascota
        Map<String, Object> mascota = new HashMap<>();
        mascota.put("nombre", nombre);
        mascota.put("direccion", direccion);
        mascota.put("peso", peso);
        mascota.put("edad", edad);


// Agrega la mascota al documento del usuario
        userDocRef.collection("mascotas").add(mascota)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("aniadir", "Mascota agregada con ID: " + documentReference.getId());

                        // Aquí puedes mostrar un mensaje o realizar alguna otra acción si es necesario
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("aniadir", "Error al agregar la mascota", e);
                        // Maneja el error aquí si es necesario
                    }
                });



    }


     */

    private void aniadirmascota() {
        String nombre = editarNombre.getText().toString();
        String direccion = editarDierrecion.getText().toString();
        double peso = Double.parseDouble(editarPesp.getText().toString());
        int edad = Integer.parseInt(editarEdad.getText().toString());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Referencia al documento del usuario específico
        DocumentReference userDocRef = db.collection("mascotas").document();

        // Crea un mapa con los datos de la mascota
        Map<String, Object> mascota = new HashMap<>();
        mascota.put("nombre", nombre);
        mascota.put("direccion", direccion);
        mascota.put("peso", peso);
        mascota.put("edad", edad);

        // Agrega o sobrescribe la mascota en el documento del usuario
        userDocRef.set(mascota)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("aniadir", "Datos de la mascota agregados con éxito.");

                        // Aquí puedes mostrar un mensaje o realizar alguna otra acción si es necesario
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("aniadir", "Error al agregar los datos de la mascota", e);
                        // Maneja el error aquí si es necesario
                    }
                });
    }

    //----------------------------------------------------


}
