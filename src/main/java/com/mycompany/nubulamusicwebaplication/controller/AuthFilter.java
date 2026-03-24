package com.mycompany.nubulamusicwebaplication.controller;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author martinbl
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = { "/*" })
public class AuthFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        HttpSession session = req.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("usuario") != null);

        boolean loginRequest = path.contains("iniciar-sesion.jsp") || path.contains("registro.jsp")
                || path.contains("autenticacion");

        boolean resourceStaticRequest = path.contains("/assets/") || path.contains("css") || path.contains("img");

        if (path.startsWith(req.getContextPath() + "/api/")) {
            chain.doFilter(request, response);
            return;
        }

        if (loggedIn || loginRequest || resourceStaticRequest || path.endsWith("tyc.jsp")) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/views/auth/iniciar-sesion.jsp");
        }
    }
}
