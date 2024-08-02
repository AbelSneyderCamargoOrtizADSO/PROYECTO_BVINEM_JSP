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
        
        if (session == null || session.getAttribute("logueado") == null) {
            request.setAttribute("error", "Por favor, inicie sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        } else if (!"3".equals(session.getAttribute("rol")) && !"4".equals(session.getAttribute("rol"))){
            session.setAttribute("error", "Solo se permite el ingreso de administradores");
            response.sendRedirect("sv_documentos");
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String tipoUsuario = request.getParameter("tipoUsuario");
        int rol;
        if (tipoUsuario == null || tipoUsuario.isEmpty()) {
            tipoUsuario = "docente"; // Valor por defecto si no se proporciona el parámetro
        }
        
        if ("estudiante".equals(tipoUsuario)) {
            rol = 1; 
        } else if ("docente".equals(tipoUsuario)){
            rol = 2; 
        } else{
            rol = 3;
        }

        String docUsuario = request.getParameter("docUsuario");

        List<UsuarioClass> usuarios;
        if (docUsuario == null) {
            usuarios = usuarioDAO.listarUsuarios(rol);
        } else {
            usuarios = usuarioDAO.buscarUsuarioPorDocumento(rol, docUsuario);
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
        String tipoUsuario = request.getParameter("tipoUsuario");

        // Acciones: habilitar o inhabilitar usuario
        String actionDel = request.getParameter("actionDel");

        if (actionDel != null) {
            // Determinar el tipo de documento según el tipo de usuario
            String docParam = tipoUsuario.equals("docente") ? "docDocente" : tipoUsuario.equals("estudiante") ? "docEstudiante" : "docAdmin";
            
            int docUsuario = Integer.parseInt(request.getParameter(docParam));
            usuario.setDocUsu(docUsuario);

            try {
                switch (actionDel) {
                    case "habilitarUsu":
                        usuarioDAO.habilitarUsuario(usuario);
                        session.setAttribute("success", tipoUsuario + " habilitado exitosamente");
                        break;
                    case "inhabilitarUsu":
                        usuarioDAO.inhabilitarUsuario(usuario);
                        session.setAttribute("success", tipoUsuario + " inhabilitado correctamente");
                        break;
                }
                response.sendRedirect("sv_usuario?tipoUsuario=" + tipoUsuario);
                return;
            } catch (Exception error) {
                error.printStackTrace();
                response.sendRedirect("sv_usuario?tipoUsuario=" + tipoUsuario);
                return;
            }
        }

        // VALIDACIONES - Validar los datos del formulario usando la clase Validador
        String errorMessage = Validador.validarDocumento(NewdocUsuStr);
        if (errorMessage == null && !(NewdocUsuStr.equals(docUsuStr))) errorMessage = Validador.validarDocumentoEnUso(NewdocUsuStr);
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
            response.sendRedirect(request.getHeader("Referer"));
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
            if (request.getParameter("editDocente") != null || request.getParameter("editEstudiante") != null || request.getParameter("editAdmin") != null) {
                int docUsuario = Integer.parseInt(docUsuStr);
                boolean isDocente = request.getParameter("editDocente") != null;
                usuarioDAO.editarUsuario(docUsuario, usuario, isDocente);
                session.setAttribute("success", "Datos del " + tipoUsuario + " actualizados correctamente");
                response.sendRedirect(request.getHeader("Referer"));
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("Error: " + e.getMessage());
            return;
        }

        // REGISTRAR O AGREGAR USUARIO
        try {
            if (request.getParameter("regDocente") != null || request.getParameter("regEstudiante") != null || request.getParameter("regAdmin") != null) {
                int rol = tipoUsuario.equals("docente") ? 2 : tipoUsuario.equals("estudiante") ? 1 : 3;
                usuario.setRol(rol);
                usuarioDAO.agregarUsuario(usuario);
                if (tipoUsuario.equals("estudiante")) {
                    request.setAttribute("success", "Estudiante registrado exitosamente, ahora puedes iniciar sesión");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } else {
                    session.setAttribute("success", tipoUsuario + " registrado exitosamente");
                    response.sendRedirect("sv_usuario");
                }
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().print("Error: " + e.getMessage());
            return;
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
