package com.tuyalink.iot.listener;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Call back. If IMqttMessageListener is defined when subscribing to topic, the response message takes priority to IMqttMessageListener
 */
public class PublishCallBack implements MqttCallback {
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("If the connection is disconnected, it can be reconnected");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Receive message topic:" + s);
        System.out.println("Receive message QoS:" + mqttMessage.getQos());
        System.out.println("Receive message payload:" + new String(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        System.out.println("deliveryComplete---------" + iMqttDeliveryToken.isComplete());
    }
}
