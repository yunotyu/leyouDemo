package com.yfr.mapper;

import com.yfr.pojo.Spu;
import com.yfr.pojo.bo.SpuBo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpuMapper extends Mapper<Spu> {
    @Select("select * from")
    public List<SpuBo> querySpu();
}
