package cn.xyyg.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.zhenzi.sms.ZhenziSmsClient;

import cn.xyyg.dao.shopDao;
import cn.xyyg.dao.userDao;
import cn.xyyg.pojo.exchart;
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
	public boolean auditing(Integer id, Integer isPass,String text) {
		shop shop = shopDao.getShopById(id);
		user user = userDao.getUserById(shop.getUserId());
		ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "100861", "8419db37-1168-41b2-957d-9ca46863c994");
		
       if(isPass==1){
    	   try {
   			String result = client.send(user.getPhone(), "您的商家入驻申请已通过");
   		} catch (Exception e) {
   			
   			e.printStackTrace();
   		}
			this.shopDao.passShop(id);
			this.userDao.updateUserRole(shop.getUserId());
			return true;
		}
		else if(isPass==0){
			try {
				String result = client.send(user.getPhone(), "您的商家入驻申请失败,原因("+text+")");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			this.shopDao.notPassShop(id);
			this.userDao.updateUserNoRole(shop.getUserId());
			return true;
		}
       
		else {
			try {
				String result = client.send(user.getPhone(), "您的店铺已被管理员关闭,原因("+text+")");
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			this.shopDao.notPassShop(id);
			this.userDao.updateUserNoRole(shop.getUserId());
			return true;
		}
		
	}
	
	/**
	 * 通过分类id查找商家
	 */
	@Override
	public List<shop> getShopByCategoryId(Integer sellerCategoryId) {
		
		return this.shopDao.getShopByCategoryId(sellerCategoryId);
	}
	
	/**
	 * 根据商家id查询商家商品数量和订单数量
	 */
	@Override
	public Object getCount(int sellerId) {
		int goodsCount=this.shopDao.getGoodsCountById(sellerId);
		int orderCount=this.shopDao.getOrderCountById(sellerId);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("goodsCount", goodsCount);
		obj.put("orderCount", orderCount);
		return obj;
	}
	
	/**
	 * 根据年份获取每月营业额
	 */
	@Override
	public exchart getShopMoneyByYear(int year,int sellerId) {
		
		return this.shopDao.getShopMoneyByYear(year,sellerId);
	}
	/**
	 * 修改起送费
	 */
	@Override
	public boolean updateLowPrice(int lowPrice, int userId) {
		int rows = this.shopDao.updateLowPrice(lowPrice, userId);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	/**
	 * 修改配送费
	 */
	@Override
	public boolean updateFreight(int freight, int userId) {
		int rows = this.shopDao.updateFreight(freight, userId);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 商家开门或打烊
	 */
	@Override
	public boolean openShop(int status, int userId) {
		int rows = this.shopDao.openShop(status, userId);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
		
	}

}
