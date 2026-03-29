package com.mycompany.nubulamusicwebaplication.controller;

import com.mycompany.nubulamusicwebaplication.util.JWTUtil;
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
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // 🔑 JWT
        String authHeader = req.getHeader("Authorization");
        boolean tokenValido = false;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                String usuario = JWTUtil.validarToken(token);
                req.setAttribute("usuario", usuario);
                tokenValido = true;
            } catch (Exception e) {
                tokenValido = false;
            }
        }

        // 🌐 SESIÓN (para JSP)
        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("usuario") != null);

        // 📂 TIPOS DE RUTA
        boolean apiRequest = path.contains("/api/");
        boolean loginRequest = path.contains("iniciar-sesion.jsp")
                || path.contains("registro.jsp")
                || path.contains("autenticacion")
                || path.contains("/api/auth");

        boolean resourceStaticRequest = path.contains("/assets/")
                || path.contains("css")
                || path.contains("img");

        boolean tyc = path.endsWith("tyc.jsp");

        // 🟢 1. RUTAS PÚBLICAS
        if (loginRequest || resourceStaticRequest || tyc) {
            chain.doFilter(request, response);
            return;
        }

        // 🔑 2. API → JWT
        if (apiRequest) {
            if (!tokenValido) {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                res.getWriter().write("No autorizado");
                return;
            }

            chain.doFilter(request, response);
            return;
        }

        // 🌐 3. VISTAS → SESIÓN
        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(req.getContextPath() + "/views/auth/iniciar-sesion.jsp");
        }
    }
}
