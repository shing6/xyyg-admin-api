package cn.xyyg.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
    
}