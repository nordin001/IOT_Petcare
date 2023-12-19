package com.example.raspberry;

import com.example.comun.Mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Principal {
    public static void main(String[] args){
        System.out.println("¡Hola Raspberry Pi!");
        Mqtt mqtt = new Mqtt();
        System.out.println("Conectado a " + mqtt.broker);
        //Publicación
        long tiempo = System.currentTimeMillis();
        String s = mqtt.publicar("tiempo", Long.toHexString(tiempo));
        System.out.println(s);

        //Suscripción
        s = mqtt.suscribir("a", new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Conexión perdida"+cause);
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("Entrega completa");
            }
            @Override
            public void messageArrived(String topic, MqttMessage message)

                    throws Exception {
                String payload = new String(message.getPayload());
                System.out.println("Recibiendo: " + topic + "->" + payload);
            }

        });
        System.out.println(s);
//Esperamos 10 segundos
       /* try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    //Desconexión
        //s = mqtt.desconectar();
        System.out.println(s);

    }

}