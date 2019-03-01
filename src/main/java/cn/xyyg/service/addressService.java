package cn.xyyg.service;

import java.util.List;

import cn.xyyg.pojo.address;

public interface addressService {
	/**
	  * 添加微信用户地址
	  * @param address
	  */
    public  boolean insertAddress(address address);
    /**
     * 根据用户id查询用户地址
     * @param wechatUserId
     * @return
     */
    public List<address> getAddressById(Integer wechatUserId);
    
    /**
     * 根据地址id修改地址
     * @param address
     * @return
     */
    public  boolean updateAddressById(address address);
    
    /**
     * 根据地址id删除地址
     * @param id
     * @return
     */
    public  boolean deleteAddressById(Integer id);
    
    /**
     * 根据用户id查询用户默认收货地址
     * @param wechatUserId
     * @return
     */
    public address getDefaultAddressById(Integer wechatUserId);

}
