package cn.xyyg.pojo;

/**
 * 商品扩展类，增加商品销量
 * @author whc
 *
 */
public class goodsWithCounts extends goods{
   private Integer counts;

public Integer getCounts() {
	return counts;
}

public void setCounts(Integer counts) {
	this.counts = counts;
}
   
}
