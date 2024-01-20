package com.example.sprint_1_nuevo_15;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import android.view.View;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.SignInMethodQueryResult;

//-----customlogidm------------------------
public class CustomLogInActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String correo = "";
    private String contraseña = "";
    private ViewGroup contenedor;
    private EditText etCorreo, etContraseña;
    private TextInputLayout tilCorreo, tilContraseña;
    private ProgressDialog dialogo;

    //-------------inicio personalizado con google--------
    private static final int RC_GOOGLE_SIGN_IN = 123;
    GoogleSignInClient googleSignInClient;

    //----------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etCorreo = findViewById(R.id.correElec);
        etContraseña = findViewById(R.id.contrasena);
        contenedor = findViewById(R.id.contenedor);
        dialogo = new ProgressDialog(this);
        dialogo.setTitle("Verificando usuario");
        dialogo.setMessage("Por favor espere...");

        verificaSiUsuarioValidado();


        //--------------entrar con googleee-----------------

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_cleint_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    //---------------metodo de si el usuario es valido ----------------

    private void verificaSiUsuarioValidado() {
        if (auth.getCurrentUser() != null) {
            Usuarios.guardarUsuario(auth.getCurrentUser());
           // Intent i = new Intent(this, MainActivity.class);
            Intent i = new Intent(this, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    }


    //-------------------metodos de registro y inicio -----------------
    public void inicioSesiónCorreo(View v) {
        if (verificaCampos()) {
            dialogo.show();
            auth.signInWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            dialogo.dismiss();
                            if (task.isSuccessful()) {
                                verificaSiUsuarioValidado();
                            } else {
                                handleFirebaseAuthException(task.getException());
                            }
                        }
                    });
        }
    }

    private void handleFirebaseAuthException(Exception exception) {
        if (exception instanceof FirebaseAuthInvalidUserException) {
            mensaje("Invalid email or unregistered user");
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            mensaje("Invalid password");
        } else {
            mensaje(exception.getLocalizedMessage());
        }
    }

    //------------------MENSAJE DE ERROR-------------------------------

    private void mensaje(String mensaje) {
        Snackbar.make(contenedor, mensaje, Snackbar.LENGTH_LONG).show();
    }

    private boolean verificaCampos() {
        correo = etCorreo.getText().toString();
        contraseña = etContraseña.getText().toString();
        if (correo.isEmpty() || !correo.matches(".+@.+[.].+")) {
            mensaje("Correo no válido !");
            return false;
        } else if (contraseña.isEmpty() || contraseña.length() < 6 ||
                !contraseña.matches(".*[0-9].*") ||
                !contraseña.matches(".*[A-Z].*")) {
            mensaje("Contraseña no válida. Ha de contener al menos 6 caracteres, una letra mayúscula.");
            return false;
        }
        return true;
    }

    public void firebaseUI(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }



/*





    public void registroCorreo(View v) {
        if (verificaCampos()) {
            dialogo.show();
            auth.createUserWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                verificaSiUsuarioValidado();
                            } else {
                                dialogo.dismiss();
                                mensaje(task.getException().getLocalizedMessage());
                            }
                        }
                    });
        }
    }

 */

    //---------------metodo button GOOGLE CLCIK --------------
    public void autentificarGoogle(View v) {
        Intent i = googleSignInClient.getSignInIntent();
        startActivityForResult(i, RC_GOOGLE_SIGN_IN);

    }

    //La respuesta nos volverá a onActivityResult(). Las siguientes líneas
    //detectan si se ha recibido y le pasa el resultado a googleAuth():

    @Override public void onActivityResult(int requestCode, int resultCode,
                                           Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task =
                    GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                googleAuth(account.getIdToken());
            } catch (ApiException e) {
                mensaje("Error de autentificación con Google");
            }
        }
    }
    //El método googleAuth() simplemente le pasa este valor a Firebase:
    private void googleAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,
                null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            verificaSiUsuarioValidado();
                        }else{
                            mensaje(task.getException().getLocalizedMessage());
                        }
                    }
                });
    }

    //-----------FIN METODOS DE BUTTON GOOGLE Y AUTENTIFICACION GOOGLE CUSTOM--------------

//---------recuperacion de contraseña-----------
public void reestablecerContraseña(View v) {
    correo = etCorreo.getText().toString();

    if (correo.isEmpty()) {
        mensaje("Introduce un correo");
    } else if (!correo.matches(".+@.+[.].+")) {
        mensaje("Correo no válido");
  /*  } else {
        dialogo.show();

   */
        // Check if the email is registered before sending the reset email
        /*
        auth.fetchSignInMethodsForEmail(correo)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            if (result.getSignInMethods().isEmpty()) {
                                // The email is not registered
                                dialogo.dismiss();
                                mensaje("No hay ninguna cuenta registrada con este correo");
                            } else {
                                // The email is registered, proceed with sending the reset email
                                sendPasswordResetEmail();
                            }
                        } else {
                            dialogo.dismiss();
                            mensaje("Error al verificar la existencia de la cuenta");
                        }
                    }
                });


         */


    } else {
        dialogo.show();
        auth.sendPasswordResetEmail(correo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override public void onComplete(@NonNull Task<Void> task) {
                        dialogo.dismiss();
                        if (task.isSuccessful()) {
                            mensaje("Verifica tu correo para cambiar contraseña.");
                        } else {
                            mensaje("ERROR al mandar correo para cambiar contraseña");
                        }
                    }
                });
    }
}



    private void sendPasswordResetEmail() {
        auth.sendPasswordResetEmail(correo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialogo.dismiss();
                        if (task.isSuccessful()) {
                            mensaje("Verifica tu correo para cambiar contraseña.");
                        } else {
                            mensaje("ERROR al mandar correo para cambiar contraseña");
                        }
                    }
                });
    }


    public void IrAlRegistro(View view){
        Intent i = new Intent(this, CustomRegistroActivity.class);
        startActivity(i);
    }


}
