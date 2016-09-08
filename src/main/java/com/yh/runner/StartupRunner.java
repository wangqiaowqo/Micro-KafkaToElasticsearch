package com.yh.runner;


import com.alibaba.fastjson.JSON;
import com.yh.bean.Invetory;
import com.yh.model.ESInvetory;
import com.yh.service.ESService;
import com.yh.util.KafkaConsumerUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
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

    public static Map<String, Double> cacheMap = new ConcurrentHashMap<>();
    public static Map<String, Double> cacheEsMap = new ConcurrentHashMap<>();
    public static List<ESInvetory> list = new ArrayList<>();
    public static List<String> keylist = new ArrayList<>();

    @Autowired
    ESService esService;
    @Override
    public void run(String... strings) throws Exception {

        //初始化消费者
        KafkaConsumerUtils.getInstance().initKafkaConsumer().subscribe(Arrays.asList("FZ0005"));
        long start = System.currentTimeMillis();

        System.out.println("开始消费："+start);
        while (true) {

            ConsumerRecords<String, String> records =  KafkaConsumerUtils.getInstance().initKafkaConsumer().poll(10000);//超时时间1秒后没反应再去轮询
            //循环获取消息
                for (ConsumerRecord<String, String> record : records){
            //将json字符串转为对象
                    Invetory invetory = JSON.parseObject(record.value(), Invetory.class);//这里如果不是json格式的消息将会报错
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
            if(cacheMap != null && !records.isEmpty()){
                //批量查询
                //keylist装入批量查询条件
                for (String key : cacheMap.keySet()){

                        keylist.add(key);
                }
                List<ESInvetory> eslist = esService.getInvetoryList(keylist);
                //cacheESmap放入查询出来的主键和库存量
                for (ESInvetory esInvetory : eslist) {

                    cacheEsMap.put(esInvetory.getKey(),Double.valueOf(esInvetory.getRepertory()));

                }
                //将计算结果放入list用来批量更新
                for (Map.Entry<String,Double> entry : cacheMap.entrySet()) {
                    //如果es取到的值不为空就相加
                    if (cacheEsMap.get(entry.getKey()) !=  null){

                        Double temp = entry.getValue() + cacheEsMap.get(entry.getKey());
                        list.add(esService.part(entry.getKey(), temp));
                    }//如果es取不到值就初始化直接add
                    list.add(esService.part(entry.getKey(), entry.getValue()));

                }

                long a= System.currentTimeMillis();
                System.out.println("更新到es开始"+a);
                esService.update(list);
                long b= System.currentTimeMillis();
                System.out.println("更新花费时间"+(b-a));
                //清空map,list
                cacheMap.clear();
                cacheEsMap.clear();
                list.clear();
                keylist.clear();

            }
            long end = System.currentTimeMillis();
            System.out.println("结束消费用时："+( end-start));
        }
    }
}
