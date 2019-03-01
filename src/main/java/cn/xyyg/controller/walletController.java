package cn.xyyg.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xyyg.pojo.category;
import cn.xyyg.pojo.wallet;
import cn.xyyg.pojo.wechatUser;
import cn.xyyg.service.userService;
import cn.xyyg.service.walletService;
import cn.xyyg.util.MD5;
import cn.xyyg.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@RestController
@RequestMapping("/wallet")
public class walletController {
	@Autowired
	private walletService walletService;
	
	@Autowired
    private userService userService;
	
	/**
	 * 获取钱包余额
	 * @param request
	 * @return
	 */
	@PostMapping("/getWechatMoney")
    public Object getWechatMoney(HttpServletRequest request){
    	String openId =request.getParameter("open_id");
    	//查看用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser != null){
        	wallet wallet = walletService.getWechatMoney(wechatUser.getId());
        	return wallet;
        }
        
        else{
        	return ResponseUtil.unlogin();
        }
		
    	
    }
	
	
	/**
	 * 添加密码
	 * @param request
	 * @return
	 */
	@PostMapping("/insertPassword")
    public Object insertWechatWalletPassword(HttpServletRequest request){
    	String openId =request.getParameter("open_id");
    	String password =request.getParameter("pwdVal");
    	String pwdByMd5 =null;
    	try {
			pwdByMd5= MD5.EncoderByMd5(password);//md5加密密码
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
    	//查看用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser != null){
        	wallet newWallet =new wallet();
        	newWallet.setWechatUserId(wechatUser.getId());
        	newWallet.setPassword(pwdByMd5);
        	Object obj = walletService.insertWechatWalletPassword(newWallet);
        	return obj;
        }
        
        else{
        	return ResponseUtil.unlogin();
        }
		
    	
    }
	
	@PostMapping("/pay")
    public Object pay(HttpServletRequest request){
    	String openId =request.getParameter("open_id");
    	String pwd=request.getParameter("pwdVal");
    	String orderNo=request.getParameter("orderNoList");
    	JSONArray shopIdListJson = JSONArray.fromObject(orderNo);
    	List<String> orderNoList = JSONArray.toList(shopIdListJson, new String(), new JsonConfig());
    	//查看用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser != null){
        	Object obj = walletService.updateWallet(wechatUser.getId(), pwd, orderNoList);
        	return obj;
        }
        
        else{
        	return ResponseUtil.unlogin();
        }
		
    	
    }
	
	/**
	 * 二次支付
	 * @param request
	 * @return
	 */
	@PostMapping("/secondPay")
    public Object secondPay(HttpServletRequest request){
    	String openId =request.getParameter("open_id");
    	String pwd=request.getParameter("pwdVal");
    	String orderNo=request.getParameter("orderNo");
    	//查看用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser != null){
        	Object obj = walletService.SecondPay(wechatUser.getId(), pwd, orderNo);
        	return obj;
        }
        
        else{
        	return ResponseUtil.unlogin();
        }
		
    	
    }
	
	
	
	

}
