package com.example.juegodebolsa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{

    EditText editNombreJugador;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnviar = findViewById(R.id.btnEnviar);
        editNombreJugador = findViewById((R.id.editNombreJugador));

        btnEnviar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (editNombreJugador.getText().toString().isEmpty()) {
                    editNombreJugador.setError("Debe introducir un nombre");
                } else {
                    Intent intent = new Intent(MainActivity.this, Bolsa.class);
                    intent.putExtra(("nombre"), editNombreJugador.getText().toString());
                    startActivity(intent);
                }
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        editNombreJugador.requestFocus();
    }
}


