package com.example.sprint_1_nuevo_15;

public interface RepositorioLugares {
    Mascota elemento(int id); //Devuelve el elemento dado su id
    void añade(Mascota lugar); //Añade el elemento indicado
    int nuevo(); //Añade un elemento en blanco y devuelve su id
    void borrar(int id); //Elimina el elemento con el id indicado
    int tamaño(); //Devuelve el número de elementos
    void actualiza(int id, Mascota lugar); //Reemplaza un elemento
}
