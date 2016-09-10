package com.yh.service;



import com.yh.model.ESInvetory;
import com.yh.repository.ESRepository;
import com.yh.serviceImpl.ESServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by youchaofan on 2016/9/1.
 */
@Service
public class ESService implements ESServiceImpl {

    @Autowired
    ESRepository esRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 向ES取最新库存量
     */
    @Override
    public String findRepertory(String key) {
        if (esRepository.findOne(key) == null) {
            return "0.0";
        } else {
            ESInvetory esInvetory = esRepository.findOne(key);
            return esInvetory.getRepertory();
        }

    }


    /*@Override
    public boolean update(String key,Double value) {
        String[] invetoryArr = key.split("_");//将拼接主键拆分
        ESInvetory esInvetory = new ESInvetory();
        esInvetory.setStore(invetoryArr[0]);
        esInvetory.setItems(invetoryArr[1]);
        esInvetory.setStorage(invetoryArr[2]);
        esInvetory.setChannel_mark(invetoryArr[3]);
        esInvetory.setRepertory(String.valueOf(value));
        esInvetory.setKey(key);
        esRepository.save(esInvetory);
        return true;
    }*/
    /**
     * 更新到ES
     */
    @Override
    public boolean update(List<ESInvetory> list) {

        esRepository.save(list);
        return true;

    }
    /**
     * 拆分
     */
    @Override
    public ESInvetory part(String key,Double value) {
        String[] invetoryArr = key.split("_");//将拼接主键拆分
        ESInvetory esInvetory = new ESInvetory();
        esInvetory.setStore(invetoryArr[0]);
        esInvetory.setItems(invetoryArr[1]);
        esInvetory.setStorage(invetoryArr[2]);
        esInvetory.setChannel_mark(invetoryArr[3]);
        esInvetory.setRepertory(String.valueOf(value));
        esInvetory.setKey(key);

        return esInvetory;
    }
    /**
     * 模版批量插入
     */
  /* public <T> boolean batchInsertOrUpdate(List<T> invetoryList) {
        List<IndexQuery> queries = new ArrayList<IndexQuery>();
        // 转化
        @SuppressWarnings("unchecked")
        final List<ESInvetory> esInvetoryList = (List<ESInvetory>) invetoryList;

        for (ESInvetory esInvetory : esInvetoryList) {
            IndexQuery indexQuery = new IndexQueryBuilder().withId(esInvetory.getKey()).withObject(esInvetory).build();
            queries.add(indexQuery);
        }
        elasticsearchTemplate.bulkIndex(queries);
        return true;
    }*/
    @Override
    public List<ESInvetory> getInvetoryList(List<String> keylist){

        if (keylist != null){
            List<ESInvetory> invetoryList = (List<ESInvetory>) esRepository.findAll(keylist);
            return   invetoryList;
        }
        List<ESInvetory> list = new ArrayList<>();
        return list;
    }
}
