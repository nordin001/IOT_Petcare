package com.example.sprint_1_nuevo_15;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditarMascotaActivity extends AppCompatActivity {
    //--------editar mascota ---------------
    EditText editarNombre;
    EditText editarDierrecion;
    EditText editarPesp;
    EditText editarEdad;

    //-----------------------------------

    private MascotasAsinc lugares;
    private AdaptadorFirestoreUI adaptador;
    private Mascota mascota;
    // private LugaresFirestore lugar;
    public CasosDeUsoMascota usoMascota;
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
        adaptador = ((Aplicacion) getApplication()).adaptador;
        mascota = adaptador.getItem(pos);
        usoMascota = new CasosDeUsoMascota(this, lugares);
        if (adaptador != null && adaptador.getItemCount() > 0 && extras != null && extras.containsKey("pos")) {
            pos = extras.getInt("pos", 0);
            // Initialize EditText fields
            editarNombre = findViewById(R.id.editarNombre);
            editarDierrecion = findViewById(R.id.editarDierrecion);
            editarPesp = findViewById(R.id.editarPesp);
            editarEdad = findViewById(R.id.editarEdad);

            mascota = adaptador.getItem(pos); // Retrieve the Mascota object at position pos

            if (mascota != null) {
                editarNombre.setText(mascota.getNombre());
                editarDierrecion.setText(mascota.getDireccion());
                editarPesp.setText(String.valueOf(mascota.getPeso()));
                editarEdad.setText(String.valueOf(mascota.getEdad()));
                //  editarEdad.setText((int) mascota.getEdad());
            } else {
                Log.e("EditarMascotaActivity", "Error: Mascota object at position " + pos + " is null");
            }
        } else {
            Log.e("EditarMascotaActivity", "Error: Intent extras do not contain 'pos' or adaptador is empty");
        }


        //-------------toolbar-------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //------------------------------------------

/*
        // Obtener la referencia del botón por su ID
        Button btnAjustarCambios = findViewById(R.id.button5);

        // Asignar el evento de clic al botón
        btnAjustarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajustarCambios(v);
            }
        });


 */



        //4.BORRAR DATOS
        Button buttondELETE =findViewById(R.id.button6);
        buttondELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarPopupEliminarMascota(v);
            }
        });
    }


    //---------------  MENU -----------------
    //------------------menu--------------
    //El menu arriba para el acercade
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true; /** true -> el menú ya está visible*/}

    public static class AcercaDeActivity extends AppCompatActivity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.acercade);
        }
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
        if (id == R.id.descubrir) {

            lanzarDescubrir(null);
            //  getMenuInflater().inflate(R.menu.menu_main, item.getSubMenu());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void lanzarEditarPerfil(View view) {
        Intent i = new Intent(this, EditarPerfil.class);
        startActivity(i);
    }

    public void lanzarDescubrir(View view) {
        Intent i = new Intent(this, DescubrirActivity.class);
        startActivity(i);
    }

    public void lanzarAcercaDe(View view) {
        Intent i = new Intent(this, MainActivity.AcercaDeActivity.class);
        startActivity(i);
    }

    //----- ------------- fin menu--------------------------------------
    /*
    public void ajustarCambios(View view) {
        // Get the updated values from EditText fields
        String nombre = editarNombre.getText().toString();
        String direccion = editarDierrecion.getText().toString();
        double peso = Double.parseDouble(editarPesp.getText().toString());
        int edad = Integer.parseInt(editarEdad.getText().toString());

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        if (usuario != null) {
            // Obtener la referencia del documento de la mascota del usuario actual
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference mascotaRef = db.collection("mascotas").document(usuario.getUid());

            // Actualizar los campos de la mascota
            mascotaRef.update(
                    "nombre", nombre,
                    "direccion", direccion,
                    "peso", peso,
                    "edad", edad
            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // Actualización exitosa
                    Log.d("EditarMascotaActivity", "Datos de la mascota actualizados correctamente en Firestore");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Manejar errores
                    Log.e("EditarMascotaActivity", "Error al actualizar datos de la mascota en Firestore", e);
                }
            });
        }
    }

     */
/*

    public void ajustarCambios2(View view) {
        // Get the updated values from EditText fields
        String nombre = editarNombre.getText().toString();
        String direccion = editarDierrecion.getText().toString();
        double peso = Double.parseDouble(editarPesp.getText().toString());
        int edad = Integer.parseInt(editarEdad.getText().toString());

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();



 */
/*
        if (usuario != null) {
            // Obtener el ID del usuario actual
            String userId = usuario.getUid();

            // Obtener la referencia de la mascota del usuario actual
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            CollectionReference mascotasRef = db.collection("mascotas");

            // Consultar la mascota del usuario actual
            Query query = mascotasRef.whereEqualTo("userId", userId);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Obtener la referencia del documento de la mascota
                            DocumentReference mascotaRef = mascotasRef.document(document.getId());

                            // Actualizar los campos de la mascota
                            mascotaRef.update(
                                    "nombre", nombre,
                                    "direccion", direccion,
                                    "peso", peso,
                                    "edad", edad
                            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Actualización exitosa
                                    Log.d("EditarMascotaActivity", "Datos de la mascota actualizados correctamente en Firestore");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Manejar errores
                                    Log.e("EditarMascotaActivity", "Error al actualizar datos de la mascota en Firestore", e);
                                }
                            });
                        }
                    } else {
                        // Manejar errores en la consulta
                        Log.e("EditarMascotaActivity", "Error al consultar mascotas", task.getException());
                    }
                }
            });
        } else {
            // El usuario no está autenticado
            Log.e("EditarMascotaActivity", "Usuario no autenticado");
        }

 */
   // }


    public void ajustarCambios2(View view) {
        // Get the updated values from EditText fields
        String nombre = editarNombre.getText().toString();
        String direccion = editarDierrecion.getText().toString();
        double peso = Double.parseDouble(editarPesp.getText().toString());
        int edad = Integer.parseInt(editarEdad.getText().toString());
/*
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
                        Intent i = new Intent(EditarMascotaActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(EditarMascotaActivity.this, "Error updating data", Toast.LENGTH_SHORT).show();
                    }
                });

 */

        // Create a map with updated data
        Map<String, Object> updates = new HashMap<>();
        updates.put("nombre", nombre);
        updates.put("direccion", direccion);
        updates.put("peso", peso);
        updates.put("edad", edad);

// Update the data in Firestore
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

        // Get the reference to the Firestore collection and document
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference mascotaRef = db.collection("mascotas").document(usuario.getUid());
        mascotaRef
                .update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Redirect to another activity or perform any other necessary actions
                        Intent i = new Intent(EditarMascotaActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(EditarMascotaActivity.this, "Error updating data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //--------------pop up delete mascota si no ---------
    public static class PopupEliminarMascotaActivity extends AppCompatActivity {
        @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.eliminarmascotapregunta);
            //4.BORRAR DATOS
            Button buttonnodeleete =findViewById(R.id.btnNo);
            buttonnodeleete .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lanzarhomeDe();
                }
            });

            //4.BORRAR DATOS
            Button buttondELETE =findViewById(R.id.btnSi);
            buttondELETE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

                    // Get the reference to the Firestore collection and document
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    //Vziimam ot Firestore
                    db.collection("mascotas").document(usuario.getUid())
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(PopupEliminarMascotaActivity.this, "Mascota eliminada correctamente", Toast.LENGTH_SHORT).show();
                                        lanzarhomeDe();
                                    } else {
                                        Toast.makeText(PopupEliminarMascotaActivity.this, "Error al eliminar mascota: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    //Handle error here
                                    Log.e("ERROR", e.getMessage());
                                    Toast.makeText(PopupEliminarMascotaActivity.this, "Error desconocido al eliminar mascota", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });

        }
        private void lanzarhomeDe() {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            // Asegúrate de que la actividad actual se cierre después de iniciar HomeActivity
            finish();
        }
    }
    public void lanzarPopupEliminarMascota(View view) {
        Intent i = new Intent(this, EditarMascotaActivity.PopupEliminarMascotaActivity.class);
        startActivity(i);
    }
    //----- ------------- fin menu--------------------------------------

}

