package cn.xyyg.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.xyyg.dao.userDao;
import cn.xyyg.dao.shopDao;
import cn.xyyg.dao.walletDao;
import cn.xyyg.pojo.shop;
import cn.xyyg.pojo.user;
import cn.xyyg.pojo.userLogin;
import cn.xyyg.pojo.userWithShop;
import cn.xyyg.pojo.wallet;
import cn.xyyg.pojo.wechatUser;
import cn.xyyg.service.userService;
import cn.xyyg.util.JwtUtil;
import cn.xyyg.util.MD5;
import cn.xyyg.util.ResponseUtil;

@Service
@Transactional
public class userServiceImpl implements userService {
	@Autowired
	private userDao userDao;
	
	@Autowired
	private shopDao shopDao;
	
	@Autowired
	private walletDao walletDao;
	/**
	 * 通过openId查询用户信息
	 */
	@Override
	public wechatUser getUserByOpenId(String openId) {
		
		return this.userDao.getUserByOpenId(openId);
	}
	/**
	 * 通过openId更改用户信息
	 */
	@Override
	public void updateByOpenId(wechatUser wechatUser) {
		this.userDao.updateByOpenId(wechatUser);
		
	}
	/**
	 * 添加微信用户信息
	 */
	@Override
	public boolean insertWechatUser(wechatUser wechatUser) {
		wechatUser wcUser =userDao.getUserByOpenId(wechatUser.getOpenId());
		if(wcUser==null){
			String password = null;
			userDao.insertWechatUser(wechatUser);
			wechatUser newwcUser =userDao.getUserByOpenId(wechatUser.getOpenId());
			wallet wallet =new wallet();
			wallet.setMoney(new BigDecimal(0));//初始化钱包
			try {
				 password = MD5.EncoderByMd5("123456");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wallet.setPassword(password);//初始化密码为123456
			wallet.setWechatUserId(newwcUser.getId());
			walletDao.insertWechatWallet(wallet);
			return true;
			
		}
		else  return false;
	}
	
	/**
	 * 后台用户登录
	 */
	@Override
	public Object findUser(String username, String password) {
		user user=this.userDao.findUserName(username);
		boolean flag=false;
		if(user!=null){ 
			try {
				 flag=MD5.checkpassword(password, user.getPassword());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(flag){
				shop shop=this.shopDao.getShopByUserId(user.getId());
				Map<String, Object> obj = new HashMap<String, Object>();
				    obj.put("token", JwtUtil.sign(user.getId()));
					obj.put("userId", user.getId());
				    obj.put("role", user.getRole());
				    obj.put("login", 0);
				if(shop!=null){
					obj.put("user", shop.getName());
					obj.put("user_image", shop.getLogoPic());
					
				}
				
				return obj;
				
			}
			else{
				return ResponseUtil.loginFailed();
			}
			
			
		}
		else{
			return ResponseUtil.NotUserName();
		}
		
	}
	
	/**
	 * 根据用户id查询后台用户信息
	 */
	@Override
	public user getUserById(Integer id) {
		
		return this.userDao.getUserById(id);
	}
	
	/**
	 * 查询商家信息用于审核
	 */
	@Override
	public List<userWithShop> getUserWithShop(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.userDao.getUserWithShop();
	}
	
	/**
	 * 根据微信用户id获取微信用户信息
	 */
	@Override
	public wechatUser getWechatUserById(Integer id) {
		
		return this.userDao.getWechatUserById(id);
	}
	
	/**
	 * 审核通过后可登陆
	 */
	@Override
	public boolean updateUserRole(Integer id) {
		 int rows = this.userDao.updateUserRole(id);
		 if(rows>0){
			 return true;
		 }
		 else{
			 return false; 
		 }
		
	}
	
	/**
	 * 通过商家id获取用户信息
	 */
	@Override
	public String getPhoneBySellerId(Integer id) {
		
		return this.userDao.getPhoneBySellerId(id);
	}
	
	

}
