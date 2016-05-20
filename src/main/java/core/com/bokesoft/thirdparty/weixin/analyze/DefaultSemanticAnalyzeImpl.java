package com.bokesoft.thirdparty.weixin.analyze;

import org.apache.log4j.Logger;

public class DefaultSemanticAnalyzeImpl implements SemanticAnalyze{
	
	private static final Logger logger = Logger.getLogger(DefaultSemanticAnalyzeImpl.class);

	public SemanticAnalyzeResult analyze(String word) throws Exception{
		if (word == null) {
			return null;
		}
		SemanticAnalyzeResult analyzeResult = null;
		int index = word.indexOf(" ");
		if(index > 0){
			String param = word.substring(index+1);
			String key = word.substring(0, index);
			analyzeResult = new SemanticAnalyzeResult(key, new String[]{param});
		}else if((index = word.indexOf("#")) > 0){
			String param = word.substring(index+1);
			String key = word.substring(0, index);
			analyzeResult = new SemanticAnalyzeResult(key, new String[]{param});
		}else if(word.startsWith("t")||word.startsWith("T")){
			String param = word.length() >9?word.substring(1):null;
			analyzeResult = new SemanticAnalyzeResult("订单查询", new String[]{param});
		}else{
			if (word.startsWith("我要去")) {
				String param = word.length() > 3 ? word.substring(3):null;
				analyzeResult = new SemanticAnalyzeResult("我要去", new String[]{param});
			}else if(word.startsWith("我要找")){
				String param = word.length() > 3 ? word.substring(3):null;
				analyzeResult = new SemanticAnalyzeResult("我要去", new String[]{param});
				
			}else if(word.startsWith("附近的")){
				String param = word.length() > 3 ? word.substring(3):null;
				analyzeResult = new SemanticAnalyzeResult("附近", new String[]{param});
				
			}else if(word.startsWith("附近")){
				String param = word.length() > 3 ? word.substring(2):null;
				analyzeResult = new SemanticAnalyzeResult("附近", new String[]{param});
				
			}else if(word.endsWith("怎么走")){
				String param = word.length() > 3 ? word.substring(0,word.length() - 3):null;
				analyzeResult = new SemanticAnalyzeResult("我要去", new String[]{param});
				
			}else if(word.startsWith("怎么去")){
				String param = word.length() > 3 ? word.substring(3):null;
				analyzeResult = new SemanticAnalyzeResult("我要去", new String[]{param});
			}else{
				logger.info("Analyze >> word:"+word);
				return analyzeResult;
			}
		}
		logger.info("Analyze >> word:"+word+",result:"+analyzeResult.toString());
		return analyzeResult;
	}
	
	public static void main(String[] args) throws Exception {
		DefaultSemanticAnalyzeImpl analyzeImpl = new DefaultSemanticAnalyzeImpl();
		
		SemanticAnalyzeResult result = analyzeImpl.analyze("我要去");
		System.out.println(result);
		
		result = analyzeImpl.analyze("我要去XX");
		System.out.println(result);
		
		result = analyzeImpl.analyze("我要找XX");
		System.out.println(result);
		
		result = analyzeImpl.analyze("附近的XX");
		System.out.println(result);
		
		result = analyzeImpl.analyze("附近XX");
		System.out.println(result);
		
		result = analyzeImpl.analyze("XX怎么走");
		System.out.println(result);
		
		result = analyzeImpl.analyze("怎么去XX");
		System.out.println(result);
		
		result = analyzeImpl.analyze("S 施耐德");
		System.out.println(result);
		
	}
	
	
	
}
