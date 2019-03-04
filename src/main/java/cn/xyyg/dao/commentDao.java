package cn.xyyg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.xyyg.pojo.comment;
import cn.xyyg.pojo.commentPicture;
import cn.xyyg.pojo.commentWithPicture;
import cn.xyyg.pojo.page;
import cn.xyyg.pojo.reply;
@Mapper
public interface commentDao {
	/**
	 * 添加评论
	 * @param comment
	 * @return
	 */
	public int insertComment(comment comment);
	
	/**
	 * 根据商品id查询评论
	 * @param goodsId
	 * @return
	 */
	public List<comment> getCommentById(int goodsId);
	
	/**
	 * 批量插入评论图片
	 * @param commentPicture
	 * @return
	 */
	public int insertCommentPicture(List<commentPicture> commentPicture);
	/**
	 * 根据商品id一对多查询评论
	 * @param page
	 * @param goodsId
	 * @return
	 */
	public List<commentWithPicture> getCommentWithPictureById(page page);
	
	/**
	 * 根据商品id查评论数量
	 * @param goodsId
	 * @return
	 */
	public int getCommentCountById(int goodsId);
	
	/**
	 * 根据商品id查好评数量
	 * @param goodsId
	 * @return
	 */
	public int getGoodCommentCount(int goodsId);
	
	/**
	 * 根据商品id查中评数量
	 * @param goodsId
	 * @return
	 */
	public int getMiddleCommentCount(int goodsId);
	
	/**
	 * 根据商品id查差评数量
	 * @param goodsId
	 * @return
	 */
	public int getBadCommentCount(int goodsId);
	
	/**
	 * 一对多查询好评
	 * @param page
	 * @return
	 */
	public List<commentWithPicture> getGoodCommentById(page page);
	
	/**
	 * 一对多查询中评
	 * @param page
	 * @return
	 */
	public List<commentWithPicture> getMiddleCommentById(page page);
	
	/**
	 * 一对多查询差评
	 * @param page
	 * @return
	 */
	public List<commentWithPicture> getBadCommentById(page page);
	
	/**
	 * 根据商家id一对多查询商品评论
	 * @param page
	 * @return
	 */
	public List<commentWithPicture> getCommentWithPictureBySellerId(page page);
	
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
	public int insertReply(reply reply);
}
