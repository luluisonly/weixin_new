package test.thirdparty.weixin;

import java.io.IOException;

import com.bokesoft.thirdparty.weixin.common.Encryptor;
import com.bokesoft.thirdparty.weixin.common.Kaptcha;

public class Test2 {
	static String [] codes = "abcdefghijklmnopqrstuvwxyz".split("");
	public static void main(String[] args) throws IOException {
		System.out.println(Encryptor.encodeBYSHA1("r"));
		String test = Kaptcha.randomCode(16);
		System.out.println(test.toLowerCase());
		Encryptor.decryptData("PoGUNpNKDXR8rQBg6X", "LOl4");
	}
}
