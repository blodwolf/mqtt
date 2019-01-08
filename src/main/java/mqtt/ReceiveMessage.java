package mqtt;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
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


    /**
     * 阻塞方法，接收topic message。
     * connectionLost 含有自动连接设置。具体信息看Client.getClient(runMode, CLIENT_KEY)
     *
     * @param topic
     * @param runMode
     */
    public void receiveMessage(String topic, RunMode runMode) {
        logger.info("topic :" + topic);
        logger.info("run mode :" + runMode.toString());

        try {

            Client.getClient(runMode, CLIENT_KEY).subscribe(topic);
            Client.getClient(runMode, CLIENT_KEY).setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    logger.info("connect is lost");
                    try {
                        Client.getClient(runMode, CLIENT_KEY).subscribe(topic);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    messageHandler(topic, new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

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
