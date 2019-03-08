package cn.xyyg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.xyyg.pojo.category;
import cn.xyyg.pojo.categoryShop;
import cn.xyyg.service.categoryService;
import cn.xyyg.dao.categoryDao;
@Service
@Transactional
public class categoryServiceImpl implements categoryService {
     @Autowired
     private categoryDao categoryDao;
	/**
	 * 根据id查商品分类
	 */
	public List<category> getShopCategoryById(Integer id) {
		
		return this.categoryDao.getShopCategoryById(id);
	}
	
	/**
	 * 获取所有分类
	 */
	@Override
	public List<categoryShop> getShopCategory() {
		
		return this.categoryDao.getShopCategory();
	}
    
	/**
     * 获取分类数量
     */
	@Override
	public int getCountShopCategoryById(Integer id) {
		
		return this.categoryDao.getCountShopCategoryById(id);
	}
    /**
     * 分页查询商家分类
     */
	@Override
	public List<category> getShopCategoryByPage(Integer id, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.categoryDao.getShopCategoryById(id);
	}
     
	/**
	 * 商家添加商品分类
	 */
	@Override
	public boolean insertCategory(category category) {
		int rows = this.categoryDao.insertCategory(category);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
		
	}
     /**
      * 商家修改分类
      */
	@Override
	public boolean updateCategory(category category) {
		int rows = this.categoryDao.updateCategory(category);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
		
	}

}
