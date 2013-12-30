package com.youthclub.filter;

import com.youthclub.authentication.Authenticator;
import com.youthclub.authentication.IPAuthenticator;
import com.youthclub.authentication.PasswordAuthenticator;
import com.youthclub.lookup.GlobalDefinition;
import com.youthclub.lookup.LookUp;
import com.youthclub.model.User;
import org.jboss.logging.Logger;
import com.youthclub.path.ApplicationPath;
import com.youthclub.persister.UserPersister;
import com.youthclub.resource.LookUpExtension;
import com.youthclub.resource.ResourceHolder;
import com.youthclub.session.SessionPaths;
import com.youthclub.web.CookieUtil;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * @author Frank
 */
@WebFilter(urlPatterns = ApplicationPath.APPLICATION_PATH + "/*")
public class SessionFilter implements Filter {

    private static final Logger log = Logger.getLogger(SessionFilter.class.getCanonicalName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        final String uri = httpServletRequest.getRequestURI();
        final String ip = servletRequest.getRemoteAddr();
        final String userAgent = httpServletRequest.getHeader("User-Agent");
        final String sessionId = CookieUtil.getValue(httpServletRequest.getCookies(), GlobalDefinition.SESSION_COOKIE_NAME);
        final Authenticator authenticator = createAuthenticator(ip, userAgent, sessionId);

        if (uri.contains(SessionPaths.LOGIN_PATH)) {
            process(servletRequest, servletResponse, filterChain);
        } else if (uri.contains(SessionPaths.STATUS_PATH)) {
            updateExpiry(authenticator);
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (!authenticator.hasLoggedIn()) {
            final ResourceHolder resourceHolder = LookUpExtension.getResourceHolder();
            resourceHolder.removeAuthenticator();
            final HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.sendError(Response.Status.UNAUTHORIZED.getStatusCode());
        } else {
            updateExpiry(authenticator);
            process(servletRequest, servletResponse, filterChain);
        }
    }

    private Authenticator createAuthenticator(String ip, String userAgent, String sessionId) {
        final UserPersister userPersister = LookUpExtension.getPersister(UserPersister.class);
        final User user = userPersister.findByIp(ip);
        Authenticator authenticator = null;
        if (user != null) {
            authenticator = new IPAuthenticator();
            authenticator.setCurrentUser(user);
        } else {
            authenticator = new PasswordAuthenticator();
            authenticator.setParam(PasswordAuthenticator.SESSION_ID, sessionId);
        }
        authenticator.setParam(Authenticator.IP, ip);
        authenticator.setParam(Authenticator.USER_AGENT, userAgent);
        final ResourceHolder resourceHolder = LookUpExtension.getResourceHolder();
        resourceHolder.setAuthenticator(authenticator);

        return authenticator;
    }

    private void updateExpiry(final Authenticator authenticator) {
        if (authenticator == null) {
            return;
        }

        UserTransaction userTransaction = LookUp.getUserTransaction();
        try {
            userTransaction.begin();
            authenticator.updateExpiry();
            userTransaction.commit();
        } catch (Exception ex) {
            log.log(Logger.Level.ERROR, ex.getMessage(), ex);
        }
    }

    private void process(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) {
        final UserTransaction userTransaction = LookUp.getUserTransaction();

        try {
            userTransaction.begin();
        } catch (NotSupportedException | SystemException ex) {
            log.log(Logger.Level.ERROR, ex.getMessage(), ex);
            return;
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (final Throwable ex) {
            try {
                userTransaction.rollback();
            } catch (SystemException e) {
                log.log(Logger.Level.ERROR, e.getMessage(), e);
            }
            throw new RuntimeException(ex);
        }

        try {
            userTransaction.commit();
        } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SystemException ex) {
            log.log(Logger.Level.ERROR, ex.getMessage(), ex);
        }

    }

    @Override
    public void destroy() {
    }
}
