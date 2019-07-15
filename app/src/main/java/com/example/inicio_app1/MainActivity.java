package com.example.inicio_app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //OBJETOS
    private EditText et_correo;
    private EditText et_password;
    private Button boton_registrar, boton_loguin;
    private ProgressDialog progressDialog;
    //Firebase
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INICIA FIREBASES
        firebaseAuth = FirebaseAuth.getInstance();
        //REFERENCIA A INTERFAZ
        et_correo = (EditText)findViewById(R.id.txt_correo);
        et_password = (EditText)findViewById(R.id.txt_password);
        boton_registrar = (Button)findViewById(R.id.btn_registrar);
        boton_loguin = (Button)findViewById(R.id.btn_loguin);

        progressDialog = new ProgressDialog(this);

        boton_registrar.setOnClickListener(this);
        boton_loguin.setOnClickListener(this);

    }

    private void RegistrarUsuario(){
        //OBTENER EMAIL Y CONTRASEÑA
        final String correo_string = et_correo.getText().toString().trim();
        String password_string = et_password.getText().toString().trim();
        //VERIFICAR SI HAY CAMPOS VACIOS
        if(TextUtils.isEmpty(correo_string)){
            et_correo.setError("Obligatorio");
            return;
        }
        if(TextUtils.isEmpty(password_string)){
            et_password.setError("Obligatorio");
            return;
        }
        //Dialogo emergente de proceso
        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //CREAR UN NUEVO USUARIO
        firebaseAuth.createUserWithEmailAndPassword
                (correo_string, password_string).addOnCompleteListener
                (this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //CHECAR SI EL REGISTRO SE REALIZO CORREXTAMENTE
                if(task.isSuccessful()){
                    Toast.makeText
                            (MainActivity.this,"Se ha registrado el email : " + correo_string + " correctamente .",Toast.LENGTH_LONG).show();
                }else{ //Detectar si el usuario ya esta registrado
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){//si se presenta una coincidencia
                        Toast.makeText(MainActivity.this, "El usuario " + correo_string + " ya existe ",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText
                                (MainActivity.this, "No se ha podido registrar el usuario,",Toast.LENGTH_LONG).show();
                    }
                }
                progressDialog.dismiss();
            }
        });
    }


    private void IniciarSesion(){
        //OBTENER EMAIL Y CONTRASEÑA
        final String correo_string = et_correo.getText().toString().trim();
        String password_string = et_password.getText().toString().trim();
        //VERIFICAR SI HAY CAMPOS VACIOS
        if(TextUtils.isEmpty(correo_string)){
            et_correo.setError("Obligatorio");
            return;
        }
        if(TextUtils.isEmpty(password_string)){
            et_password.setError("Obligatorio");
            return;
        }
        //Dialogo emergente de proceso
        progressDialog.setMessage("Iniciando Sesión...");
        progressDialog.show();

        //INICIAR SESION DE USUARIO
        firebaseAuth.signInWithEmailAndPassword
                (correo_string, password_string).addOnCompleteListener
                (this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //CHECAR SI EL REGISTRO SE REALIZO CORREXTAMENTE
                        if(task.isSuccessful()){
                            int posicion = correo_string.indexOf("@");
                            String usuario = correo_string.substring(0, posicion);
                            Toast.makeText
                                    (MainActivity.this,"Bienvenido : " + correo_string + "!",Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(), Usuario_App1.class);
                            intencion.putExtra(Usuario_App1.usuario, usuario);
                            startActivity(intencion);

                        }else{
                            Toast.makeText
                                    (MainActivity.this, "No se ha podidoiniciar sesion.",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_registrar:
                RegistrarUsuario();
                break;
            case R.id.btn_loguin:
                IniciarSesion();
                break;
        }
        RegistrarUsuario();
    }
}
