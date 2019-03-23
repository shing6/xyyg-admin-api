package cn.xyyg.service;

import java.util.List;

import cn.xyyg.pojo.work;

public interface workService {

	/**
	 * 分页获取兼职列表
	 * @return
	 */
	public List<work> getWork(int pageNum, int pageSize );
	
	/**
	 * 获取兼职数量
	 * @return
	 */
	public int getWorkCount();
	
	/**
	 * 添加兼职
	 * @param work
	 * @return
	 */
	public boolean insertWork(work work);
	
	/**
	 * 获取兼职详情
	 * @param id
	 * @return
	 */
	public work getWorkById(int id);
	
	/**
	 * 批量删除兼职
	 */
	public boolean deleteWork(List<Integer >ids);
	
	/**
	 * 修改兼职
	 * @param work
	 * @return
	 */
	public boolean updateWork(work work);
}
