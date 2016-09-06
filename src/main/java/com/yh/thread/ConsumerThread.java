package com.yh.thread;


import java.util.Arrays;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsumerThread  implements Runnable {



private final KafkaConsumer<String, String> consumer;

    public ConsumerThread(String topic, String name) {	
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.1.54.182:9092");
        props.put("group.id", "DemoConsumer1");
        //props.put("num.consumer.fetchers", "6");
        props.put("max.poll.records", "10000");
        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "true");
        props.put("request.required.acks","0");
        props.put("auto.commit.interval.ms", "6000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        
        String[] a = {"9346","9381"};
       
        this.consumer = new KafkaConsumer<>(props); 
        this.consumer.subscribe(Arrays.asList("1"));

    }


    public void run() {
        try {
            boolean isRunning = true;

            while (isRunning) {
                ConsumerRecords<String,String> records= consumer.poll(10L);
                for (ConsumerRecord<String, String> record : records){

	            }
            }
            consumer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}