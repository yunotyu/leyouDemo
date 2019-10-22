package com.yfr.service;

import com.yfr.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    /**
     * 通过id查询对应的目录结构
     *
     * @param id
     * @return
     */
    public List<Category> queryCategoryById(Long id);

    public String queryBYyIds(Long id1, Long id2, Long id3);
}
