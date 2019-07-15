package com.example.inicio_app1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Usuario_App1 extends AppCompatActivity {

    public static final String usuario = "names";
    TextView txt_NombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario__app1);

        txt_NombreUsuario = (TextView)findViewById(R.id.txt_usuario);
        String usuario = getIntent().getStringExtra("names");
        txt_NombreUsuario.setText("¡Bienvenido " + usuario + "!");
    }
}
