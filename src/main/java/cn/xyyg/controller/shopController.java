package cn.xyyg.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;

import cn.xyyg.pojo.exchart;
import cn.xyyg.pojo.page;
import cn.xyyg.pojo.shop;
import cn.xyyg.pojo.shopWithGoods;
import cn.xyyg.pojo.user;
import cn.xyyg.service.shopService;
import cn.xyyg.service.userService;
import cn.xyyg.util.JwtUtil;
import cn.xyyg.util.MD5;
import cn.xyyg.util.ResponseUtil;
import cn.xyyg.util.createTimeUtil;

@RestController
@RequestMapping("/shop")
public class shopController {
    @Autowired
    private shopService shopService;
    @Autowired
    private userService userService;
    /**
     * 分页查询所有商家
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getAllShop")
    public List<shop> getAllShop(int pageNum, int pageSize){
    	//使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
        List<shop> shoplist=shopService.getAllShop(pageNum,pageSize);
		return shoplist;
    	
    }
    
    /**
     * 根据id查商家信息
     * @param id
     * @return
     */
    @GetMapping("/getShopById")
    public shop getShopById(int id){
    	shop shop=shopService.getShopById(id);
		return shop;
    	}
    
    /**
     * 根据分类id查商家信息
     * @param id
     * @return
     */
    @GetMapping("/getShopByCategoryId")
    public List<shop> getShopByCategoryId(int sellerCategoryId){
    	List<shop> shopList=shopService.getShopByCategoryId(sellerCategoryId);
		return shopList;
    	}
    
    /**
     * 分页查询所有商家及其商品
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/getAllShopWithGoods")
    public List<shopWithGoods> getAllShopWithGoods(HttpServletResponse response,int pageNum, int pageSize){
    	int count =shopService.getAllShopCount();
    	List<shopWithGoods> shopWithGoodsList=shopService.getAllShopWithGoods(pageNum, pageSize);
    	response.setIntHeader("X-Total-Count",count);
        return shopWithGoodsList;
    	
    }
    /**
     * 根据id数组查询商铺信息
     * @param request
     * @return
     */
    @PostMapping("/getShopByIds")
    public List<shop> getShopByIds(HttpServletRequest request){
    	
    		 String items = request.getParameter("ids");
        	 //正则表达式去掉首尾非数字字符 []
             Pattern pattern = Pattern.compile("^\\D+|\\D+$");
             Matcher matcher = pattern.matcher(items);
             items = matcher.replaceAll("");
             String[] stuList = items.split(",");
             
             List<Integer> ids = new ArrayList<Integer>();
             for(String str : stuList){
            	 
            	 ids.add(new Integer(str));
             }
            List<shop> shopList =shopService.getShopByIds(ids);
            
            
     		return shopList;
    	
    	
  	}
    
    /**
     * 商家入驻
     * @param request
     * @return
     */
    @PostMapping("/insertShop")
    public Object insertShop(HttpServletRequest request){
    	     String shopName = request.getParameter("shopName");
    	     String address = request.getParameter("address");
    	     String detailAddr = request.getParameter("detailAddr");
    	     String imgUrl = request.getParameter("imgUrl");
    	     String name = request.getParameter("name");
    	     String phone = request.getParameter("phone");
    	     String username = request.getParameter("username");
    	     String password = request.getParameter("password");
    	     double longitude = Double.parseDouble(request.getParameter("longitude"));
    	     double latitude = Double.parseDouble(request.getParameter("latitude"));
    	     String pwdByMd5 =null;
    	    	try {
    				pwdByMd5= MD5.EncoderByMd5(password);//md5加密密码
    			} catch (NoSuchAlgorithmException e) {
    				
    				e.printStackTrace();
    			} catch (UnsupportedEncodingException e) {
    				
    				e.printStackTrace();
    			}
    	     user user =new user();
    	     user.setName(name);
    	     user.setPhone(phone);
    	     user.setUsername(username);
    	     user.setPassword(pwdByMd5);
    	     shop shop =new shop();
    	     shop.setName(shopName);
    	     shop.setAddress(address);
    	     shop.setDetailAddr(detailAddr);
    	     shop.setLogoPic(imgUrl);
    	     shop.setLongitude(longitude);
    	     shop.setLatitude(latitude);
    	     shop.setCreateTime(createTimeUtil.getTime());
    	     Object obj = this.shopService.insertShop(shop, user);
    	     return obj;
    }
    /**
     * 商家审核
     * @param request
     * @return
     */
    @PostMapping("/auditing")
    public Object auditing(HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	int id=Integer.parseInt(request.getParameter("id")) ;
    	int isPass = Integer.parseInt(request.getParameter("isPass"));
    	String text=request.getParameter("text");
    	if(flag){
    		boolean shopflag = this.shopService.auditing(id, isPass,text);
    		if(shopflag){
//    			  shop shop= this.shopService.getShopById(id);
//    			  this.userService.updateUserRole(shop.getUserId());
    		}
    		return ResponseUtil.ok();
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
    	
    }
    
    /**
     * 根据用户id获取商家信息
     * @param request
     * @return
     */
    @PostMapping("/getShopByUserId")
    public Object getShopByUserId(HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	int userId=Integer.parseInt(request.getParameter("userId")) ;
    	if(flag){
    		shop shop = this.shopService.getShopByUserId(userId);
    		return shop;
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
    	
    }
    
    /**
     * 根据商家id获取商家信息
     * @param request
     * @return
     */
    @PostMapping("/getCount")
    public Object getCount(HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	int userId=Integer.parseInt(request.getParameter("userId")) ;
    	if(flag){
    		shop shop = this.shopService.getShopByUserId(userId);
    		Object obj = this.shopService.getCount(shop.getId());
    		return obj;
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
    	
    }
    
    /**
     * 根据年份获取每月营业额
     * @param request
     * @return
     */
    @PostMapping("/getShopMoneyByYear")
    public Object  getShopMoneyByYear(HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	int userId=Integer.parseInt(request.getParameter("userId")) ;
    	int year=Integer.parseInt(request.getParameter("year")) ;
    	if(flag){
    		shop shop = this.shopService.getShopByUserId(userId);
    		exchart exchart = this.shopService.getShopMoneyByYear(year, shop.getId());
    		return exchart;
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
    	
    }
    
    /**
     * 修改起送费
     * @param request
     * @return
     */
    @PostMapping("/updateLowPrice")
    public Object  updateLowPrice(HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	int userId=Integer.parseInt(request.getParameter("userId")) ;
    	int lowPrice=Integer.parseInt(request.getParameter("lowPrice")) ;
    	if(flag){
    		boolean updateFlag = shopService.updateLowPrice(lowPrice, userId);
    		if(updateFlag){
    			return ResponseUtil.ok();
    		}
    		else{
    			return  ResponseUtil.fail();
    		}
    		
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
    	
    }
    
    /**
     * 修改配送费
     * @param request
     * @return
     */
    @PostMapping("/updateFreight")
    public Object updateFreight(HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	int userId=Integer.parseInt(request.getParameter("userId")) ;
    	int freight=Integer.parseInt(request.getParameter("freight")) ;
    	if(flag){
    		boolean updateFlag = shopService.updateFreight(freight, userId);
    		if(updateFlag){
    			return ResponseUtil.ok();
    		}
    		else{
    			return  ResponseUtil.fail();
    		}
    		
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
    	
    }
    
    /**
     * 商家开店或打烊
     * @param request
     * @return
     */
    @PostMapping("/openShop")
    public Object openShop(HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	int userId=Integer.parseInt(request.getParameter("userId")) ;
    	int status=Integer.parseInt(request.getParameter("status")) ;
    	if(flag){
    		boolean updateFlag = shopService.openShop(status, userId);
    		if(updateFlag){
    			return ResponseUtil.ok();
    		}
    		else{
    			return  ResponseUtil.fail();
    		}
    		
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
    	
    }
    
}
