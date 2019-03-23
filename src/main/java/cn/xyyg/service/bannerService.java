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
	 * 根据id获取轮播图
	 * @param id
	 * @return
	 */
	public banner getBannerById(int id);
	
	/**
	 * 获取所有轮播图微信端
	 * @param pageSize 
	 * @param pageNum 
	 * @return
	 */
	public List<banner> getBannerForwx();
	
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
	
	/**
	 * 删除轮播图
	 * @return
	 */
	public boolean deleteBanner(List<Integer> ids);
	
	/**
	 * 修改伦比图
	 * @param banner
	 * @return
	 */
	public boolean updateBanner(banner banner);

}
