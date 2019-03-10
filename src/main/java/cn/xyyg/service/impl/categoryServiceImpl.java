package cn.xyyg.service.impl;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.xyyg.pojo.category;
import cn.xyyg.pojo.categoryShop;
import cn.xyyg.pojo.goods;
import cn.xyyg.pojo.goodsWithCounts;
import cn.xyyg.service.categoryService;
import cn.xyyg.dao.categoryDao;
import cn.xyyg.dao.goodsDao;
@Service
@Transactional
public class categoryServiceImpl implements categoryService {
     @Autowired
     private categoryDao categoryDao;
     
     @Autowired
     private goodsDao goodsDao;
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
    /**
     * 商家删除分类
     */
	@Override
	public boolean deleteCategory(Integer id) {
		List<goodsWithCounts> goods = this.goodsDao.getGoodsByCategoryId(id);
		if(goods.size()<=0){
			int rows = this.categoryDao.deleteCategory(id);
			if(rows>0){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
		
	}
    /**
     * 获取所有商家分类
     */
	@Override
	public List<categoryShop> getShopCategoryByPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.categoryDao.getShopCategory();
	}
     
	 /**
      * 获取所有商家分类数量
      */
	@Override
	public int getShopCategoryCount() {
		
		return this.categoryDao.getShopCategoryCount();
	}
     
	/**
	 * 管理员添加商家分类
	 */
	@Override
	public boolean insertCategoryShop(categoryShop categoryShop) {
		int rows = categoryDao.insertCategoryShop(categoryShop);
		if(rows>0){
		   return true;
		}
		else{
			return false;
		}
		
	}
	/**
	 * 管理员修改商家分类
	 */
	@Override
	public boolean updateCategoryShop(categoryShop categoryShop) {
		int rows = categoryDao.updateCategoryShop(categoryShop);
		if(rows>0){
		   return true;
		}
		else{
			return false;
		}
	}
	
	

}
