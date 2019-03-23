package cn.xyyg.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.xyyg.pojo.goods;
import cn.xyyg.pojo.order;
import cn.xyyg.pojo.orderGoods;
import cn.xyyg.service.orderService;
import cn.xyyg.service.goodsService;

@Component
public class ScheduledTask {
	@Autowired
	private orderService orderService;
	
	@Autowired
	private goodsService goodsService;
	  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   
	    private Integer count2 = 1;

	    @Scheduled(cron = "*/10 * * * * *")        //10秒执行一次
	    public void reportCurrentTimeCron() throws InterruptedException {
	        System.out.println(String.format("+++第%s次执行，当前时间为：%s", count2++, dateFormat.format(new Date())));
	        List<order> orderList =orderService.getNotPayOrder();
	        for(int i=0;i<orderList.size();i++){
	        	long time=createTimeUtil.getTime().getTime()-orderList.get(i).getCreateTime().getTime();
	        	long minute=time/(1000* 60);
	        	//超过30分钟未付款取消
	        	if(minute>30){
	        		orderService.cancelOrder(orderList.get(i).getOrderNo());
	        		List<orderGoods> orderGoodsList=orderService.getGoodById(orderList.get(i).getId());
	        		for(int j=0; j<orderGoodsList.size();j++){
	        			goods goods=goodsService.getGoodById(orderGoodsList.get(j).getId());
	        			int stock=goods.getStock()+orderGoodsList.get(j).getCounts();
	        			orderService.updateStockById(orderGoodsList.get(j).getId(), stock);
	        		}
	        	}
	        }
	        //收货
	        List<order> NotRecevierOrderList =orderService.getNotReceiveOrder();
	        
	        for(int j=0;j<NotRecevierOrderList.size();j++){
	        	long time2=createTimeUtil.getTime().getTime()-NotRecevierOrderList.get(j).getCreateTime().getTime();
	        	long minute2=time2/(1000* 60);
	        	//超过24小时自动收货
	        	if(minute2>1440){
	        		orderService.takeGoods(NotRecevierOrderList.get(j).getOrderNo());
	        	}
	        }
	        
	        
	        
	    }


}
