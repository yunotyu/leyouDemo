package com.yfr.service;

import com.yfr.pojo.PageResult;
import com.yfr.pojo.SpecGroup;
import com.yfr.pojo.SpecParam;
import com.yfr.pojo.bo.SpuBo;

import java.util.List;

public interface GoodService {
    PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows);

    List<SpecGroup> queryGroupByCid(Long cid);

    List<SpecParam> queryParamByGid(Long aLong, Long gid, Boolean searching, Boolean generic);

    void saveGood(SpuBo spuBo);
}
