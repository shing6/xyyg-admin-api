package cn.xyyg.service.impl;



import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import cn.xyyg.pojo.goodsPicture;
import cn.xyyg.pojo.goodsWithCounts;
import cn.xyyg.pojo.goods;
import cn.xyyg.pojo.goodsDesc;
import cn.xyyg.pojo.goodsDescPic;
import cn.xyyg.pojo.page;
import cn.xyyg.service.goodsService;
import cn.xyyg.dao.goodsDao;

@Service
@Transactional
public class goodsServiceImpl implements goodsService {
    @Autowired
	private goodsDao goodsDao;
    /**
     * 一对多查找商品及其图片属性
     */
	@Override
	public List<goodsDescPic> getAllGoodsDescPic() {
		
		return this.goodsDao.getAllGoodsDescPic();
	}
	/**
	 * 根据分类id查找商品
	 */
	@Override
	public List<goodsWithCounts> getGoodsByCategoryId(Integer id) {
		
		return this.goodsDao.getGoodsByCategoryId(id);
	}
	/**
	 * 根据商品id查询商品信息
	 */
	@Override
	public goodsDescPic getGoodsById(Integer id) {
		
		return this.goodsDao.getGoodsById(id);
	}
	/**
	 * 根据数组ids查询商品信息
	 */
	@Override
	public List<goods> getGoodsByIds(List<Integer> ids) {
		
		return this.goodsDao.getGoodsByIds(ids);
	}
	/**
	 * 根据商品名字模糊查询商品信息
	 */
	@Override
	public List<goods> getGoodsByName(String name) {
		
		return this.goodsDao.getGoodsByName(name);
	}
	/**
	 * 根据商品名称模糊查询商品信息并根据condition进行升降序排序
	 */
	@Override
	public List<goodsWithCounts> getGoodsByNameOrderBySort(String name, String condition, String sort,int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return this.goodsDao.getGoodsByNameOrderBySort(name, condition, sort);
	}
	
	/**
	 * 根据商品名称模糊查询返回数量
	 */
	@Override
	public int getGoodsByNameOrderBySortCount(String name, String condition, String sort) {
		
		return this.goodsDao.getGoodsByNameOrderBySortCount(name, condition, sort);
	}
	
	/**
	 * 根据商品id获取商品简要信息
	 */
	@Override
	public goods getGoodById(int id) {
		
		return this.goodsDao.getGoodById(id);
	}
	
	/**
	 * 根据商家id查询商品详细信息
	 */
	@Override
	public List<goods> getGoodsBySellerId(int pageNum,int pageSize,int sellerId) {
		int newPageNum=pageNum*pageSize;
        page page=new page();
		page.setPageNum(newPageNum);
		page.setPageSize(pageSize);
		page.setSellerId(sellerId);
		return this.goodsDao.getGoodsBySellerId(page);
	}
	
	/**
	 * 根据商品id查询商品数量
	 */
	@Override
	public int getGoodsCountBySellerId(int sellerId) {
		
		return this.goodsDao.getGoodsCountBySellerId(sellerId);
	}
	
	/**
	 * 添加商品
	 */
	@Override
	public boolean insertGoods(goods goods, List<String> picAddrList, List<goodsDesc> goodsDesc) {
		int counts=this.goodsDao.insertGoods(goods);
		if(counts>0){
			//图片
			List<goodsPicture> goodsPictureList=new ArrayList<>();
			for(int i=0;i<picAddrList.size();i++){
				goodsPicture goodsPicture=new goodsPicture();
				goodsPicture.setPicAddr(picAddrList.get(i));
				goodsPicture.setGoodsId(goods.getId());;
				goodsPictureList.add(goodsPicture);
			    
			}
			//如果有图片
			if(goodsPictureList.size()>0){
				//批量添加图片
				this.goodsDao.insertGoodsPicture(goodsPictureList);
			}
			
			if(goodsDesc.size()>0){
				for(int i=0;i<goodsDesc.size();i++){
					goodsDesc.get(i).setGoodsId(goods.getId());
				}
				//批量添加参数
				this.goodsDao.insertGoodsDesc(goodsDesc);
			}
			
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 修改商品
	 */
	@Override
	public boolean updateGoods(goods goods, List<goodsPicture> picAddrList, List<goodsDesc> goodsDesc) {
		int counts=this.goodsDao.updateGoods(goods);
		
		if(counts>0){
            /**
			 * 修改图片详情
			 */
			if(picAddrList.size()>0){
				
				//分两种情况，如果数据库已经有的图片就修改，没有就新加
				//图片
				List<goodsPicture> goodsPictureList=new ArrayList<>();
				for(int i=0;i<picAddrList.size();i++){
					
					if(picAddrList.get(i).getId()!=null){
						//如果图片地址为空则删除
						if(picAddrList.get(i).getPicAddr()==null){
							this.goodsDao.deleteGoodsPic(picAddrList.get(i).getId());
						}
						else{
							goodsPicture goodsPicture=new goodsPicture();
							goodsPicture.setPicAddr(picAddrList.get(i).getPicAddr());
							goodsPicture.setId(picAddrList.get(i).getId());
							this.goodsDao.updateGoodsPic(goodsPicture);
						}
						
					}
					else if(picAddrList.get(i).getId()==null){
						
						goodsPicture goodsPicture=new goodsPicture();
						goodsPicture.setPicAddr(picAddrList.get(i).getPicAddr());
						goodsPicture.setGoodsId(goods.getId());;
						goodsPictureList.add(goodsPicture);
					}
				}
				if(goodsPictureList.size()>0){
					
					this.goodsDao.insertGoodsPicture(goodsPictureList);
				}
				
			}
			
			/**
			 * 修改商品参数
			 */
			if(goodsDesc.size()>0){
				List<goodsDesc> goodsDescList=new ArrayList<>();
			for(int i=0;i<goodsDesc.size();i++){
                //分两种情况，如果数据库已经有的参数就修改，没有就新加
				if(goodsDesc.get(i).getId()!=null){
					if(goodsDesc.get(i).getDetail()==null){
						this.goodsDao.deleteGoodsDesc(goodsDesc.get(i).getId());
					}
					else{
						goodsDesc goodsDes=new goodsDesc();
						goodsDes.setId(goodsDesc.get(i).getId());
						goodsDes.setName(goodsDesc.get(i).getName());
						goodsDes.setDetail(goodsDesc.get(i).getDetail());
						this.goodsDao.updateGoodsDesc(goodsDes);
					}
					
				}
				else{
					goodsDesc goodsDes=new goodsDesc();
					goodsDes.setName(goodsDesc.get(i).getName());
					goodsDes.setDetail(goodsDesc.get(i).getDetail());
					goodsDes.setGoodsId(goods.getId());
					goodsDescList.add(goodsDes);
				}
				
			}
			if(goodsDescList.size()>0){
				this.goodsDao.insertGoodsDesc(goodsDescList);
			}
			
			}
			
			
			return true;
		}
		else{
			return false;
		}
		
	}
	
	/**
	 * 上下架商品
	 */
	@Override
	public boolean updateGoodsStatus(goods goods) {
		int rows = this.goodsDao.updateGoodsStatus(goods);
		if(rows>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 商家模糊查询商品
	 */
	@Override
	public List<goodsDescPic> selectGoodsByName(page page) {
		
		return this.goodsDao.selectGoodsByName(page);
	}
	
	/**
	 * 商家模糊查询商品数量
	 */
	@Override
	public int selectGoodsCountByName(goods goods) {
	
		return goodsDao.selectGoodsCountByName(goods);
	}
	
	
	

}
