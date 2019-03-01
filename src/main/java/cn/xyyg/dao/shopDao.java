package cn.xyyg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.xyyg.pojo.shop;
import cn.xyyg.pojo.shopWithGoods;
import cn.xyyg.pojo.page;

@Mapper
public interface shopDao {
	/**
	 * 获取所有商家
	 * @return
	 */
	public List<shop> getAllShop();
	
	/**
	 * 根据id查询商家信息
	 * @param id
	 * @return
	 */
    public shop getShopById(Integer id);
    
    
	/**
	 * 查询商家总数
	 * @return
	 */
	public int getAllShopCount();
	/**
	 * 获取所有商家及其商品
	 * @return
	 */
	public List<shopWithGoods>  getAllShopWithGoods(page page);
	/**
	 * 传入id数组查询
	 * @param ids
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
     * 商家入驻
     * @param shop
     * @return
     */
    public int insertShop(shop shop);
    
    /**
     * 审核通过
     * @param id
     * @return
     */
    public int passShop(Integer id);
    
    /**
     * 审核未通过
     * @param id
     * @return
     */
    public int notPassShop(Integer id);
}
