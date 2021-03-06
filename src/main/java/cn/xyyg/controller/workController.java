package cn.xyyg.controller;

import java.sql.Timestamp;
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

import cn.xyyg.pojo.banner;
import cn.xyyg.pojo.work;
import cn.xyyg.service. workService;
import cn.xyyg.util.JwtUtil;
import cn.xyyg.util.ResponseUtil;
import cn.xyyg.util.createTimeUtil;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/work")
public class workController {
	@Autowired
    private workService  workService;
	
	/**
     * 后台分页获取所有兼职
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
    
    /**
     * 发布兼职
     * @param request
     * @return
     */
    @PostMapping("/insertWork")
    public Object insertWork(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
	     if(flag){
	       String work=request.getParameter("work");
	       int userId=Integer.parseInt(request.getParameter("userId"));
	       JSONObject workJson = JSONObject.fromObject(work);
	       work workPojo = new work();
	       workPojo.setTitle(workJson.getString("title"));
	       workPojo.setDetail(workJson.getString("detail"));
	       workPojo.setCreateTime(createTimeUtil.getTime());
	       workPojo.setSalary(workJson.getString("salary"));
	       workPojo.setType(workJson.getInt("type"));
	       workPojo.setCounts(workJson.getInt("counts"));
	       workPojo.setStartTime(workJson.getString("startTime"));
	       workPojo.setEndTime(workJson.getString("endTime"));
	       workPojo.setAddress(workJson.getString("address"));
	       workPojo.setUserId(userId);;
           boolean iFlag = workService.insertWork(workPojo);
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
     * 小程序端分页获取所有兼职
     * @param request
     * @return
     */
    @PostMapping("/getWorkForWx")
    public Object getWorkForWx(HttpServletRequest request,HttpServletResponse response){
    	
	       int pageNum=Integer.parseInt(request.getParameter("pageNum"));
	       int pageSize=Integer.parseInt(request.getParameter("pageSize"));
	       int count = workService.getWorkCount();
	       response.setIntHeader("X-Total-Count",count);
	       //使用分页插件,核心代码就这一行
           PageHelper.startPage(pageNum, pageSize);
           List<work> workList = workService.getWork(pageNum, pageSize);
           return workList;
	 }
    
    /**
     * 小程序端分页获取兼职详情
     * @param request
     * @return
     */
    @PostMapping("/getWorkById")
    public Object getWorkById(HttpServletRequest request,HttpServletResponse response){
    	
    	   int id=Integer.parseInt(request.getParameter("id"));
           work work=workService.getWorkById(id);
           return work;//12
	 }
    
    /**
     * 根据id数组删除兼职
     * @param request
     * @return
     */
    @PostMapping("/deleteWork")
    public Object deleteWork(HttpServletRequest request){
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
                boolean deleteflag = workService.deleteWork(ids);
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
     * 修改兼职
     * @param request
     * @return
     */
    @PostMapping("/updateWork")
    public Object updateWork(HttpServletRequest request,HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
	     if(flag){
	       String work=request.getParameter("work");
	       JSONObject workJson = JSONObject.fromObject(work);
	       work workPojo = new work();
	       workPojo.setId(workJson.getInt("id"));
	       workPojo.setTitle(workJson.getString("title"));
	       workPojo.setDetail(workJson.getString("detail"));
	       workPojo.setSalary(workJson.getString("salary"));
	       workPojo.setType(workJson.getInt("type"));
	       workPojo.setCounts(workJson.getInt("counts"));
	       workPojo.setStartTime(workJson.getString("startTime"));
	       workPojo.setEndTime(workJson.getString("endTime"));
	       workPojo.setAddress(workJson.getString("address"));
           boolean iFlag = workService.updateWork(workPojo);
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
}
