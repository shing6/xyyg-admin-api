package cn.xyyg.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.xyyg.pojo.goods;
import cn.xyyg.pojo.order;
import cn.xyyg.pojo.orderGoods;
import cn.xyyg.pojo.orderWithGoods;
import cn.xyyg.pojo.page;

@Mapper
public interface orderDao {
	/**
	 * 创建订单
	 * @param order
	 * @return
	 */
   public int createOrder(order order);
   /**
    * 更新库存
    * @return
    */
   public int updateStockById(@Param("id")int id,@Param("stock")int stock);
   /**
    * 添加订单详情
    * @param orderGoods
    * @return
    */
   public int createOrderGoods(orderGoods orderGoods);
   
   /**
    * 根据订单编号查询订单
    * @param orderNo
    * @return
    */
   public order getOrderByNo(String orderNo);
   
   /**
    * 分页查询全部订单
    * @param page
    * @return
    */
   public List<orderWithGoods> getAllOrderList(page page);
   
   /**
    * 查询订单数量
    * @return
    */
   public int getOrderCount(int wechatUserId);
   
   /**
    * 根据订单状态一对多查询订单
    * @param page
    * @return
    */
   public List<orderWithGoods> getAllOrderListByStatus(page page);
   
   /**
    * 根据用户id和状态查询订单
    * @param wechatUserId
    * @param status
    * @return
    */
   public int getOrderCountByStatus(@Param("wechatUserId")int wechatUserId,@Param("status")int status);
   
   /**
    * 获取订单详情
    * @param id
    * @return
    */
   public orderWithGoods getOrderDetailById(int id);
   
   /**
    * 更改订单某商品可评论状态
    * @param orderId
    * @param goodsId
    * @return
    */
   public int updateCommentStatus(@Param("orderId")int orderId,@Param("goodsId")int goodsId);
   
   /**
    * 根据订单id查询订单商品的是否已经评论
    * @param orderId
    * @return
    */
   public List<Integer> getIsCommentById(int orderId);
   
   /**
    * 更改订单状态
    * @param orderId
    * @param status
    * @return
    */
   public int updateOrderStaus(@Param("orderId")int orderId,@Param("status")int status);
   
   /**
    * 根据商家id查询订单
    * @param sellerId
    * @return
    */
   public List<order> getOrderBySellerId(int sellerId);
   
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
   public List<order> selectOrderByStatus(order order);
   
   /**
    * 根据订单id和状态获取订单数量
    * @param order
    * @return
    */
   public int selectOrderCountByStatus(order order);
   
   /**
    * 支付后根据订单号更改订单状态和支付金额
    * @param order
    * @return
    */
   public int updateOrderStausAfterPay(String orderNo);
   
   /**
    * 根据订单号获取订单需要支付的价格
    * @param orderNo
    * @return
    */
   public BigDecimal getOrderNeedPrice(String orderNo);
   
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
  public int cancelOrder(String orderNo);
  
  /**
   * 根据订单id获取详细订单信息
   * @param orderId
   * @return
   */
  public List<orderGoods> getGoodById(int orderId);
  
  /**
   * 卖家发货
   * @param orderNo
   * @return
   */
  public int sendGoods(String orderNo);
  
  /**
   * 买家确认收货
   * @param orderNo
   * @return
   */
  public int takeGoods(String orderNo);
  
  /**
   * 买家申请退款
   * @param orderNo
   * @return
   */
  public int applyRefund(String orderNo);
  
  /**
   * 商家同意退款
   * @param orderNo
   * @return
   */
  public int agreeRefund(String orderNo);
  
 
}
