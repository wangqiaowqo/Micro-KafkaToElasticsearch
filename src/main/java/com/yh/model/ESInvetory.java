package com.yh.model;

import com.yh.config.ESProp;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by youchaofan on 2016/9/1.
 */
@Document(indexName = ESProp.INDEX_NAME, type = ESProp.TYPE_NAME)
public class ESInvetory {


    /**
     * 拼接主键(当作ES的ID)
     */
    @Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String  key;
    /**
     * 门店
     */
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String store;
    /**
     * 商品
     */
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String items;
    /**
     * 库位
     */
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String storage;
    /**
     * 渠道标识
     */
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String channel_mark;
    /**
     * 库存数量
     */
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String repertory;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getChannel_mark() {
        return channel_mark;
    }

    public void setChannel_mark(String channel_mark) {
        this.channel_mark = channel_mark;
    }

    public String getRepertory() {
        return repertory;
    }

    public void setRepertory(String repertory) {
        this.repertory = repertory;
    }
}
