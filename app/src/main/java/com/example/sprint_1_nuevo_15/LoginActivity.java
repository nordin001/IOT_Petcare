package com.example.sprint_1_nuevo_15;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    //atributos
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usernameEditText = findViewById(R.id.editTextTextEmailAddress2);
        passwordEditText = findViewById(R.id.editTextTextPassword3);
        loginButton = findViewById(R.id.button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString(); //obtener el texto ingresado
                String password = passwordEditText.getText().toString();

                if (isValidCredentials(username, password)) { //isValidCredentials es un método personalizado
                    // Inicio de sesion con exito
                    Toast.makeText(LoginActivity.this, "Has iniciado sesion con exito", Toast.LENGTH_SHORT).show();
                    // Redirect to the next screen or perform other actions
                } else {
                    // mensaje error
                    Toast.makeText(LoginActivity.this, "Credenciales invalidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidCredentials(String username, String password) {
        // Implement your validation and authentication logic here
        // Return true if the credentials are valid, otherwise return false
        return username.equals("your_valid_username") && password.equals("your_valid_password");
    }
}

