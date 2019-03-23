package cn.xyyg.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
	public static Object ok() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        return obj;
    }

    public static Object ok(Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", "成功");
        obj.put("data", data);
        return obj;
    }

    public static Object ok(String errmsg, Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", 0);
        obj.put("errmsg", errmsg);
        obj.put("data", data);
        return obj;
    }

    public static Object fail() {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", -1);
        obj.put("errmsg", "错误");
        return obj;
    }

    public static Object fail(int errno, String errmsg) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", errno);
        obj.put("errmsg", errmsg);
        return obj;
    }

    public static Object badArgument() {
        return fail(401, "参数不对");
    }

    public static Object badArgumentValue() {
        return fail(402, "参数值不对");
    }
    public static Object badArgumentType() {
        return fail(409, "图片类型不对");
    }
    

    public static Object unlogin() {
        return fail(501, "请登录");
    }
    
  

    public static Object serious() {
        return fail(502, "系统内部错误");
    }

    public static Object unsupport() {
        return fail(503, "业务不支持");
    }

    public static Object updatedDateExpired() {
        return fail(504, "更新数据已经失效");
    }

    public static Object updatedDataFailed() {
        return fail(505, "更新数据失败");
    }

    public static Object unauthz() {
        return fail(506, "无操作权限");
    }
    public static Object insertDataFailed() {
        return fail(507, "添加数据失败");
    }
    
    public static Object loginFailed() {
        return fail(508, "密码错误");
    }
    
    public static Object payFailed() {
        return fail(509, "支付密码错误");
    }
    public static Object SameUserName() {
        return fail(510, "用户名已存在");
    }
    public static Object NotUserName() {
        return fail(511, "用户名不存在");
    }
    public static Object unOldPwd() {
        return fail(512, "旧密码错误");
    }
    public static Object isUse() {
        return fail(513, "卡号已使用");
    }
    public static Object faileNo() {
        return fail(514, "卡号错误");
    }
    

}
