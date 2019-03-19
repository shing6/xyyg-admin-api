package cn.xyyg.service;

import java.util.List;

import cn.xyyg.pojo.user;
import cn.xyyg.pojo.userLogin;
import cn.xyyg.pojo.userWithShop;
import cn.xyyg.pojo.wechatUser;

public interface userService {
	/**
	 * 通过openId查询用户信息
	 * @param Openid
	 * @return
	 */
    public wechatUser getUserByOpenId(String openId);
    
    /**
     * 根据微信用户id获取用户信息
     * @param id
     * @return
     */
    public wechatUser getWechatUserById(Integer id);
    
    /**
     * 通过openId更新用户信息
     * @param openId
     */
    public void updateByOpenId(wechatUser wechatUser);
    
    
    
    /**
     * 添加微信用户信息
     * @param wechatUser
     * @return
     */
    public boolean insertWechatUser(wechatUser wechatUser);
    
    
    /**
     * 后台用户登录
     * @param username
     * @param password
     * @return
     */
    public Object findUser(String username,String password);
    
    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    public user getUserById(Integer id);
    
    /**
     * 查询商家信息用于审核
     * @return
     */
    public List<userWithShop> getUserWithShop(int pageNum, int pageSize);
    
    /**
     * 审核通过后可登陆
     * @param id
     * @return
     */
    public boolean updateUserRole(Integer id);
    
    /**
     * 通过商家id获取用户信息
     * @param id
     * @return
     */
    public String getPhoneBySellerId(Integer id);

}
