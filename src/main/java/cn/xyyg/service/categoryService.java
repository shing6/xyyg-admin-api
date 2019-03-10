package cn.xyyg.service;

import java.util.List;

import cn.xyyg.pojo.category;
import cn.xyyg.pojo.categoryShop;

public interface categoryService {
	
	    /**
	    * 根据商家id查询所属分类
	    */
		public List<category> getShopCategoryById(Integer id);
		
		 /**
		    * 根据商家id分页查询所属分类
		    */
			public List<category> getShopCategoryByPage(Integer id, int pageNum, int pageSize);
		
		/**
		 * 获取所有商家分类
		 * @return
		 */
		public List<categoryShop> getShopCategory();
		
		/**
		 * 获取商家分类数量
		 * @return
		 */
		public int getCountShopCategoryById(Integer id);
		
		/**
		 * 商家添加商品分类
		 * @param category
		 * @return
		 */
		public boolean insertCategory(category category);
		
		/**
		 * 商家修改商品分类
		 * @param category
		 * @return
		 */
		public boolean updateCategory(category category);
		
		/**
		 * 获取所有商家分类数量
		 * @return
		 */
		public int getShopCategoryCount();
		
		/**
		 * 商家删除商品分类
		 * @param category
		 * @return
		 */
		public boolean deleteCategory(Integer id);
		
		/**
		 * 分页获取所有商家分类
		 * @param pageNum
		 * @param pageSize
		 * @return
		 */
		public List<categoryShop> getShopCategoryByPage(int pageNum,int pageSize);
		
		/**
		 * 管理员添加商家分类
		 * @param categoryShop
		 * @return
		 */
		public boolean insertCategoryShop(categoryShop categoryShop);

}
