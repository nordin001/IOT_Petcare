package com.example.comun;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Mqtt {
    public static final String TAG = "MQTT";
    public static final String topicRoot="ntallal/practica/";//Reemplaza jtomas
    public static final int qos = 0;
    public static final String broker = "tcp://broker.hivemq.com:1883";
    public static final String broker2 = "mqtt://broker.emqx.io:1883";//va con el software que tengo instalado
    public static final String broker3="tcp://broker.emqx.io:1883";
    public static final String clientId = "Tallal2023"; //Reemplaza
    private MqttClient client;
    public Mqtt() {
        try {
            client = new MqttClient(broker3, clientId, new MemoryPersistence());
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(60);
            connOpts.setWill(topicRoot+"WillTopic","App desconectada".getBytes(),
                    qos, false);
            client.connect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public String publicar(String topic, String mensageStr) {
        try {
            MqttMessage message = new MqttMessage(mensageStr.getBytes());
            message.setQos(qos);
            message.setRetained(false);
            client.publish(topicRoot + topic, message);
            return "Publicando mensaje: " + topic+ "->"+mensageStr;
        } catch (MqttException e) {
            return "Error al publicar en MQTT." + e;
        }
    }

    public String desconectar() {
        try {
            client.disconnect();
            return "Desconectado";
        } catch (MqttException e) {
            return "Error al desconectar.";
        }
    }

    public String suscribir (String topic, MqttCallback listener) {
        try {
            client.subscribe(topicRoot + topic, qos);
            client.setCallback(listener);
            return "Suscrito a " + topicRoot + topic;
        } catch (MqttException e) {
            return "Error al suscribir." + e;
        }
    }



        }
