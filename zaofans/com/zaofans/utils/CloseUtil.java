package com.zaofans.utils;

import java.io.Closeable;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;

public class CloseUtil {
//	public static void close(AutoCloseable c) {
//		if (c != null)
//			try {
//				c.close();
//			} catch (Throwable ignore) {
//			}
//	}
	public static final void close(Closeable c) {
		if (c != null)
			try {
				c.close();
			} catch (Throwable ignore) {
			}
	}
	public static final void close(Socket c) {
		if (c != null)
			try {
				c.close();
			} catch (Throwable ignore) {
			}
	}
	public static final void close(Statement c) {
		if (c != null)
			try {
				c.close();
			} catch (Throwable ignore) {
			}
	}
	public static final void close(ResultSet c) {
		if (c != null)
			try {
				c.close();
			} catch (Throwable ignore) {
			}
	}
	public static void close(Connection c) {
		if (c != null)
			try {
				c.close();
			} catch (Throwable ignore) {
			}
	}
	public static void close(Context c) {
		if (c != null)
			try {
				c.close();
			} catch (Throwable ignore) {
			}
	}
}
