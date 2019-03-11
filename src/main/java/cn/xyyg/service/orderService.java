package cn.xyyg.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.xyyg.pojo.order;
import cn.xyyg.pojo.orderGoods;
import cn.xyyg.pojo.orderShop;
import cn.xyyg.pojo.orderWithGoods;
import cn.xyyg.pojo.ordersExtends;
import cn.xyyg.pojo.page;
import cn.xyyg.pojo.wallet;
import net.sf.json.JSONObject;

public interface orderService {
	
	/**
	 * 创建订单
	 * @param order
	 * @param address 
	 * @param formId 
	 * @param shopslist 
	 * @param goodslist 
	 * @return
	 */
//   public Object createOrder(order order, List<ordersExtends> goodsList, List<Integer> shopsList, String address);

	public Object createOrder(order order, List<ordersExtends> goodsList, List<Integer> shopsList, String address,
			List<String> formId, int wechatUserId);
	
	   /**
	    * 客户分页查询全部订单
	 * @param status 
	    * @param page
	    * @return
	    */
	   public List<orderWithGoods> getAllOrderList(int pageNum, int pageSize,int wechatUserId);
	   /**
	    * 查询订单数量
	    * @return
	    */
	   public int getOrderCount(int wechatUserId);
	   
	   /**
	    * 客户根据订单状态一对多查询订单
	    * @param page
	    * @return
	    */
	   public List<orderWithGoods> getAllOrderListByStatus(int pageNum, int pageSize, int status,int wechatUserId);
	   
	   /**
	    * 根据用户id和状态查询订单
	    * @param wechatUserId
	    * @param status
	    * @return
	    */
	   public int getOrderCountByStatus(int wechatUserId,int status);
	   
	   /**
	    * 获取订单详情
	    * @param id
	    * @return
	    */
	   public orderWithGoods getOrderDetailById(int id);
	   
	   /**
	    * 根据商家id查询订单
	    * @param sellerId
	 * @param pageSize 
	 * @param pageNum 
	    * @return
	    */
	   public List<order> getOrderBySellerId(int sellerId, int pageNum, int pageSize);
	   
	   /**
	    * 根据商家id获取订单数量
	    * @param sellerId
	    * @return
	    */
	   public int getOrderCountBySellerId(int sellerId);
	   
	   /**
	    * 根据订单id和状态查询订单
	    * @param order
	    * @return
	    */
	   public List<order> selectOrderByStatus(int sellerId,int status,int pageNum,int pageSize);
	   
	   /**
	    * 根据订单id和状态获取订单数量
	    * @param order
	    * @return
	    */
	   public int selectOrderCountByStatus(int sellerId,int status);
	   
	   /**
	    * 根据商家id和订单号查订单
	    * @param order
	    * @return
	    */
	   public List<order>  selectOrderByNo(order order,int pageNum,int pageSize);
	  
	   /**
	    * 根据商家id和订单号查订单数量
	    * @param order
	    * @return
	    */
	   public int selectOrderCountByNo(order order);
	   
	   /**
	    * 查找未付款订单
	    * @return
	    */
	   public List<order> getNotPayOrder();
	   
	   /**
	    * 取消超时未支付订单
	    * @param orderNo
	    * @return
	    */
	  public  Object cancelOrder(String orderNo);
	  

	  /**
	   * 根据订单id获取详细订单信息
	   * @param orderId
	   * @return
	   */
	  public List<orderGoods> getGoodById(int orderId);
	  
	  /**
	    * 更新库存
	    * @return
	    */
	   public Object updateStockById(int id,int stock);
	   
	   /**
	    * 卖家发货
	    * @param orderNo
	    * @return
	    */
	   public boolean sendGoods(String orderNo);
	   
	   
	   /**
	    * 买家确认收货
	    * @param orderNo
	    * @return
	    */
	   public boolean takeGoods(String orderNo);
	   
	   /**
	    * 根据订单编号查询订单
	    * @param orderNo
	    * @return
	    */
	   public order getOrderByNo(String orderNo);
	   
	   /**
	    * 买家申请退款
	    * @return
	    */
	   public Object applyRefund(String orderNo);
	   
	   /**
	    * 商家同意退款
	    * @param orderNo
	    * @return
	    */
	   public boolean agreeRefund(String orderNo,int wechatUserId);
	   
	   /**
	    * 用户更改订单状态
	    * @param orderId
	    * @param status
	    * @return
	    */
	   public boolean updateOrderStaus(int orderId,int status);
	   
	   /**
	    * 用户删除订单
	    * @param orderId
	    * @return
	    */
	   public boolean deleteOrder(int orderId);
  
}
