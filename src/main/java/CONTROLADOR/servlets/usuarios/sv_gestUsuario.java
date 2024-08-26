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
        // Obtener la sesión existente, pero no crear una nueva si no existe
        HttpSession session = request.getSession(false);

        // Verificar si la sesión es nula o si el usuario no ha iniciado sesión
        if (session == null || session.getAttribute("logueado") == null) {
            // Si no hay sesión o el usuario no ha iniciado sesión, redirigir a la página de inicio de sesión con un mensaje de error
            request.setAttribute("error", "Por favor, inicie sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        } else if (!"3".equals(session.getAttribute("rol")) && !"4".equals(session.getAttribute("rol"))) {
            // Verificar si el usuario tiene un rol diferente al de administrador
            // Si el rol no es el de administrador, redirigir con un mensaje de error
            session.setAttribute("error", "Solo se permite el ingreso de administradores");
            response.sendRedirect("sv_documentos");
            return;
        }

        // Obtener los parámetros action, idUser y rol de la solicitud
        String action = request.getParameter("action");
        String idUser = request.getParameter("idUser");
        String tipoUser = request.getParameter("rol");

        // Determinar el rol numérico a partir del tipo de usuario
        int rol = tipoUser.equals("docente") ? 2 : tipoUser.equals("estudiante") ? 1 : 3;

        UsuarioDAO usuarioDAO = new UsuarioDAO(); // Crear una instancia del DAO para manejar usuarios

        // Verificar si la acción solicitada es editar un usuario
        if (action.equals("edit")) {
            // Buscar el usuario por su documento y rol
            List<UsuarioClass> usuarios = usuarioDAO.buscarUsuarioPorDocumento(rol, idUser);

            // Si se encuentra el usuario, establecerlo como atributo de la solicitud
            if (!usuarios.isEmpty()) {
                UsuarioClass usuario = usuarios.get(0);
                request.setAttribute("usuario", usuario);
            }
            // Establecer el tipo de usuario como atributo de la solicitud y redirigir a la vista de edición de usuario
            request.setAttribute("tipoUser", tipoUser);
            request.getRequestDispatcher("vistas/vistas_admin/editar_usuario.jsp").forward(request, response);

        } else {
            // Si no se va a editar, se asume que es para registrar un nuevo usuario
            // Establecer el tipo de usuario como atributo de la solicitud y redirigir a la vista de registro de usuario
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
