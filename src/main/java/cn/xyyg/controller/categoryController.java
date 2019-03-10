package cn.xyyg.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * 商家后台分页获取分类
     * @param request
     * @return
     */
    @PostMapping("/getCategoryByPage")
    public Object getCategoryByPage(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		int userId=Integer.parseInt(request.getParameter("userId"));
    		int pageNum=Integer.parseInt(request.getParameter("pageNum"));
    		int pageSize=Integer.parseInt(request.getParameter("pageSize"));
        	user user=userService.getUserById(userId);
        	shop shop=shopService.getShopByUserId(user.getId());
        	int count =categoryService.getCountShopCategoryById(shop.getId());
          	response.setIntHeader("X-Total-Count",count);
        	List<category> categoryList=categoryService.getShopCategoryByPage(shop.getId(), pageNum, pageSize);
            return categoryList;
            
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
       }
    
    /**
	 * 获取所有商家分类
	 * @param id
	 * @return
	 */
	@GetMapping("/getShopCategory")
    public List<categoryShop> getShopCategory(){
        List<categoryShop> categoryShopList=categoryService.getShopCategory();
		return categoryShopList;
    	
    }
    
    /**
     * 商家后台添加分类
     * @param request
     * @return
     */
    @PostMapping("/insertCategory")
    public Object insertCategory(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		int userId=Integer.parseInt(request.getParameter("userId"));
    		String categoryName=request.getParameter("categoryName");
    		user user=userService.getUserById(userId);
        	shop shop=shopService.getShopByUserId(user.getId());
        	category category = new category();
        	category.setCategoryName(categoryName);
        	category.setSellerId(shop.getId());
        	boolean insertFlag=categoryService.insertCategory(category);
        	if(insertFlag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.fail();
        	}
             
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
       }
    
    /**
     * 商家后台修改分类
     * @param request
     * @return
     */
    @PostMapping("/updateCategory")
    public Object updateCategory(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		int id=Integer.parseInt(request.getParameter("id"));
    		String categoryName=request.getParameter("categoryName");
    		category category = new category();
        	category.setCategoryName(categoryName);
        	category.setId(id);
        	boolean insertFlag=categoryService.updateCategory(category);
        	if(insertFlag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.fail();
        	}
             
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
       }
    
    /**
     * 商家后台删除分类
     * @param request
     * @return
     */
    @PostMapping("/deleteCategory")
    public Object deleteCategory(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		int id=Integer.parseInt(request.getParameter("id"));
    		
    		boolean insertFlag=categoryService.deleteCategory(id);
        	if(insertFlag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.fail();
        	}
             
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
       }
    
    /**
     * 商家后台分页获取所有商家分类
     * @param request
     * @param response
     * @return
     */
	@PostMapping("/getShopCategoryByPage")
    public Object getShopCategoryByPage(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		int pageNum=Integer.parseInt(request.getParameter("pageNum"));
    		int pageSize=Integer.parseInt(request.getParameter("pageSize"));
        	int count =categoryService.getShopCategoryCount();
          	response.setIntHeader("X-Total-Count",count);
        	List<categoryShop> categoryShopList=categoryService.getShopCategoryByPage(pageNum, pageSize);
            return categoryShopList;
            
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
        
    	
    }
	
	/**
     * 管理员添加商家分类
     * @param request
     * @return
     */
    @PostMapping("/insertCategoryShop")
    public Object insertCategoryShop(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		String name=request.getParameter("name");
    		String desc=request.getParameter("desc");
        	categoryShop categoryShop = new categoryShop();
        	categoryShop.setName(name);
        	categoryShop.setDesc(desc);;
        	boolean insertFlag=categoryService.insertCategoryShop(categoryShop);
        	if(insertFlag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.fail();
        	}
             
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
       }
    
    /**
     * 管理员后台修改分类
     * @param request
     * @return
     */
    @PostMapping("/updateCategoryShop")
    public Object updateCategoryShop(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		int id=Integer.parseInt(request.getParameter("id"));
    		String name=request.getParameter("name");
    		categoryShop categoryShop = new categoryShop();
        	categoryShop.setName(name);
        	categoryShop.setId(id);
        	boolean insertFlag=categoryService.updateCategoryShop(categoryShop);
        	if(insertFlag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.fail();
        	}
             
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
       }
    
    /**
     * 管理员删除商家分类
     * @param request
     * @return
     */
    @PostMapping("/deleteCategoryShop")
    public Object deleteCategoryShop(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		int id=Integer.parseInt(request.getParameter("id"));
    		
    		boolean insertFlag=categoryService.deleteCategoryShop(id);
        	if(insertFlag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.fail();
        	}
             
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
       }
    
}
