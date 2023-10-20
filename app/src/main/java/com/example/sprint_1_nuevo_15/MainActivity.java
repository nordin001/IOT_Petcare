package com.example.sprint_1_nuevo_15;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    //primer comentario de mimi
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // inicial xml


        // encontar el boton "Iniciar sesión" tras su id
        Button iniciarSesionButton = findViewById(R.id.inicioSesion);

        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creamos un intento de entrar en "login.xml" layout
                // la parte de codigo mainactivity.this, LoginActivity.class indica que quiero navegar de mainActivity en loginactivity
                Intent intento = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intento); //iniciar una nueva actividad
            }
        });
    }

}
