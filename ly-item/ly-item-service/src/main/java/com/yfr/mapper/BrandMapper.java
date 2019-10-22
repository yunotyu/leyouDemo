package com.yfr.mapper;

import com.yfr.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    /**
     * 将分类id和品牌id插入中间表
     * @param cid
     * @param bid
     * @return
     */
    @Insert("insert into tb_category_brand value(#{cid},#{bid})")
    public Integer InsertCgyAndBra(@Param("cid") Long cid, @Param("bid") Long bid);

    /**
     * 通过中间表查询分类对应的品牌
     * @param cid
     * @return
     */
    @Select("SELECT b.* FROM tb_brand b INNER JOIN tb_category_brand c on b.id=c.brand_id where category_id=#{cid}")
    public List<Brand> queryBidByCid(@Param("cid") Long cid);
}
