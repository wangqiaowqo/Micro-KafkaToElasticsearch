package com.yh.service;

import com.yh.serviceImpl.StoreServiceImpl;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * Created by youchaofan on 2016/9/6.
 */
public class StoreService implements StoreServiceImpl {
    @Override
    public String[] getStore() throws IOException {
        File file = ResourceUtils.getFile("classpath:store.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String[] storeArray = new String[10];
        int i = 0;
        String b;
        while ((b=reader.readLine())!=null){
            storeArray[i] = b;
            i++;
        }
        return storeArray;
    }
}
