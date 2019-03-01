package cn.xyyg.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class createTimeUtil {
	
	public static Timestamp getTime() 
	{        
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = df.format(dt);
		java.sql.Timestamp Time = java.sql.Timestamp.valueOf(nowTime);
		
		return Time;
		
	}

}
