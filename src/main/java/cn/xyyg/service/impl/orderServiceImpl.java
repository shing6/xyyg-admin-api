package cn.xyyg.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.xyyg.pojo.adminWallet;
import cn.xyyg.pojo.goods;
import cn.xyyg.pojo.order;
import cn.xyyg.pojo.orderGoods;
import cn.xyyg.pojo.orderShop;
import cn.xyyg.pojo.orderWithGoods;
import cn.xyyg.pojo.ordersExtends;
import cn.xyyg.pojo.page;
import cn.xyyg.pojo.shop;
import cn.xyyg.pojo.wallet;
import cn.xyyg.service.orderService;
import cn.xyyg.util.ResponseUtil;
import cn.xyyg.util.createTimeUtil;
import cn.xyyg.util.orderNoUtil;
import net.sf.json.JSONObject;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;
import cn.xyyg.dao.goodsDao;
import cn.xyyg.dao.orderDao;
import cn.xyyg.dao.shopDao;
import cn.xyyg.dao.walletDao;

@Service
@Transactional
public class orderServiceImpl implements orderService {
	@Autowired
	private orderDao orderDao;
	
	@Autowired
	private goodsDao goodsDao;
	
	@Autowired
	private shopDao shopDao;
	
	@Autowired
	private walletDao walletDao;
	/**
	 * 创建订单
	 */
	@Override
	@Transactional(rollbackFor = {IllegalArgumentException.class})
	public Object createOrder(order order, List<ordersExtends> goodsList,  List<Integer>  shopsList,
			String address,List<String> formId,int wechatUserId) 
	{
		
        boolean isCreate=true;//商品能否购买标志
		//检测库存，库存足够则减库存，否则回滚
		for(int i=0;i<goodsList.size();i++){
			goods goods = this.goodsDao.getGoodsById(goodsList.get(i).getId());
			shop shop = this.shopDao.getShopById(goods.getSellerId());
			
			if(goodsList.get(i).getCounts()>goods.getStock()){
				isCreate=false;
				throw new IllegalArgumentException(goods.getGoodsName()+"库存不足");
			}
			//检测商品是否下架
			else if(goods.getStatus()!=1){
				isCreate=false;
				throw new IllegalArgumentException(goods.getGoodsName()+"已下架");
			}
			
			//检测店铺是否打样
			else if(shop.getStatus()!=2){
				isCreate=false;
				throw new IllegalArgumentException(shop.getName()+"已打烊");
			}
//			//检测是否满足最低起送费
//			else if(shop.getStatus()!=2){
//				isCreate=false;
//				throw new IllegalArgumentException(shop.getName()+"已打烊");
//			}
			else{
				int stock=goods.getStock()-goodsList.get(i).getCounts();
				this.orderDao.updateStockById(goodsList.get(i).getId(),stock);
				
			}
//			System.out.println(goods.getStock());
//			System.out.println(goodsList.get(i).getCounts());
		}
		if(isCreate){
			Timestamp buydate=createTimeUtil.getTime();
			List<String> orderNoList =new ArrayList<String>();
			int f=0;
			for(int i=0;i<shopsList.size();i++){
				int totalCount=0;//订单商品总数
				int count=0;//每种商品的数量
				int sellerId=shopsList.get(i);
				shop shop = shopDao.getShopById(shopsList.get(i));
				BigDecimal totalPrice=new BigDecimal(0) ;//每个店铺的商品总价不算运费
				BigDecimal needPrice=new BigDecimal(0);//应付金额算运费
				String No=orderNoUtil.getOrderIdByUUId();
				orderNoList.add(No);//存储订单号用于支付
				order.setOrderNo(No);
				order.setSnapAddr(address);
				order.setFreight(shop.getFreight());
				order.setFromId(formId.get(f));
				order.setFromId2(formId.get(f+1));
				order.setIsDelete(0);
				f+=3;
				
				
				//循环计算商品数量价格
				for(int j=0;j<goodsList.size();j++){
					orderGoods orderGoods=new orderGoods();
					if(goodsList.get(j).getSellerId()==sellerId){
						 totalCount = totalCount+goodsList.get(j).getCounts();
						 count = goodsList.get(j).getCounts();
						 BigDecimal priceCount = new BigDecimal(count);
						 totalPrice=totalPrice.add(goodsList.get(j).getPrice().multiply(priceCount));
						 
					}
					
				}
				needPrice=totalPrice.add(shop.getFreight());
				
				if(needPrice.compareTo(shop.getLowPrice())==-1){
					throw new IllegalArgumentException(shop.getName()+"未满足起送费");
				}
				order.setNeedPrice(needPrice);
				order.setTotalCount(totalCount);
				order.setTotalPrice(totalPrice);
				order.setCreateTime(buydate);
				order.setStatus(1);//1默认未支付
				order.setSellerId(sellerId);
				order.setWechatUserId(wechatUserId);
				//创建订单
				this.orderDao.createOrder(order);
				
				//查询订单用于创建订单详情
				order orderWithGoods=this.orderDao.getOrderByNo(No);
				//创建订单详情
				for(int k=0;k<goodsList.size();k++){
					orderGoods orderGoods=new orderGoods();
					if(goodsList.get(k).getSellerId()==sellerId){
						 orderGoods.setOrderId(orderWithGoods.getId());
						 orderGoods.setId(goodsList.get(k).getId());
						 orderGoods.setMainPic(goodsList.get(k).getMainPic());
						 orderGoods.setGoodsName(goodsList.get(k).getGoodsName());
						 orderGoods.setPrice(goodsList.get(k).getPrice());
						 orderGoods.setCounts(goodsList.get(k).getCounts());
						 orderGoods.setIsComment(0);
						 this.orderDao.createOrderGoods(orderGoods);
						 
				    }
					
				}
			}
			return orderNoList;
		}
		else{
			return ResponseUtil.fail();
		}
		
	}
	/**
	 * 客户分页查询全部订单
	 */
	@Override
	public List<orderWithGoods> getAllOrderList(int pageNum, int pageSize,int wechatUserId) {
		int newPageNum=pageNum*pageSize;
        page page=new page();
        page.setWechatUserId(wechatUserId);
		page.setPageNum(newPageNum);
		page.setPageSize(pageSize);
		return this.orderDao.getAllOrderList(page);
	}
	
	/**
	 * 查询订单数量
	 */
	@Override
	public int getOrderCount(int wechatUserId) {
		
		return this.orderDao.getOrderCount(wechatUserId);
	}
	
	/**
	 * 客户根据订单状态查询订单
	 */
	@Override
	public List<orderWithGoods> getAllOrderListByStatus(int pageNum, int pageSize, int status,int wechatUserId) {
		int newPageNum=pageNum*pageSize;
        page page=new page();
        page.setWechatUserId(wechatUserId);
		page.setPageNum(newPageNum);
		page.setPageSize(pageSize);
		page.setStatus(status);
		return this.orderDao.getAllOrderListByStatus(page);
	}
	
	/**
	 * 客户根据状态查询订单数量
	 */
	@Override
	public int getOrderCountByStatus(int wechatUserId, int status) {
		
		return this.orderDao.getOrderCountByStatus(wechatUserId, status);
	}
	
	/**
	 * 获取订单详情
	 */
	@Override
	public orderWithGoods getOrderDetailById(int id) {
		
		return this.orderDao.getOrderDetailById(id);
	}
	
	/**
	 * 根据商家id查订单
	 */
	@Override
	public List<order> getOrderBySellerId(int sellerId,int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.orderDao.getOrderBySellerId(sellerId);
	}
	
	/**
	 * 根据商家id获取订单数量
	 */
	@Override
	public int getOrderCountBySellerId(int sellerId) {
		
		return this.orderDao.getOrderCountBySellerId(sellerId);
	}
	
	/**
	 * 查找未付款订单
	 */
	@Override
	public List<order> getNotPayOrder() {
		
		return this.orderDao.getNotPayOrder();
	}
	
	/**
	 * 取消超时未付款订单
	 */
	@Override
	public Object cancelOrder(String orderNo) {
		int rows=this.orderDao.cancelOrder(orderNo);
		if(rows>0){
			return ResponseUtil.ok();
		}
		else{
			return ResponseUtil.fail();
		}
		
	}
	/**
	 * 获取订单简要信息
	 */
	@Override
	public List<orderGoods> getGoodById(int orderId) {
		
		return this.orderDao.getGoodById(orderId);
	}
	
	/**
	 * 更新库存
	 */
	@Override
	public Object updateStockById(int id, int stock) {
		int rows=this.orderDao.updateStockById(id, stock);
		if(rows>0){
			return ResponseUtil.ok();
		}
		else{
			return ResponseUtil.fail();
		}
		
	}
	/**
	 * 卖家发货
	 */
	@Override
	public boolean sendGoods(String orderNo) {
		int rows = this.orderDao.sendGoods(orderNo);
		if(rows>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 买家确认收货
	 */
	@Override
	public boolean takeGoods(String orderNo) {
		
		int rows= this.orderDao.takeGoods(orderNo);
		if(rows>0){
			order order = this.orderDao.getOrderByNo(orderNo);
			shop shop =this.shopDao.getShopById(order.getSellerId());//查询商户信息,用来获取商家id
			this.walletDao.deleteAdminWallet(order.getPayPrice());//先减去支付给商家的钱
			
			adminWallet adminWallet = new adminWallet();
			adminWallet.setMoney(order.getPayPrice());
			adminWallet.setUserId(shop.getUserId());
			this.walletDao.updateAdminWalletToSeller(adminWallet);//把订单总额支付给商家
			return true;
		}
		return false;
	}
	
	/**
	 * 根据订单编号获取订单
	 */
	@Override
	public order getOrderByNo(String orderNo) {
		
		return this.orderDao.getOrderByNo(orderNo);
	}
	
	/**
	 * 买家申请退款
	 */
	@Override
	public Object applyRefund(String orderNo) {
		int rows = this.orderDao.applyRefund(orderNo);
		if(rows>0){
			return ResponseUtil.ok();
		}
		else{
			return ResponseUtil.fail();
		}
	}
	
	/**
	 * 商家同意退款
	 */
	@Override
	public boolean agreeRefund(String orderNo,int wechatUserId) {
		int rows = this.orderDao.agreeRefund(orderNo);
		if(rows>0){
			order order = this.orderDao.getOrderByNo(orderNo);
			this.walletDao.shopRefund(order.getPayPrice());
			wallet wallet = new wallet();
			wallet.setWechatUserId(wechatUserId);
			wallet.setMoney(order.getPayPrice());
			this.walletDao.userRefund(wallet);
			return true;
		}
		return false;
	}
	/**
	 * 根据商家id和订单状态查询订单
	 */
	@Override
	public List<order> selectOrderByStatus(int sellerId,int status,int pageNum,int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.orderDao.selectOrderByStatus(sellerId,status);
	}
	
	/**
	 * 根据商家id和订单状态查询订单数量
	 */
	@Override
	public int selectOrderCountByStatus(int sellerId,int status) {
		
		return this.orderDao.selectOrderCountByStatus(sellerId,status);
	}
	
	/**
	 * 根据商家id和订单号查询订单
	 */
	@Override
	public List<order>  selectOrderByNo(order order, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.orderDao.selectOrderByNo(order);
	}
	
	/**
	 * 根据商家id和订单号查询订单数量
	 */
	@Override
	public int selectOrderCountByNo(order order) {
		
		return this.orderDao.selectOrderCountByNo(order);
	}
	
	/**
	 * 用户更改订单状态
	 */
	@Override
	public boolean updateOrderStaus(int orderId, int status) {
		int rows = this.orderDao.updateOrderStaus(orderId, status);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	/**
	 * 用户删除订单
	 */
	@Override
	public boolean deleteOrder(int orderId) {
		
		int rows = this.orderDao.deleteOrder(orderId);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	
	
	
	
		

}
