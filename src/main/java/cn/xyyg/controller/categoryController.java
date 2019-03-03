package cn.xyyg.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xyyg.pojo.category;
import cn.xyyg.pojo.categoryShop;
import cn.xyyg.pojo.orderWithGoods;
import cn.xyyg.pojo.shop;
import cn.xyyg.pojo.user;
import cn.xyyg.service.categoryService;
import cn.xyyg.service.shopService;
import cn.xyyg.service.userService;
import cn.xyyg.util.JwtUtil;
import cn.xyyg.util.ResponseUtil;
@RestController
@RequestMapping("/category")
public class categoryController {
	@Autowired
	private categoryService categoryService;
	@Autowired
    private userService userService;
	@Autowired
    private shopService shopService;
	/**
	 * 通过商店id获取商品分类
	 * @param id
	 * @return
	 */
	@GetMapping("/getShopCategoryById")
    public List<category> getShopCategoryById(int id){
        List<category> categoryList=categoryService.getShopCategoryById(id);
		return categoryList;
    	
    }
    
    /**
     * 商家后台获取分类
     * @param request
     * @return
     */
    @PostMapping("/getCategoryById")
    public Object getCategoryById(HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		int userId=Integer.parseInt(request.getParameter("userId"));
        	user user=userService.getUserById(userId);
        	shop shop=shopService.getShopByUserId(user.getId());
        	List<category> categoryList=categoryService.getShopCategoryById(shop.getId());
            return categoryList;
            
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
        
        
		
    	
    }
    
    /**
	 * 获取所有分类
	 * @param id
	 * @return
	 */
	@GetMapping("/getShopCategory")
    public List<categoryShop> getShopCategory(){
        List<categoryShop> categoryShopList=categoryService.getShopCategory();
		return categoryShopList;
    	
    }
}
