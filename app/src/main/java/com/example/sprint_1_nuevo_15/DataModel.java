package com.example.sprint_1_nuevo_15;

public class DataModel {
    private String nombre;
    private String direccion;
    private String direccion2;
    private String direccion3;
    private int imagenResId; // Resource ID for the image, assuming you use drawable resources

    public DataModel(String nombre, String direccion, String direccion2, String direccion3, int imagenResId) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.direccion2 = direccion2;
        this.direccion3 = direccion3;
        this.imagenResId = imagenResId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDireccion2() {
        return direccion2;
    }

    public String getDireccion3() {
        return direccion3;
    }

    public int getImagenResId() {
        return imagenResId;
    }
}

