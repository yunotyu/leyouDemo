package com.yfr.controller;

import com.yfr.pojo.Brand;
import com.yfr.pojo.PageResult;
import com.yfr.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService service;

    /**
     * 分页查询排序品牌信息
     *
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @RequestMapping("/page")
    @ResponseBody
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "desc", defaultValue = "true") Boolean desc
    ) {
        //调用service层方法进行查询
        PageResult<Brand> result = service.queryBrandByPage(key, page, rows, sortBy, desc);
        //判断查询结果是否为空
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //返回查询结果
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //因为前端发送过来的是一个个字符串(name=..&letter=..)，所以这里的参数可以使用下面的方法接收
    //如果发送过来的是{name:"a",letter:"b"}JSON格式数据，就只能使用实体类对象来接收
    @PostMapping
    public ResponseEntity<Void> InsertBrand(Brand brand, @RequestParam(name = "cids", required = true) List<Long> cid) {
        System.out.println("方法...");
        boolean flag = false;

        if (brand != null) {
            //先插入数据到品牌表
            flag = service.insertBrand(brand, cid);
        }

        if (flag) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 通过对应的分类id查询品牌名称
     *
     * @param cid
     * @return
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> queryNameByCid(@PathVariable(name = "cid") Long cid) {

        List<Brand> list = service.queryBrandsByCid(cid);
        if (!CollectionUtils.isEmpty(list)) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

