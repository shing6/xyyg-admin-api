package cn.xyyg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.xyyg.pojo.work;
@Mapper
public interface workDao {
	
	/**
	 * 分页获取兼职列表
	 * @return
	 */
	public List<work> getWork();
	
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
	public int insertWork(work work);
	
	/**
	 * 获取兼职详情
	 * @param id
	 * @return
	 */
	public work getWorkById(int id);
	
	/**
	 * 批量删除兼职
	 */
	public int deleteWork(List<Integer >ids);
	
	/**
	 * 修改兼职
	 * @param work
	 * @return
	 */
	public int updateWork(work work);

}
