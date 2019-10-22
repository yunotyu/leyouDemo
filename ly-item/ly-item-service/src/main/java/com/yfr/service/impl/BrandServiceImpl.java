package com.yfr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yfr.mapper.BrandMapper;
import com.yfr.pojo.Brand;
import com.yfr.pojo.PageResult;
import com.yfr.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;


import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper mapper;

    /**
     * 分页查询品牌列表
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     */
    @Override
    public PageResult<Brand> queryBrandByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {

        //使用PageHelper进行分页
        PageHelper.startPage(page, rows);

        //进行数据查询
        //创建一个Example对象，进行排序和模糊条件的设置，然后再调用selectExample方法进行查询
        Example example = new Example(Brand.class);

        //该字符串的长度大于0，且不为null
        if (StringUtils.isNotBlank(key)) {
            //创造查询的条件，这里是模糊查询,两个%代表SQL语句的模糊查询
            example.createCriteria().andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }

        //下面进行查询结果排序条件的设置
        if (StringUtils.isNotBlank(sortBy)) {
            //排序使用的是列名+DESC或ASC
            String orderby = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderby);
        }

        List<Brand> brandList = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(brandList)) {
            return null;
        }

        //创建PageInfo对象保存分页的数据
        PageInfo pageInfo = new PageInfo(brandList);

        //保存数据到PageResult对象中，返回给浏览器
        PageResult<Brand> result = new PageResult<>();
        result.setItems(brandList);
        result.setTotal(pageInfo.getTotal());
        result.setTotalPage((long) pageInfo.getPages());
        return result;
    }

    /**
     * 插入数据品牌表和分类品牌中间表
     *
     * @param brand
     * @return
     */
    @Override
    public Boolean insertBrand(Brand brand, List<Long> cids) {
        //insertSelective保存一个实体，null的属性不会保存
        int num = mapper.insertSelective(brand);
        //判断插入数据是否成功
        if (num < 1) {
            return false;
        }
        //将数据插入中间表，因为可能一个品牌对应多个分类，所以这里使用集合遍历
        for (Long cid : cids) {
            int num1 = mapper.InsertCgyAndBra(cid, brand.getId());
            if (num1 < 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 通过分类id查询对应的品牌名称
     *
     * @param cid
     * @return
     */
    @Override
    public List<Brand> queryBrandsByCid(Long cid) {
        List<Brand> list = mapper.queryBidByCid(cid);
        return list;
    }


}
