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

}