package cn.xyyg.pojo;

/**
 * 用户信息和商铺整合类
 * @author whc
 *
 */
public class userWithShop extends user{
      private shop shop;

	public shop getShop() {
		return shop;
	}

	public void setShop(shop shop) {
		this.shop = shop;
	}
      
}
