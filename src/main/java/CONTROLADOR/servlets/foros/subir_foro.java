/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets.foros;

import MODELO.categorias.AsignaturaClass;
import MODELO.FormDoc;
import MODELO.categorias.IdiomaClass;
import MODELO.foros.FormForo;
import MODELO.foros.ForoClass;
import MODELO.foros.ForoDAO;
import MODELO.foros.TipoForoClass;
import MODELO.usuarios.Validador;
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

        ForoDAO forodao = new ForoDAO();
        ForoClass foro = new ForoClass();

        // VARIABLES
        String dni = (String) session.getAttribute("UserDoc"); // Recuperar y castear a int el documento del usario
        int UserDoc = Integer.parseInt((String) dni);
        String tit = request.getParameter("titulo");
        String descrip = request.getParameter("descripcion");
        String asig = request.getParameter("asignatura");
        String idioma = request.getParameter("idioma");
        String tipo = request.getParameter("tipof");
        
        // VALIDACIONES
        String errorMessage = Validador.validarTitulo(tit);
        if (errorMessage == null) errorMessage = Validador.validarDescripcion(descrip);
        if (errorMessage == null) errorMessage = Validador.validarAsignatura(asig);
        if (errorMessage == null) errorMessage = Validador.validarIdioma(idioma);
        if (errorMessage == null) errorMessage = Validador.validarTipoForo(tipo);

        if (errorMessage != null) {
            session.setAttribute("error", errorMessage);
            response.sendRedirect("subir_foro");
            return;
        }

        // ASIGNAMOS LOS SETTERS
        foro.setUsuarioDoc(UserDoc);
        foro.setTitulo(tit);
        foro.setDescripcion(descrip);
        foro.setAsignaturaId(Integer.parseInt(asig));
        foro.setIdiomaId(Integer.parseInt(idioma));
        foro.setTipoId(Integer.parseInt(tipo));

        if (request.getParameter("enviar") != null) {
            try {
                forodao.subirForo(foro);
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
