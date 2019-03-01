package cn.xyyg.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;

import cn.xyyg.pojo.adminWallet;
import cn.xyyg.pojo.wallet;
@Mapper
public interface walletDao {
	/**
	 * 添加钱包信息
	 * @param wallet
	 * @return
	 */
	public int insertWechatWallet(wallet wallet);
	
	/**
	 * 根据用户ID查询用户余额
	 * @param wechatUserId
	 * @return
	 */
	public wallet getWechatMoney(int wechatUserId);
	
	/**
	 * 支付后余额减掉付款额
	 * @param wechatUserId
	 * @return
	 */
	public int updateWallet(wallet wallet);
	
	/**
	 * 总额支付到运营商
	 * @param adminWallet
	 * @return
	 */
	public int updateAdminWallet(BigDecimal money);
	
	/**
	 * 总额支付到商家
	 * @param adminWallet
	 * @return
	 */
	public int updateAdminWalletToSeller(adminWallet adminWallet);
	
	/**
	 * 运营商支付给商家后减掉余额
	 * @param money
	 * @return
	 */
	public int deleteAdminWallet(BigDecimal money);
	
	/**
	 * 根据用户id获取用户支付密码
	 * @param wechatUserId
	 * @return
	 */
	public wallet getWechatPassword(int wechatUserId);
	
	/**
	 * 添加用户支付密码
	 * @return
	 */
	public int insertWechatWalletPassword(wallet wallet);
}
