package com.coffee.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import com.coffee.utils.StringUtils;

@WebFilter("/*")
public class LocaleFilter implements Filter {

	public LocaleFilter() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String locale = request.getParameter("locale");
		if (StringUtils.isNotEmpty(locale)) {
			HttpSession session = getHttpSession(request);
			Config.set(session, Config.FMT_LOCALE, locale);
		}
		chain.doFilter(request, response);
	}

	private HttpSession getHttpSession(ServletRequest request) {
		HttpSession httpSession = getHttpRequest(request).getSession();
		return httpSession;
	}

	private HttpServletRequest getHttpRequest(ServletRequest request) {
		HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
		return httpServletRequest;
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
