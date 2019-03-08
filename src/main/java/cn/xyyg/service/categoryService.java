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
		 * 获取所有分类
		 * @return
		 */
		public List<categoryShop> getShopCategory();
		
		/**
		 * 获取商家分类数量
		 * @return
		 */
		public int getCountShopCategoryById(Integer id);

}
