package mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mqtt message send
 * CLIENT_KEY @see{Client.clientMap} 缓存客户端的key
 *
 * @author yinjinliang
 */
public class SendMessage {

    private final static Logger logger = LoggerFactory.getLogger(SendMessage.class);

    private final static String CLIENT_KEY = "send";


    /**
     * 发送消息 以CLIENT_KEY的客户端进行发送
     *
     * @param topic   主题
     * @param message 消息内容
     * @param runMode 运行模式
     */
    public void sendMessage(String topic, String message, RunMode runMode) {
        logger.info("topic :" + topic);
        logger.info("message :" + message);
        logger.info("run mode :" + runMode.toString());

        if (runMode.getTopics().indexOf(topic) == -1) {
            logger.info("the topic is not exists with:" + topic);
            return;
        }
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(runMode.getQos());
        try {
            Client.getClient(runMode, CLIENT_KEY).publish(topic, mqttMessage);
        } catch (MqttException e) {
            logger.info("mqtt message send exception");
            logger.info("topic:" + topic + "message:" + message + "runMode:" + runMode);
            logger.info("" + e.toString());
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

    }
}
