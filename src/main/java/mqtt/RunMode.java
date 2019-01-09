package mqtt;

/**
 * 环境变量存储
 *
 * @author yinjinliang
 */
public enum RunMode {
    DEV("tcp://localhost:1883", 0, "dev"),
    PROD("tcp://localhost:1883", 0, "prod");


    RunMode(String brokerUrl, int qos, String mode) {
        this.brokerUrl = brokerUrl;
        this.qos = qos;
        this.mode = mode;

    }

    //mq服务器地址
    private String brokerUrl;
    //消息类型 0最多一次 1最少一次 2只有一次
    private int qos;
    //开发模式
    private String mode;


    public String getBrokerUrl() {
        return brokerUrl;
    }

    public int getQos() {
        return qos;
    }

    public String getMode() {
        return mode;
    }


    @Override
    public String toString() {
        return "broker url:" + this.brokerUrl + " qos:" + this.qos + " mode:" + this.mode;
    }
}
