package cn.xyyg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.xyyg.dao.shopDao;
import cn.xyyg.dao.userDao;
import cn.xyyg.pojo.goods;
import cn.xyyg.pojo.page;
import cn.xyyg.pojo.shop;
import cn.xyyg.pojo.shopWithGoods;
import cn.xyyg.pojo.user;
import cn.xyyg.service.shopService;
import cn.xyyg.util.ResponseUtil;


@Service
@Transactional
public class shopServiceImpl implements shopService {
	@Autowired
	private shopDao shopDao;
	@Autowired
	private userDao userDao;
	@Override
	/**
	 * 分页查询所有商家
	 */
	public List<shop> getAllShop(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
        return this.shopDao.getAllShop();
	}
	/**
	 * 查询所有商家及其商品
	 */
	public List<shopWithGoods> getAllShopWithGoods(int pageNum, int pageSize) {
		int newPageNum=pageNum*pageSize;
        page page=new page();
		page.setPageNum(newPageNum);
		page.setPageSize(pageSize);
		
		
		return this.shopDao.getAllShopWithGoods(page);
	}
	/**
	 * 查询商家总数
	 */
	public int getAllShopCount() {
		
		return this.shopDao.getAllShopCount();
	}
	/**
	 * 根据id查询商家信息
	 */
	public shop getShopById(Integer id) {
		
		return this.shopDao.getShopById(id);
	}
	/**
	 * 传入id数组查询商家信息
	 */
	@Override
	public List<shop> getShopByIds(List<Integer> ids) {
		
		return this.shopDao.getShopByIds(ids);
	}
	
	/**
	 * 根据用户id查询所属商家信息
	 */
	@Override
	public shop getShopByUserId(Integer userId) {
		
		return this.shopDao.getShopByUserId(userId);
	}
	
	/**
	 * 商家入驻
	 */
	@Override
	public Object insertShop(shop shop,user user) {
		user userName = this.userDao.findUserName(user.getUsername());//判断用户名是否存在
		if(userName==null){
			int rows =this.userDao.insertUser(user);
			if(rows>0){
				shop.setUserId(user.getId());
				this.shopDao.insertShop(shop);
				return ResponseUtil.ok();
			}
			else{
				return ResponseUtil.fail();
			}
		}
		else{
			return ResponseUtil.SameUserName();
		}
		
	}
	
	/**
	 * 审核商家
	 */
	@Override
	public boolean auditing(Integer id, Integer isPass) {
		if(isPass==1){
			this.shopDao.passShop(id);
			return true;
		}
		else{
			this.shopDao.notPassShop(id);
			return true;
		}
		
	}
	
	/**
	 * 通过分类id查找商家
	 */
	@Override
	public shop getShopByCategoryId(Integer sellerCategoryId) {
		
		return this.shopDao.getShopByCategoryId(sellerCategoryId);
	}

}
