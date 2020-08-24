package com.tianlei.item.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tianlei.common.pojo.PageResult;
import com.tianlei.item.mapper.BrandMapper;
import com.tianlei.item.pojo.Brand;
import com.tianlei.item.service.Interf.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {

        // 开始分页
        PageHelper.startPage(page, rows);

        // 过滤
        Example example = new Example(Brand.class);
//        str != null && str.length > 0 && str.trim().length > 0
        if ((key != null) && (key.length() > 0) && (key.trim().length() > 0)) {
            example.createCriteria().andLike("name", "%" + key + "%")
                    .orEqualTo("letter", key);
        }

        if ((key != null) && (key.length() > 0) && (key.trim().length() > 0)) {
            // 排序
            String orderByClause = sortBy + (desc ? "DESC" : "ASC");
            example.setOrderByClause(orderByClause);
        }

        // 查询
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);

        //返回结果
        return new PageResult<>(pageInfo.getTotal(), pageInfo);
    }


    /**
     * 增加新的品牌并维护品牌和分类表格
     *
     * @param brand
     * @param cids
     */
    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {

        // 新增商品信息
        this.brandMapper.insertSelective(brand);

        //新增品牌分类中间表
        for (Long cid : cids) {
            this.brandMapper.insertCategoryBrand(cid, brand.getId());
        }

    }

}
