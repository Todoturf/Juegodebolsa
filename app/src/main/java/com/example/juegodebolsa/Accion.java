package com.example.juegodebolsa;

public class Accion
{
    public String nombre;
    public double valor;
    public double tendencia;

    public Accion (String nombre, double valor, double tendencia)
    {
        this.nombre = nombre;
        this.valor = valor;
        this.tendencia = tendencia;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        this.valor = valor;
    }

    public double getTendencia()
    {
        return tendencia;
    }

    public void setTendencia(double tendencia)
    {
        this.tendencia = tendencia;
    }
}
