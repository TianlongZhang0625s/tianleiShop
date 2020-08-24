package com.tianlei.item.service.impl;

import com.tianlei.item.pojo.Categroy;
import com.tianlei.item.service.Interf.CategoryService;
import com.tianlei.item.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据parentId查询子类目实现service
     * return 类目
     */
    public List<Categroy> queryCategoryListByParentId(Long pid) {
        Categroy record = new Categroy();
        record.setParentId(pid);
        return this.categoryMapper.select(record);
    }

    @Override
    public List<Categroy> queryByBrandId(Long id) {
        return this.categoryMapper.queryByBrandId(id);
    }


    @Override
    public void saveCategory(Categroy categroy) {

        // 首先将传递过来的id置换位null
        categroy.setId(null);
        // 保存
        this.categoryMapper.insert(categroy);

        // 修改父亲节点
        Categroy parent = new Categroy();
        parent.setId(categroy.getParentId());

        parent.setIsParent(true);
        this.categoryMapper.updateByPrimaryKeySelective(parent);
    }


    @Override
    public void updateCategory(Categroy categroy) {
        this.categoryMapper.updateByPrimaryKeySelective(categroy);
    }

    @Override
    public void deleteCategory(Long id) {

        Categroy categroy = this.categoryMapper.selectByPrimaryKey(id);

        if (categroy.getIsParent()) {
            // 查找所有的叶子节点
            List<Categroy> list = new ArrayList<>();
            queryAllLeafNode(categroy, list);

            // 查找所有的字节点
            List<Categroy> list2 = new ArrayList<>();
            queryAllNode(categroy, list2);

            // 删除tb_category重的数据，使用list2
            for (Categroy categroy1 : list2) {
                this.categoryMapper.delete(categroy1);
            }

            // 整理维护中间表
            for (Categroy categroy1 : list2) {
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(categroy1.getId());
            }

        } else {
            // 查询此节点的父亲节点的孩子个数====>含有几个兄弟节点
            Example example = new Example(Categroy.class);
            example.createCriteria().andEqualTo("parentId", categroy.getId());
            List<Categroy> lists = this.categoryMapper.selectByExample(example);

            if (lists.size() != 1) {
                this.categoryMapper.deleteByPrimaryKey(categroy.getId());

                // 再次维护中间表格
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(categroy.getId());
            } else {
                // 无兄弟节点时
                this.categoryMapper.deleteByPrimaryKey(categroy.getId());

                Categroy parent = new Categroy();
                parent.setId(categroy.getParentId());
                parent.setIsParent(false);
                this.categoryMapper.updateByPrimaryKeySelective(parent);

                // 再次维护中间表
                this.categoryMapper.deleteByCategoryIdInCategoryBrand(categroy.getId());
            }
        }
    }

    /**
     * 根据ids查询对应的名称列表
     *
     * @param ids
     * @return
     */
    @Override
    public List<String> queryNameByIds(List<Long> ids) {

        List<String> names = new ArrayList<>();
        if (ids != null && ids.size() != 0) {
            for (Long id : ids) {
                names.add(this.categoryMapper.queryNameById(id));
            }
        }
        return names;
    }

    public List<Categroy> queryLast() {
        List<Categroy> list = this.categoryMapper.selectLast();
        return list;
    }


    @Override
    public List<Categroy> queryCategoryByIds(List<Long> ids) {
        return this.categoryMapper.selectByIdList(ids);
    }

    private void queryAllNode(Categroy categroy, List<Categroy> list2) {

        list2.add(categroy);
        Example example = new Example(Categroy.class);
        example.createCriteria().andEqualTo("parentId", categroy.getId());
        List<Categroy> categroyList = this.categoryMapper.selectByExample(example);

        for (Categroy categroy1 : categroyList) {
            queryAllNode(categroy1, list2);


        }
    }

    @Override
    public List<Categroy> queryAllCategoryLevelByCid3(Long id) {
        List<Categroy> categroyList = new ArrayList<>();
        Categroy categroy = this.categoryMapper.selectByPrimaryKey(id);
        while (categroy.getParentId() != 0) {
            categroyList.add(categroy);
            categroy = this.categoryMapper.selectByPrimaryKey(categroy.getParentId());
        }
        categroyList.add(categroy);
        return categroyList;
    }

    private void queryAllLeafNode(Categroy categroy, List<Categroy> list) {
        if (!categroy.getIsParent()) {
            list.add(categroy);
        }

        Example example = new Example(Categroy.class);
        example.createCriteria().andEqualTo("parentId", categroy.getId());
        List<Categroy> categroyList = this.categoryMapper.selectByExample(example);

        for (Categroy categroy1 : categroyList) {
            queryAllLeafNode(categroy1, list);
        }

    }
}
