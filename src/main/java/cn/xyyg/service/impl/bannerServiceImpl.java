package cn.xyyg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.xyyg.pojo.banner;
import cn.xyyg.service.bannerService;
import cn.xyyg.dao.bannerDao;

@Service
@Transactional
public class bannerServiceImpl implements bannerService {
	@Autowired
    private bannerDao bannerDao;
	/**
	 * 获取所有轮播图
	 */
	@Override
	public List<banner> getBanner(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.bannerDao.getBanner();
	}
	
	/**
	 * 获取广告数量
	 */
	@Override
	public int getBannerCount() {
		
		return this.bannerDao.getBannerCount();
	}
     
	/**
	 * 添加轮播图
	 */
	@Override
	public boolean insertBanner(banner banner) {
		int rows = this.bannerDao.insertBanner(banner);
		if(rows > 0){
			return true;
		}
		else{
			return false;
		}
		
	}
     /**
      * 获取所有轮播图
      */
	@Override
	public List<banner> getBannerForwx() {
		
		return this.bannerDao.getBanner();
	}

	/**
	 * 删除轮播图
	 */
	@Override
	public boolean deleteBanner(List<Integer> ids) {
		int rows = this.bannerDao.deleteBanner(ids);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
		
	}
    /**
     *  修改轮播图
     */
	@Override
	public boolean updateBanner(banner banner) {
		int rows = this.bannerDao.updateBanner(banner);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
		
	}
    /**
     * 根据id获取轮播图
     */
	@Override
	public banner getBannerById(int id) {
		
		return this.bannerDao.getBannerById(id);
	}

}
