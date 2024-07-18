/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets.usuarios;

import MODELO.usuarios.DocenteDAO;
import MODELO.usuarios.UsuarioClass;
import java.io.IOException;
import java.sql.SQLException;
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
@WebServlet(name = "sv_docente", urlPatterns = {"/sv_docente"})
public class sv_docente extends HttpServlet {

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
        DocenteDAO docenteDAO = new DocenteDAO();

        String docDocente = request.getParameter("docDocente");

        List<UsuarioClass> docentes;
        if (docDocente == null) {
            docentes = docenteDAO.listarDocentes();
        } else {
            docentes = docenteDAO.buscarDocentePorDocumento(docDocente);
        }

        request.setAttribute("docentes", docentes);

        request.getRequestDispatcher("vistas/vistas_admin/gest_docentes.jsp").forward(request, response);
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

        DocenteDAO docentedao = new DocenteDAO();

        // VARIABLES
        String docDocenteStr = request.getParameter("docenteId");
        String nuevoDocDocenteStr = request.getParameter("nuevoDocenteId");
        String nombre = request.getParameter("nombres");
        String apellido = request.getParameter("apellidos");
        String correo = request.getParameter("correo");
        String pass = request.getParameter("password");

        // REGISTRAR O AGREGAR DOCENTE
        if (request.getParameter("regDocente") != null) {

            if (nuevoDocDocenteStr == null || nuevoDocDocenteStr.trim().isEmpty() || nuevoDocDocenteStr.equals("0")) {
                session.setAttribute("error", "El documento del docente no puede estar vacío");
                response.sendRedirect("sv_docente");
                return;
            }

            if (nombre == null || nombre.trim().isEmpty()) {
                session.setAttribute("error", "El nombre no puede estar vacío");
                response.sendRedirect("sv_docente");
                return;
            }

            if (apellido == null || apellido.trim().isEmpty()) {
                session.setAttribute("error", "El apellido no puede estar vacío");
                response.sendRedirect("sv_docente");
                return;
            }

            if (correo == null || correo.trim().isEmpty()) {
                session.setAttribute("error", "El correo no puede estar vacío");
                response.sendRedirect("sv_docente");
                return;
            }

            String emailRegex = "^[a-zA-Z0-9._%+-]{3,}@[a-zA-Z0-9.-]{2,}\\.[a-zA-Z]{2,5}$";
            if (!correo.matches(emailRegex)) {
                session.setAttribute("error", "El correo no es válido");
                response.sendRedirect("sv_docente");
                return;
            }

            if (pass == null || pass.trim().isEmpty()) {
                session.setAttribute("error", "La contraseña no puede estar vacía");
                response.sendRedirect("sv_docente");
                return;
            }

            try {
                int nuevoDocDocente = Integer.parseInt(nuevoDocDocenteStr);

                docentedao.agregarDocente(nuevoDocDocente, nombre, apellido, correo, pass);
                session.setAttribute("success", "Docente registrado correctamente");
                response.sendRedirect("sv_docente");
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().print("Error: " + e.getMessage());
            }
        }

        // EDITAR DOCENTE
        if (request.getParameter("editDocente") != null) {

            if (nuevoDocDocenteStr == null || nuevoDocDocenteStr.trim().isEmpty() || nuevoDocDocenteStr.equals("0")) {
                session.setAttribute("error", "El documento del docente no puede estar vacío");
                response.sendRedirect("sv_docente");
                return;
            }

            if (nombre == null || nombre.trim().isEmpty()) {
                session.setAttribute("error", "El nombre no puede estar vacío");
                response.sendRedirect("sv_docente");
                return;
            }

            if (apellido == null || apellido.trim().isEmpty()) {
                session.setAttribute("error", "El apellido no puede estar vacío");
                response.sendRedirect("sv_docente");
                return;
            }

            if (correo == null || correo.trim().isEmpty()) {
                session.setAttribute("error", "El correo no puede estar vacío");
                response.sendRedirect("sv_docente");
                return;
            }

            String emailRegex = "^[a-zA-Z0-9._%+-]{3,}@[a-zA-Z0-9.-]{2,}\\.[a-zA-Z]{2,5}$";
            if (!correo.matches(emailRegex)) {
                session.setAttribute("error", "El correo no es válido");
                response.sendRedirect("sv_docente");
                return;
            }

            if (pass == null || pass.trim().isEmpty()) {
                session.setAttribute("error", "La contraseña no puede estar vacía");
                response.sendRedirect("sv_docente");
                return;
            }

            try {
                int nuevoDocDocente = Integer.parseInt(nuevoDocDocenteStr);
                int docDocente = Integer.parseInt(docDocenteStr);
                docentedao.editarDocente(docDocente, nuevoDocDocente, nombre, apellido, correo, pass);
                session.setAttribute("success", "Datos del docente actualizados correctamente");
                response.sendRedirect("sv_docente");
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().print("Error: " + e.getMessage());
            }
        }

        // ELIMINAR DOCENTE
        if (request.getParameter("eliminarDocente") != null) {
            int docDocente = Integer.parseInt(request.getParameter("docDocente"));

            try {
                docentedao.eliminarDocente(docDocente);
                session.setAttribute("success", "Docente eliminado correctamente");

                response.sendRedirect("sv_docente");
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
