package com.yfr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yfr.mapper.*;
import com.yfr.pojo.*;
import com.yfr.pojo.bo.SpuBo;
import com.yfr.service.CategoryService;
import com.yfr.service.GoodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GoodServiceImpl implements GoodService {

    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private StockMapper stockMapper;

    /**
     * 对商品进行分页查询
     *
     * @param key
     * @param saleable
     * @param page
     * @param rows
     */
    @Override
    public PageResult<SpuBo> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {

        // 分页,最多允许查100条
        PageHelper.startPage(page, Math.min(rows, 100));

        List<Spu> spus = new ArrayList<>();

        List<SpuBo> spuBos = new ArrayList<>();

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        //模糊查询和查询是否上下架
        //模糊查询和上下架
//        if(StringUtils.isNotBlank(key)&&saleable!=null){
//            criteria.andLike("title", "%"+key+"%");
//            criteria.andEqualTo("saleable", saleable);
//            spus = spuMapper.selectByExample(example);
//            //上下架
//        }else if(StringUtils.isBlank(key)&&saleable!=null){
//            criteria.andEqualTo("saleable", saleable);
//            spus = spuMapper.selectByExample(example);
//            //模糊查询
//        }else if(StringUtils.isNotBlank(key)&&saleable==null){
//            criteria.andLike("title", "%"+key+"%");
//            spus = spuMapper.selectByExample(example);
//            //查询全部
//        }else {
//            spus=spuMapper.selectAll();
//        }


        // 是否模糊查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        // 是否过滤上下架
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        //当Example不设置值时，查询全部内容
        spus = spuMapper.selectByExample(example);

        //h获取每个SPU的分类名称和品牌名称
        for (Spu spu : spus) {
            //将父类的值向下传递给子类
            SpuBo spuBo = new SpuBo();
            //属性拷贝
            BeanUtils.copyProperties(spu, spuBo);
            spuBo.setCname(categoryService.queryBYyIds(spu.getCid1(), spu.getCid2(), spu.getCid3()));

            Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            spuBo.setBname(brand.getName());

            spuBos.add(spuBo);
        }
        PageResult<SpuBo> pageResult = new PageResult<>();
        pageResult.setItems(spuBos);
        pageResult.setTotal(new PageInfo<Spu>(spus).getTotal());
        return pageResult;

    }

    /**
     * 根据分类id查询对应的商品组信息
     *
     * @param cid
     * @return
     */
    @Override
    public List<SpecGroup> queryGroupByCid(Long cid) {

        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return specGroupMapper.select(specGroup);
    }

    /**
     * 通过组id查询对应的参数信息
     *
     * @param
     * @param gid
     * @param searching
     * @param generic
     * @return
     */
    @Override
    public List<SpecParam> queryParamByGid(Long gid, Long cid, Boolean searching, Boolean generic) {

        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setGroupId(gid);
        specParam.setGeneric(generic);
        specParam.setSearching(searching);
        return specParamMapper.select(specParam);
    }

    /**
     * 新增商品
     *
     * @param spuBo
     */
    @Override
    @Transactional
    public void saveGood(SpuBo spuBo) {
        //保存商品的Spu信息,为了不让人使用别的软件来恶意添加我们的商品信息，
        //我们这里的一些对象的字段必须要强制设置，比如这个商品的创建时间，
        //是否上下架等
        spuBo.setCreateTime(new Date());
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        spuMapper.insert(spuBo);

        //获取并保存sku信息
        List<Sku> skus = spuBo.getSkus();
        for (Sku sku : skus) {
            //如果这条信息不是有效的
            if (!sku.getEnable()) {
                continue;
            }
            //保存sku表信息
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            skuMapper.insert(sku);

            //获取并保存库存信息
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);

        }

        //获取并保存spudetail信息
        SpuDetail detail = spuBo.getSpuDetail();
        detail.setSpuId(spuBo.getId());
        spuDetailMapper.insert(detail);

    }


}
