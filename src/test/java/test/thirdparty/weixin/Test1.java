package test.thirdparty.weixin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;


public class Test1{
	@SuppressWarnings("hiding")
	public <String> List<String> test (){
		return new ArrayList<String>();
	}
	public static void main(String args[]) throws IOException {
		System.out.println(System.getenv("java_home"));
		System.out.println(Byte.MAX_VALUE);
		System.out.println(Byte.MIN_VALUE);
		System.out.println(-1 << 3);
		System.out.println((1 << 31) - 1 );
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Long.MAX_VALUE);
		System.out.println(" - - - - - - - - - - - - - - ");
		System.out.println(Integer.MAX_VALUE >> 0);
		System.out.println((byte)(129 >> 0));
		System.out.println((byte)(150));
		System.out.println(Integer.toBinaryString(278));
		System.out.println(10110);
		System.out.println(280 >> 8);
		System.out.println(0x1);
		long old = System.currentTimeMillis();
//		for (int i = 0; i < 100000000; i++) {
//			int value = Integer.MAX_VALUE;
//			byte [] bytes = int2byte(value);
//			value = byte2int(bytes);
//		}
		System.out.println("Time:"+(System.currentTimeMillis()-old));
		System.out.println(Integer.MAX_VALUE);
		System.out.println(100>>3);
		System.out.println(1200&0XFF);
		System.out.println(Integer.toBinaryString(0XFF));
		System.out.println(0XFF);
		System.out.println(0X64);
//		Map<String, String> params = new HashMap<String, String>();
//		String nonce = "qqq";
//		String timestamp = String.valueOf(System.currentTimeMillis());
//		List<String> list = new ArrayList<String>();
//		list.add(nonce);
//		list.add(timestamp);
//		list.add(TConstant.WEIXIN_TOKEN);
//		Collections.sort(list);
//		StringBuffer stringBuffer = new StringBuffer();
//		for (String str : list) {
//			stringBuffer.append(str);
//		}
//		String signature = Encryptor.getInstance().encodeBYSHA1(
//				stringBuffer.toString());
//		params.put("nonce", nonce);
//		params.put("timestamp", timestamp);
//		params.put("signature", signature);
//		String result = SimpleHttpClient.invokePost4String("http://localhost:8300/yigo/weixin", params);
//		System.out.println(result);
		String [][] aa = new String[3][2];
		aa[0][0] = "00";
		aa[0][1] = "01";
		aa[1][0] = "01";
		aa[1][1] = "01";
		aa[2][0] = "01";
		aa[2][1] = "01";
		System.out.println(aa);
//		try {
//			throw new InvocationTargetException(new NullPointerException());
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		try {
//			throw new ThrowTimeoutException();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
		try {
			JSONObject.parse("{a:[{a:1,b:true,c:'c'}]}");
			throw new Error();
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("ioerror");
		}
		System.out.println("eeeeeeeeeeee");
	}
	
	public static byte[] int2byte(int number){
		byte[] bytes = new byte[4];
		bytes[0] = (byte)(number >> 0);
		bytes[1] = (byte)(number >> 8);
		bytes[2] = (byte)(number >> 16);
		bytes[3] = (byte)(number >> 24);
		return bytes;
	}
	
	public static int byte2int(byte [] bytes){
       return (int)(bytes[0] | bytes[1]<<8 | bytes[2]<<16 | bytes[3]<<24);
        
	}
	
	public boolean onlyNumberOrLetter(String code){
		char [] codes = code.toCharArray();
		for (int i = 0; i < codes.length; i++) {
			if(!isNumberOrLetter(codes[i])){
				return false;
			}
		}
		return true;
	}
	
	private boolean isNumberOrLetter(char ch){
		return (ch > 47 && ch < 59) || (ch > 96 && ch < 123) || (ch > 65 && ch < 91);
	}
	

}
