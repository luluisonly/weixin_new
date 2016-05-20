package test.thirdparty.weixin;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class TestEncoder {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "123\n456\n789";
		System.out.println(str);
		String str2 = str = URLEncoder.encode(str,"UTF-8");
		System.out.print(URLDecoder.decode(str2,"UTF-8"));
	}
}
