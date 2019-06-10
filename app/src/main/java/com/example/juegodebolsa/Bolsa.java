package com.example.juegodebolsa;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Intent;

import java.text.DecimalFormat;

public class Bolsa extends AppCompatActivity
{
    public Double capitalBolsaInicial;
    public Double capitalBolsaActual;
    public Double valorInicialAccion;
    public Double valorActualAccion;
    public Double valorAcciones;
    public Double valorTotal;

    public TextView txtTituloJuegodeBolsa2;
    public TextView txtNombreJugadorBolsa;
    public TextView txtValorActualAccion;

    public ImageView imgFlechaArriba;
    public ImageView imgFlechaAbajo;
    public TextView txtAcciones;
    public TextView txtCapital;
    public TextView txtValor;
    public TextView txtValorAcciones;
    public TextView txtValorCapital;
    public TextView txtNumeroAcciones;
    public TextView txtTotal;


    Button btnCompro;
    Button btnVendo;

    String nombre;

    Thread Hilo;
    Handler handler;
    Jugador jugador;
    Accion accion;
    DecimalFormat df;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolsa);

        txtTituloJuegodeBolsa2 = findViewById(R.id.txtTituloJuegodeBolsa2);
        txtNombreJugadorBolsa = findViewById(R.id.txtNombreJugadorBolsa);
        txtValorActualAccion = findViewById(R.id.txtValorActualAccion);
        txtAcciones = findViewById(R.id.txtAcciones);
        txtCapital = findViewById(R.id.txtCapital);
        txtValor = findViewById(R.id.txtValor);
        txtValorAcciones = findViewById(R.id.txtValorAcciones);
        txtValorCapital = findViewById(R.id.txtValorCapital);
        txtNumeroAcciones = findViewById(R.id.txtNumeroAcciones);
        txtTotal = findViewById(R.id.txtTotal);

        btnCompro = findViewById(R.id.btnCompro);
        btnVendo = findViewById(R.id.btnVendo);

        imgFlechaArriba = findViewById(R.id.imgFlechaArriba);
        imgFlechaAbajo = findViewById(R.id.imgFlechaAbajo);

        imgFlechaArriba.setVisibility(ImageView.INVISIBLE);
        imgFlechaAbajo.setVisibility(ImageView.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        nombre = extras.getString("nombre", "PPPP");

        jugador = new Jugador(nombre, 1000.00);
        accion = new Accion("Iberdrola", 50.00, 50.00);

        inicioJuego();
        //resetearPreferencias();
        actualizacionValores();

        btnCompro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (capitalBolsaActual >= accion.getValor())
                {
                    jugador.setNumeroAccionesSumar();
                    capitalBolsaActual = capitalBolsaActual - accion.getValor();
                    actualizacionValores();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No tienes capital suficiente",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnVendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (jugador.getNumeroAcciones() > 0)
                {
                    jugador.setNumeroAccionesRestar();
                    capitalBolsaActual = capitalBolsaActual + accion.getValor();
                    actualizacionValores();
                }
            }
        });

       Hilo miHilo = new Hilo(20, 3000, accion.getValor());
       miHilo.start();
       handler = new Controlador();
    }

    public void resetearPreferencias()
    {
        SharedPreferences pref = getSharedPreferences(
                "recordBroker",
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("record", (int) 0);
        editor.commit();
    }

    public void inicioJuego() {

        txtNombreJugadorBolsa.setText(jugador.getNombre());
        capitalBolsaInicial = jugador.getCapital();
        capitalBolsaActual = jugador.getCapital();
        valorInicialAccion = accion.getValor();
        valorActualAccion = accion.getValor();
        valorAcciones = valorActualAccion * jugador.getNumeroAcciones();
        valorTotal = 0.00;
    }

    public void actualizacionValores()
    {
        comprobarValorAccion();
        df = new DecimalFormat("#0.00");
        txtValorActualAccion.setText(String.valueOf(df.format(accion.getValor())));
        txtNumeroAcciones.setText(String.valueOf(jugador.getNumeroAcciones()));
        valorAcciones = accion.getValor() * jugador.getNumeroAcciones();
        txtValorAcciones.setText(String. valueOf(df.format(valorAcciones)));
        txtValorCapital.setText(String.valueOf(df.format(capitalBolsaActual)));
        valorTotal = capitalBolsaActual + valorAcciones;
        jugador.setCapital(valorTotal);
        txtTotal.setText(String.valueOf(df.format(valorTotal)));
    }


    public void comprobarValorAccion()
    {
        imgFlechaArriba.setVisibility(ImageView.INVISIBLE);
        imgFlechaAbajo.setVisibility(ImageView.INVISIBLE);
        if (accion.getTendencia() > 0)
        {
            imgFlechaArriba.setVisibility(ImageView.VISIBLE);
        }
        else
        {
            imgFlechaAbajo.setVisibility(ImageView.VISIBLE);
        }
    }

    public class Hilo extends Thread
    {
        int maximo;
        int tiempo;
        double valorAccion;
        double cotaValoracion;

        public Hilo(int m, int t, double valorAccion)
        {
            this.maximo = m;
            this.tiempo = t;
            this.valorAccion = valorAccion;
            cotaValoracion = valorAccion/2;
        }

        public void run()
        {
            boolean finJuego = false;
            Message msg;
            Bundle b;

            for (int i = 0; i < maximo; i++)
            {
                try
                {
                    Thread.sleep(tiempo);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                double valorAleatorio = Math.random() * (cotaValoracion+cotaValoracion) + (-cotaValoracion);
                msg = handler.obtainMessage();
                b = new Bundle();
                b.putDouble("valor", valorAleatorio);
                b.putBoolean("finJuego", finJuego);
                msg.setData(b);
                handler.sendMessage(msg);
                Log.i ("Contador", "contador: " + i);
            }
            msg = handler.obtainMessage();
            b = new Bundle();
            //b.putDouble("valor", valorAleatorio);
            finJuego = true;
            b.putBoolean("finJuego", finJuego);
            msg.setData(b);
            handler.sendMessage(msg);
        }
    }
    //controlador para recibir mensajes del hilo
    public class Controlador extends Handler
    {

        @Override
        public void handleMessage(Message msg)
        {
            double variacion = msg.getData().getDouble("valor");
            boolean finJuego = msg.getData().getBoolean("finJuego");

            if(!finJuego)
            {//el juego no ha terminado
                //comprobar que el valor de la acción no baje de 10
                if((accion.getValor() + variacion) > 10)
                {
                    accion.setValor(accion.getValor() + variacion);
                }
                accion.setTendencia(variacion);
                actualizacionValores();
            }
            else
            {
                terminarJuego();
            }
        }

        public void terminarJuego()
        {
            //accedo a los datos guardados en preferencias
            SharedPreferences prefs =
                    getSharedPreferences(
                            "recordBroker",
                            Context.MODE_PRIVATE);
            int record = prefs.getInt("record", 0);
            String resultado = "";
            //compruebo si se ha batido un record
            if(jugador.getCapital() > record)
            {//se ha batido el record
                resultado = "ganador";
                record = (int) jugador.getCapital();
                //guardo el nuevo record en preferencias
                SharedPreferences pref = getSharedPreferences(
                        "recordBroker",
                        Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("record", (int) jugador.getCapital());
                editor.commit();
            }
            else
            {//no se ha batido el record
                resultado = "perdedor";
            }
            Intent intent = new Intent(Bolsa.this, FinJuegoActivity.class);
            intent.putExtra("resultado", resultado);
            intent.putExtra("record", record);
            startActivity(intent);
        }
    }
}