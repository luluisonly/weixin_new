package com.bokesoft.thirdparty.weixin.yigo.extend;

@Deprecated
public class WeixinDingcanUIFunctionImpl{// implements IUIFunction {

//	private TreeMap<String, IUIFunctionImpl> mapFuncImpl = new TreeMap<String, IUIFunctionImpl>();
//
//	public WeixinDingcanUIFunctionImpl() {
//		init();
//	}
//
//	public Object eval(BillViewer ui, String f, Object[] paras) throws Throwable {
//		f = f.toLowerCase();
//		IUIFunctionImpl impl = mapFuncImpl.get(f);
//		if (impl == null) {
//			throw new RuntimeException("unkown function:" + f);
//		}
//		return impl.eval(ui, f, paras);
//	}
//
//	public String[] getNames() {
//		return mapFuncImpl.keySet().toArray(new String[0]);
//	}
//
//	protected void init() {
//		mapFuncImpl.put("saveobject1", new SaveObjectImpl1());
//		
//		mapFuncImpl.put("savemymenu", new SaveMyMenu());
//		
//		mapFuncImpl.put("calcbalance", new CalcBalance());
//	}
//
//	private class SaveMyMenu implements IUIFunctionImpl {
//
//		public Object eval(BillViewer ui, String f, Object[] paras) throws Throwable {
//			BKRowSet rst2 = null;
//			BKRowSet bkRowSet = null;
//			MBillContext context = null;
//			BillViewer billViewer = null;
//			try {
//				int dicID = (Integer) paras[0];
//				int cantingID = (Integer) paras[1];
//				double jiage = (Double) paras[2];
//				int operatorID = ui.getEnv().getOperatorID();
//				IDictionary dic = BKDictionarys.INSTANCE.getDictionaryInstance(ui.getEnv(), "caidan", "",
//						"#id=" + dicID, true);
//				String code = (String) dic.getFieldValue(dicID, "Code");
//				context = new MBillContext(ui.getEnv());
//				DBManager dbManager = context.getDBM();
//				bkRowSet = dbManager.getPrepareResultSet("select id from cp_lcaidan  where code = ? and suoshurenyuan = ? and canting = ?", new Object[]{code,operatorID,cantingID});
//				if(bkRowSet.bkFirst()){
//					return "该喜爱的菜已添加，请勿重复添加！";
//				}else{
//				BillDocument billDocument = new BillDocument(ui.getEnv(), ContextUtil.getMetaTable("Lcaidan"));
//				billViewer = new BillViewer(App.getGlobalViewerMgr(), billDocument);
//				billViewer.setAutoCommit(true);
//				billViewer.doUIOpteration("DicNew");
//				rst2 = billViewer.getBillDoc().getRst(1);
//				rst2.bkInsertRow();
////				String code = (String) dic.getFieldValue(dicID, "Code");
//				rst2.bkUpdateObject("Code", code+"1");
//				rst2.bkUpdateObject("Name", dic.getFieldValue(dicID, "Name"));
//				rst2.bkUpdateObject("suoshurenyuan", operatorID);
//				rst2.bkUpdateObject("Status", 0);
//				rst2.bkUpdateObject("id", dicID);
//				billViewer.doUIOpteration("DicSave");
//				dbManager.executePrepareUpdate("update cp_lcaidan set code = ? , canting = ? ,jiage = ? where id = ?", 
//						new Object[]{code,cantingID,jiage,rst2.bkGetInt("id")});
//				context.setComplete();
//				return "添加成功";
//				}
//				
//			} finally{
//				if (rst2 != null) {
//					rst2.close();
//				}
//				if (bkRowSet != null) {
//					bkRowSet.close();
//				}
//				if (billViewer != null) {
//					CloseUtil.close(billViewer);
//				}
//				if (context != null) {
//					CloseUtil.close(context);
//				}
//			}
//		}
//		
//	}
//	
//	
//	private class CalcBalance implements IUIFunctionImpl {
//
//		public Object eval(BillViewer ui, String f, Object[] paras) throws Throwable {
//			BKRowSet bkRowSet = ui.getBillDoc().getRst(1);
//			BillFormGrid2 grid = ui.getBillForm().getGrid(0);
//			Env env = MAP_C_ENV.createEnv(MAP_C_ENV.getDefaultDSN());
//			MBillContext context = new MBillContext(env);
//			DBManager manager = context.getDBM();
//			BKRowSet rst = null;
//			boolean checkContinue = true;
//			try{
//			for (int i = 0; i < grid.getRowCount(); i++) {
//				if(checkContinue){
//					Object v = grid.getCellValue(i, 0);
//					if (v == null || "订餐人".equals(v)) {
//						continue;
//					}else{
//						checkContinue = false;
//					}
//				}
//				BillFormRow2 row = grid.getRowByIndex(i);
//				int pos = row.bookmark.getI();
//				bkRowSet.bkAbsolute(pos);
//				int id = bkRowSet.bkGetInt("dingcanren");
//				int caidan = bkRowSet.bkGetInt("caidan");
//				double jiage = bkRowSet.bkGetDouble("jiage");
////				double yue = Double.parseDouble(ui.getBillForm().getGrid(0).getCell(i,3).getUnitData().getData().toString());
//				rst =  context.getDBM().getPrepareResultSet("select yue from gp_operator  where id = ?", new Object[]{id});
//				rst.bkFirst();
//				double yue = rst.bkGetInt("yue");
//				double shifujine = Double.parseDouble(ui.getBillForm().getGrid(0).getCell(i,4).getUnitData().getData().toString());
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式
//				double newyue=yue+shifujine-jiage;
//				manager.executePrepareUpdate("insert into scm_recordyue values (?,?,?,?,?,?) ", new Object[]{id,caidan,jiage,yue,shifujine,df.format(new Date())});
//				manager.executePrepareUpdate("update gp_operator set yue = ? where id = ? ", new Object[]{newyue,id});
//				manager.setComplete();
//			}
//			return true;
//			}finally{
//				if (context != null) {
//					CloseUtil.close(context);
//				}
//				if (bkRowSet != null) {
//					CloseUtil.close(bkRowSet);
//				}
//				if (rst != null) {
//					CloseUtil.close(rst);
//				}
//			}
//		}
//	}
//	
//
//	private class SaveObjectImpl1 implements IUIFunctionImpl {
//
//		public Object eval(BillViewer ui, String f, Object[] paras) throws Throwable {
//			BillMidLib.MBillContext_MidSubOperation(ui.getBillDoc().saveToStr(), "SaveObject", ui
//					.getBillDoc().getEnv());
//			return true;
//		}
//
//	}

}
