package com.yh.service;


import com.yh.model.ESInvetory;
import com.yh.repository.ESRepository;
import com.yh.serviceImpl.ESServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by youchaofan on 2016/9/1.
 */
@Service
public class ESService implements ESServiceImpl {
    @Autowired
    ESRepository esRepository;
    private  ESService ESService;

    public ESService getInstance (){
        return this.ESService;
    }


    /**
     * 向ES取最新库存量
     */
    @Override
    public String findRepertory(String key) {
        if(esRepository.findOne(key) == null){
            return "0.0";
        } else {
            ESInvetory esInvetory = esRepository.findOne(key);
            return esInvetory.getRepertory();
        }

    }

    /**
     * 更新到ES
     */
    @Override
    public boolean update(String key,Double value) {
        String[] invetoryArr = key.split("_");//将拼接主键拆分
        ESInvetory esInvetory = esRepository.findOne("2");
        esInvetory.setStore(invetoryArr[0]);
       esInvetory.setItems(invetoryArr[1]);
        esInvetory.setStorage(invetoryArr[2]);
        esInvetory.setChannel_mark(invetoryArr[3]);
        esInvetory.setRepertory(String.valueOf(value));
        esInvetory.setKey(key);
        esRepository.save(esInvetory);
        return true;
    }
}
