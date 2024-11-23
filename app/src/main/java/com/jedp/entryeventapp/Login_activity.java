package com.jedp.entryeventapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class Login_activity extends Activity {

	private EditText txt_correo,txt_contrasena;
	private Button btn_login,btn;

	AwesomeValidation awesomeValidation;
	FirebaseAuth firebaseAuth;

	@SuppressLint("MissingInflatedId")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		firebaseAuth = FirebaseAuth.getInstance();

		FirebaseAuth mAuth = FirebaseAuth.getInstance();

		//Control de la sesión
		FirebaseUser user = mAuth.getCurrentUser();



		awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

		awesomeValidation.addValidation(this,R.id.txt_correo, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);

		String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
		awesomeValidation.addValidation(this, R.id.txt_contrasena, regexPassword, R.string.invalid_password);

		txt_contrasena = (EditText) findViewById(R.id.txt_contrasena);
		txt_correo = (EditText) findViewById(R.id.txt_correo);
		btn_login = (Button) findViewById(R.id.btn_login);

		if(user != null){
			irHome();
		}
	
	}

	public void olvidePass(View view){
		Intent olvidePass = new Intent(this, ForgetPass_activity.class);
		startActivity(olvidePass);
	}

	public void nuevoUsuario(View view){
		Intent nuevoUsuario = new Intent(this,Register_activity.class);
		startActivity(nuevoUsuario);
	}
	public void Ingresar(View view){
		String correo = txt_correo.getText().toString();
		String clave = txt_contrasena.getText().toString();
		if(awesomeValidation.validate()){
			firebaseAuth.signInWithEmailAndPassword(correo,clave).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
				@Override
				public void onComplete(@NonNull Task<AuthResult> task) {
					if(task.isSuccessful()){
						irHome();
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

	public void irHome(){
		Intent ingreso = new Intent(this,Menu_activity.class);
		ingreso.putExtra("mail",txt_correo.getText().toString());
		ingreso.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(ingreso);
	}

	private void dameToastdeerror(String error) {

		switch (error) {

			case "ERROR_INVALID_CUSTOM_TOKEN":
				Toast.makeText(Login_activity.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_CUSTOM_TOKEN_MISMATCH":
				Toast.makeText(Login_activity.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_INVALID_CREDENTIAL":
				Toast.makeText(Login_activity.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_INVALID_EMAIL":
				Toast.makeText(Login_activity.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
				txt_correo.setError("La dirección de correo electrónico está mal formateada.");
				txt_correo.requestFocus();
				break;

			case "ERROR_WRONG_PASSWORD":
				Toast.makeText(Login_activity.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
				txt_contrasena.setError("la contraseña es incorrecta ");
				txt_contrasena.requestFocus();
				txt_contrasena.setText("");
				break;

			case "ERROR_USER_MISMATCH":
				Toast.makeText(Login_activity.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_REQUIRES_RECENT_LOGIN":
				Toast.makeText(Login_activity.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
				Toast.makeText(Login_activity.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_EMAIL_ALREADY_IN_USE":
				Toast.makeText(Login_activity.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
				txt_correo.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
				txt_correo.requestFocus();
				break;

			case "ERROR_CREDENTIAL_ALREADY_IN_USE":
				Toast.makeText(Login_activity.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_USER_DISABLED":
				Toast.makeText(Login_activity.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_USER_TOKEN_EXPIRED":
				Toast.makeText(Login_activity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_USER_NOT_FOUND":
				Toast.makeText(Login_activity.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_INVALID_USER_TOKEN":
				Toast.makeText(Login_activity.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_OPERATION_NOT_ALLOWED":
				Toast.makeText(Login_activity.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
				break;

			case "ERROR_WEAK_PASSWORD":
				Toast.makeText(Login_activity.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
				txt_contrasena.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
				txt_contrasena.requestFocus();
				break;

		}

	}
}
	
	