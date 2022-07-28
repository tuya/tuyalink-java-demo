package com.tuyalink.iot;

import com.tuyalink.iot.listener.MqttMessageListener;
import com.tuyalink.iot.listener.PublishCallBack;
import com.tuyalink.iot.sign.TuyaMqttSign;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import static org.eclipse.paho.client.mqttv3.MqttConnectOptions.MQTT_VERSION_3_1_1;

/**
 * Example of device access of MQTT3.1.1 protocol
 *
 * @author Tuya inc.
 */
public class TuyaMQTT3ClientDemo {

    public static void main(String[] args) throws Exception {
        // TuyaLink device configuration is as follows, you must change it
        String productId = "gm**********qf";
        String deviceId = "6cc****************xb";
        String deviceSecret = "ff***********17";

        // Online environment domain name
        String broker = "ssl://m1.tuyacn.com:8883";

        TuyaMqttSign sign = new TuyaMqttSign();
        sign.calculate(productId, deviceId, deviceSecret);
        System.out.println("username: " + sign.getUsername());
        System.out.println("password: " + sign.getPassword());
        System.out.println("MqttClientId: " + sign.getClientId());

        MemoryPersistence persistence = new MemoryPersistence();
        try {
            // Paho Mqtt client
            MqttClient sampleClient = new MqttClient(broker, sign.getClientId(), persistence);

            // Paho Mqtt connect parameters
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            // Automatic reconnection
            connOpts.setAutomaticReconnect(true);
            connOpts.setKeepAliveInterval(60);
            connOpts.setUserName(sign.getUsername());
            connOpts.setPassword(sign.getPassword().toCharArray());
            connOpts.setMqttVersion(MQTT_VERSION_3_1_1);
            //  Bottom callback
            sampleClient.setCallback(new PublishCallBack());
            connOpts.setSocketFactory(getSSLSocketFactory());
            // Establish connection
            sampleClient.connect(connOpts);
            System.out.println("broker: " + broker + " connected");

            // Property set topic
            String topicReply = "tylink/" + deviceId + "/thing/property/set";
            sampleClient.subscribe(topicReply, new MqttMessageListener());
            System.out.println("subscribe: " + topicReply);

            //****************************************device property report********************************************

            // Property report topic
            String topic = "tylink/" + deviceId + "/thing/property/report";
            // Current timestamp
            long timestamp = System.currentTimeMillis();
            // Property report content
            String content = "{\n" +
                    "\t\"msgId\":\"45lkj3551234002\",\n" +
                    "  \t\"time\":" + timestamp + ",\n" +
                    "\t\"data\":{\n" +
                    "    \t\"switch_led_1\":{\n" +
                    "        \t\"value\":true,\n" +
                    "        \t\"time\": " + timestamp + "  \n" +
                    "        }\n" +
                    "\t}\n" +
                    "}";

            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(1);
            sampleClient.publish(topic, message);
            System.out.println("publish topic: " + topic);
            System.out.println("publish content: " + content);

            //Thread.sleep(20000);

            //Paho Mqtt disconnect
            //sampleClient.disconnect();
            //System.out.println("Disconnected");
            //System.exit(0);
        } catch (MqttException e) {
            System.out.println("reason " + e.getReasonCode());
            System.out.println("msg " + e.getMessage());
            System.out.println("loc " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
        }
    }

    public static SSLSocketFactory getSSLSocketFactory() throws Exception {
        URL url = TuyaMQTT3ClientDemo.class.getClassLoader().getResource("iot-device.cer");
        String fileName = url.getPath();

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate oCert = (X509Certificate) cf.generateCertificate(new FileInputStream(fileName));

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("cam2", oCert);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
        trustManagerFactory.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

        return sslContext.getSocketFactory();
    }
}