package com.yh.serviceImpl;

import com.yh.model.ESInvetory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by youchaofan on 2016/9/3.
 */
public interface ESServiceImpl  {

    public String findRepertory(String key);

    public boolean update(List<ESInvetory> list);

    public ESInvetory part(String key,Double value);

    public List<ESInvetory> getInvetoryList(List<String> keylist);

}
