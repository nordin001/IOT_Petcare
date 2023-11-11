package com.example.sprint_1_nuevo_15;


import java.util.ArrayList;
import java.util.List;

public class MascotasLista implements RepositorioLugares {
    protected List<Mascota> listaLugares ;//= añadeEjemplos();//ejemploLugares();

    public MascotasLista() {
        //listaLugares = ejemploLugares();
        listaLugares = new ArrayList<Mascota>();
        añadeEjemplos();
    }

    public Mascota elemento(int id) {
        return listaLugares.get(id);
    }

    public void añade(Mascota lugar) {
        listaLugares.add(lugar);
    }

    public int nuevo() {
        Mascota lugar = new Mascota();
        listaLugares.add(lugar);
        return listaLugares.size()-1;
    }

    public void borrar(int id) {
        listaLugares.remove(id);
    }

    public int tamaño() {
        return listaLugares.size();
    }
    public void actualiza(int id, Mascota lugar) {
        listaLugares.set(id, lugar);
    }







    public void añadeEjemplos() {
        añade(new Mascota("Berta",
                "Vía Verde del río Serpis. Villalonga (Valencia)",
                -0.295058, 38.867180, TipoLugar.OTROS));
        añade(new Mascota("Añadir tu mascota",
                ".", -0.166093, 38.995656,
                TipoLugar.AñadirMascota));

    }


}
