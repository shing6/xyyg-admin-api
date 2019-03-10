package cn.xyyg.service;

import java.util.List;

import cn.xyyg.pojo.banner;

public interface bannerService {
	/**
	 * 分页获取所有轮播图
	 * @param pageSize 
	 * @param pageNum 
	 * @return
	 */
	public List<banner> getBanner(int pageNum, int pageSize);
	
	/**
	 * 获取广告数量
	 * @return
	 */
	public int getBannerCount();
	
	/**
	 * 添加轮播图
	 * @param banner
	 * @return
	 */
	public boolean insertBanner(banner banner);

}
