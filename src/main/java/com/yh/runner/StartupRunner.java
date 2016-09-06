package com.yh.runner;


import com.alibaba.fastjson.JSON;
import com.yh.bean.Invetory;
import com.yh.service.ESService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by youchaofan on 2016/8/31.
 */
@Component
public class StartupRunner implements CommandLineRunner {

    @Autowired
    ESService esService;
    @Override
    public void run(String... strings) throws Exception {
        Map<String, Double> cacheMap = new ConcurrentHashMap<String, Double>();

        Properties props = new Properties();
        props.put("bootstrap.servers", "10.1.54.182:9092");
        props.put("group.id", "test1");
        props.put("max.poll.records", "10000");//批量拉取消息
        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "10000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
       // String[] a = {"9466","9467"};
        consumer.subscribe(Arrays.asList("9012"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(10L);//超时时间1秒后没反应再去轮询
            //循环获取消息
                for (ConsumerRecord<String, String> record : records){
            //将json字符串转为对象
                    Invetory invetory = JSON.parseObject(record.value(),Invetory.class);//这里如果不是json格式的消息将会报错
            //获取库存量
                    Double b = invetory.getRepertory();
            //如果map里的库存量被清除则为null，将初始值放入，否则累加
                    if(cacheMap.get(invetory.getKey()) == null ){

                        cacheMap.put(invetory.getKey(),b) ;
                    } else {
                       cacheMap.put(invetory.getKey(),cacheMap.get(invetory.getKey())+b);
                    }

            }
            //更新到es
            if(cacheMap!=null){
                for (Map.Entry<String,Double> entry:cacheMap.entrySet()) {
                    //将ES里的库存量和map里的相加，当es里不存在的时候，find方法里返回0.0
                        esService.update(entry.getKey(),entry.getValue()+Double.valueOf(esService.findRepertory(entry.getKey())));

                }
                //清空map
                cacheMap.clear();
            }

        }
    }
}
