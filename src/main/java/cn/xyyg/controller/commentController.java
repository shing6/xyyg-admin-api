package cn.xyyg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xyyg.pojo.category;
import cn.xyyg.pojo.comment;
import cn.xyyg.pojo.commentPicture;
import cn.xyyg.pojo.commentWithPicture;
import cn.xyyg.pojo.orderWithGoods;
import cn.xyyg.pojo.reply;
import cn.xyyg.pojo.shop;
import cn.xyyg.pojo.user;
import cn.xyyg.pojo.wechatUser;
import cn.xyyg.service.userService;
import cn.xyyg.service.commentService;
import cn.xyyg.service.shopService;
import cn.xyyg.util.JwtUtil;
import cn.xyyg.util.ResponseUtil;
import cn.xyyg.util.createTimeUtil;
import net.sf.json.JSONArray;

@RestController
@RequestMapping("/comment")
public class commentController {
	@Autowired
    private userService userService;
	
	@Autowired
    private commentService commentService;
	
	@Autowired
    private shopService shopService;
	/**
	 * 添加评论
	 * @param request
	 * @return
	 */
	@PostMapping("/insertComment")
    public Object insertComment(HttpServletRequest request){
		
		String picAddr = request.getParameter("successArr");
		String openId=request.getParameter("open_id");
		String content=request.getParameter("content");
		int goodsId=Integer.parseInt(request.getParameter("goodsId"));
		int orderId=Integer.parseInt(request.getParameter("orderId"));
		int score=Integer.parseInt(request.getParameter("score"));
        String[] stuList = picAddr.split(",");
		List<String> picAddrList = new ArrayList<String>();	
		for(String str : stuList){
       	 
			picAddrList.add(new String(str));
        }
		
		
	
		// 根据返回的user实体类，判断用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser!=null){
        	int wechatUserId=wechatUser.getId();
        	comment comment =new comment();
        	comment.setGoodsId(goodsId);
        	comment.setWechatUserId(wechatUserId);
        	comment.setCreateTime(createTimeUtil.getTime());
        	comment.setScore(score);
        	comment.setContent(content);
            boolean flag = commentService.insertComment(comment,orderId,picAddrList);
        	if(flag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.insertDataFailed();
        	}
        }
        else{
        	return ResponseUtil.unlogin();
        }
		
       
    }
	/**
	 * 查询评论
	 * @param goodsId
	 * @return
	 */
	@GetMapping("/getCommentById")
    public List<comment> getCommentById(int goodsId){
        List<comment>commentList=commentService.getCommentById(goodsId);
		return commentList;
    	
    }
    
    /**
     * 根据商品id一对多查询评论
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/getCommentWithPictureById")
    public Object getCommentWithPictureById(HttpServletResponse response,HttpServletRequest request){
    	int pageNum=Integer.parseInt(request.getParameter("pageNum"));
    	int pageSize=Integer.parseInt(request.getParameter("pageSize"));
    	int goodsId=Integer.parseInt(request.getParameter("goodsId"));
    	int showType=Integer.parseInt(request.getParameter("showType"));
    	int count=commentService.getCommentCountById(goodsId,showType);
    	int goodCount=commentService.getGoodComment(goodsId);
    	int middleCount=commentService.getMiddleComment(goodsId);
    	int badCount=commentService.getBadComment(goodsId);
    	int allCount=commentService.getAllComment(goodsId);
    	response.setIntHeader("X-Total-Count",count);
    	response.setIntHeader("X-Good-Count",goodCount);
    	response.setIntHeader("X-Middle-Count",middleCount);
    	response.setIntHeader("X-Bad-Count",badCount);
    	response.setIntHeader("X-All-Count",allCount);
        List<commentWithPicture> commentWithPictureList = commentService.getCommentWithPictureById(pageNum, pageSize, goodsId,showType);
		return commentWithPictureList;
    	
    }
    
    /**
     * 根据商家id一对多查询评论
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/getCommentWithPictureBySellerId")
    public Object getCommentWithPictureBySellerId(HttpServletResponse response,HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		int pageNum=Integer.parseInt(request.getParameter("pageNum"));
        	int pageSize=Integer.parseInt(request.getParameter("pageSize"));
        	int userId=Integer.parseInt(request.getParameter("userId"));
        	
        	user user=userService.getUserById(userId);
        	shop shop=shopService.getShopByUserId(user.getId());
        	List<commentWithPicture> commentWithPictureList = commentService.getCommentWithPictureBySellerId(pageNum, pageSize,shop.getId());
            return commentWithPictureList;
             
             
    	}
    	else{
         	return ResponseUtil.unlogin();
         }
    	
    	
    }

    /**
	 * 添加商家回复
	 * @param request
	 * @return
	 */
	@PostMapping("/insertReply")
    public Object insertReply(HttpServletRequest request){
		boolean  flag= JwtUtil.verify(request.getParameter("token"));
		if(flag){
			String content= request.getParameter("content");
			int commentId=Integer.parseInt(request.getParameter("commentId"));
			    reply reply =new reply();
	        	reply.setContent(content);
	        	reply.setCommentId(commentId);
	        	reply.setCreateTime(createTimeUtil.getTime());
	            boolean insertflag = commentService.insertReply(reply);
	        	if(insertflag){
	        		return ResponseUtil.ok();
	        	}
	        	else{
	        		return ResponseUtil.insertDataFailed();
	        	}
		}
		else{
			return ResponseUtil.unlogin();
		}
		
        
        
		
       
    }
	

}
