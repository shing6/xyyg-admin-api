package cn.xyyg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.xyyg.pojo.banner;
@Mapper
public interface bannerDao {
	
	/**
	 * 分页获取所有轮播图
	 * @return
	 */
	public List<banner> getBanner();
	
	/**
	 * 获取广告数量
	 * @return
	 */
	public int getBannerCount();

}