package cn.xyyg.controller;

import java.math.BigDecimal;
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

import com.github.pagehelper.PageHelper;

import cn.xyyg.pojo.comment;
import cn.xyyg.pojo.goods;
import cn.xyyg.pojo.goodsDesc;
import cn.xyyg.pojo.goodsDescPic;
import cn.xyyg.pojo.goodsPicture;
import cn.xyyg.pojo.goodsWithCounts;
import cn.xyyg.pojo.order;
import cn.xyyg.pojo.page;
import cn.xyyg.pojo.shop;
import cn.xyyg.pojo.user;
import cn.xyyg.pojo.wechatUser;
import cn.xyyg.service.goodsService;
import cn.xyyg.service.shopService;
import cn.xyyg.service.userService;
import cn.xyyg.util.JwtUtil;
import cn.xyyg.util.ResponseUtil;
import cn.xyyg.util.createTimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@RestController
@RequestMapping("/goods")
public class goodsController {
	@Autowired
    private goodsService goodsService;
	
	@Autowired
    private userService userService;
	
	@Autowired
    private shopService shopService;
	/**
	 * 一对多获取商品
	 * @return
	 */
	@GetMapping("/getAllGoodsDescPic")
    public List<goodsDescPic> getAllGoodsDescPic(){
        List<goodsDescPic> goodsDescPicList=goodsService.getAllGoodsDescPic();
		return goodsDescPicList;
    	
    }
    /**
     * 根据商品分类id查询商品
     * @param categoryId
     * @return
     */
    @GetMapping("/getGoodsByCategoryId")
    public List<goodsWithCounts> getGoodsByCategoryId(int categoryId){
        List<goodsWithCounts> goodsList=goodsService.getGoodsByCategoryId(categoryId);
		return goodsList;
    	
    }
    
    /**
	 * 根据商品id查询商品详细信息
	 * @param id
	 * @return
	 */
    @GetMapping("/getGoodsById")
	public goodsDescPic getGoodsById(int id){
		goodsDescPic goodsDescPic=goodsService.getGoodsById(id);
		return goodsDescPic;
	}
    
    /**
     * 根据id数组查询商铺信息
     * @param request
     * @return
     */
    @PostMapping("/getGoodsByIds")
    public List<goods> getGoodsByIds(HttpServletRequest request){
    	
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
            List<goods> goodsList =goodsService.getGoodsByIds(ids);
            
            
     		return goodsList;
    	
    	
  	}
    /**
     * 根据商品名字模糊查询商品信息
     * @param name
     * @return
     */
    @GetMapping("/getGoodsByName")
    public List<goods> getGoodsByName(HttpServletRequest request){
    	String name = request.getParameter("name");
    	List<goods> goodsList =goodsService.getGoodsByName(name);
		return goodsList ;
    	
    }
    
    /**
     * 根据商品名字模糊查询商品信息并根据传递过来的condition进行升降排序
     * @param name
     * @param condition
     * @param sort
     * @return
     */
    @PostMapping("/getGoodsByNameOrderBySort")
    public List<goodsWithCounts> getGoodsByNameOrderBySort(HttpServletResponse response,HttpServletRequest request){
    	String name = request.getParameter("name");
    	String condition = request.getParameter("condition");
    	String sort = request.getParameter("sort");
    	int pageNum=Integer.parseInt(request.getParameter("pageNum"));
    	int pageSize=Integer.parseInt(request.getParameter("pageSize"));
    	//使用分页插件,核心代码就这一行
        PageHelper.startPage(pageNum, pageSize);
    	List<goodsWithCounts> goodsList =goodsService.getGoodsByNameOrderBySort(name, condition, sort,pageNum,pageSize);
    	int count=goodsService.getGoodsByNameOrderBySortCount(name, condition, sort);
    	response.setIntHeader("X-Total-Count",count);
		return goodsList ;
    	
    }
    /**
     * 根据商家id分页查询商品信息信息
     * @param request
     * @return
     */
    @PostMapping("/getGoodsBySellerId")
    public Object getGoodsBySellerId(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		int userId=Integer.parseInt(request.getParameter("userId"));
    		int pageNum=Integer.parseInt(request.getParameter("pageNum"));
        	int pageSize=Integer.parseInt(request.getParameter("pageSize"));
    		user user=userService.getUserById(userId);
            shop shop=shopService.getShopByUserId(user.getId());
        	int count =goodsService.getGoodsCountBySellerId(shop.getId());
        	response.setIntHeader("X-Total-Count",count);
        	List<goods> goodsList=goodsService.getGoodsBySellerId(pageNum, pageSize, shop.getId());
        	return goodsList;
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
    }
    
    /**
     * 添加商品
     * @param request
     * @return
     */
    @PostMapping("/insertGoods")
    public Object insertGoods(HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		String picAddr = request.getParameter("goodsPicture");
    		String attributes=request.getParameter("attributes");
    		String goods=request.getParameter("goods");
    		int userId=Integer.parseInt(request.getParameter("userId"));
    		String[] stuList = picAddr.split(",");
    		List<String> picAddrList = new ArrayList<String>();
    		if(!picAddr.isEmpty()){
    			System.out.println("picAddr1"+picAddr);
    		}
    		
    			for(String str : stuList){
    	           	 
        			picAddrList.add(new String(str));
                }
    		
    		
    		JSONObject goodsJson = JSONObject.fromObject(goods);
    		JSONArray attributesJson = JSONArray.fromObject(attributes);
    		List<goodsDesc> goodsDescList =JSONArray.toList(attributesJson, new goodsDesc(), new JsonConfig());
    		
    		    
                user user=userService.getUserById(userId);
                shop shop=shopService.getShopByUserId(user.getId());
                goods goodsPojo =new goods();
                int price=goodsJson.getInt("price");
                int oldPrice=goodsJson.getInt("oldPrice");
                goodsPojo.setGoodsName(goodsJson.getString("goodsName"));
                goodsPojo.setOldPrice(new BigDecimal(oldPrice));
                goodsPojo.setPrice(new BigDecimal(price));
                goodsPojo.setMainPic(goodsJson.getString("picUrl"));
                goodsPojo.setCreateTime(createTimeUtil.getTime());
                goodsPojo.setStock(goodsJson.getInt("stock"));
                goodsPojo.setStatus(goodsJson.getInt("status"));
                goodsPojo.setCategoryId(goodsJson.getInt("categoryName"));
                goodsPojo.setSellerId(shop.getId());
                boolean insertflag=this.goodsService.insertGoods(goodsPojo, picAddrList, goodsDescList);
                if(insertflag){
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
     * 修改商品
     * @param request
     * @return
     */
    @PostMapping("/updateGoods")
    public Object updateGoods(HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		String picAddr = request.getParameter("goodsPicture");
    		String attributes=request.getParameter("attributes");
    		String goods=request.getParameter("goods");
    		int userId=Integer.parseInt(request.getParameter("userId"));
    		JSONObject goodsJson = JSONObject.fromObject(goods);
    		JSONArray attributesJson = JSONArray.fromObject(attributes);
    		JSONArray picAddrJson = JSONArray.fromObject(picAddr);
    		List<goodsDesc> goodsDescList =JSONArray.toList(attributesJson, new goodsDesc(), new JsonConfig());
    		List<goodsPicture> goodsPictureList =JSONArray.toList(picAddrJson, new goodsPicture(), new JsonConfig());
    		
            user user=userService.getUserById(userId);
           
            	shop shop=shopService.getShopByUserId(user.getId());
                goods goodsPojo = new goods();
                int price=goodsJson.getInt("price");
                int oldPrice=goodsJson.getInt("oldPrice");
                goodsPojo.setId(goodsJson.getInt("id"));
                goodsPojo.setGoodsName(goodsJson.getString("goodsName"));
                goodsPojo.setOldPrice(new BigDecimal(oldPrice));
                goodsPojo.setPrice(new BigDecimal(price));
                goodsPojo.setMainPic(goodsJson.getString("mainPic"));
                goodsPojo.setUpdateTime(createTimeUtil.getTime());
                goodsPojo.setStock(goodsJson.getInt("stock"));
                goodsPojo.setStatus(goodsJson.getInt("status"));
                goodsPojo.setCategoryId(goodsJson.getInt("categoryName"));
                boolean updateflag=this.goodsService.updateGoods(goodsPojo, goodsPictureList, goodsDescList);
                if(updateflag){
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
     * 上下架商品商品
     * @param request
     * @return
     */
    @PostMapping("/updateGoodsStatus")
    public Object updateGoodsStatus(HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		
    		String goods=request.getParameter("goods");
    		JSONObject goodsJson = JSONObject.fromObject(goods);
    		  
                goods goodsPojo = new goods();
                
                goodsPojo.setId(goodsJson.getInt("id"));
                goodsPojo.setStatus(goodsJson.getInt("status"));
              
                boolean updateflag=this.goodsService.updateGoodsStatus(goodsPojo);
                if(updateflag){
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
     * 根据商家id和name分页模糊查询商品
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/selectGoodsByName")
    public Object selectGoodsByName(HttpServletResponse response,HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	
    	if (flag){
    		int userId=Integer.parseInt(request.getParameter("userId"));
    		int pageNum=Integer.parseInt(request.getParameter("pageNum"));
        	int pageSize=Integer.parseInt(request.getParameter("pageSize"));
        	String name = request.getParameter("goodsName");
        	
                user user=userService.getUserById(userId);
                shop shop=shopService.getShopByUserId(user.getId());
                goods goods =new goods();
                goods.setSellerId(shop.getId());
                goods.setGoodsName(name);
                int count =goodsService.selectGoodsCountByName(goods);
            	response.setIntHeader("X-Total-Count",count);
            	page page =new page();
            	page.setName(name);
            	page.setSellerId(shop.getId());
            	page.setPageNum(pageNum);
            	page.setPageSize(pageSize);
             	List<goodsDescPic> goodsList =goodsService.selectGoodsByName(page);
            	return goodsList;
            
         }
    	
    	else{
    		return ResponseUtil.unlogin();
    	}
        
		
    	
    }
    
    /**
     * 根据id数组删除商品
     * @param request
     * @return
     */
    @PostMapping("/deleteGoodsByIds")
    public Object deleteGoodsByIds(HttpServletRequest request){
    	     boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	     if(flag){
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
                boolean deleteflag = goodsService.deleteGoodsByIds(ids);
                if(deleteflag){
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
