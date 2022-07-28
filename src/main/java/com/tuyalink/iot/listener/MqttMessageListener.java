package com.tuyalink.iot.listener;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * MQTT Message Listener
 */
public class MqttMessageListener implements IMqttMessageListener {
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("response topic  : " + topic);
        System.out.println("response payload: " + message.toString());
    }
}
