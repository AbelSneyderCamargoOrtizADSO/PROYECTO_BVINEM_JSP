/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package my.proyecto_bvinem.servlets;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Abelito
 */
@WebFilter(filterName = "filter_pag", urlPatterns = {"/vistas/home.jsp"})
public class filter_pag implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Convertir el objeto ServletRequest a HttpServletRequest para usar métodos específicos de HTTP
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Convertir el objeto ServletResponse a HttpServletResponse para usar métodos específicos de HTTP
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Obtener la sesión actual sin crear una nueva si no existe
        HttpSession session = httpRequest.getSession(false);

        // Comprobar si la sesión es nula o si no contiene el atributo "logueado"
        if (session == null || session.getAttribute("logueado") == null) {
            // Si no hay una sesión activa o el usuario no está logueado, redirigir a la página de inicio de sesión
            httpResponse.sendRedirect("../index.jsp");
        } else {
            // Si la sesión está activa y el usuario está logueado, continuar con la solicitud
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        // Método de inicialización del filtro
    }

    public void destroy() {
        // Método de destrucción del filtro
    }
}
