package com.yfr.controller;

import com.yfr.pojo.Category;
import com.yfr.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoryById(@RequestParam(value = "pid", defaultValue = "0") Long id) {

        System.out.println("方法执行了");
        List<Category> categories = service.queryCategoryById(id);
        if (CollectionUtils.isEmpty(categories)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
