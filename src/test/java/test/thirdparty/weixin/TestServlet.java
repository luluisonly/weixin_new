package test.thirdparty.weixin;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.bokesoft.thirdparty.weixin.common.SimpleHttpClient;
import com.zaofans.weixin.common.PayUtils;
import com.zaofans.weixin.common.XmlUtils;

public class TestServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
//		String orderXML="";
		String str ="";
		try {
//			orderXML = createOrder();
			Map<String,String> map = createQRCode();
//			str = SimpleHttpClient.invokePostWithBody4String("http://www.joyseed.com/weixin/open-api", orderXML, 7200);
			str = SimpleHttpClient.invokePost4String("http://www.joyseed.com/weixin/open-api", map, 7200);
			JSONObject json = JSONObject.parseObject(str);
			Date date = new Date();
			SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String dateStr = sdf.format(date);
			str = "生成时间 ： " + dateStr + "<br/><br/>" + "二维码链接 ： <a target='_blank' href='"+ json.getString("message") +"'>点击打开二维码链接</a>";
		} catch (Exception e) {
			e.printStackTrace();
		}		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(str);
		writer.flush();
	}
	public static String createOrder2() throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		String temp = System.currentTimeMillis()+"";
		map.put("result_code","SUCCESS");
		map.put("return_code","SUCCESS");
		map.put("openid","oZTTgssuiCaJXATAJlk5wsm8csf0");
		map.put("time_end", temp);
		map.put("out_trade_no", "ZF"+temp);
		map.put("total_fee","10000");
		map.put("trade_type","JSAPI");
		map.put("transaction_id",temp);
//		long startTime = System.currentTimeMillis();
		String signValue = PayUtils.paySign(map, "9150ec9565808b2f3e914de3d0257270");
//		long endTime = System.currentTimeMillis();
//		System.out.println("----------"+(endTime-startTime));
		map.put("sign", signValue);
		String xml = XmlUtils.maptoXml(map);
		return xml;
	}
	
	public static String createOrder() throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		String temp = System.currentTimeMillis()+"";
		map.put("result_code","SUCCESS");
		map.put("return_code","SUCCESS");
		map.put("openid","oheWMt8Xu6lIHJ1UOLvy1BEZILcs");
		map.put("time_end", temp);
		map.put("out_trade_no", "ZF1000150924000003");
		map.put("transaction_id", "1009800929201509240979280604");
		map.put("total_fee","14200");
//		map.put("trade_type","JSAPI");
//		long startTime = System.currentTimeMillis();
		String signValue = PayUtils.paySign(map, "WEIXINlisa0616WEIXINlisa06160123");
//		long endTime = System.currentTimeMillis();
//		System.out.println("----------"+(endTime-startTime));
		map.put("sign", signValue);
		String xml = XmlUtils.maptoXml(map);
		return xml;
	}
	
	private static Map<String,String> createQRCode(){
		Map<String,String> map = new HashMap<String, String>();
		map.put("uname", "zaofans");
		map.put("type", "getTemporaryQRCode");
		map.put("param", "1800:91529");//陈洁
		return map;
	}
	public static void main(String[] args) {
//		String xml ="";
//		String str = "";
//		try {
//			xml = createOrder();
//			str = SimpleHttpClient.invokePostWithBody4String("http://wx.zumuquqi.com/weixin/pay-notify", xml, 10000);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(str);
	}
}
