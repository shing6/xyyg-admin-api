package cn.xyyg.service;

import java.util.List;

import cn.xyyg.pojo.comment;
import cn.xyyg.pojo.commentWithPicture;
import cn.xyyg.pojo.page;
import cn.xyyg.pojo.reply;

public interface commentService {
	/**
	 * 添加评论
	 * @param comment
	 * @param orderId 
	 * @param picAddrList 
	 * @return
	 */
	public boolean insertComment(comment comment, int orderId, List<String> picAddrList);
	
	/**
	 * 根据商品id查询评论
	 * @param goodsId
	 * @return
	 */
	public List<comment> getCommentById(int goodsId);
	
	/**
	 * 一对多查询评论
	 * @param pageNum
	 * @param pageSize
	 * @param goodsId 
	 * @param showType
	 * @return
	 */
	public List<commentWithPicture> getCommentWithPictureById(int pageNum,int pageSize, int goodsId,int showType);
	
	/**
	 * 根据商品id查评论数量
	 * @param goodsId
	 * @return
	 */
	public int getCommentCountById(int goodsId,int showType);
	
	/**
	 * 根据商品id查全部评论数量
	 * @param goodsId
	 * @return
	 */
	public int getAllComment(int goodsId);
	
	/**
	 * 根据商品id查好评数量
	 * @param goodsId
	 * @return
	 */
	public int getGoodComment(int goodsId);
	/**
	 * 根据商品id查中评数量
	 * @param goodsId
	 * @return
	 */
	public int getMiddleComment(int goodsId);
	/**
	 * 根据商品id查差评数量
	 * @param goodsId
	 * @return
	 */
	public int getBadComment(int goodsId);
	
	/**
	 * 根据商家id一对多查询商品评论
	 * @param page
	 * @return
	 */
	public List<commentWithPicture> getCommentWithPictureBySellerId(int pageNum,int pageSize,int sellerId);
	
	/**
	 * 根据商家id查找评论数量
	 * @param sellerId
	 * @return
	 */
	public int getCommentCountBySellerId(int sellerId);
	
	/**
	 * 商家回复
	 * @param reply
	 * @return
	 */
	public boolean insertReply(reply reply);

}
