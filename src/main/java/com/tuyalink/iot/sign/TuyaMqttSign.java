package com.tuyalink.iot.sign;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

public class TuyaMqttSign {

    /**
     * MQTT username
     */
    private String username = "";

    /**
     * MQTT password
     */
    private String password = "";

    /**
     * MQTT clientId，used to identify this connection
     */
    private String clientId = "";

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getClientId() {
        return clientId;
    }

    /**
     * Calculate mqtt connection parameters
     *
     * @param productKey   productKey
     * @param deviceId     deviceId
     * @param deviceSecret deviceSecret
     */
    public void calculate(String productKey, String deviceId, String deviceSecret) {
        if (productKey == null || deviceId == null || deviceSecret == null) {
            return;
        }
        try {
            String timestamp = Long.toString(System.currentTimeMillis());
            //MQTT username
            this.username = deviceId + "|signMethod=hmacSha256,timestamp=" + timestamp + ",secureMode=1,accessType=1";
            //MQTT clientId
            this.clientId = "tuyalink_" + deviceId;
            String plainPasswd = "deviceId=" + deviceId + ",timestamp=" + timestamp + ",secureMode=1,accessType=1";
            System.out.println("plainPasswd= " + plainPasswd);
            //MQTT password
            this.password = hmacSha256(plainPasswd, deviceSecret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String hmacSha256(String plainText, String key) throws Exception {
        // Zero filling less than 64 characters
        return hmac(plainText, key, "HmacSHA256", "%064x");
    }

    private static String hmac(String plainText, String key, String algorithm, String format) throws Exception {
        if (plainText == null || key == null) {
            return null;
        }
        Mac mac = Mac.getInstance(algorithm);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        mac.init(secretKeySpec);
        return String.format(format, new BigInteger(1, mac.doFinal(plainText.getBytes())));
    }
}