package cn.xyyg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.xyyg.pojo.category;
@Mapper
public interface categoryDao {
   /**
    * 根据商家id查询所属分类
    */
	public List<category> getShopCategoryById(Integer id);
}
