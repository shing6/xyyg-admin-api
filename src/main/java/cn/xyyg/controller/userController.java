package cn.xyyg.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.xyyg.pojo.userWithShop;
import cn.xyyg.pojo.wechatUser;
import cn.xyyg.util.HttpClientUtil;
import cn.xyyg.util.JwtUtil;
import cn.xyyg.util.ResponseUtil;
import cn.xyyg.util.UserConstantInterface;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import cn.xyyg.service.userService;
import cn.xyyg.service.shopService;
@RestController
@RequestMapping("/user")
public class userController {
	   @Autowired
	    private userService userService;
	   @Autowired
	    private shopService shopService;
	   

        /**
         * 微信用户登录
         * @param code
         * @param avatarUrl
         * @param nickName
         * @return
         */
	    @PostMapping("/wxLogin")
	    public Object user_login(
	            @RequestParam("code") String code,
	            @RequestParam("avatarUrl") String avatarUrl,
	            @RequestParam("nickName") String nickName
	           
	    ){
	        // 配置请求参数
	        Map<String, String> param = new HashMap<>();
	        param.put("appid", UserConstantInterface.WX_LOGIN_APPID);
	        param.put("secret", UserConstantInterface.WX_LOGIN_SECRET);
	        param.put("js_code", code);
	        param.put("grant_type", UserConstantInterface.WX_LOGIN_GRANT_TYPE);
	        // 发送请求
	        String wxResult = HttpClientUtil.doGet(UserConstantInterface.WX_LOGIN_URL, param);
	        
	        JSONObject jsonObject = JSONObject.fromObject(wxResult);
	        
	        // 获取参数返回的
	        String session_key = jsonObject.get("session_key").toString();
	        String openId = jsonObject.get("openid").toString();
	        // 根据返回的user实体类，判断用户是否是新用户，不是的话，更新最新登录时间，是的话，将用户信息存到数据库
	        wechatUser wechatUser = userService.getUserByOpenId(openId);
	        if(wechatUser != null){
	        	
	        	wechatUser.setNickName(nickName);
	        	wechatUser.setAvatarUrl(avatarUrl);
	            userService.updateByOpenId(wechatUser);
	        }else{
	        	wechatUser insert_wechatUser = new wechatUser();
	        	insert_wechatUser.setAvatarUrl(avatarUrl);
	        	insert_wechatUser.setNickName(nickName);
	        	insert_wechatUser.setOpenId(openId);
	            // 添加到数据库
	            Boolean flag = userService.insertWechatUser(insert_wechatUser);
	            if(!flag){
	                return ResponseUtil.fail();
	            }
	        }
	        // 封装返回小程序
	        Map<String, String> result = new HashMap<>();
	        result.put("session_key", session_key);
	        result.put("open_id", openId);
	        return result;

	    }
	    
	    /**
	     * 后台用户登录
	     * @param request
	     * @return
	     */
	    @PostMapping("/adminLogin")
	    public Object admin_login(HttpServletRequest request){
	    String username=request.getParameter("username");
	    String password=request.getParameter("password");
	    Object obj= userService.findUser(username, password);
	    return obj;

	    }
	    
	    /**
	     * 查询商家信息用于审核
	     * @param request
	     * @return
	     */
	    @PostMapping("/getUserWithShop")
	    public Object getUserWithShop(HttpServletRequest request,HttpServletResponse response){
	    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
	    	if(flag){
	    		int count = shopService.getAllShopCount();
	    		response.setIntHeader("X-Total-Count",count);
	    		List<userWithShop>  userWithShopList = userService.getUserWithShop();
	    	    return userWithShopList;
	    	}
	    	else{
	    		return ResponseUtil.unlogin();
	    	}
	      

	    }
	

}
