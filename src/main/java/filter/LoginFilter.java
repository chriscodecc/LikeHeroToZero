package filter;

import java.io.IOException;

/*
import javax.servlet.http.HttpServletRequest;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import usermanagement.UserManager;

public class LoginFilter implements Filter{

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);
	
	public static final String LOGIN_PAGE = "/login.xhtml";
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
	
		//USERMANAGER NULL
		UserManager userManager = (UserManager) httpServletRequest.getSession().getAttribute("userManager");
		
		if(userManager != null) {
			if(userManager.isLoggedIn()){
				LOGGER.debug("user is logged in");
				// user is logged in, continue request
				filterChain.doFilter(servletRequest, servletResponse);
			} else {
				LOGGER.debug("user is not logged in");
				// user is not logged in, redirect to login page
				 httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
			}
		} else {
			LOGGER.debug("userManager not found");
			// user is not logged in, redirect to login page
		    httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + LOGIN_PAGE);
		}
	}
	
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("loginFilter initialized");
	    LOGGER.debug("loginFilter initialized");
	  }

	 @Override
	  public void destroy() {
	    // close resources
	  }
}
