package cn.xyyg.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xyyg.dao.commentDao;
import cn.xyyg.dao.orderDao;
import cn.xyyg.pojo.comment;
import cn.xyyg.pojo.commentPicture;
import cn.xyyg.pojo.commentWithPicture;
import cn.xyyg.pojo.page;
import cn.xyyg.pojo.reply;
import cn.xyyg.service.commentService;


@Service
@Transactional
public class commentServiceImpl implements commentService {
	@Autowired
    private commentDao commentDao;
	@Autowired
    private orderDao orderDao;
	
	/**
	 * 添加评论
	 */
	@Override
	public boolean insertComment(comment comment,int orderId,List<String> picAddrList) {
		int counts=this.commentDao.insertComment(comment);
		if(counts>0){
			boolean flag=true;
			this.orderDao.updateCommentStatus(orderId, comment.getGoodsId());
			List<Integer> isCommentList=this.orderDao.getIsCommentById(orderId);
			//图片
			List<commentPicture> commentPictureList=new ArrayList<>();
			for(int i=0;i<picAddrList.size();i++){
				commentPicture commentPicture=new commentPicture();
				commentPicture.setPicAddr(picAddrList.get(i));
				commentPicture.setCommentId(comment.getId());
				commentPictureList.add(commentPicture);
			    
			}
			
			this.commentDao.insertCommentPicture(commentPictureList);
			for(int i=0;i<isCommentList.size();i++){
				if(isCommentList.get(i)==0){
					flag=false;
				}
			}
			if(flag){
				this.orderDao.updateOrderStaus(orderId, 5);
			}
			return true;
		}
		else{
			return false;
		}
		
	}
	
	/**
	 * 根据商品id查询评论
	 */
	@Override
	public List<comment> getCommentById(int goodsId) {
		
		return this.commentDao.getCommentById(goodsId);
	}
    /**
     * 一对多查询评论
     */
	@Override
	public List<commentWithPicture> getCommentWithPictureById(int pageNum,int pageSize,int goodsId ,int showType) {
		int newPageNum=pageNum*pageSize;
        page page=new page();
		page.setPageNum(newPageNum);
		page.setPageSize(pageSize);
		page.setGoodsId(goodsId);
		//好评
		if(showType==1){
			return this.commentDao.getGoodCommentById(page);
		}
		//中评
		else if(showType==2){
			return this.commentDao.getMiddleCommentById(page);
		}
		//差评
		else if(showType==3){
			return this.commentDao.getBadCommentById(page);
		}
		else{
			return this.commentDao.getCommentWithPictureById(page);
		}
	}

	/**
	 * 根据商品id查评论数量
	 */
	@Override
	public int getCommentCountById(int goodsId,int showType) {
		        //好评
				if(showType==1){
					return this.commentDao.getGoodCommentCount(goodsId);
				}
				//中评
				else if(showType==2){
					return this.commentDao.getMiddleCommentCount(goodsId);
				}
				//差评
				else if(showType==3){
					return this.commentDao.getBadCommentCount(goodsId);
				}
				else{
					return this.commentDao.getCommentCountById(goodsId);
				}
		
	}
    /**
     * 查好评数量
     */
	@Override
	public int getGoodComment(int goodsId) {
		
		return this.commentDao.getGoodCommentCount(goodsId);
	}
	/**
     * 查中评数量
     */
	@Override
	public int getMiddleComment(int goodsId) {
		
		return this.commentDao.getMiddleCommentCount(goodsId);
	}
	/**
     * 查差评数量
     */
	@Override
	public int getBadComment(int goodsId) {
		
		return this.commentDao.getBadCommentCount(goodsId);
	}
    /**
     * 查全部数量
     */
	@Override
	public int getAllComment(int goodsId) {
		
		return this.commentDao.getCommentCountById(goodsId);
	}
     
	/**
	 * 根据商家id一对多查询商品评论
	 */
	@Override
	public List<commentWithPicture> getCommentWithPictureBySellerId(int pageNum,int pageSize,int sellerId) {
		int newPageNum=pageNum*pageSize;
        page page=new page();
		page.setPageNum(newPageNum);
		page.setPageSize(pageSize);
		page.setSellerId(sellerId);
		return this.commentDao.getCommentWithPictureBySellerId(page);
	}
     
	/**
	 * 商家回复
	 */
	@Override
	public boolean insertReply(reply reply) {
		int rows =this.commentDao.insertReply(reply);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
		
	}
    /**
     * 根据商家id获取商家评论数
     */
	@Override
	public int getCommentCountBySellerId(int sellerId) {
		
		return this.commentDao.getCommentCountBySellerId(sellerId);
	}

}
