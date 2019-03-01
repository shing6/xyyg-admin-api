package cn.xyyg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.xyyg.service.userService;
import cn.xyyg.util.HttpClientUtil;
import cn.xyyg.util.ResponseUtil;
import cn.xyyg.util.UserConstantInterface;
import net.sf.json.JSONObject;
import cn.xyyg.pojo.address;
import cn.xyyg.pojo.goods;
import cn.xyyg.pojo.wechatUser;
import cn.xyyg.service.addressService;

@RestController
@RequestMapping("/address")
public class addressController {
	@Autowired
    private userService userService;
	@Autowired
    private addressService addressService;
    /**
     * 添加收货地址
     * @param request
     * @return
     */
	@PostMapping("/add")
    public Object insertAddress(HttpServletRequest request){
		String openId=request.getParameter("open_id");
		String name=request.getParameter("linkMan");
		String address=request.getParameter("address");
		String detailAddr=request.getParameter("detailAddress");
		String mobile=request.getParameter("mobile");
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude= Double.parseDouble(request.getParameter("longitude"));
		int isDefault=Integer.parseInt(request.getParameter("isDefault"));
		address addressObj=new address();
		addressObj.setName(name);
		addressObj.setAddress(address);
		addressObj.setDetailAddr(detailAddr);
		addressObj.setMobile(mobile);
		addressObj.setLatitude(latitude);
		addressObj.setLongitude(longitude);
		addressObj.setIsDefault(isDefault);
		
		
        // 根据返回的user实体类，判断用户是否存在，存在则添加地址，不存在返回
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser != null){
        	addressObj.setWechatUserId(wechatUser.getId());
        	boolean flag=addressService.insertAddress(addressObj);
        	if(flag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.insertDataFailed();
        	}
        	
        }else{
        	
                return ResponseUtil.unlogin();
        }
      }
	
	/**
     * 根据用户id查询用户地址
     * @param wechatUserId
     * @return
     */
    @PostMapping("/getAddressById")
    public Object getAddressById(HttpServletRequest request){
    	String openId=request.getParameter("open_id");
    	wechatUser wechatUser = userService.getUserByOpenId(openId);
    	if(wechatUser!=null){
    		List<address> addressList=addressService.getAddressById(wechatUser.getId());
    		return addressList;
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    		
    	
    }
    
    @PostMapping("/update")
    public Object updateAddressById(HttpServletRequest request){
		String openId=request.getParameter("open_id");
		int id=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("linkMan");
		String address=request.getParameter("address");
		String detailAddr=request.getParameter("detailAddress");
		String mobile=request.getParameter("mobile");
		double latitude = Double.parseDouble(request.getParameter("latitude"));
		double longitude= Double.parseDouble(request.getParameter("longitude"));
		int isDefault=Integer.parseInt(request.getParameter("isDefault"));
		address addressObj=new address();
		addressObj.setId(id);
		addressObj.setName(name);
		addressObj.setAddress(address);
		addressObj.setDetailAddr(detailAddr);
		addressObj.setMobile(mobile);
		addressObj.setLatitude(latitude);
		addressObj.setLongitude(longitude);
		addressObj.setIsDefault(isDefault);
		
		
        // 根据返回的user实体类，判断用户是否存在，存在则添加地址，不存在返回
        wechatUser wechatUser = userService.getUserByOpenId(openId);
        if(wechatUser != null){
        	addressObj.setWechatUserId(wechatUser.getId());
        	boolean flag=addressService.updateAddressById(addressObj);
        	if(flag){
        		return ResponseUtil.ok();
        	}
        	else{
        		return ResponseUtil.updatedDataFailed();
        	}
        	
        }else{
        	
                return ResponseUtil.unlogin();
        }
      }
    
    /**
     * 根据地址id删除地址
     * @param id
     * @return
     */
    @PostMapping("/deleteAddressById")
    public Object deleteAddressById(HttpServletRequest request){
    	String openId=request.getParameter("open_id");
    	int id=Integer.parseInt(request.getParameter("id"));
    	wechatUser wechatUser = userService.getUserByOpenId(openId);
    	if(wechatUser!=null){
    		boolean flag= addressService.deleteAddressById(id);
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
     * 根据用户id查询用户默认收货地址
     * @param wechatUserId
     * @return
     */
    @PostMapping("/getDefaultAddressById")
    public Object getDefaultAddressById(HttpServletRequest request){
    	String openId=request.getParameter("open_id");
    	wechatUser wechatUser = userService.getUserByOpenId(openId);
    	if(wechatUser!=null){
    		address address=addressService.getDefaultAddressById(wechatUser.getId());
    		return address;
    	}
    	else{
    		return ResponseUtil.unlogin();
    	}
    		
    	
    }

}
