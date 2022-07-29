# TuyaLink MQTT Protocol Java Demo

Tuyalink-demo is written in Java and supports the Tuya-defined things data model,aiming to help developers to connect their proprietary hardware to the Tuya Cloud.

## Procedure

### Step 1: Download the project
The following describes the structure of the SDK directory:
+ Core example code: tuyamqtt3clientdemo
+ Sign package: store TuyaLink mqtt connection related signature logic
+ Listener package: store mqtt messages listener
+ Resources: store certificates

### Step 2: Import project to IDEA
The downloaded sample code can be decompressed and imported into IntelliJ IDEA

### Step 3: Change device configuration
You need to get the ProductID, DeviceID and DeviceSecret information you got on the Tuya IOT platform before,You must change it.
```java
 // TuyaLink device configuration is as follows, you must change it
 String productId = "dsadusiau";
 String deviceId = "6cc87b393436fb754i2xb";
 String deviceSecret = "ffad8e34m1ae8c717";
```
Tips: the above identification example values are only for example, please do not use them directly.

### Step 4: Run it
At this point, the preparations are completed. Right click the TuyaMQTT3ClientDemo file and click Run to run the code.
You can see that the console attribute has been reported successfully.
```text
publish topic:xxx
publish content:{}
deliveryComplete---------true
```