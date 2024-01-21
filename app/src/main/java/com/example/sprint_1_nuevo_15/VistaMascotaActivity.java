package com.example.sprint_1_nuevo_15;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.comun.Mqtt.*;

import com.example.comun.Mqtt;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class VistaMascotaActivity extends AppCompatActivity {
    private MascotasAsinc lugares;
    private static final String CHANNEL_ID = "MyChannel";
    private int pos;
    private Mascota mascota;
    // private LugaresFirestore lugar;
    public CasosDeUsoMascota usoMascota;
    private Uri uriUltimaFoto;
    private AdaptadorFirestoreUI adaptador;
    public TextView peso, humedad, temperatura;
    public ImageView firestoragepic;
    Mqtt mqtt;
    private StorageReference storageRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_mascota);
        storageRef = FirebaseStorage.getInstance().getReference();
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
        firestoragepic = findViewById(R.id.firestoragepic);

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
        TextView edad = findViewById(R.id.edad);
        peso = findViewById(R.id.peso);
        humedad = findViewById(R.id.humedad);
        temperatura = findViewById(R.id.temperatura);
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
        if (mascota.getTemperatura() > 35) {

            createNotificationChannel();
            showNotification("Alta Temperatura", "El perro se esta quemando ");
        } else if (mascota.getTemperatura() < 3) {
            createNotificationChannel();
            showNotification("Muy Baja Temperatura", "El perro se esta congenlando");
        }
        if (mascota.getHumedad() > 70) {
            createNotificationChannel();
            showNotification("Alta Humedad", "El perro no `puede respirar");
        }


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
    //------------------ fin menu--------------------------------------
    // METODOS
    public void editarMascota(View view) {
        Intent i = new Intent(this, EditarMascotaActivity.class);
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
    public void encenderLuz(View v) {
        mqtt = new Mqtt();
        String s = mqtt.publicar("luz", "luz");
        Log.d(TAG, s);
        mqtt.desconectar();
    }

    public void capturarImagen(View v) {
        mqtt = new Mqtt();
        String s = mqtt.publicar("foto", "foto");
        Log.d(TAG, s);
        mqtt.desconectar();
        File localFile = null;
        try {
            localFile = File.createTempFile("image", "jpg"); //nombre y extensión
        } catch (IOException e) {
            e.printStackTrace(); //Si hay problemas mostramos la causa
        }
        final String path = localFile.getAbsolutePath();
        Log.d("Almacenamiento", "creando fichero: " + path);
        //--------------------------------------------------------------
        StorageReference ficheroRef = storageRef.child("imagenes/imagen.jpg");
        ficheroRef.getFile(localFile)
                .addOnSuccessListener(new
                                              OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                  @Override
                                                  public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                      Log.d("Almacenamiento", "Fichero bajado" + path);
                                                      //Aquí ya disponemos del fichero
                                                      firestoragepic.setImageBitmap(BitmapFactory.decodeFile(path));
                                                  }
                                              }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("Almacenamiento", "ERROR: bajando fichero");
                    }
                });
        firestoragepic.setImageBitmap(BitmapFactory.decodeFile(path));


    }

    //cuando salimos de la actividad se disconecta de la app

    @Override
    public void onDestroy() {
        String s = mqtt.desconectar();
        Log.d(TAG, s);
        super.onDestroy();
    }


    private void showAlertDialog(String title, String message) {
        // Use the Builder class to construct the alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the dialog title and message
        builder.setTitle(title)
                .setMessage(message);

        // Set positive button and its click listener
        builder.setPositiveButton("OK", null);

        // Create the AlertDialog object and show it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showNotification(String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_mascota) // Replace with your own icon
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        int notificationId = 1;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            notificationManager.notify(notificationId, builder.build());
            return;
        }
        notificationManager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "My Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}


