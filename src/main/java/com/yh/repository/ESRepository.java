package com.yh.repository;

import com.yh.model.ESInvetory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by youchaofan on 2016/9/3.
 */
public interface ESRepository extends ElasticsearchRepository<ESInvetory,String> {}
