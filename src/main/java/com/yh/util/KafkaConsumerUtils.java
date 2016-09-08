package com.yh.util;


import com.yh.config.KafkaConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.Properties;

/**
 * Created by youchaofan on 2016/8/26.
 * kafka消费者静态工厂
 */
public class KafkaConsumerUtils {

    //private final Logger log =  LoggerFactory.getLogger(KafkaProducerUtils.class);

    private static KafkaConsumerUtils instance = new KafkaConsumerUtils();
    /**
     * 消费者
     */
    private static KafkaConsumer<String,String> consumerNew;

    /**
     * 静态类配置
     */
    public static KafkaConsumerUtils getInstance(){
        return instance;
    }
    /**
     * 构造函数
     */
    public KafkaConsumerUtils(){

    }


    /**
     * 初始化kafka发送消息配置
     */
    public Properties initConsumerConfig() {
        Properties props = new Properties();

        // kafka服务端口
        props.put("bootstrap.servers", KafkaConsumerConfig.BOOTSTRAP_SERVERS);
        //消费者组
        props.put("group.id", KafkaConsumerConfig.GROUP_ID);
        //配置可以消费在消费者开启前已经存在的消息
        props.put("auto.offset.reset", KafkaConsumerConfig.AUTO_OFFSET_RESET );
        //自动提交偏移量
        props.put("enable.auto.commit", KafkaConsumerConfig.ENABLE_AUTO_COMMIT);
        //自动提交偏移量间隔
        props.put("auto.commit.interval.ms", KafkaConsumerConfig.AUTO_COMMIT_INTERVAL_MS);
        //session超时时间
        props.put("session.timeout.ms", KafkaConsumerConfig.SESSION_TIMEOUT_MS );
        //批量拉取消息
        props.put("max.poll.records", KafkaConsumerConfig.MAX_POLL_RECORDS );
        //key序列化类
        props.put("key.deserializer", KafkaConsumerConfig.KEY_SERIALIZER);
        //value序列化类
        props.put("value.deserializer", KafkaConsumerConfig.VALUE_SERIALIZER );

        return props;
    }

    /**
     * 初始化Consumer
     */
    public KafkaConsumer<String,String> initKafkaConsumer() {
        if (consumerNew == null) {
            // 初始化参数
            Properties props = this.initConsumerConfig();
            // consumer对象
            consumerNew = new KafkaConsumer<>(props);
        }

        return consumerNew;
    }


}
