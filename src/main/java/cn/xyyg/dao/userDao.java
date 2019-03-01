package cn.xyyg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.xyyg.pojo.user;
import cn.xyyg.pojo.userLogin;
import cn.xyyg.pojo.userWithShop;
import cn.xyyg.pojo.wechatUser;
@Mapper
public interface userDao {
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
     * 后台登录
     * @param username
     * @param password
     * @return
     */
    public user findUser(@Param("username") String username,@Param("password") String password);
    
    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    public user getUserById(Integer id);
    
    /**
     * 添加用户信息
     * @param user
     * @return
     */
    public int insertUser(user user);
    
    /**
     * 查找用户名是否存在
     * @param username
     * @return
     */
    public user findUserName(String username);
    
    /**
     * 查询商家信息用于审核
     * @return
     */
    public List<userWithShop> getUserWithShop();
    
    
    /**
     * 审核通过后可登陆
     * @param id
     * @return
     */
    public int updateUserRole(Integer id);
    
    
}
