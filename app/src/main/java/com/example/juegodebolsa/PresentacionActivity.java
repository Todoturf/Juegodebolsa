package com.example.juegodebolsa;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PresentacionActivity extends AppCompatActivity {

    public static int MILISEGUNDOS_ESPERA = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                Intent intent = new Intent(PresentacionActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            };
        }, MILISEGUNDOS_ESPERA);
    }
}
