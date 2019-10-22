package com.yfr.service.impl;

import com.yfr.mapper.CategoryMapper;
import com.yfr.pojo.Category;
import com.yfr.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper mapper;

    /**
     * 根据父节点查询商品类目
     *
     * @param id
     * @return
     */
    @Override
    public List<Category> queryCategoryById(Long id) {
        Category category = new Category();
        category.setParentId(id);
        List<Category> categories = mapper.select(category);
        return categories;
    }

    /**
     * 通过id查询对应的分类名称
     *
     * @param
     * @return
     */
    @Override
    public String queryBYyIds(Long id1, Long id2, Long id3) {

        Category category1 = mapper.selectByPrimaryKey(id1);
        Category category2 = mapper.selectByPrimaryKey(id2);
        Category category3 = mapper.selectByPrimaryKey(id3);
        return category1.getName() + "-" + category2.getName() + "-" + category3.getName();

    }
}
