package br.com.ingenium.web.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = { "*.xhtml" })
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String uri = httpRequest.getRequestURI();

        boolean isPublicPage = uri.endsWith("/login.xhtml")
                || uri.endsWith("/index.xhtml")
                || uri.endsWith("/uc/usuario/cadastraUsuario.xhtml")
                || uri.contains("jakarta.faces.resource");

        boolean isAuthenticated = session != null && session.getAttribute("username") != null;

        if (isPublicPage || isAuthenticated) {
            chain.doFilter(request, response);
            return;
        }

        httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.xhtml");
    }

    @Override
    public void destroy() {
    }
}
