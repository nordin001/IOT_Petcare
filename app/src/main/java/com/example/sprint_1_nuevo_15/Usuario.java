package com.example.sprint_1_nuevo_15;

import java.util.List;

public class Usuario {
    private List<Mascota> mascotas; // Lista de mascotas del usuario

    // Constructor, métodos de acceso y otros métodos...

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
    private String nombre;
    private String correo;
    private long inicioSesion;

    private String telefono;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
//CONSTRUCTOR 1 vacío,

    public Usuario () {}

    public String getNombre() {
        return nombre;
    }
    public  String getCorreo(){return correo;}
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getInicioSesion() {return inicioSesion;}
    public void setInicioSesion(long inicioSesion) {
        this.inicioSesion = inicioSesion;
    }



    public Usuario(String nombre, String correo, long inicioSesion) {
        this.nombre = nombre;
        this.correo = correo;
        this.inicioSesion = inicioSesion;
    }

    public Usuario(String nombre, String correo) {
        this(nombre, correo, System.currentTimeMillis());
    }

    public Usuario(String nombre, String correo, long inicioSesion, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.inicioSesion = inicioSesion;
        this.telefono = telefono;
    }

}

