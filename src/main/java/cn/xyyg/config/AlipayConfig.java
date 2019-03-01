package cn.xyyg.config;

public class AlipayConfig {
	//开发者中心 / 研发服务 / 沙箱环境 / 沙箱应用/ 信息配置/ 必看部分/ APPID（填自己的，下面都是改过的~）
		public static String app_id = "2016092800613585";
		
		//开发者中心 / 研发服务 / 沙箱环境 / 沙箱应用/ 信息配置/ 必看部分/ 生成公钥时对应的私钥（填自己的，下面都是改过的~）
		public static String private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0rIzXWLNH6BHHZZsisPhv23sNeEO8YcLQIFfDL/UwFcuW+0+T738PdSUTvmIpcsNnKF+CgYT9pthmEzasEI3eBM7wVmPgiA0bwjJlU+Ih0IJ4UibImtfXRkb59hbJ0DEiAJdYiWZmL5IeVwhwQK1u9yRmrTtcooXvLsPQG7MbHUtBYzK4RvaYQlKV11LXC9+PQDfc1TUEQifRELQVAdDOV/TBunK7KYJ9B770KfCZrMTcphVgcbbJe6hXK0t74vUSQOZinM38ZfBV8BsiA/oIMPigptcyHBlgEhTcDDieezMtS5ETonKaGN1ALGlqwLIBC9ONZUiuBgIRbUHhobxDAgMBAAECggEAUbtJD1Xp7Fp4SHqXXm8uQ/PGy8Ye5kZXSa2H5h14zmLdsZRIYITUNtrohfD3GQb4jF8ALdqHnfNvK2X/soDenJQSRTjff8AsJeWIxLvYrTFVBDctokwh3hnFm+gOUQdkYpxf2sZ1Uz6r3BvFDnrDulIE3on5M6O18Qh3vln5gQbP6l+FfaMvg3yF3p3jXdyNo2MTHz3bDm00YcKIB7SeT1/FDdgAmo/PY7dV+6VK3AkUMcWMu4Gao7kHZzqY5d8JkOoBweJV3v/0E/00JCKmOpWHm7p7szvZzZru6hXuW/EkcKjzxvHLMgajTVT1IxKPbcFV1DCp98tye8IkPINuUQKBgQD37bFsV39cy33Suye1hftmzDUnQF+xXtca+CY/f7jD1YXtavkxUaj009TFIJ9Alh4CGxcB7PxGFSLAI1bgg+yTzBLCoALWZiuKNxU4rvPGjX5kYqwOArK/UfdGQBhSkutMTAbCMl8xMlYzW/j6F0ShStLaEHPYb7Grzco8FnoSuQKBgQC6jlbTBKo7Z9MokjKh8EI9O6817bs0SeTqrd8Vwsb5TTe1wmOQBQxhG9bEF13nA/+ieuAgtrsLk/F30QdfG9mLTQFp3j/skwsYloHFWMfEoW1nJfKjsdrSu6kdb/e6ix8pLA12iWog9yUdyjCzwfrbO6AxCmwdF/yahYiDb1V42wKBgQDMY7v84TUyPt//tiLKVyYmXPsE3OEGzHTw2tpNRvH/ICxEygedTQDItUmXKYmgKSGcAtE42TIwZIQ9eeTJOnjyby6YHGm6bog+baKlFDYL3eHwyBenhTnu15fO5lAm4s/wGk0IE5a+bAz8UjozkrMVdSXWd/HI4GvMgt7SIoWIoQKBgDWcxlLqWkmsMsDMEwT62O5kApxK76u9OK5AvYWxbiEjEcmITbiL2tzldTjxRiknuwVkrph4KxTOtWwrhCTPChvAS5GzL0e7kkrEphKiyvY1Ng2YmDx21l4NVDKDhPv1E0VvxYa1QuyqWd5PxuNB8eejIQw25I8P36ZAjdTH4BZVAoGBAJkXt9C9Fa7j40NDXkZlYNPMf8RX/70gvn0X1cVbCfmRZKn8PfVWZ9fL6A90lVynwCOF0AxQObIbB+SLUmp/Bbg7tXDR6EAjChf75FMKFh+oDLkOrAVOJPVi4hA47QmJGoQC+x6+blRNzBXSgoOJFiQonaDBZfwJSi9PwAnqjhr7";
		
		//Controller Mapping:得放到服务器上，且使用域名解析 IP
		public static String notify_url = "https://www.shing6.cn:8081/alipay/notifyUrl";
		
		//Controller Mapping:得放到服务器上，且使用域名解析 IP
		public static String return_url = "https://www.shing6.cn:8081/alipay/returnUrl";
		
		//开发者中心 / 研发服务 / 沙箱环境 / 沙箱应用/ 信息配置/ 必看部分/ 支付宝网关（注意沙箱alipaydev，正式则为 alipay）
		public static String url = "https://openapi.alipaydev.com/gateway.do";
		
		public static String charset = "UTF-8";
		
		public static String format = "json";
		
		//开发者中心 / 研发服务 / 沙箱环境 / 沙箱应用/ 信息配置/ 必看部分/ 公钥（填自己的，下面都是改过的~）
		public static String public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3r2fiB/L1cZHsK6uU1ZWfMWcHOCkrSsL4n4y6POtsQrgzmn6GhVTnTeHyRXa09GHfjVzLs2chcEn3G3KsmiLPdhCBRGC4FGpjvXEEkxqJrYM1+l3D0YiSaIkHKLqN9uJKDojvDasTbKsoKWVtARGIo+obTmmbCC2fhBWYcWxPTPGXH5o2nxs4dGopTPfa77FEOOznWbGYg8Lpol5Pa/TcWya5XrK9bWY1VxJZ9aBPA1hmi+RxK5cilv+xx+B3S45FeQ9IPCjq13LwqoqBrbwKrevTumVZ/RyW2ogRZ44KOTfA0Yii11LiZ3UYJkcN/1maGy36IjMPb/1W9B3irBwrwIDAQAB";
		
		public static String signtype = "RSA2";
	

}
