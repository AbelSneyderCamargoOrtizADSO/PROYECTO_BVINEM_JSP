/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets.usuarios;

import MODELO.usuarios.UsuarioDAO;
import MODELO.usuarios.UsuarioClass;
import MODELO.usuarios.Validador;
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
@WebServlet(name = "sv_usuario", urlPatterns = {"/sv_usuario"})
public class sv_usuario extends HttpServlet {

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
        HttpSession session = request.getSession();

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String tipoUsuario = request.getParameter("tipoUsuario");
        if (tipoUsuario == null || tipoUsuario.isEmpty()) {
            tipoUsuario = "docente"; // Valor por defecto si no se proporciona el parámetro
        }

        String docUsuario = request.getParameter("docUsuario");

        List<UsuarioClass> usuarios;
        if (docUsuario == null) {
            usuarios = usuarioDAO.listarUsuarios("tb_" + tipoUsuario);
        } else {
            usuarios = usuarioDAO.buscarUsuarioPorDocumento("tb_" + tipoUsuario, docUsuario);
        }

        request.setAttribute("usuarios", usuarios);
        request.setAttribute("tipoUsuario", tipoUsuario);

        request.getRequestDispatcher("vistas/vistas_admin/gest_usuarios.jsp").forward(request, response);
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

        if (session == null || session.getAttribute("logueado") == null) {
            request.setAttribute("error", "Sesión expirada. Por favor, vuelva a iniciar sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Instanciamos las clases
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioClass usuario = new UsuarioClass();

        // VARIABLES
        String docUsuStr = request.getParameter("userId");
        String NewdocUsuStr = request.getParameter("nuevoDoc");
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        String correo = request.getParameter("correo");
        String pass = request.getParameter("password");

        // HABILITAR USUARIO
        if (request.getParameter("habilitarUsu") != null) {
            String tipoUsuario = request.getParameter("tipoUsuario");
            int docUsuario = Integer.parseInt(request.getParameter(tipoUsuario.equals("docente") ? "docDocente" : "docEstudiante"));
            usuario.setDocUsu(docUsuario);
            try {
                usuarioDAO.habilitarUsuario(usuario);
                session.setAttribute("success", tipoUsuario + " habilitado exitosamente");
                response.sendRedirect("sv_usuario?tipoUsuario=" + tipoUsuario);
                return; // Salir del método después de habilitar
            } catch (Exception error) {
                error.printStackTrace();
                response.sendRedirect("sv_usuario?tipoUsuario=" + tipoUsuario);
                return;
            }
        }

        // INHABILITAR USUARIO
        if (request.getParameter("inhabilitarUsu") != null) {
            String tipoUsuario = request.getParameter("tipoUsuario");
            int docUsuario = Integer.parseInt(request.getParameter(tipoUsuario.equals("docente") ? "docDocente" : "docEstudiante"));
            usuario.setDocUsu(docUsuario);
            try {
                usuarioDAO.inhabilitarUsuario(usuario);
                session.setAttribute("success", tipoUsuario + " inhabilitado correctamente");
                response.sendRedirect("sv_usuario?tipoUsuario=" + tipoUsuario);
                return;
            } catch (Exception error) {
                error.printStackTrace();
                response.getWriter().print("Error: " + error.getMessage());
                return;
            }
        }

        // VALIDACIONES - Validar los datos del formulario usando la clase Validador
        String errorMessage = Validador.validarDocumento(NewdocUsuStr);
        if (errorMessage == null) errorMessage = Validador.validarNombre(nombres);
        if (errorMessage == null) errorMessage = Validador.validarApellido(apellidos);       
        if (errorMessage == null) errorMessage = Validador.validarCorreo(correo);        
        if (errorMessage == null && (pass != null && !pass.isEmpty())) errorMessage = Validador.validarContrasena(pass);

        int docUsu = Integer.parseInt(NewdocUsuStr);
        int docUsuOriginal = docUsuStr != null && !docUsuStr.isEmpty() ? Integer.parseInt(docUsuStr) : docUsu;
        if (errorMessage == null) {
            errorMessage = Validador.validarCorreoEnUso(correo, docUsuOriginal);
        }

        if (errorMessage != null) {
            session.setAttribute("error", errorMessage);
            response.sendRedirect("sv_usuario");
            return;
        }

        // Asignamos los setters a la clase Usuario
        usuario.setDocUsu(docUsu);
        usuario.setNombre(nombres);
        usuario.setApellido(apellidos);
        usuario.setCorreo(correo);
        if (pass != null && !pass.isEmpty()) {
            usuario.setPass(pass);
        } else {
            usuario.setPass(null); // Indicar que no se actualiza la contraseña
        }

        // EDITAR USUARIO
        try {
            if (request.getParameter("editDocente") != null) {
                int docDocente = Integer.parseInt(docUsuStr);
                usuarioDAO.editarUsuario(docDocente, usuario, "tb_docente", true);
                session.setAttribute("success", "Datos del docente actualizados correctamente");
                response.sendRedirect("sv_usuario?tipoUsuario=docente");
                return;
            } else if (request.getParameter("editEstudiante") != null){
                int docEstudiante = Integer.parseInt(docUsuStr);
                usuarioDAO.editarUsuario(docEstudiante, usuario, "tb_estudiante", false);
                session.setAttribute("success", "Datos del estudiante actualizados correctamente");
                response.sendRedirect("sv_usuario?tipoUsuario=estudiante");
                return;
            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Código de error para clave duplicada en MySQL
                session.setAttribute("error", "El documento de usuario ya está en uso.");
                response.sendRedirect("sv_usuario");
                return;
            } else {
                e.printStackTrace();
                return;
            }
        }
        

        // REGISTRAR O AGREGAR USUARIO
        try {
            if (request.getParameter("regDocente") != null) {
                // Configurar el rol de docente (asumimos que es 2)
                usuario.setRol(2);
                usuarioDAO.agregarUsuario(usuario, "tb_docente");
                session.setAttribute("success", "Docente registrado exitosamente");
                response.sendRedirect("sv_usuario");
            }
//            else if (request.getParameter("regEstudiante") != null) {
//                // Configurar el rol de estudiante (asumimos que es 3)
//                usuario.setRol(3);
//                usuarioDAO.agregarUsuario(usuario, "tb_estudiante");
//                session.setAttribute("success", "Estudiante registrado exitosamente");
//                response.sendRedirect("sv_usuario");
//
//            }
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // Código de error para clave duplicada en MySQL
                session.setAttribute("error", "El documento de usuario ya está en uso.");
                response.sendRedirect("sv_usuario");
                return;

            } else {
                e.printStackTrace();
                response.getWriter().print("Error: " + e.getMessage());
                return;
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
