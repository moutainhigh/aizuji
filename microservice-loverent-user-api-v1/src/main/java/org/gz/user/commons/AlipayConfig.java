package org.gz.user.commons;

public class AlipayConfig {
	// 商户appid
	public static String APP_ID = "2017032206340079";
	// 私钥 pkcs8格式的
	public static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDEP3lTTFS+nLU1udDlPIdasHv36oFwbUtPjuTPmUZe2MKIF0Lt4oFkcIpEXwr4wwnSahmUrMbhRbkdPuttk5c5wGqUhpOavHKMo/GlesL4C3p7FvZROe2gd2xTaqJg7ldoPwXzUdpUmDjreBQ4GeuyclxQuJJuk8GVZpP9xn6oUkvZb0FB62mzbyAoJoA/hjoKlHw822meIsuqItkNdaXlpECQg7ksE4yUz6i7yiJJwqDh2kty6wbuQafpXg9Jdo+wjznko4RDYEqLygKoZP7jPnfAVFgnbmNHlIHZWFpedSInO+DEigsWKW8aNt+d+vVJ7Ns8I1k3w1vVuYZYnzGxAgMBAAECggEAB+S1sJAFaJVNBJgwuseweglXMyWPh0IeIVkSAvebSP1W9bpZys+tfKi6Jv3bpx3RK1FxLfW1GiJ/y5lYRtQE5SHEJ60yCqtBwD5n3cwOQ4PSrVjDF4lI8YdcKA7F8Tgk8+B1ENBeA8ELqQzvY0JHpKnM5MJHwYLX6nZxxWdSeNqkZUJaxgfVbYKgyu5cCu/ROjOSPEi+KWy1wnxQoFip7aDf68OChRCW4GJh5LWGtOtW5otIziDpESbgAtWPFEbmsEgGtSnis78fMoj0I1oX4SfN3vZp4GKoKEcfDvsBgcnvrRhq8St/KpCblcPebgQ2BXHhx0ZhYt8Ln5zP+JaFQQKBgQD6zsCK71WrzhcRDnFk66P01KFZF7ZR3K8ImXopADpr5sc5EzGqCTUwUi+34lXwbvz0D0QBtahBi/IcO0Zc0YHtkmobNJ69vf+aSpB+nvYVETSaLM5L9LDzMxXSFBtOevRSzDJMVGmC3AQq6oAE1MFlKLhww0jkALygneV5cVwMFQKBgQDIT5AFJOGLpy8L11yixuJBIUiOw70ibBlUCqFkn/JcvCnLmoLIxI6686TFCnUHRi3NRVx4N9mTEkrFZUv0VGmpk/m1r5KUQA2otobzG9zsnNlm0zRjVN+R+u0TZwdj/lyKTW6T5TUWmC03IvKo4Q2pm5TUzL8zcUElydmHhedKLQKBgQCT4qMviO3YDv4U5ZJBMpyLeXDl6pBrdpeSl4SrdmH8S6h+4+1t2F4PbvaKE6Ae1CK8+d9crrNWauFIej8D7dkRrzp3bjCHqudzH7JRpwNs6qc8nVzK9q37yMx+AbMUNHWp+JNT5JYOPhMLBxiTQKwELN2MR+nL3w9aGaA8WnYu/QKBgGprarZw66sIPURrPHn2c8nqZuQCNf+Nsr8ljq0pFNgT15imFAmZ88J7IvTUI2yj6j1+Nixgb6VlOHg64umB10kSpr3R82vzw834eyW5jdvfUNkCKHkFZGnoCAAg4ppLTtrjXHYSdev1GUCLvqPePSX41E9iH8qyxsvOu1T23PRZAoGAfTRZPgQ86KP3US04chXZqFURl8IWljDA7z55nGg1JT3AbK6AUmD9PIih43X1QpbwKP6IwxkcPhjDdlXipeT1lT9KEIuZYTlb9rajRiaS2bjPifz1J3SDGK7NsIYCfF4GYaXJYG1w+uCN65M1/dKjtNTP0nuwLLLhnyANSQLQR04=";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String notify_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";
	public static String notify_url = "http://www.bcyj99.com/alipay.trade.wap.pay-java-utf-8/notify_url.jsp";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://www.bcyj99.com/alipay.trade.wap.pay-java-utf-8/return_url.jsp";
	// 请求网关地址
	public static String GATEWAY_URL = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjrEVFMOSiNJXaRNKicQuQdsREraftDA9Tua3WNZwcpeXeh8Wrt+V9JilLqSa7N7sVqwpvv8zWChgXhX/A96hEg97Oxe6GKUmzaZRNh0cZZ88vpkn5tlgL4mH/dhSr3Ip00kvM4rHq9PwuT4k7z1DpZAf1eghK8Q5BgxL88d0X07m9X96Ijd0yMkXArzD7jg+noqfbztEKoH3kPMRJC2w4ByVdweWUT2PwrlATpZZtYLmtDvUKG/sOkNAIKEMg3Rut1oKWpjyYanzDgS7Cg3awr1KPTl9rHCazk15aNYowmYtVabKwbGVToCAGK+qQ1gT3ELhkGnf3+h53fukNqRH+wIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
}
