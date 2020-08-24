package com.tianlei.item.service.Interf;

import com.tianlei.common.pojo.PageResult;
import com.tianlei.item.pojo.Brand;

import java.util.List;

public interface BrandService {

    PageResult<Brand> queryBrandByPageAndSort(
            Integer page, Integer rows, String sortBy, Boolean desc, String key);

    void saveBrand(Brand brand, List<Long> cids);
}
