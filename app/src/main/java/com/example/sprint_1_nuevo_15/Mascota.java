package com.example.sprint_1_nuevo_15;

/*
public class Mascota {
    private String nombre;
    private String direccion;
    private GeoPunto posicion;
    private TipoLugar tipo;
    private String foto;
    private double peso;
    private int edad;
    //private int telefono;
   // private String url;
   // private String comentario;
   // private long fecha;
   // private float valoracion;

    public Mascota(String nombre, String direccion ,double longitud,
                   double latitud, TipoLugar tipo) {
      //  fecha = System.currentTimeMillis();
        posicion = new GeoPunto(longitud, latitud);
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipo = tipo;

        this.edad=edad;
       // this.telefono = telefono;
       // this.url = url;
      //  this.comentario = comentario;
       // this.valoracion = valoracion;
    }

    public Mascota() {
        //fecha = System.currentTimeMillis();
        posicion = new GeoPunto(0.0,0.0);
        tipo = TipoLugar.OTROS;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public GeoPunto getPosicion() {
        return posicion;
    }

    public void setPosicion(GeoPunto posicion) {
        this.posicion = posicion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }




    public TipoLugar getTipo() {
        return tipo;
    }

    public void setTipo(TipoLugar tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Lugar{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", posicion=" + posicion +
                ", tipo=" + tipo +
                ", foto='" + foto + '\'' +
                '}';
    }


}

 */
public class Mascota {
    private String nombre;
    private String direccion;
    private GeoPunto posicion;
    private TipoLugar tipo;
    private String foto;
    private double peso;
    private int edad;
    private String ownerID;

    // Constructors
    public Mascota(String nombre, String direccion, double longitud, double latitud, TipoLugar tipo, String ownerID) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.posicion = new GeoPunto(longitud, latitud);
        this.tipo = tipo;
        this.ownerID = ownerID;
    }

    public Mascota() {
        this.posicion = new GeoPunto(0.0, 0.0);
        this.tipo = TipoLugar.OTROS;
    }

    // Getters and Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public GeoPunto getPosicion() {
        return posicion;
    }

    public void setPosicion(GeoPunto posicion) {
        this.posicion = posicion;
    }

    public TipoLugar getTipo() {
        return tipo;
    }

    public void setTipo(TipoLugar tipo) {
        this.tipo = tipo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", posicion=" + posicion +
                ", tipo=" + tipo +
                ", foto='" + foto + '\'' +
                ", peso=" + peso +
                ", edad=" + edad +
                ", ownerID='" + ownerID + '\'' +
                '}';
    }
}

        /*

public class Mascota {
    private String nombre;
    private String direccion;
    private GeoPunto posicion;
    private TipoLugar tipo;
    private String foto;
    private double peso;
    private int edad;
    private String ownerID;  // New field for the owner's ID

    // --------------- GETTER AND SETTER FOR USER ID ---------------
    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    // ----------------- CONSTRUCTORS -----------------

    public Mascota(String nombre, String direccion, double longitud, double latitud, TipoLugar tipo, String ownerID) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.posicion = new GeoPunto(longitud, latitud);
        this.tipo = tipo;
        this.ownerID = ownerID;
    }

    public Mascota() {
        this.posicion = new GeoPunto(0.0, 0.0);
        this.tipo = TipoLugar.OTROS;
    }

    // ----------------- OTHER GETTERS AND SETTERS -----------------

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public GeoPunto getPosicion() {
        return posicion;
    }

    public void setPosicion(GeoPunto posicion) {
        this.posicion = posicion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public TipoLugar getTipo() {
        return tipo;
    }

    public void setTipo(TipoLugar tipo) {
        this.tipo = tipo;
    }

    // ----------------- ADDITIONAL METHODS -----------------

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", posicion=" + posicion +
                ", tipo=" + tipo +
                ", foto='" + foto + '\'' +
                ", ownerID='" + ownerID + '\'' +
                '}';
    }
}



         */