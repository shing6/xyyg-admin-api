package cn.xyyg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xyyg.dao.addressDao;
import cn.xyyg.pojo.address;
import cn.xyyg.service.addressService;
@Service
@Transactional
public class addressServiceImpl implements addressService {
	 @Autowired
     private addressDao addressDao;
    /**
     * 添加微信用户地址
     */
	@Override
	public boolean insertAddress(address address) {
		int isDefault =address.getIsDefault();
		if(isDefault==1){
			this.addressDao.updateIsDefaultAddr(address.getWechatUserId());
		}
		int rowCount=this.addressDao.insertAddress(address);
		if(rowCount > 0){
           return true;
        }
        return false;


	}
	/**
	 * 根据用户id查询用户地址
	 */
	@Override
	public List<address> getAddressById(Integer wechatUserId) {
		
		return this.addressDao.getAddressById(wechatUserId);
	}
	/**
	 * 根据地址id修改地址
	 */
	@Override
	public boolean updateAddressById(address address) {
		int isDefault =address.getIsDefault();
		if(isDefault==1){
			this.addressDao.updateIsDefaultAddr(address.getWechatUserId());
		}
		int rowCount=this.addressDao.updateAddressById(address);
		if(rowCount > 0){
           return true;
        }
        return false;
	}
	
	/**
	 * 根据地址id删除地址
	 */
	@Override
	public boolean deleteAddressById(Integer id) {
		int rowCount=this.addressDao.deleteAddressById(id);
		if(rowCount > 0){
           return true;
        }
        return false;
	}
	
	/**
	 * 根据用户id查询用户默认收货地址
	 */
	@Override
	public address getDefaultAddressById(Integer wechatUserId) {
		
		return this.addressDao.getDefaultAddressById(wechatUserId);
	}

}
