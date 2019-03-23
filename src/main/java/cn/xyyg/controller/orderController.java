package cn.xyyg.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;

import cn.xyyg.pojo.TemplateData;
import cn.xyyg.pojo.Token;
import cn.xyyg.pojo.WxMssVo;
import cn.xyyg.pojo.category;
import cn.xyyg.pojo.order;
import cn.xyyg.pojo.orderShop;
import cn.xyyg.pojo.orderWithGoods;
import cn.xyyg.pojo.ordersExtends;
import cn.xyyg.pojo.shop;
import cn.xyyg.pojo.shopWithGoods;
import cn.xyyg.pojo.user;
import cn.xyyg.pojo.wallet;
import cn.xyyg.pojo.wechatUser;
import cn.xyyg.service.orderService;
import cn.xyyg.service.userService;
import cn.xyyg.service.shopService;
import cn.xyyg.util.CommonUtil;
import cn.xyyg.util.JwtUtil;
import cn.xyyg.util.ResponseUtil;
import cn.xyyg.util.createTimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@RestController
@RequestMapping("/order")
public class orderController {
	@Autowired
	private orderService orderService;
	@Autowired
    private userService userService;
	@Autowired
    private shopService shopService;
	/**
	 * 创建订单
	 * 
	 * @return
	 */
	@PostMapping("/createOrder")
    public Object createOrder(HttpServletRequest request){
        String orders=request.getParameter("orders");
        String openId=request.getParameter("open_id");
        String shopIdList=request.getParameter("shopIdList");
        String address=request.getParameter("address");
        String formIdList=request.getParameter("formId");
        JSONObject addressJson = JSONObject.fromObject(address);
        JSONArray ordersJson = JSONArray.fromObject(orders);
        JSONArray shopIdListJson = JSONArray.fromObject(shopIdList);
        JSONArray formIdListJson = JSONArray.fromObject(formIdList);
        List<ordersExtends> goodsList = JSONArray.toList(ordersJson, new ordersExtends(), new JsonConfig());//参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据
        List<Integer> shopsList = JSONArray.toList(shopIdListJson, new Integer(0), new JsonConfig());
        List<String> formId = JSONArray.toList(formIdListJson, new String(), new JsonConfig());
        order order=new order();
        
        // 根据返回的user实体类，判断用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        int wechatUserId=wechatUser.getId();
        if(wechatUser != null){
        	  Object object=this.orderService.createOrder(order,goodsList,shopsList,address,formId,wechatUserId);
        	  return object;
        }
        else
        	 return ResponseUtil.unlogin();
		
      
     }
	
	/**
     * 分页查询个人订单
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/getAllOrderList")
    public Object getAllOrderList(HttpServletResponse response,HttpServletRequest request){
    	
    	int pageNum=Integer.parseInt(request.getParameter("pageNum"));
    	int pageSize=Integer.parseInt(request.getParameter("pageSize"));
    	int status=Integer.parseInt(request.getParameter("showType"));
    	String openId=request.getParameter("open_id");
    	// 根据返回的user实体类，判断用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser!=null){
        	if(status==0){
        		List<orderWithGoods> orderWithGoodsList=orderService.getAllOrderList(pageNum, pageSize,wechatUser.getId());
            	int count=orderService.getOrderCount(wechatUser.getId());
            	response.setIntHeader("X-Total-Count",count);
            	
                return orderWithGoodsList;
        		
        	}
        	else{
        		List<orderWithGoods> orderWithGoodsList=orderService.getAllOrderListByStatus(pageNum, pageSize, status,wechatUser.getId());
            	int count=orderService.getOrderCountByStatus(wechatUser.getId(), status);
            	response.setIntHeader("X-Total-Count",count);
            	
                return orderWithGoodsList;
        	}
        	
        }
        else{
        	return ResponseUtil.unlogin();
        }
    	
    	
    	
    }
    
    /**
     * 根据订单状态查询订单数量
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/getAllOrderListByStatus")
    public Object getAllOrderListByStatus(HttpServletResponse response,HttpServletRequest request){
    	
    	int pageNum=Integer.parseInt(request.getParameter("pageNum"));
    	int pageSize=Integer.parseInt(request.getParameter("pageSize"));
    	int status=Integer.parseInt(request.getParameter("showType"));
    	String openId=request.getParameter("open_id");
    	// 根据返回的user实体类，判断用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser!=null){
        	List<orderWithGoods> orderWithGoodsList=orderService.getAllOrderListByStatus(pageNum, pageSize, status,wechatUser.getId());
        	int count=orderService.getOrderCountByStatus(wechatUser.getId(), status);
        	response.setIntHeader("X-Total-Count",count);
            return orderWithGoodsList;
        }
        else{
        	return ResponseUtil.unlogin();
        }
    }
    
    /**
     * 根据订单id查询详细订单
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/getOrderDetailById")
    public Object getOrderDetailById(HttpServletResponse response,HttpServletRequest request){
    	int id=Integer.parseInt(request.getParameter("orderId"));
    	String openId=request.getParameter("open_id");
    	// 根据返回的user实体类，判断用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser!=null){
        	orderWithGoods orderWithGoods=orderService.getOrderDetailById(id);
        	return orderWithGoods;
        }
        else{
        	return ResponseUtil.unlogin();
        }
    }
    
    /**
     * 根据商家id分页查询订单
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/getOrderBySellerId")
    public Object getOrderBySellerId(HttpServletResponse response,HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	
    	if (flag){
    		int userId=Integer.parseInt(request.getParameter("userId"));
        	int pageNum=Integer.parseInt(request.getParameter("pageNum"));
        	int pageSize=Integer.parseInt(request.getParameter("pageSize"));
        	
                user user=userService.getUserById(userId);
                shop shop=shopService.getShopByUserId(user.getId());
            	int count =orderService.getOrderCountBySellerId(shop.getId());
            	response.setIntHeader("X-Total-Count",count);
            	//使用分页插件,核心代码就这一行
                PageHelper.startPage(pageNum, pageSize);
             	List<order> orderList =orderService.getOrderBySellerId(shop.getId(),pageNum, pageSize);
            	return orderList;
            
         }
    	
    	else{
    		return ResponseUtil.unlogin();
    	}
        
		
    	
    }
    
    /**
     * 根据订单id查询详细订单，后台商家查询
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/getOrderById")
    public Object getOrderById(HttpServletResponse response,HttpServletRequest request){
    	
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	
    	if(flag){
    		int id=Integer.parseInt(request.getParameter("orderId"));
        	int userId=Integer.parseInt(request.getParameter("userId"));
        	orderWithGoods orderWithGoods=orderService.getOrderDetailById(id);
            return orderWithGoods;
         }
    	
    	else{
    		return ResponseUtil.unlogin();
    	}
    	
    }
    
    /**
     * 买家确认收货
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/takeGoods")
    public Object takeGoods(HttpServletResponse response,HttpServletRequest request){
    	String openId=request.getParameter("open_id");
    	String orderNo=request.getParameter("orderNo");
    	//查看用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser != null){
        	boolean flag = orderService.takeGoods(orderNo);
        	if(flag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.fail();
        	}
        }
        
        else{
        	return ResponseUtil.unlogin();
        }
    	
    }
    
    /**
     * 商家发货
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/sendGoods")
    public Object publishModelMessage(HttpServletRequest request, HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		String orderNo = request.getParameter("orderNo");
    		order order = this.orderService.getOrderByNo(orderNo);
    		wechatUser  wechatUser =  this.userService.getWechatUserById(order.getWechatUserId());
    		order sendOrder=new order();
    		sendOrder.setSendTime(createTimeUtil.getTime());
    		sendOrder.setOrderNo(orderNo);
            boolean sendflag = this.orderService.sendGoods(sendOrder);
            if(sendflag){
            	Token token = CommonUtil.getToken("wx7671a8f065d92af5","a78afeab52d6212d45eb3f9e8c762d79");

                WxMssVo wxMssVo = new WxMssVo();

                wxMssVo.setTemplate_id("oZnUGUAWoCxc7s23onTpGRmnzmgFFrtcfa7uc9AR2GA");

                wxMssVo.setTouser(wechatUser.getOpenId());

                wxMssVo.setPage("pages/profile/orderDetail/orderDetail?id="+order.getId());

                wxMssVo.setRequest_url("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + token.getAccessToken());

                wxMssVo.setForm_id(order.getFromId());

                List<TemplateData> list = new ArrayList<>();

                list.add(new TemplateData(orderNo,"#ffffff"));

                list.add(new TemplateData(createTimeUtil.getTime().toString(),"#ffffff"));

                list.add(new TemplateData("送货上门","#ffffff"));

                wxMssVo.setParams(list);

                CommonUtil.sendTemplateMessage(wxMssVo);
            	
            }
            return ResponseUtil.ok();
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
		
    	

       

    }
    
    /**
     * 商家同意退款
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/agreeRefund")
    public Object agreeRefund(HttpServletRequest request, HttpServletResponse response){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	if(flag){
    		String orderNo = request.getParameter("orderNo");
    		order order = this.orderService.getOrderByNo(orderNo);
    		wechatUser  wechatUser =  this.userService.getWechatUserById(order.getWechatUserId());
            boolean agreeflag = this.orderService.agreeRefund(orderNo, wechatUser.getId());
            if(agreeflag ){
            	Token token = CommonUtil.getToken("wx7671a8f065d92af5","a78afeab52d6212d45eb3f9e8c762d79");
                order orderM = orderService.getOrderByNo(orderNo);
                WxMssVo wxMssVo = new WxMssVo();

                wxMssVo.setTemplate_id("pbNFKMiTnwqSi_qmcWFps_Al7rgoeK1PAQPnxUhvOHo");

                wxMssVo.setTouser(wechatUser.getOpenId());

                wxMssVo.setPage("pages/profile/orderDetail/orderDetail?id="+order.getId());

                wxMssVo.setRequest_url("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + token.getAccessToken());

                wxMssVo.setForm_id(order.getFromId2());

                List<TemplateData> list = new ArrayList<>();

                list.add(new TemplateData(orderNo,"#ffffff"));

                list.add(new TemplateData(orderM.getPayPrice().toString(),"#ffffff"));

                list.add(new TemplateData("用户余额","#ffffff"));

                wxMssVo.setParams(list);

                CommonUtil.sendTemplateMessage(wxMssVo);
            	
            }
            return ResponseUtil.ok();
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}

    }
    
    /**
     * 根据商家id和状态分页查询订单
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/selectOrderByStatus")
    public Object selectOrderByStatus(HttpServletResponse response,HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	
    	if (flag){
    		int userId=Integer.parseInt(request.getParameter("userId"));
    		int pageNum=Integer.parseInt(request.getParameter("pageNum"));
        	int pageSize=Integer.parseInt(request.getParameter("pageSize"));
        	int status=Integer.parseInt(request.getParameter("status"));
                user user=userService.getUserById(userId);
                shop shop=shopService.getShopByUserId(user.getId());
                int count =orderService.selectOrderCountByStatus(shop.getId(),status);
            	response.setIntHeader("X-Total-Count",count);
            	//使用分页插件,核心代码就这一行
                PageHelper.startPage(pageNum, pageSize);
             	List<order> orderList =orderService.selectOrderByStatus(shop.getId(),status, pageNum, pageSize);
            	return orderList;
            
         }
    	
    	else{
    		return ResponseUtil.unlogin();
    	}
        
		
    	
    }
    
    /**
     * 根据商家id和编号分页查询订单
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/selectOrderByNo")
    public Object selectOrderByNo(HttpServletResponse response,HttpServletRequest request){
    	boolean  flag= JwtUtil.verify(request.getParameter("token"));
    	
    	if (flag){
    		int userId=Integer.parseInt(request.getParameter("userId"));
    		int pageNum=Integer.parseInt(request.getParameter("pageNum"));
        	int pageSize=Integer.parseInt(request.getParameter("pageSize"));
        	String orderNo=request.getParameter("orderNo");
                user user=userService.getUserById(userId);
                shop shop=shopService.getShopByUserId(user.getId());
                order order =new order();
                order.setSellerId(shop.getId());
                order.setOrderNo(orderNo);
                int count =orderService.selectOrderCountByNo(order);
            	response.setIntHeader("X-Total-Count",count);
            	//使用分页插件,核心代码就这一行
                PageHelper.startPage(pageNum, pageSize);
             	List<order> orderList =orderService.selectOrderByNo(order, pageNum, pageSize);
            	return orderList;
            
         }
    	
    	else{
    		return ResponseUtil.unlogin();
    	}
        
		
    	
    }
    
    /**
     * 买家取消订单
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/updateOrderStaus")
    public Object updateOrderStaus(HttpServletResponse response,HttpServletRequest request){
    	String openId=request.getParameter("open_id");
    	int orderId=Integer.parseInt(request.getParameter("orderId"));
    	int status=Integer.parseInt(request.getParameter("status"));
    	//查看用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser != null){
        	boolean flag = orderService.updateOrderStaus(orderId, status);
        	if(flag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.fail();
        	}
        }
        
        else{
        	return ResponseUtil.unlogin();
        }
    	
    }
    
    /**
     * 买家删除订单
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/deleteOrder")
    public Object deleteOrder(HttpServletResponse response,HttpServletRequest request){
    	String openId=request.getParameter("open_id");
    	int orderId=Integer.parseInt(request.getParameter("orderId"));
    	//查看用户是否存在
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser != null){
        	boolean flag = orderService.deleteOrder(orderId);
        	if(flag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.fail();
        	}
        }
        
        else{
        	return ResponseUtil.unlogin();
        }
    	
    }
    
}
