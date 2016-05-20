/*
 * Created on 2007-4-6
 *
 */
package yigo.soa.weixin.mid.dispatch;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.bokesoft.myerp.common.DebugUtil;
import com.bokesoft.myerp.common.io.CloseUtil;

/**
 * 对象清理器
 * 
 * @author zhouxw
 * 
 */
public class Clear {
	//
	// /**
	// * Comment for <code>blobList</code> 数据库2进制大对象, 监听器列表
	// */
	// ArrayList<BLOB> blobList = new ArrayList<BLOB>();
	//
	/**
	 * 数据流对象, 监听器列表
	 */
	ArrayList<InputStream> insList = new ArrayList<InputStream>();

	/**
	 * 数据库连接对象
	 */
	ArrayList<Connection> connList = new ArrayList<Connection>();

	/**
	 * 数据库状态对象
	 */
	ArrayList<Statement> statList = new ArrayList<Statement>();

	/**
	 * 数据库数据集对象
	 */
	ArrayList<ResultSet> restList = new ArrayList<ResultSet>();

//	/**
//	 * @param context
//	 */
//	public static volatile long CONN_COUNT = 0;

	/**
	 * @param conn
	 */
	public void addConn(Connection conn) {
		connList.add(conn);
//		DebugUtil.debug(">1.db count:" + (++CONN_COUNT));
	}

	/**
	 * @param stat
	 */
	public void addStat(Statement stat) {
		statList.add(stat);
	}

	/**
	 * @param rest
	 */
	public void addRst(ResultSet rest) {
		restList.add(rest);
	}

	/**
	 * @param ins
	 */
	public void addInputStream(InputStream ins) {
		insList.add(ins);
	}

	// /**
	// * @param blob
	// */
	// public void addBlob(oracle.sql.BLOB blob) {
	// blobList.add(blob);
	// }

	// /**
	// * @param blob
	// */
	// public void addBlob(java.sql.Blob blob) {
	// blobList.add(blob);
	// }

	public void cleanState() throws Throwable {
		// for (int i = 0; i < blobList.size(); i++) {
		// oracle.sql.BLOB blob = (oracle.sql.BLOB) blobList.get(i);
		// try {
		// DebugUtil.debug(">清除器关闭BLOB:" + blob);
		// blob.freeTemporary();
		// } catch (Throwable ex) {
		// ex.printStackTrace();
		// }
		// }
		// blobList.clear();

		for (int i = 0; i < insList.size(); i++) {
			InputStream ins = (InputStream) insList.get(i);
			CloseUtil.close(ins);
		}
		insList.clear();
		
		for (int i = 0; i < restList.size(); i++) {
			ResultSet rest = (ResultSet) restList.get(i);
			try {
				if (rest != null) {
					Statement stat = rest.getStatement();
					if (stat != null && !stat.getConnection().isClosed()) {
						if (!stat.getConnection().getAutoCommit()) {
							stat.getConnection().rollback();
							stat.getConnection().setAutoCommit(true);
						}
						rest.close();
					}
				}
				rest = null;
			} catch (Throwable ignore) {
				//ignore.printStackTrace();
			}
		}
		restList.clear();
		
		for (int i = 0; i < statList.size(); i++) {
			Statement stat = (Statement) statList.get(i);
			try {
				if (stat != null)
					if (!stat.getConnection().isClosed()) {
						if (!stat.getConnection().getAutoCommit()) {
							stat.getConnection().rollback();
							stat.getConnection().setAutoCommit(true);
						}
						stat.close();
					}
				stat = null;
			} catch (Throwable ignore) {
				//ignore.printStackTrace();
			}
		}
		statList.clear();
	}
	
	public void cleanConn() { // 自动清理
		Connection conn;
		for (int i = 0; i < connList.size(); i++) {
			conn = (Connection) connList.get(i);
			if (conn == null)
				continue;
			closeConnection(conn);
		}
		connList.clear();
	}
	
	public static void closeConnection(Connection conn) {
		if (conn==null)
			return;
		try {
			if (!conn.isClosed() && !conn.getAutoCommit()) {
				conn.rollback();
				DebugUtil.debug(">db rollback");
				conn.setAutoCommit(true);
			}
		} catch (Throwable ignore) {
			//ignore.printStackTrace(); // ignore
		}
		try {
			if (!conn.isClosed()) {
				DebugUtil.debug(">connection closing:" + conn.hashCode());
				conn.close();
			}
		} catch (Throwable ignore) {
			//ignore.printStackTrace(); // ignore
		}
	}

	/**
	 * @throws Throwable
	 */
	public void clean() throws Throwable {
		cleanState();
		cleanConn();
		// DebugUtil.debug(">clear.clean()");
		// 这里做gc太频繁了
		// System.gc();
	}

}
