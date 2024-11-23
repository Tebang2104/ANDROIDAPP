package com.jedp.entryeventapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class ForgetPass_activity extends AppCompatActivity {

    private EditText txt_correoCC;
    private Button btn_cambiarC;

    FirebaseAuth firebaseAuth;

    private String correoCC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass1);

        firebaseAuth = FirebaseAuth.getInstance();

        txt_correoCC = (EditText) findViewById(R.id.txt_correoCC);
    }

    public void cambiarContrasena(View view){
        correoCC = txt_correoCC.getText().toString();

        if(!correoCC.isEmpty()){
            resetPassword();
        }else{
            Toast.makeText(this,"Debe ingresar el correo", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetPassword(){
        firebaseAuth.sendPasswordResetEmail(correoCC).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPass_activity.this, "Se ha enviado un correo para restablecer la contraseña", Toast.LENGTH_LONG).show();
                }else{
                    String error = ((FirebaseAuthException) task.getException()).getErrorCode();
                    dameToastdeerror(error);
                }
            }
        });
    }

    private void dameToastdeerror(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(ForgetPass_activity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(ForgetPass_activity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(ForgetPass_activity.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(ForgetPass_activity.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                txt_correoCC.setError("La dirección de correo electrónico está mal formateada.");
                txt_correoCC.requestFocus();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(ForgetPass_activity.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(ForgetPass_activity.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                txt_correoCC.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                txt_correoCC.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(ForgetPass_activity.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(ForgetPass_activity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(ForgetPass_activity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(ForgetPass_activity.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(ForgetPass_activity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(ForgetPass_activity.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

        }

    }

    //Método para anterior
    public void volver(View view){
        Intent volver = new Intent(this,Login_activity.class);
        startActivity(volver);
    }
}