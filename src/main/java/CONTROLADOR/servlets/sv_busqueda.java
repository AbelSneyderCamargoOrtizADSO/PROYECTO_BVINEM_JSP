/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets;

import MODELO.BusquedaClass;
import MODELO.DocumentoClass;
import MODELO.foros.ForoClass;
import com.google.gson.Gson;
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
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("logueado") == null) {
            request.setAttribute("error", "Por favor, inicie sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        String query = request.getParameter("query");
        String tipo = request.getParameter("tipo");

        BusquedaClass busqueda = new BusquedaClass();
        List<DocumentoClass> resultadosLibros = null;
        List<ForoClass> resultadosForos = null;

        if ("libros".equalsIgnoreCase(tipo)) {
            resultadosLibros = busqueda.buscarDocumentos(query);
        } else if ("foros".equalsIgnoreCase(tipo)) {
            resultadosForos = busqueda.buscarForos(query);
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if ("libros".equalsIgnoreCase(tipo)) {
            out.write(new Gson().toJson(resultadosLibros));  // Usar Gson para convertir la lista a JSON
        } else if ("foros".equalsIgnoreCase(tipo)) {
            out.write(new Gson().toJson(resultadosForos)); // Convierte la lista resultadosLibros a una cadena JSON usando la biblioteca Gson y luego escribe esa cadena en la respuesta HTTP.
        }
        out.flush(); // Asegurar que todo el contenido escrito sea enviado al cliente
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
