package com.bokesoft.thirdparty.weixin.common;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@SuppressWarnings({"unchecked","rawtypes"})
public final class CorsFilter implements Filter {
	private static final Log log = LogFactory.getLog(CorsFilter.class);
//	private static final StringManager sm = StringManager.getManager("org.apache.catalina.filters");
	private final Collection<String> allowedHttpMethods;
	private final Collection<String> allowedHttpHeaders;
	private final Collection<String> exposedHeaders;
	private final Collection<String> allowedOrigins;
	private long preflightMaxAge;
	private boolean anyOriginAllowed;
	private boolean supportsCredentials;
	private boolean decorateRequest;
	
	// request & response
	public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
	public static final String RESPONSE_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	public static final String RESPONSE_HEADER_ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
	public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	
	public static final String REQUEST_HEADER_ORIGIN = "Origin";
	public static final String REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";
	public static final String REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";
	
	// web.param
	public static final String PARAM_CORS_ALLOWED_ORIGINS = "cors.allowed.origins";
	public static final String PARAM_CORS_SUPPORT_CREDENTIALS = "cors.support.credentials";
	public static final String PARAM_CORS_EXPOSED_HEADERS = "cors.exposed.headers";
	public static final String PARAM_CORS_ALLOWED_HEADERS = "cors.allowed.headers";
	public static final String PARAM_CORS_ALLOWED_METHODS = "cors.allowed.methods";
	public static final String PARAM_CORS_PREFLIGHT_MAXAGE = "cors.preflight.maxage";
	public static final String PARAM_CORS_REQUEST_DECORATE = "cors.request.decorate";
	
	// cors.param
	public static final String HTTP_REQUEST_ATTRIBUTE_PREFIX = "cors.";
	public static final String HTTP_REQUEST_ATTRIBUTE_ORIGIN = "cors.request.origin";
	public static final String HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST = "cors.isCorsRequest";
	public static final String HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE = "cors.request.type";
	public static final String HTTP_REQUEST_ATTRIBUTE_REQUEST_HEADERS = "cors.request.headers"; 
	
	// defalut-value
	public static final Collection<String> HTTP_METHODS = new HashSet(
			Arrays.asList(new String[] { "OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "CONNECT" }));
	public static final Collection<String> COMPLEX_HTTP_METHODS = new HashSet(
			Arrays.asList(new String[] { "PUT", "DELETE", "TRACE", "CONNECT" }));
	public static final Collection<String> SIMPLE_HTTP_METHODS = new HashSet(
			Arrays.asList(new String[] { "GET", "POST", "HEAD" }));
	public static final Collection<String> SIMPLE_HTTP_REQUEST_CONTENT_TYPE_VALUES = new HashSet(
			Arrays.asList(new String[] { "application/x-www-form-urlencoded", "application/json", "multipart/form-data", "text/plain" }));
	public static final Collection<String> SIMPLE_HTTP_REQUEST_HEADERS = new HashSet(
			Arrays.asList(new String[] { "Accept", "Accept-Language", "Content-Language", 
			"__sessionstatus", "__sessionid" }));
	public static final Collection<String> SIMPLE_HTTP_RESPONSE_HEADERS = new HashSet(
			Arrays.asList(new String[] { "Cache-Control", "Content-Language", "Content-Type", "Expires", "Last-Modified", "Pragma", 
					"__sessionstatus", "__sessionid"  }));
	
	// default-value
	public static final String DEFAULT_SUPPORTS_CREDENTIALS = "true";
	public static final String DEFAULT_PREFLIGHT_MAXAGE = "1800";
	public static final String DEFAULT_ALLOWED_ORIGINS = "*";
	public static final String DEFAULT_ALLOWED_HTTP_METHODS = "GET,POST,HEAD,OPTIONS";
	public static final String DEFAULT_ALLOWED_HTTP_HEADERS = 
			"Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers"
			+","+
			"__sessionstatus"+","+"__sessionid"; // __sessionid __sessionstatus
	public static final String DEFAULT_EXPOSED_HEADERS = 
			"__sessionstatus"+","+"__sessionid"; // __sessionid __sessionstatus
	public static final String DEFAULT_DECORATE_REQUEST = "true";

	public CorsFilter() {
		this.allowedOrigins = new HashSet();
		this.allowedHttpMethods = new HashSet();
		this.allowedHttpHeaders = new HashSet();
		this.exposedHeaders = new HashSet();
	}
	@Override
	public void destroy() {
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		parseAndStore(
				DEFAULT_ALLOWED_ORIGINS,
				DEFAULT_ALLOWED_HTTP_METHODS,
				DEFAULT_ALLOWED_HTTP_HEADERS,
				DEFAULT_EXPOSED_HEADERS, 
				DEFAULT_SUPPORTS_CREDENTIALS, 
				DEFAULT_PREFLIGHT_MAXAGE, 
				DEFAULT_DECORATE_REQUEST);
		if (filterConfig != null) {
			String configAllowedOrigins = filterConfig.getInitParameter(PARAM_CORS_ALLOWED_ORIGINS);
			String configAllowedHttpMethods = filterConfig.getInitParameter(PARAM_CORS_ALLOWED_METHODS);
			String configAllowedHttpHeaders = filterConfig.getInitParameter(PARAM_CORS_ALLOWED_HEADERS);
			String configExposedHeaders = filterConfig.getInitParameter(PARAM_CORS_EXPOSED_HEADERS);
			String configSupportsCredentials = filterConfig.getInitParameter(PARAM_CORS_SUPPORT_CREDENTIALS);
			String configPreflightMaxAge = filterConfig.getInitParameter(PARAM_CORS_PREFLIGHT_MAXAGE);
			String configDecorateRequest = filterConfig.getInitParameter(PARAM_CORS_REQUEST_DECORATE);
			parseAndStore(configAllowedOrigins, configAllowedHttpMethods,
					configAllowedHttpHeaders, configExposedHeaders,
					configSupportsCredentials, configPreflightMaxAge,
					configDecorateRequest);
		}
	}
	@Override
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		if ((!(servletRequest instanceof HttpServletRequest))
				|| (!(servletResponse instanceof HttpServletResponse))) {
			throw new ServletException("corsFilter.onlyHttp");//sm.getString("corsFilter.onlyHttp"));
		}
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		CORSRequestType requestType = checkRequestType(request);
		if (this.decorateRequest) {
			decorateCORSProperties(request, requestType);
		}
		int ordinal = requestType.ordinal();
		if (ordinal==0||ordinal==4) // by zhouxw 0:get 4:post
			ordinal=5;
		switch (ordinal) {
		case 1:
			handleSimpleCORS(request, response, filterChain);
			break;
		case 2:
			handleSimpleCORS(request, response, filterChain);
			break;
		case 3:
			handlePreflightCORS(request, response, filterChain);
			break;
		case 4:
			handleNonCORS(request, response, filterChain);
			break;
		case 5:
			handleSimpleCORS(request, response, filterChain);
			break;
		default:
			handleInvalidCORS(request, response, filterChain);
		}
	}
	protected void handleSimpleCORS(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
//		CORSRequestType requestType = checkRequestType(request);
//		if ((requestType != CORSRequestType.SIMPLE) && (requestType != CORSRequestType.ACTUAL)) {
////			throw new IllegalArgumentException(sm.getString(
////					"corsFilter.wrongType2", new Object[] { CORSRequestType.SIMPLE, CORSRequestType.ACTUAL });
//			throw new IllegalArgumentException("corsFilter.wrongType2");
//		}
		String origin = request.getHeader(REQUEST_HEADER_ORIGIN);
		if (StringUtils.isEmpty(origin)) { // by zhouxw 20150119
			origin = "*";
		}
		if (!isOriginAllowed(origin)) {
			handleInvalidCORS(request, response, filterChain);
			return;
		}

		String method = request.getMethod();
		if (!this.allowedHttpMethods.contains(method)) {
			handleInvalidCORS(request, response, filterChain);
			return;
		}
		response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS, method);
		
		String accessControlRequestHeadersHeader = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
		List<String> accessControlRequestHeaders = new LinkedList<String>();
		if ((accessControlRequestHeadersHeader != null)
				&& (!accessControlRequestHeadersHeader.trim().isEmpty())) {
			String[] headers = accessControlRequestHeadersHeader.trim().split(",");
			for (String header : headers) {
				accessControlRequestHeaders.add(header.trim().toLowerCase());
			}
		}
		if (!accessControlRequestHeaders.isEmpty()) {
			for (String header : accessControlRequestHeaders) {
				if (!this.allowedHttpHeaders.contains(header)) {
					handleInvalidCORS(request, response, filterChain);
					return;
				}
			}
		}
		
		if (this.supportsCredentials) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		} else if (this.anyOriginAllowed) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		} else {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
		}
		
		if ((this.exposedHeaders != null) && (this.exposedHeaders.size() > 0)) {
			String exposedHeadersString = join(this.exposedHeaders, ",");
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS, exposedHeadersString);
		}
		
		if ((this.allowedHttpHeaders != null) && (!this.allowedHttpHeaders.isEmpty()))
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS, join(this.allowedHttpHeaders, ","));
		
		filterChain.doFilter(request, response);
	}
	protected void handlePreflightCORS(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
//		CORSRequestType requestType = checkRequestType(request);
//		if (requestType != CORSRequestType.PRE_FLIGHT) {
//			filterChain.doFilter(request, response);
//			return;
//			//throw new IllegalArgumentException(sm.getString("corsFilter.wrongType1",
//			//		new Object[] { CORSRequestType.PRE_FLIGHT.name().toLowerCase() }));
//		}
		String origin = request.getHeader(REQUEST_HEADER_ORIGIN);
		if (StringUtils.isEmpty(origin)) { // by zhouxw 20150119
			origin = "*";
		}
		if (!isOriginAllowed(origin)) {
			handleInvalidCORS(request, response, filterChain);
			return;
		}
		
		String accessControlRequestMethod = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD);
		if ((accessControlRequestMethod == null)
				|| (!HTTP_METHODS.contains(accessControlRequestMethod.trim()))) {
			handleInvalidCORS(request, response, filterChain);
			return;
		}
		accessControlRequestMethod = accessControlRequestMethod.trim();
		if (!this.allowedHttpMethods.contains(accessControlRequestMethod)) {
			handleInvalidCORS(request, response, filterChain);
			return;
		}
		response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS, accessControlRequestMethod);

		String accessControlRequestHeadersHeader = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
		List<String> accessControlRequestHeaders = new LinkedList<String>();
		if ((accessControlRequestHeadersHeader != null)
				&& (!accessControlRequestHeadersHeader.trim().isEmpty())) {
			String[] headers = accessControlRequestHeadersHeader.trim().split(",");
			for (String header : headers) {
				accessControlRequestHeaders.add(header.trim().toLowerCase());
			}
		}
		if (!accessControlRequestHeaders.isEmpty()) {
			for (String header : accessControlRequestHeaders) {
				if (!this.allowedHttpHeaders.contains(header)) {
					handleInvalidCORS(request, response, filterChain);
					return;
				}
			}
		}
		
		if (this.preflightMaxAge > 0L) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_MAX_AGE, String.valueOf(this.preflightMaxAge));
		}
		
		if (this.supportsCredentials) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
		} else if (this.anyOriginAllowed) {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		} else {
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
		}
		
		if ((this.exposedHeaders != null) && (this.exposedHeaders.size() > 0)) {
			String exposedHeadersString = join(this.exposedHeaders, ",");
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS, exposedHeadersString);
		}
		
		if ((this.allowedHttpHeaders != null) && (!this.allowedHttpHeaders.isEmpty()))
			response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS, join(this.allowedHttpHeaders, ","));
		
//		filterChain.doFilter(request, response);
	}
	private void handleInvalidCORS(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain) {
		String origin = request.getHeader(REQUEST_HEADER_ORIGIN);
		if (StringUtils.isEmpty(origin)) { // by zhouxw 20150119
			origin = "*";
		}
		String method = request.getMethod();
		String accessControlRequestHeaders = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
		response.setContentType("text/plain");
		response.setStatus(403);
		response.resetBuffer();
		if (log.isDebugEnabled()) {
			StringBuilder message = new StringBuilder("Invalid CORS request; Origin=");
			message.append(origin);
			message.append(";Method=");
			message.append(method);
			if (accessControlRequestHeaders != null) {
				message.append(";Access-Control-Request-Headers=");
				message.append(accessControlRequestHeaders);
			}
			log.debug(message.toString());
		}
	}
	private void handleNonCORS(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		filterChain.doFilter(request, response);
	}
	protected static void decorateCORSProperties(HttpServletRequest request,
			CORSRequestType corsRequestType) {
		if (request == null) {
			throw new IllegalArgumentException("corsFilter.nullRequest");
		}
		if (corsRequestType == null) {
			throw new IllegalArgumentException("corsFilter.nullRequestType");
		}
		String origin = request.getHeader(REQUEST_HEADER_ORIGIN);
		if (StringUtils.isEmpty(origin)) { // by zhouxw 20150119
			origin = "*";
		}
		String headers;
		switch (corsRequestType.ordinal()) {
		case 1:
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST, Boolean.TRUE);
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_ORIGIN, origin);
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE, corsRequestType.name().toLowerCase());
			break;
		case 2:
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST, Boolean.TRUE);
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_ORIGIN, origin);
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE, corsRequestType.name().toLowerCase());
			break;
		case 3:
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST, Boolean.TRUE);
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_ORIGIN, origin);
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE, corsRequestType.name().toLowerCase());
			headers = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
			if (headers == null) {
				headers = "";
			}
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_REQUEST_HEADERS, headers);
			break;
		case 4:
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST, Boolean.FALSE);
			break;
		case 5:
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST, Boolean.TRUE);
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_ORIGIN, origin);
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE, corsRequestType.name().toLowerCase());
			headers = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
			if (headers == null) {
				headers = "";
			}
			request.setAttribute(HTTP_REQUEST_ATTRIBUTE_REQUEST_HEADERS, headers);
			break;
		}
	}
	protected static String join(Collection<String> elements,
			String joinSeparator) {
		String separator = ",";
		if (elements == null) {
			return null;
		}
		if (joinSeparator != null) {
			separator = joinSeparator;
		}
		StringBuilder buffer = new StringBuilder();
		boolean isFirst = true;
		for (String element : elements) {
			if (!isFirst)
				buffer.append(separator);
			else {
				isFirst = false;
			}
			if (element != null) {
				buffer.append(element);
			}
		}
		return buffer.toString();
	}
	protected CORSRequestType checkRequestType(HttpServletRequest request) {
		CORSRequestType requestType = CORSRequestType.INVALID_CORS;
		if (request == null) {
			throw new IllegalArgumentException("corsFilter.nullRequest");
		}
		String origin = request.getHeader(REQUEST_HEADER_ORIGIN);
		if (StringUtils.isEmpty(origin)) { // by zhouxw 20150119
			origin = "*";
		}
		if (origin != null) {
			if (origin.isEmpty()) {
				requestType = CORSRequestType.INVALID_CORS;
			} else if ("*".equals(origin)) {
				requestType = CORSRequestType.GIFTED; // TODO 专用的requestType by zhouxw
			} else if (!isValidOrigin(origin)) {
				requestType = CORSRequestType.ACTUAL;
			} else {
				String method = request.getMethod();
				if ((method != null) && (HTTP_METHODS.contains(method)))
					if ("OPTIONS".equals(method)) {
						String accessControlRequestMethodHeader = request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD);
						if ((accessControlRequestMethodHeader != null) && (!accessControlRequestMethodHeader.isEmpty())) {
							requestType = CORSRequestType.PRE_FLIGHT;
						} else if ((accessControlRequestMethodHeader != null) && (accessControlRequestMethodHeader.isEmpty())) {
							requestType = CORSRequestType.INVALID_CORS;
						} else
							requestType = CORSRequestType.ACTUAL;
					} else if (("GET".equals(method)) || ("HEAD".equals(method))) {
						requestType = CORSRequestType.SIMPLE;
					} else if ("POST".equals(method)) {
						String contentType = request.getContentType();
						if (contentType != null) {
							contentType = contentType.toLowerCase().trim();
							if (SIMPLE_HTTP_REQUEST_CONTENT_TYPE_VALUES.contains(contentType)) {
								requestType = CORSRequestType.SIMPLE;
							} else
								requestType = CORSRequestType.ACTUAL;
						}
					} else if (COMPLEX_HTTP_METHODS.contains(method)) {
						requestType = CORSRequestType.ACTUAL;
					}
			}
		} else {
			requestType = CORSRequestType.NOT_CORS;
		}
		return requestType;
	}
	protected boolean isOriginAllowed(String origin) {
		if (this.anyOriginAllowed) {
			return true;
		}
		return this.allowedOrigins.contains(origin);
	}
	protected void parseAndStore(String allowedOrigins,
			String allowedHttpMethods, String allowedHttpHeaders,
			String exposedHeaders, String supportsCredentials,
			String preflightMaxAge, String decorateRequest)
			throws ServletException {
		if (allowedOrigins != null) {
			if (allowedOrigins.trim().equals("*")) {
				this.anyOriginAllowed = true;
			} else {
				this.anyOriginAllowed = false;
				Set setAllowedOrigins = parseStringToSet(allowedOrigins);
				this.allowedOrigins.clear();
				this.allowedOrigins.addAll(setAllowedOrigins);
			}
		}
		if (allowedHttpMethods != null) {
			Set setAllowedHttpMethods = parseStringToSet(allowedHttpMethods);
			this.allowedHttpMethods.clear();
			this.allowedHttpMethods.addAll(setAllowedHttpMethods);
		}
		if (allowedHttpHeaders != null) {
			Set<String> setAllowedHttpHeaders = parseStringToSet(allowedHttpHeaders);
			Set lowerCaseHeaders = new HashSet();
			for (String header : setAllowedHttpHeaders) {
				String lowerCase = header.toLowerCase();
				lowerCaseHeaders.add(lowerCase);
			}
			this.allowedHttpHeaders.clear();
			this.allowedHttpHeaders.addAll(lowerCaseHeaders);
		}
		if (exposedHeaders != null) {
			Set setExposedHeaders = parseStringToSet(exposedHeaders);
			this.exposedHeaders.clear();
			this.exposedHeaders.addAll(setExposedHeaders);
		}
		if (supportsCredentials != null) {
			this.supportsCredentials = Boolean.parseBoolean(supportsCredentials);
		}
		if (preflightMaxAge != null) {
			try {
				if (!preflightMaxAge.isEmpty())
					this.preflightMaxAge = Long.parseLong(preflightMaxAge);
				else
					this.preflightMaxAge = 0L;
			} catch (NumberFormatException e) {
				throw new ServletException("corsFilter.invalidPreflightMaxAge", e);
			}
		}
		if (decorateRequest != null) {
			this.decorateRequest = Boolean.parseBoolean(decorateRequest);
		}
	}
	private Set<String> parseStringToSet(String data) {
		String[] splits;
		if ((data != null) && (data.length() > 0))
			splits = data.split(",");
		else {
			splits = new String[0];
		}
		Set set = new HashSet();
		if (splits.length > 0) {
			for (String split : splits) {
				set.add(split.trim());
			}
		}
		return set;
	}
	protected static boolean isValidOrigin(String origin) {
		if (origin.contains("%")) {
			return false;
		}
		URI originURI;
		try {
			originURI = new URI(origin);
		} catch (URISyntaxException e) {
			return false;
		}
		return originURI.getScheme() != null;
	}
	public boolean isAnyOriginAllowed() {
		return this.anyOriginAllowed;
	}
	public Collection<String> getExposedHeaders() {
		return this.exposedHeaders;
	}
	public boolean isSupportsCredentials() {
		return this.supportsCredentials;
	}
	public long getPreflightMaxAge() {
		return this.preflightMaxAge;
	}
	public Collection<String> getAllowedOrigins() {
		return this.allowedOrigins;
	}
	public Collection<String> getAllowedHttpMethods() {
		return this.allowedHttpMethods;
	}
	public Collection<String> getAllowedHttpHeaders() {
		return this.allowedHttpHeaders;
	}
	protected static enum CORSRequestType {
		SIMPLE,
		ACTUAL,
		PRE_FLIGHT,
		NOT_CORS,
		INVALID_CORS,
		GIFTED;
	}
}