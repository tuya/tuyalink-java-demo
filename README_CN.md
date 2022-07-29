# TuyaLink MQTT标准协议 Java版demo

Tuyalink-demo 是TuyaLink MQTT标准协议java版本的示例代码，用来模拟设备通过TuyaLink协议连接到涂鸦云，支持涂鸦物模型的属性、动作、事件上报下发。

TuyaLink MQTT标准协议提供了很多开箱即用的内置访问协议规范，涵盖了绝大多数开发者需要用到的业务场景，例如设备模型、上报下发、拓扑关系、OTA、定时、远程配置下发、文件传输、NTP等。

具体可访问[官方文档](https://developer.tuya.com/cn/docs/iot/device-connection?id=Kb46bqq71kwtd)
## 步骤

### 第一步: 下载代码到本地
可以看到如下结构：
+ 核心示例代码：TuyaMQTT3ClientDemo 
+ sign包：存放涂鸦MQTT连接相关签名逻辑 
+ listener：存放MQTT消息监听Listener 
+ resources：存放证书
### 第二步: 导入到IntelliJ IDEA
项目示例代码下载完成后，可以解压导入到IntelliJ IDEA
或者也可直接clone项目到IntelliJ IDEA。

### 第三步: Change device configuration
导入后的代码，需要访问涂鸦IoT平台拿到ProductID、DeviceID、DeviceSecret信息，将如下代码位置对应的配置信息替换，其他代码均不需要改动，示例代码已经实现了订阅基础的设备属性值上报topic。
```java
 // TuyaLink device configuration is as follows, you must change it
 String productId = "dsadusiau";
 String deviceId = "6cc87b393436fb754i2xb";
 String deviceSecret = "ffad8e34m1ae8c717";
```
Tips：以上标识示例值仅用于举例，请不要直接使用。

![img_3.png](img_3.png)

### 第四步: 运行代码
至此，准备工作完毕，右键 TuyaMQTT3ClientDemo文件，点击Run运行代码。

可以看到控制台属性已上报成功。
![img_1.png](img_1.png)

此时，切换到IoT平台的设备调试页面，可以看到设备上报的数据日志。
![img_2.png](img_2.png)

