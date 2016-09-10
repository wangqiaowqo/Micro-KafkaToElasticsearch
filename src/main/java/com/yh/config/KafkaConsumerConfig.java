package com.yh.config;

/**
 * Created by youchaofan on 2016/9/7.
 * 消费者配置常量
 */
public class KafkaConsumerConfig {

    public static final String BOOTSTRAP_SERVERS = "10.1.54.182:9092";
    public static final String GROUP_ID = "service02";
    public static final String MAX_POLL_RECORDS = "1000";
    public static final String AUTO_OFFSET_RESET = "earliest";
    public static final String ENABLE_AUTO_COMMIT = "true";
    public static final String AUTO_COMMIT_INTERVAL_MS = "6000";
    public static final String SESSION_TIMEOUT_MS = "30000";
    public static final String KEY_SERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
    public static final String VALUE_SERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";

}
