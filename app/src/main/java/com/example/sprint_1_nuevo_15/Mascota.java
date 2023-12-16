package com.example.sprint_1_nuevo_15;

public class Mascota {
    private String nombre;
    private String direccion;
    private GeoPunto posicion;
    private String foto;
    private double peso;
    private int edad;
    private String userid;
    private String sensorID;
    private boolean genero;




    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    private double humedad;
    private double temperatura;


    public boolean isGenero() {
        return genero;
    }

    public void setGenero(boolean genero) {
        this.genero = genero;
    }

    // Constructors
    public Mascota(String nombre, String direccion, double longitud, double latitud, String userid, String sensorID) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.posicion = new GeoPunto(longitud, latitud);
        this.userid = userid;
        this.sensorID = sensorID;
    }

    public Mascota() {
        this.posicion = new GeoPunto(0.0, 0.0);
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
    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", posicion=" + posicion +
                ", foto='" + foto + '\'' +
                ", peso=" + peso +
                ", edad=" + edad +
                ", userid='" + userid + '\'' +
                ", sensorID='" + sensorID + '\'' +
                '}';
    }
}



//////////////////////////////////////////////////////////////////gore e hubavo
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