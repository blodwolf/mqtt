package mqtt;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Client {

    private final static Logger logger = LoggerFactory.getLogger(Client.class);
    //根据实际场景自定义唯一标识
//    private final static String clientId = "is_receive_unique" + Math.random() * 100;


    //连接发生异常的时候重连间隔时间
    private final static long SLEEP_TIME = 1000;

    //客户端连接缓存
    private final static Map<String, MqttClient> clientMap = new HashMap<>();


    /**
     * 根据运行模式创建客户端，以clientType为key存储在clientMap
     *
     * @param runMode    运行模式
     * @param clientType 客户端key
     */
    private static void createClient(RunMode runMode, String clientType) {
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient client = new MqttClient(runMode.getBrokerUrl(), runMode.getMode() + "/" + getHost() + "/" + clientType, persistence);
            MqttConnectOptions opts = new MqttConnectOptions();/**/
            opts.setCleanSession(true);
            client.connect(opts);
            clientMap.put(clientType, client);
            logger.info("client is connect");
        } catch (MqttException e) {
            logger.info("createReceiveClient:" + e);
            logger.info("reconnect is working");
            try {
                Thread.currentThread().sleep(SLEEP_TIME);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            createClient(runMode, clientType);
        }
    }

    /**
     * 获取客户端，以clientType为单例标准
     *
     * @param runMode    运行模式
     * @param clientType 客户端key
     * @return MqttClient
     */
    public static MqttClient getClient(RunMode runMode, String clientType) {
        if (clientMap.get(clientType) == null) {
            synchronized (Client.class) {
                if (clientMap.get(clientType) == null) {
                    createClient(runMode, clientType);
                }
            }
        }

        return clientMap.get(clientType);
    }


    /**
     * 获取本机IP地址
     *
     * @return 主机名/ip
     */
    private static String getHost() {
        try {
            return InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "localhost/127.0.0.1";
        }

    }
}
