package cn.xyyg.util;

public interface UserConstantInterface {
	// 请求的网址
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
	// 你的appid
    public static final String WX_LOGIN_APPID = "wx7671a8f065d92af5";
	// 你的密匙
    public static final String WX_LOGIN_SECRET = "a78afeab52d6212d45eb3f9e8c762d79";
    
	// 固定参数
    public static final String WX_LOGIN_GRANT_TYPE = "authorization_code";


}
