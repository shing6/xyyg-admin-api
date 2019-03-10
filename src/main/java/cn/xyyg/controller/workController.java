package cn.xyyg.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;

import cn.xyyg.pojo.banner;
import cn.xyyg.pojo.work;
import cn.xyyg.service. workService;
import cn.xyyg.util.JwtUtil;
import cn.xyyg.util.ResponseUtil;

@RestController
@RequestMapping("/work")
public class workController {
	@Autowired
    private workService  workService;
	
	/**
     * 获取所有轮播图
     * @param request
     * @return
     */
    @PostMapping("/getWork")
    public Object getWork(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
	     if(flag){
	       int pageNum=Integer.parseInt(request.getParameter("pageNum"));
	       int pageSize=Integer.parseInt(request.getParameter("pageSize"));
	       int count = workService.getWorkCount();
	       response.setIntHeader("X-Total-Count",count);
	       //使用分页插件,核心代码就这一行
           PageHelper.startPage(pageNum, pageSize);
           List<work> workList = workService.getWork(pageNum, pageSize);
           return workList;
	     }
	     else{
	    	 return ResponseUtil.unlogin();
	     }
		
    		
    	
    }
}
