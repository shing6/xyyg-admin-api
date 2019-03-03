package cn.xyyg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
