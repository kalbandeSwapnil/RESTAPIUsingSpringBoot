package com.springbootrest.filter;

import com.springbootrest.service.AdminUserService;
import com.springbootrest.service.AdminUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by KalbandeSwapnil on 8/31/2018.
 */
@Component
public class AuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger( AuthenticationFilter.class );
    private static boolean authenticated = false;
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_TYPE_BASIC = "Basic";

    @Autowired
    @Qualifier("adminUserServiceImpl")
    private AdminUserService adminUserService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug( "Initiating AuthenticationFilter ... " );
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        String authHeader = ((HttpServletRequest) request).getHeader( AUTH_HEADER );
        StringTokenizer st = new StringTokenizer( authHeader );
        String basic = st.nextToken();

        if(AUTH_TYPE_BASIC.equalsIgnoreCase( basic )) {
            String credentials = new String( new BASE64Decoder().decodeBuffer( st.nextToken() ), "UTF-8" );
            //TODO :: We can create separate authentication manager class which will be responsible for different types of authentication machanism and authenticating user.

            authenticated = adminUserService.authenticateUser( credentials.substring( 0,credentials.indexOf( ":" ) ).trim(),
                                        credentials.substring( credentials.indexOf( ":" ) + 1 ).trim() );
            if (authenticated == true) {
                chain.doFilter( request, response );
            } else {
                ((HttpServletResponse) response).setStatus( HttpServletResponse.SC_UNAUTHORIZED );
            }
        }
        else {
            ((HttpServletResponse) response).setStatus( HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    public void destroy() {
        logger.debug( "Destroying AuthenticationFilter .." );
    }

}
