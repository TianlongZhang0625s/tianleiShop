package com.tianlei.item.controller;

import com.tianlei.item.pojo.Categroy;
import com.tianlei.item.service.Interf.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("list")
    public ResponseEntity<List<Categroy>> queryCategoryListByParentId(
            @RequestParam(value = "pid", defaultValue = "0") Long pid
    ) {
        try {
            if (pid == null || pid.longValue() < 0) {
                return ResponseEntity.badRequest().build();
            }

            // 查询执行
            List<Categroy> categroyList = this.categoryService.queryCategoryListByParentId(pid);
            if (CollectionUtils.isEmpty(categroyList)) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(categroyList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Categroy>> queryByBrandId(@PathVariable("bid") Long bid) {
        List<Categroy> list = this.categoryService.queryByBrandId(bid);

        if (list == null || list.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    /**
     * 保存目录修改内容
     *
     * @param categroy
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveCategory(Categroy categroy) {
        this.categoryService.saveCategory(categroy);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新列表内容
     *
     * @param categroy
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateCategory(Categroy categroy) {
        this.categoryService.updateCategory(categroy);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("cid/{cid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("cid") Long id) {
        this.categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    /**
     * 根据商品分类的id集合查询分类名称
     *
     * @param ids
     * @return
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNameByIds(@RequestParam("ids") List<Long> ids) {
        List<String> list = categoryService.queryNameByIds(ids);
        if (list == null || list.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    /**
     * 根据id集合查询所有的category
     *
     * @param ids
     * @return
     */
    @GetMapping("all")
    public ResponseEntity<List<Categroy>> queryCategoryByIds(@RequestParam("ids") List<Long> ids) {
        List<Categroy> list = categoryService.queryCategoryByIds(ids);
        if (list == null || list.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("all/level/{cid3}")
    public ResponseEntity<List<Categroy>> queryAllCategoryLevelByCid3(@PathVariable("cid") Long id) {
        List<Categroy> list = categoryService.queryAllCategoryLevelByCid3(id);

        if (list == null || list.size() < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

}
