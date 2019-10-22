package com.yfr.controller;

import com.yfr.pojo.PageResult;
import com.yfr.pojo.SpecGroup;
import com.yfr.pojo.SpecParam;
import com.yfr.pojo.Spu;
import com.yfr.pojo.bo.SpuBo;
import com.yfr.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GoodController {

    @Autowired
    private GoodService service;

    /**
     * 查询商品信息
     *
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuByPage(
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "saleable", required = false) Boolean saleable,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows
    ) {

        PageResult<SpuBo> pageResult = service.querySpuByPage(key, saleable, page, rows);
        //如果查询出来的信息为空
        if (!pageResult.getItems().isEmpty()) {
            return new ResponseEntity<>(pageResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * 根据分类id查询对应的商品组信息
     *
     * @param cid
     * @return
     */
    @GetMapping("/spec/groups/{id}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable(value = "id") Long cid) {

        List<SpecGroup> specGroups = service.queryGroupByCid(cid);
        if (!CollectionUtils.isEmpty(specGroups)) {
            return new ResponseEntity<>(specGroups, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * 根据不同信息查询对应的参数信息
     *
     * @param gid
     * @return
     */
    @GetMapping("/spec/params")
    public ResponseEntity<List<SpecParam>> queryParamByGid(
            @RequestParam(name = "gid", required = false) Long gid,
            @RequestParam(name = "cid", required = false) Long cid,
            @RequestParam(name = "searching", required = false) Boolean searching,
            @RequestParam(name = "generic", required = false) Boolean generic
    ) {
        List<SpecParam> specParams = service.queryParamByGid(gid, cid, searching, generic);
        if (!CollectionUtils.isEmpty(specParams)) {
            return new ResponseEntity<>(specParams, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * 新增商品
     *
     * @return
     */
    @PostMapping("/goods")
    public ResponseEntity<Void> SaveGood(@RequestBody SpuBo spuBo) {
        try {
            service.saveGood(spuBo);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
