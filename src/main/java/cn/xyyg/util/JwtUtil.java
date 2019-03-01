package cn.xyyg.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtil {
	private static final long EXPIRE_TIME =60 * 60 *1000;
	private static final String TOKEN_SECRET="f26e587c28064d064e855e72c0a6a0e618";
	
    public static String sign(int userId){
    	try{
    		//过期时间
        	Date date=new Date(System.currentTimeMillis()+EXPIRE_TIME);
        	//私钥及加密算法
        	Algorithm algorithm =Algorithm.HMAC256(TOKEN_SECRET);
        	Map<String,Object> header=new HashMap<>(2);
        	header.put("typ", "JWT");
        	header.put("alg", "HS256");
    		return JWT.create().withHeader(header).withClaim("userId",userId).withExpiresAt(date).sign(algorithm);
    	}
    	
		catch(Exception exception){
    		return null;
    	}
    }
    
    public static boolean verify(String token){
    	try{
    		Algorithm algorithm =Algorithm.HMAC256(TOKEN_SECRET);
        	JWTVerifier verifier =JWT.require(algorithm).build();
        	DecodedJWT jwt =verifier.verify(token);
    		return true;
    	}
    	catch(Exception exception){
    		return false;
    	}
    	
    	
    }
}
