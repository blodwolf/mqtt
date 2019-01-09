package mqtt;


import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mqtt message receive
 * CLIENT_KEY @see{Client.clientMap} 缓存客户端的key
 *
 * @author yinjinliang
 */
public class ReceiveMessage {

    private final static Logger logger = LoggerFactory.getLogger(ReceiveMessage.class);

    private final static String CLIENT_KEY = "receive";

    private final static String TOPIC = "topic_test";

    /**
     * 阻塞方法，接收topic message。
     * connectionLost 含有自动连接设置。具体信息看Client.getClient(runMode, CLIENT_KEY)
     *
     * @param runMode
     */
    public void receiveMessage(RunMode runMode) {
        logger.info("topic :" + TOPIC);
        logger.info("run mode :" + runMode.toString());

        try {
            Client.getClient(runMode, CLIENT_KEY).subscribe(TOPIC);
            Client.getClient(runMode, CLIENT_KEY).setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    logger.info("connect is lost");
                    while (!Client.getClient(runMode, CLIENT_KEY).isConnected()) {
                        try {
                            Client.getClient(runMode, CLIENT_KEY).connect();
                            Client.getClient(runMode, CLIENT_KEY).subscribe(TOPIC);
                        } catch (MqttException e) {
                            e.printStackTrace();
                            logger.info("MqttException:" + e);
                            logger.info("reconnect is now");
                        }
                    }
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    messageHandler(topic, new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("aaa==" + token.isComplete());

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    /**
     * 对接收到的消息进行处理。此方法未实现处理逻辑，使用者在这里写消息处理逻辑
     *
     * @param topic   订阅主题
     * @param message 接收到的消息
     */
    public void messageHandler(String topic, String message) {
        logger.info("topic :" + topic + "message :" + message);
    }


    public static void main(String[] args) {


    }
}
