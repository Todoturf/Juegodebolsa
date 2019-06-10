package com.example.juegodebolsa;

import java.util.HashMap;

public class Jugador
{
    private String nombre;
    private double capital;
    int numeroAcciones;

    //HashMap<int, int> hashMap = new HashMap<int, int>();


    public Jugador(String nombre, double capital)
    {
        this.nombre = nombre;
        this.capital = capital;
        numeroAcciones = 0;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public double getCapital()
    {
        return capital;
    }

    public void setCapital(double capital)
    {
        this.capital = capital;
    }

    public int getNumeroAcciones()
    {
        return numeroAcciones;
    }

    public void setNumeroAccionesSumar()
    {
        this.numeroAcciones++;
    }

    public void setNumeroAccionesRestar()
    {
        this.numeroAcciones--;
    }
}
