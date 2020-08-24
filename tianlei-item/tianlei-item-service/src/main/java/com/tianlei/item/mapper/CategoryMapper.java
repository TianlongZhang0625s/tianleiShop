package com.tianlei.item.mapper;

import com.tianlei.item.pojo.Categroy;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 定义逆向工程单个表操作之外的多个表操作的mapper接口
 * 此mapper服务CategoryService
 *
 * @author tianlong zhang
 */
public interface CategoryMapper extends Mapper<Categroy>, SelectByIdListMapper<Categroy, Long> {
    /**
     * 根据品牌id查询商品的分类
     */

    @Select("select * from tb_category where id in (select category_id from tb_category_brand where brand_id = #{bid})")
    List<Categroy> queryByBrandId(@Param("bid") Long bid);

    /**
     * 根据品牌的category_id删除表中间的相关数据
     */
    @Delete("delete from tb_category_brand where category_id = #{cid}")
    void deleteByCategoryIdInCategoryBrand(@Param("cid") Long cid);

    /**
     * 根据Id查询名字
     */

    @Select("select name from tb_category where id = #{id}")
    String queryNameById(Long id);

    /**
     * 查询最后一条数据
     */

    @Select("select from 'tb_category' where id = (select max(id) from tb_category)")
    List<Categroy> selectLast();
}
