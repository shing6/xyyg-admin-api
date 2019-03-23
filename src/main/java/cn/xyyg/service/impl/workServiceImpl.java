package cn.xyyg.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.xyyg.dao.workDao;
import cn.xyyg.pojo.work;
import cn.xyyg.service.workService;

@Service
@Transactional
public class workServiceImpl implements workService{
	 @Autowired
     private workDao workDao;
	/**
	 * 分页获取兼职列表
	 */
	@Override
	public List<work> getWork(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.workDao.getWork();
	}
	
	/**
	 * 获取兼职数量
	 */
	@Override
	public int getWorkCount() {
		
		return this.workDao.getWorkCount();
	}
    /**
     * 添加兼职
     */
	@Override
	public boolean insertWork(work work) {
		int rows = this.workDao.insertWork(work);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
		
	}
     /**
      * 根据id获取兼职详情
      */
	@Override
	public work getWorkById(int id) {
		
		return this.workDao.getWorkById(id);
	}
     /**
      * 批量删除兼职
      */
	@Override
	public boolean deleteWork(List<Integer> ids) {
		int rows = this.workDao.deleteWork(ids);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
		
	}
    
	/**
	 * 修改兼职
	 */
	@Override
	public boolean updateWork(work work) {
		int rows = this.workDao.updateWork(work);
		if(rows>0){
			return true;
		}
		else{
			return false;
		}
		
	}

}
