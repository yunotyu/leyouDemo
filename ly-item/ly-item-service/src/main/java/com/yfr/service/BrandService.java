package com.yfr.service;

import com.yfr.pojo.Brand;
import com.yfr.pojo.PageResult;

import java.util.List;

public interface BrandService {
    PageResult<Brand> queryBrandByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    Boolean insertBrand(Brand brand, List<Long> cids);

    List<Brand> queryBrandsByCid(Long cid);
}
