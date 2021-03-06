package cn.xyyg.service;

import java.util.List;

import cn.xyyg.pojo.rechange;
import cn.xyyg.pojo.wallet;

public interface walletService {
	/**
	 * 根据用户ID查询用户余额
	 * @param wechatUserId
	 * @return
	 */
	public wallet getWechatMoney(int wechatUserId);
	
	/**
	 * 支付后余额减掉付款额(首次支付)
	 * @param wechatUserId
	 * @param pwd
	 * @param orderNoList
	 * @return
	 */
	public Object updateWallet(int wechatUserId,String pwd,List<String> orderNoList);
	
	/**
	 * 支付后余额减掉付款额(详情页支付)
	 * @param wechatUserId
	 * @param pwd
	 * @param orderNo
	 * @return
	 */
	public Object SecondPay(int wechatUserId,String pwd,String orderNo);
	
	/**
	 * 修改用户支付密码
	 * @return
	 */
	public Object updateWechatWalletPassword(wallet wallet,String oldPassword);
	
	/**
	 * 添加卡号
	 * @param rechange
	 * @return
	 */
	public boolean insertRechange(rechange rechange);
	
	/**
	 * 充值
	 * @param wallet
	 * @return
	 */
	public Object rechange(int wechatUserId,String no);
	
	
	

}
