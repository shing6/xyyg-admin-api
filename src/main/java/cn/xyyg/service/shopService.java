package cn.xyyg.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.xyyg.pojo.exchart;
import cn.xyyg.pojo.shop;
import cn.xyyg.pojo.shopWithGoods;
import cn.xyyg.pojo.user;

public interface shopService {
	/**
	 * 分页查询所有商家
	 * @param pageSize 
	 * @param pageNum 
	 */
	public List<shop> getAllShop(int pageNum, int pageSize);
	
	/**
	 * 根据id查询商家信息
	 * @param id
	 * @return
	 */
    public shop getShopById(Integer id);
    
	/**
	 * 查询商家总数
	 * 
	 */
	public int getAllShopCount();
	/**
	 * 分页查询所有商家及其商品
	 */
	public List<shopWithGoods> getAllShopWithGoods(int pageNum, int pageSize);
	
	/**
	 * 传入id数组查询商家信息
	 * @param idArray
	 * @return
	 */
	public List<shop> getShopByIds(List<Integer> ids);
	
	/**
	 * 根据用户id查询商家信息
	 * @param userId
	 * @return
	 */
    public shop getShopByUserId(Integer userId);
    
    /**
     * 添加商家信息
     * @param shop
     * @return
     */
    public Object insertShop(shop shop,user user);
    
    /**
     * 审核商家
     * @return
     */
    public boolean auditing(Integer id,Integer isPass,String text);
    
    /**
     * 通过分类id查找商家
     * @param sellerCategoryId
     * @return
     */
    public List<shop> getShopByCategoryId(Integer sellerCategoryId);
    
    /**
     * 根据商家id查询商家商品数量和订单数量
     * @param sellerId
     * @return
     */
    public Object getCount(int sellerId);
    
    /**
     * 根据年份获取每月营业额
     * @param year
     * @return
     */
    public exchart getShopMoneyByYear(int year,int sellerId);
    
    /**
     * 修改起送费
     * @param lowPrice
     * @param userId
     * @return
     */
    public boolean updateLowPrice( int lowPrice, int userId);
    
   /**
    * 修改配送费
    * @param freight
    * @param userId
    * @return
    */
    public boolean updateFreight( int freight, int userId);
    
    /**
     * 商家开店或打烊
     * @param status
     * @param userId
     * @return
     */
    public boolean openShop(int status, int userId);
    

}
