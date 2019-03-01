package cn.xyyg.service;

import java.util.List;

import cn.xyyg.pojo.category;

public interface categoryService {
	
	 /**
	    * 根据商家id查询所属分类
	    */
		public List<category> getShopCategoryById(Integer id);

}
