package com.bokesoft.thirdparty.weixin.session;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.bokesoft.thirdparty.weixin.Initable;
import com.bokesoft.thirdparty.weixin.WeixinContext;

/**
 * session管理
 *
 */
public class LocalWeixinSessionFactory implements Job ,WeixinSessionFactory ,Initable {
	
	private static SchedulerFactory factory = new StdSchedulerFactory();
	
	private static final Logger logger = Logger.getLogger(LocalWeixinSessionFactory.class);

	private static Scheduler scheduler = null;
	
	private HashMap<String, LocalWeixinSession> sessions = new HashMap<String, LocalWeixinSession>();

	/**
	 * 根据openid和uname获取session
	 * @param openid
	 * @param uname
	 * @return
	 * @throws Exception
	 */
	public LocalWeixinSession getWeixinSession(String openid,String uname) throws Exception {
		String sessionId = uname+openid;
		LocalWeixinSession session = sessions.get(sessionId);
		if (session == null) {
			return null;
		}else{
			session.active();
		}
		return session;
	}
	
	public LocalWeixinSession addWeixinSession(String openid,String uname) throws Exception {
		String sessionId = uname+openid;
		LocalWeixinSession session = new LocalWeixinSession(openid,uname,sessionId);
		synchronized (sessions) {
			sessions.put(sessionId, session);
		}
		return session;
	}
	
	public void initialize(WeixinContext context) throws Exception {
		this.startManagerSession();
	}

	public void shoutdown(WeixinContext context) throws Exception {
		this.cancelManagerSession();
	}

	/**
	 * 取消session管理任务
	 * @throws SchedulerException
	 */
	public synchronized void cancelManagerSession() throws SchedulerException {
		scheduler.shutdown();
	}
	
	/**
	 * 开始管理session任务
	 * @throws SchedulerException
	 */
	public synchronized void startManagerSession() throws SchedulerException {
		
		final String JOB = "SESSION";
		final String GROUP = "COM.BOKESOFT.THIRDPARTY.WEIXIN";
		final String TRIGGER = "trigger";
		
		scheduler = factory.getScheduler();
		// @NOTICE 任务的开始时间，nextGivenSecondDate方法表示：当前时间之后，
		// 每当秒数是13的倍数都是触发时间，当然只触发一次
		// 比如：00:00:12秒开始主线程，则13秒就会触发任务，
		// 如果00:00:14秒开始主线程，则在26秒触发任务
		
		//String ONE_MIN_CLEAR_ONCE = "0 0/1 * * * ?";
		String FIVE_MIN_CLEAR_ONCE = "0 0/5 * * * ?";
		Date runTime = DateBuilder.nextGivenSecondDate(new Date(), 1);
		JobDetail job = JobBuilder.newJob(LocalWeixinSessionFactory.class).withIdentity(JOB, GROUP)
				.build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER, GROUP)
//					0 0/30 * * * ?  --------  0/1 * * * * ?
				.withSchedule(CronScheduleBuilder.cronSchedule(FIVE_MIN_CLEAR_ONCE)).startAt(runTime)
				.build();
		scheduler.scheduleJob(job, trigger);
		scheduler.start();
	}
	
	/**
	 * 停掉管理session任务
	 * @throws SchedulerException
	 */
	public synchronized void stopManagerSession() throws SchedulerException {
		scheduler.clear();
	}
	
	public void updateWeixinSession(WeixinSession session) throws Exception {
	}

	private int lastSize = 0;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Map<String, LocalWeixinSession> sessions = this.sessions;
		Collection<LocalWeixinSession> sessionCollection = sessions.values();
		LocalWeixinSession[] sessionArray = sessionCollection.toArray(new LocalWeixinSession[0]);
		for (LocalWeixinSession session : sessionArray) {
			if (!session.isValid()) {
				synchronized (sessions) {
					sessions.remove(session.getId());
//					session.remove();
				}
			}
		}
		if (sessions.size() != lastSize) {
			logger.info("Clear weixin session ,size:"+sessions.size());
		}
	}

	
//	public void execute(JobExecutionContext context) throws JobExecutionException {
//		Collection<HashMap<String, WeixinSession>> sessionCollection = sessions.values();
//		for(HashMap<String, WeixinSession> sessHashMap:sessionCollection){
//			Collection<WeixinSession> sessionCollection1 = sessHashMap.values();
//			WeixinSession[] sessionArray = sessionCollection1.toArray(new WeixinSession[0]);
//			for (WeixinSession session : sessionArray) {
//				if (!session.isValid()) {
//					synchronized (sessions) {
//						sessions.get(session.getUname()).remove(session.getSessionId());
//						session.remove();
//					}
//				}
//			}
//		}
//	}
//
//	private HashMap<String, HashMap<String, WeixinSession>> sessions = new HashMap<String, HashMap<String,WeixinSession>>();
//
//	WeixinSession getWeixinSession(String sessionId,String uname) {
//		HashMap<String, WeixinSession> sessionMap = sessions.get(uname);
//		if (sessionMap == null) {
//			synchronized (sessions) {
//				sessionMap = sessions.get(uname);
//				if(sessionMap == null){
//					sessionMap = new HashMap<String, WeixinSession>();
//					sessions.put(uname,sessionMap);
//				}
//			}
//			WeixinSession session = new WeixinSessionImpl(sessionId,uname);
//			sessionMap.put(sessionId, session);
//			return session;
//		}
//		WeixinSession session = sessionMap.get(sessionId);
//		if (session == null) {
//			session = new WeixinSessionImpl(sessionId,uname);
//			sessionMap.put(sessionId, session);
//		}else{
//			synchronized (sessions) {
//				if (!session.removed()) {
//					session.active();
//					return session;
//				}
//				session = new WeixinSessionImpl(sessionId,uname);
//				sessionMap.put(sessionId, session);
//				return session;
//			}
//			
//		}
//		return session;
//	}
	
}