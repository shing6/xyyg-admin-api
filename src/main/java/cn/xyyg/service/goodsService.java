package cn.xyyg.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.xyyg.pojo.goods;
import cn.xyyg.pojo.goodsDescPic;
import cn.xyyg.pojo.goodsPicture;
import cn.xyyg.pojo.goodsWithCounts;
import cn.xyyg.pojo.page;
import cn.xyyg.pojo.goodsDesc;

public interface goodsService {
	 /**
     * 查询所有商品 一对多
     */
	public List<goodsDescPic> getAllGoodsDescPic();
	
	/**
	 * 根据分类id查找商品属性
	 * @param id
	 * @return
	 */
	public List<goodsWithCounts> getGoodsByCategoryId(Integer id);
	
	/**
	 * 根据商品id查询商品详细信息
	 * @param id
	 * @return
	 */
	public goodsDescPic getGoodsById(Integer id);
	
	/**
	 * 传入id数组查询
	 * @param ids
	 * @return
	 */
	public List<goods> getGoodsByIds(List<Integer> ids);
	
	/**
	 * 根据商品名字模糊查询商品信息
	 * @param name
	 * @return
	 */
	public List<goods> getGoodsByName(String name);
	
	/**
	 * 模糊查询并且排序
	 * @param name  查询名字
	 * @param condition 排序列名
	 * @param sort  排序规则升降序
	 * @return
	 */
	public List<goodsWithCounts> getGoodsByNameOrderBySort(String name,String condition,String sort,int pageNum, int pageSize);
	
	/**
	 * 模糊查询返回数量
	 * @param name  查询名字
	 * @param condition 排序列名
	 * @param sort  排序规则升降序
	 * @return
	 */
	public int getGoodsByNameOrderBySortCount(String name,String condition,String sort);
	
	/**
	 * 根据商品id查询商品简要信息
	 * @param id
	 * @return
	 */
	public goods getGoodById(int id);
	
	/**
	 * 根据商家id分页查询商品详细信息
	 * @param sellerId
	 * @return
	 */
	public List<goods> getGoodsBySellerId(int pageNum,int pageSize,int sellerId);
	
	/**
	 * 根据商家id查询商品数量
	 * @param sellerId
	 * @return
	 */
	public int getGoodsCountBySellerId(int sellerId);
	
	/**
	 * 添加商品
	 * @param goods
	 * @return
	 */
	public boolean insertGoods(goods goods,List<String> picAddrList,List<goodsDesc> goodsDesc);
	
	/**
	 * 修改商品
	 * @param goods
	 * @param picAddrList
	 * @param goodsDesc
	 * @return
	 */
	public boolean updateGoods(goods goods,List<goodsPicture> picAddrList,List<goodsDesc> goodsDesc);
	
	/**
	 * 上下架商品
	 * @param goods
	 * @return
	 */
	public boolean updateGoodsStatus(goods goods);
	
	/**
	 * 商家一对多模糊查询商品
	 * @param page
	 * @return
	 */
	public List<goodsDescPic> selectGoodsByName(page page);
	
	/**
	 * 商家模糊查询返回数量
	 * @param goods
	 * @return
	 */
	public int selectGoodsCountByName(goods goods);
	
	/**
	 * 传入id数组删除商品
	 * @param ids
	 * @return
	 */
	public boolean deleteGoodsByIds(List<Integer> ids);
	
	/**
	 * 根据分类id分页查询商品详细信息
	 * @param page
	 * @return
	 */
	public List<goods> getGoodsByCategoty(int pageNum,int pageSize,int categoryId);
	
	/**
	 * 根据分类id分页查询商品数量
	 * @param categoryId
	 * @return
	 */
	public int getGoodsCountByCategory(int categoryId);
}
