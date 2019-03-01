package cn.xyyg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import cn.xyyg.pojo.address;

@Mapper
public interface addressDao {
	 /**
	  * 添加微信用户地址
	  * @param address
	  */
     public  int insertAddress(address address);
     /**
      * 根据用户id查询用户地址
      * @param wechatUserId
      * @return
      */
     public List<address> getAddressById(Integer wechatUserId);
     
     /**
      * 设所有地址为非默认
      * @param wechatUserId
      */
     public void updateIsDefaultAddr(Integer wechatUserId);
     
     /**
      * 根据地址id修改地址
      * @param address
      * @return
      */
     public  int updateAddressById(address address);
     
     /**
      * 根据地址id删除地址
      * @param id
      * @return
      */
     public  int deleteAddressById(Integer id);
     
     /**
      * 根据用户id查询用户默认收货地址
      * @param wechatUserId
      * @return
      */
     public address getDefaultAddressById(Integer wechatUserId);
}
