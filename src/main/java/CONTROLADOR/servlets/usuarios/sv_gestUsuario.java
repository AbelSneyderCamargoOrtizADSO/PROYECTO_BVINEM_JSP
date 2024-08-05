/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets.usuarios;

import MODELO.usuarios.UsuarioClass;
import MODELO.usuarios.UsuarioDAO;
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
@WebServlet(name = "sv_gestUsuario", urlPatterns = {"/sv_gestUsuario"})
public class sv_gestUsuario extends HttpServlet {

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
        } else if (!"3".equals(session.getAttribute("rol")) && !"4".equals(session.getAttribute("rol"))){
            session.setAttribute("error", "Solo se permite el ingreso de administradores");
            response.sendRedirect("sv_documentos");
            return;
        }
        
        String action = request.getParameter("action");
        String idUser = request.getParameter("idUser");
        String tipoUser = request.getParameter("rol");
        int rol = tipoUser.equals("docente") ? 2 : tipoUser.equals("estudiante") ? 1 : 3;

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (action.equals("edit")) {
            List<UsuarioClass> usuarios = usuarioDAO.buscarUsuarioPorDocumento(rol, idUser);

            if (!usuarios.isEmpty()) {
                UsuarioClass usuario = usuarios.get(0);
                request.setAttribute("usuario", usuario);
            }
            request.setAttribute("tipoUser", tipoUser);
            request.getRequestDispatcher("vistas/vistas_admin/editar_usuario.jsp").forward(request, response);

        } else {
            request.setAttribute("tipoUser", tipoUser);
            request.getRequestDispatcher("vistas/vistas_admin/registrar_usuario.jsp").forward(request, response);
        }
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
