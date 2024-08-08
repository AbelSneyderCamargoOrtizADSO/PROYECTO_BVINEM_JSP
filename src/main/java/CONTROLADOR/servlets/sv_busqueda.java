/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets;

import MODELO.BusquedaClass;
import MODELO.DocumentoClass;
import MODELO.foros.ForoClass;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Abelito
 */
@WebServlet(name = "sv_busqueda", urlPatterns = {"/sv_busqueda"})
public class sv_busqueda extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("logueado") == null) {
            request.setAttribute("error", "Por favor, inicie sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
        
        String query = request.getParameter("query"); // Envia el texto de busqueda
        String tipo = request.getParameter("tipo"); // Documento o foro

        BusquedaClass busqueda = new BusquedaClass();
        List<DocumentoClass> resultadosLibros = null; // Declaramos la lista para almacenar resultados de los libros
        List<ForoClass> resultadosForos = null;

        if ("libros".equalsIgnoreCase(tipo)) { // Verifica si el valor de la variable "tipo" es igual a "libros" (sin considerar mayúsculas o minúsculas)
            resultadosLibros = busqueda.buscarDocumentos(query); // Asignamos a la variable la lista retornada en el metodo buscarDocumentos de la clase BusquedaClass
        } else if ("foros".equalsIgnoreCase(tipo)) {
            resultadosForos = busqueda.buscarForos(query);
        }

        request.setAttribute("resultadosLibros", resultadosLibros);
        request.setAttribute("resultadosForos", resultadosForos);
        request.setAttribute("tipo", tipo);
        request.getRequestDispatcher("vistas/busqueda.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
