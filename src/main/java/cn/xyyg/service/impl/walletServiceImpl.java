package cn.xyyg.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xyyg.dao.walletDao;
import cn.xyyg.dao.orderDao;
import cn.xyyg.pojo.order;
import cn.xyyg.pojo.rechange;
import cn.xyyg.pojo.wallet;
import cn.xyyg.service.walletService;
import cn.xyyg.util.MD5;
import cn.xyyg.util.ResponseUtil;
@Service
@Transactional
public class walletServiceImpl implements walletService {
	@Autowired
	private walletDao walletDao;
	
	@Autowired
	private orderDao orderDao;
	/**
	 * 查询用户余额
	 */
	@Override
	public wallet getWechatMoney(int wechatUserId) {
		
		return this.walletDao.getWechatMoney(wechatUserId);
	}
	
	/**
	 * 支付订单
	 */
	@Override
	@Transactional(rollbackFor = {IllegalArgumentException.class})
	public Object updateWallet(int wechatUserId,String pwd,List<String>  orderNoList) {
		 boolean flag = false;//判断密码是否正确标志
		
		 BigDecimal sumPrice = new BigDecimal(0);//总需付款金额
		 //获取用户密码
		wallet wallet= this.walletDao.getWechatPassword(wechatUserId);
		String password=wallet.getPassword();
		
		try {
			flag = MD5.checkpassword(pwd, password);//比较支付密码
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		if(flag){
			wallet walletMoney = this.walletDao.getWechatMoney(wechatUserId);
			BigDecimal money= walletMoney.getMoney();
			for(int i=0;i<orderNoList.size();i++){
				BigDecimal needPrice = this.orderDao.getOrderNeedPrice(orderNoList.get(i));
				sumPrice =sumPrice.add(needPrice);
			}
			if(money.compareTo(sumPrice)==-1){
				
				throw new IllegalArgumentException("余额不足");
			}
			
			else{
                 wallet payWallet =new wallet();
				 payWallet.setMoney(sumPrice);
				 payWallet.setWechatUserId(wechatUserId);
				 int rows= this.walletDao.updateWallet(payWallet);//余额减去支付总额
				 if(rows>0){
					 
					 this.walletDao.updateAdminWallet(sumPrice);
					 for(int j=0 ;j<orderNoList.size();j++){
						 //支付成功更改订单状态
						 this.orderDao.updateOrderStausAfterPay(orderNoList.get(j));
					}
				}
			}
			return ResponseUtil.ok();
		}
		else{
			return ResponseUtil.payFailed();
		}
		
		
	}
	
	

	/**
	 * 修改用户支付密码
	 */
	@Override
	@Transactional(rollbackFor = {IllegalArgumentException.class})
	public Object updateWechatWalletPassword(wallet wallet,String oldPassword) {
		boolean flag = false;//判断密码是否正确标志
		 //获取用户密码
		wallet newwallet= this.walletDao.getWechatPassword(wallet.getWechatUserId());
		String password=newwallet.getPassword();
		try {
			flag = MD5.checkpassword(oldPassword, password);//比较支付密码
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		if(flag){
			int rows =this.walletDao.updateWechatWalletPassword(wallet);
			 if(rows > 0){
				 return ResponseUtil.ok();
			 }
			return ResponseUtil.fail();
		}
		else{
			return ResponseUtil.unOldPwd();
		}
		
	}
    /**
     * 二次支付，详情页支付
     */
	@Override
	public Object SecondPay(int wechatUserId, String pwd, String orderNo) {
		 boolean flag = false;//判断密码是否正确标志
		 //获取用户密码
		wallet wallet= this.walletDao.getWechatPassword(wechatUserId);
		String password=wallet.getPassword();
		
		try {
			flag = MD5.checkpassword(pwd, password);//比较支付密码
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		if(flag){
			wallet walletMoney = this.walletDao.getWechatMoney(wechatUserId);
			BigDecimal money= walletMoney.getMoney();
			BigDecimal needPrice = this.orderDao.getOrderNeedPrice(orderNo);//需要支付的订单总额
			if(money.compareTo(needPrice)==-1){
				
				throw new IllegalArgumentException("余额不足");
			}
			
			else{
                 wallet payWallet =new wallet();
				 payWallet.setMoney(needPrice);
				 payWallet.setWechatUserId(wechatUserId);
				 int rows= this.walletDao.updateWallet(payWallet);//余额减去支付总额
				 if(rows>0){
					 
					 this.walletDao.updateAdminWallet(needPrice);
					 //支付成功更改订单状态
					 this.orderDao.updateOrderStausAfterPay(orderNo);
				
				}
			}
			return ResponseUtil.ok();
		}
		else{
			return ResponseUtil.payFailed();
		}
	}
     
	/**
	 * 添加卡号
	 */
	@Override
	public boolean insertRechange(rechange rechange) {
	    int rows = this.walletDao.insertRechange(rechange);
	    if(rows>0){
	    	return true;
	    }
	    else{
	    	return false;
	    }
		
	}
    /**
     * 充值
     */
	@Override
	@Transactional(rollbackFor = {IllegalArgumentException.class})
	public Object rechange(int wechatUserId,String no) {
		String MD5No = null;
		try {
			MD5No = MD5.EncoderByMd5(no);
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		rechange rechange = this.walletDao.getRechangeNo(MD5No);//查询是否有此卡号
		if(rechange!=null){
			if(rechange.getIsUse()==0){
				int rows = this.walletDao.updateRechange(MD5No);//充值后使卡号失效
				if(rows>0){
					wallet wallet = new wallet();
					wallet.setMoney(rechange.getMoney());
					wallet.setWechatUserId(wechatUserId);;
					int row = this.walletDao.rechange(wallet);
					if(row>0){
						return ResponseUtil.ok();
					}
					else{
						throw new IllegalArgumentException("充值失败");
					}
					
				}
				else{
					return ResponseUtil.fail();
				}
			}
			else{
				return ResponseUtil.isUse();
			}
		}
		else{
			return ResponseUtil.faileNo();
		}
		
		
	}

}
