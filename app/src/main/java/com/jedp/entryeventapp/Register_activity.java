package com.jedp.entryeventapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register_activity extends AppCompatActivity {

    private EditText txt_nContrasena,txt_nContrasenaC,txt_nEmail,txt_nTel,txt_nUsuario;
    private Button btn_;

    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        mFirestore = FirebaseFirestore.getInstance();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.txt_nEmail, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(this, R.id.txt_nContrasena, regexPassword, R.string.invalid_password);
        // to validate a confirmation field (don't validate any rule other than confirmation on confirmation field)
        awesomeValidation.addValidation(this, R.id.txt_nContrasenaC, R.id.txt_nContrasena, R.string.invalid_password_conf);


        txt_nEmail = (EditText) findViewById(R.id.txt_nEmail);
        txt_nUsuario = (EditText) findViewById(R.id.txt_nUsuario);
        txt_nTel = (EditText) findViewById(R.id.txt_nTel);
        txt_nContrasena = (EditText) findViewById(R.id.txt_nContrasena);
        txt_nContrasenaC = (EditText) findViewById(R.id.txt_nContrasenaC);


    }

    public void volver(View view){
        Intent volver = new Intent(this,Login_activity.class);
        startActivity(volver);
    }
    public void irHome(){
        Intent ingreso = new Intent(this,Menu_activity.class);
        ingreso.putExtra("mail",txt_nEmail.getText().toString());
        ingreso.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ingreso);
    }

    public void registrarUsuario(View view){
        String correo = txt_nEmail.getText().toString();
        String  usuario = txt_nUsuario.getText().toString();
        double telefono = Double.parseDouble(txt_nTel.getText().toString());
        String pass = txt_nContrasena.getText().toString();

        if(awesomeValidation.validate()){
            firebaseAuth.createUserWithEmailAndPassword(correo,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        coleccionUsuario(correo,pass,usuario,telefono);
                    }else{
                        String error = ((FirebaseAuthException) task.getException()).getErrorCode();
                        dameToastdeerror(error);
                    }
                }
            });
        }else{
            Toast.makeText(this,"Completa todos los campos",Toast.LENGTH_SHORT).show();
        }
    }

    private void coleccionUsuario(String email, String contrasena, String usuario, double telefono){

        String id = firebaseAuth.getCurrentUser().getUid();
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("correo",email);
        map.put("contrasena",contrasena);
        map.put("usuario",usuario);
        map.put("telefono",telefono);

        mFirestore.collection("usuarios").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                finish();
                Toast.makeText(Register_activity.this,"Usuario creado exitosamente", Toast.LENGTH_LONG).show();
                irHome();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register_activity.this,"Error en insertar usuario en la colección", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(Register_activity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(Register_activity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(Register_activity.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(Register_activity.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                txt_nEmail.setError("La dirección de correo electrónico está mal formateada.");
                txt_nEmail.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(Register_activity.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                txt_nContrasena.setError("la contraseña es incorrecta ");
                txt_nContrasena.requestFocus();
                txt_nContrasena.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(Register_activity.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(Register_activity.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(Register_activity.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(Register_activity.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                txt_nEmail.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                txt_nEmail.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(Register_activity.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(Register_activity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(Register_activity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(Register_activity.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(Register_activity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(Register_activity.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(Register_activity.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                txt_nContrasena.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                txt_nContrasena.requestFocus();
                break;

        }

    }

}