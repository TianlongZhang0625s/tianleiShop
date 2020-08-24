package com.tianlei.item.service.Interf;

import com.tianlei.item.pojo.Categroy;

import java.util.List;

public interface CategoryService {

    /**
     * 根据parentId查询子类项目
     * 获取记录
     */
    List<Categroy> queryCategoryListByParentId(Long pid);

    //根据brand id查询分类信息
    List<Categroy> queryByBrandId(Long id);

    // 保存节点
    void saveCategory(Categroy categroy);

    //更新节点
    void updateCategory(Categroy categroy);

    void deleteCategory(Long id);

    /**
     * 查询所有分类的名称
     */
    List<String> queryNameByIds(List<Long> ids);

    /**
     * 根据分类id集合查询分类信息
     */
    List<Categroy> queryCategoryByIds(List<Long> ids);

    /**
     * 根据cid3查询所有层级分类
     */
    List<Categroy> queryAllCategoryLevelByCid3(Long id);
}
