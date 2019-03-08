package cn.xyyg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.xyyg.pojo.category;
import cn.xyyg.pojo.categoryShop;
@Mapper
public interface categoryDao {
   /**
    * 根据商家id查询所属分类
    */
	public List<category> getShopCategoryById(Integer id);
	
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
	
	/**
	 * 商家添加商品分类
	 * @param category
	 * @return
	 */
	public int insertCategory(category category);
	
	/**
	 * 商家修改商品分类
	 * @param category
	 * @return
	 */
	public int updateCategory(category category);
}
