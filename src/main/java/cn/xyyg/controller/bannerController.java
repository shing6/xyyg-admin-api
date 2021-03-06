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

import com.github.pagehelper.PageHelper;

import cn.xyyg.pojo.address;
import cn.xyyg.pojo.banner;
import cn.xyyg.pojo.wechatUser;
import cn.xyyg.service.bannerService;
import cn.xyyg.util.JwtUtil;
import cn.xyyg.util.ResponseUtil;

@RestController
@RequestMapping("/banner")
public class bannerController {
	@Autowired
    private bannerService  bannerService;
	/**
     * 获取所有轮播图
     * @param request
     * @return
     */
    @PostMapping("/getBanner")
    public Object getBanner(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
	     if(flag){
	       int pageNum=Integer.parseInt(request.getParameter("pageNum"));
	       int pageSize=Integer.parseInt(request.getParameter("pageSize"));
	       int count = bannerService.getBannerCount();
	       response.setIntHeader("X-Total-Count",count);
	       //使用分页插件,核心代码就这一行
           PageHelper.startPage(pageNum, pageSize);
           List<banner> bannerList = bannerService.getBanner(pageNum, pageSize);
           return bannerList;
	     }
	     else{
	    	 return ResponseUtil.unlogin();
	     }
		
    		
    	
    }
    
    /**
     * 添加轮播图
     * @param request
     * @return
     */
    @PostMapping("/insertBanner")
    public Object insertBanner(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
	     if(flag){
	       String toUrl=request.getParameter("toUrl");
	       String picAddr=request.getParameter("picAddr");
	       banner banner = new banner();
	       banner.setToUrl(toUrl);
	       banner.setPicAddr(picAddr);
           boolean iFlag = bannerService.insertBanner(banner);
           if(iFlag){
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
     * 获取所有轮播图
     * @param request
     * @return
     */
    @GetMapping("/getBannerForwx")
    public Object getBannerForwx(HttpServletRequest request,HttpServletResponse response){
           List<banner> bannerList = bannerService.getBannerForwx();
           return bannerList;
	 }
    
    /**
     * 根据id数组删除轮播图
     * @param request
     * @return
     */
    @PostMapping("/deleteBanner")
    public Object deleteBanner(HttpServletRequest request){
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
                boolean deleteflag = bannerService.deleteBanner(ids);
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
    
    /**
     * 修改轮播图
     * @param request
     * @return
     */
    @PostMapping("/updateBanner")
    public Object updateBanner(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
	     if(flag){
	       String toUrl=request.getParameter("toUrl");
	       String picAddr=request.getParameter("picAddr");
	       int id=Integer.parseInt(request.getParameter("id"));
	       banner banner = new banner();
	       banner.setId(id);
	       banner.setToUrl(toUrl);
	       banner.setPicAddr(picAddr);
           boolean iFlag = bannerService.updateBanner(banner);
           if(iFlag){
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
     * 根据id获取轮播图
     * @param request
     * @return
     */
    @PostMapping("/getBannerById")
    public Object getBannerById(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
	     if(flag){
	       int id=Integer.parseInt(request.getParameter("id"));
           banner  banner = bannerService.getBannerById(id);
           return banner;
	     }
	     else{
	    	 return ResponseUtil.unlogin();
	     }
		
    		
    	
    }
}
