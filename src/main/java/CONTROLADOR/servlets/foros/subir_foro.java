/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets.foros;

import MODELO.AsignaturaClass;
import MODELO.FormDoc;
import MODELO.IdiomaClass;
import MODELO.foros.FormForo;
import MODELO.foros.ForoDAO;
import MODELO.foros.TipoForoClass;
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
@WebServlet(name = "subir_foro", urlPatterns = {"/subir_foro"})
public class subir_foro extends HttpServlet {

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
        FormDoc formDoc = new FormDoc();
        FormForo formForo = new FormForo();

        List<AsignaturaClass> asignaturas = formDoc.obtenerAsignaturas();
        List<IdiomaClass> idiomas = formDoc.obtenerIdiomas();
        List<TipoForoClass> tiposforo = formForo.obtenerTiposForo();

        request.setAttribute("asignaturas", asignaturas);
        request.setAttribute("idiomas", idiomas);
        request.setAttribute("tiposforo", tiposforo);

        request.getRequestDispatcher("vistas/subirforo.jsp").forward(request, response);

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {
            request.setAttribute("error", "Sesión expirada. Por favor, vuelva a iniciar sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Recuperar y castear a int el documento del usario
        String dni = (String) session.getAttribute("UserDoc");
        int UserDoc = Integer.parseInt((String) dni);

        if (request.getParameter("enviar") != null) {
            // TRAEMOS O TOMAMOS LOS DATOS INGRESADOS MEDIANTE getParameter TOMANDO COMO REFERENCIA EL NAME DE CADA INPUT
            String tit = request.getParameter("titulo");
            String descrip = request.getParameter("descripcion");
            String asig = request.getParameter("asignatura");
            String idioma = request.getParameter("idioma");
            String tipo = request.getParameter("tipof");

            if (tit == null || tit.trim().isEmpty()) {
                session.setAttribute("error", "El título no puede estar vacío");
                response.sendRedirect("subir_foro");
                return;
            }
            
            if (tit.length() > 50) {
                session.setAttribute("error", "El título debe tener maximo 50 caracteres");
                response.sendRedirect("subir_foro");
                return;
            }

            if (descrip == null || descrip.trim().isEmpty() || descrip.equals("<p><br></p>")) {
                session.setAttribute("error", "La descripción no puede estar vacía");
                response.sendRedirect("subir_foro");
                return;
            }

            if (asig == null || asig.trim().isEmpty()) {
                session.setAttribute("error", "Por favor, seleccione la asignatura");
                response.sendRedirect("subir_foro");
                return;
            }

            if (idioma == null || idioma.trim().isEmpty()) {
                session.setAttribute("error", "Por favor, seleccione el idioma");
                response.sendRedirect("subir_foro");
                return;
            }

            if (tipo == null || tipo.trim().isEmpty()) {
                session.setAttribute("error", "Por favor, seleccione el tipo de foro");
                response.sendRedirect("subir_foro");
                return;
            }

            ForoDAO forodao = new ForoDAO();

            try {
                forodao.SubirForo(tit, descrip, UserDoc, asig, idioma, tipo);
                response.sendRedirect("sv_foros");
            } catch (Exception error) {
                error.printStackTrace();
                response.getWriter().print("Error: " + error.getMessage());
            }

        }

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
