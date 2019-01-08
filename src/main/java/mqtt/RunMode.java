package mqtt;

/**
 * 环境变量存储
 * topics 过滤topics。防止使用者随意创建topic
 * clientType @see{Client.clientMap} 过滤客户端key,禁止随意创建客户端.目前未启用
 *
 * @author yinjinliang
 */
public enum RunMode {
    DEV("tcp://localhost:1883", 1, "dev", "topics,test_topics", "clientType"),
    PROD("tcp://localhost:1883", 1, "prod", "topics,prod_topic", "clientType");


    RunMode(String brokerUrl, int qos, String mode, String topics, String clientType) {
        this.brokerUrl = brokerUrl;
        this.qos = qos;
        this.mode = mode;
        this.topics = topics;
        this.clientType = clientType;
    }

    //mq服务器地址
    private String brokerUrl;
    //消息类型 0最多一次 1最少一次 2只有一次
    private int qos;
    //开发模式
    private String mode;
    //主题过滤器
    private String topics;
    //客户端key过滤器
    private String clientType;

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public int getQos() {
        return qos;
    }

    public String getMode() {
        return mode;
    }

    public String getTopics() {
        return topics;
    }

    public String getClientType() {
        return clientType;
    }

    @Override
    public String toString() {
        return "broker url:" + this.brokerUrl + "qos:" + this.qos + "mode:" + this.mode +
                "topics:" + this.topics + "client type:" + this.clientType;
    }
}
