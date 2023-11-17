package com.example.sprint_1_nuevo_15;

import com.example.sprint_1_nuevo_15.Mascota;
import com.google.firebase.firestore.Query;

public interface MascotasAsinc {


    Query getQuery();

    interface EscuchadorElemento{
        void onRespuesta(Mascota mascota);
    }
    interface EscuchadorTamaño{
        void onRespuesta(long tamaño);
    }
    void elemento(String id, EscuchadorElemento escuchador);
    void añade(Mascota mascota);
    String nuevo();
    void borrar(String id);
    void actualiza(String id, Mascota mascota);
    void tamaño(EscuchadorTamaño escuchador);
}