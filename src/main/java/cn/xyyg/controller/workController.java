package cn.xyyg.controller;

import java.sql.Timestamp;
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
import cn.xyyg.util.createTimeUtil;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/work")
public class workController {
	@Autowired
    private workService  workService;
	
	/**
     * 分页获取所有兼职
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
	       workPojo.setCounts(workJson.getInt("counts"));
	       workPojo.setStartTime((Timestamp) workJson.get("startTime"));
	       workPojo.setEndTime((Timestamp) workJson.get("endTime"));
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
}
