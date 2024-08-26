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
        HttpSession session = request.getSession(); // Obtener la sesión actual

        // Verificar si la sesión es nula o si el usuario no está logueado
        if (session == null || session.getAttribute("logueado") == null) {
            // Si no hay sesión o no hay usuario logueado, redirigir a la página de inicio de sesión
            request.setAttribute("error", "Por favor, inicie sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        } else if (!"3".equals(session.getAttribute("rol")) && !"4".equals(session.getAttribute("rol"))) {
            // Verificar si el usuario no es administrador (roles 3 y 4)
            // Si no es administrador, redirigir con un mensaje de error
            session.setAttribute("error", "Solo se permite el ingreso de administradores");
            response.sendRedirect("sv_documentos");
            return;
        }

        // MOSTRAR LA LISTA DE USUARIOS POR ROL
        UsuarioDAO usuarioDAO = new UsuarioDAO(); // Instanciar el DAO para manejar usuarios

        // Obtener el parámetro tipoUsuario de la solicitud
        String tipoUsuario = request.getParameter("tipoUsuario");
        int rol;

        // Si no se proporciona el parámetro tipoUsuario, asignar "docente" como valor por defecto
        if (tipoUsuario == null || tipoUsuario.isEmpty()) {
            tipoUsuario = "docente"; // Valor por defecto
        }

        // Determinar el rol en función del tipoUsuario
        if ("estudiante".equals(tipoUsuario)) {
            rol = 1; // Rol de estudiante
        } else if ("docente".equals(tipoUsuario)) {
            rol = 2; // Rol de docente
        } else {
            rol = 3; // Otros roles, como administrador
        }

        // Obtener el parámetro docUsuario de la solicitud
        String docUsuario = request.getParameter("docUsuario");

        List<UsuarioClass> usuarios;

        // Si no se proporciona docUsuario, listar todos los usuarios según el rol
        if (docUsuario == null) {
            usuarios = usuarioDAO.listarUsuarios(rol);
        } else {
            // Si se proporciona docUsuario, buscar el usuario por documento
            usuarios = usuarioDAO.buscarUsuarioPorDocumento(rol, docUsuario);
        }

        // Establecer los atributos de la solicitud para enviar a la vista
        request.setAttribute("usuarios", usuarios);
        request.setAttribute("tipoUsuario", tipoUsuario);

        // Redirigir a la página de gestión de usuarios en la vista del administrador
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
            // Si se ha recibido una acción (actionDel) en la solicitud, proceder a manejarla

            // Obtener el documento del usuario desde los parámetros de la solicitud
            int docUsuario = Integer.parseInt(request.getParameter("docUsuario"));
            usuario.setDocUsu(docUsuario); // Establecer el documento del usuario en el objeto UsuarioClass

            try {
                // Manejar la acción en función del valor de actionDel
                switch (actionDel) {
                    case "habilitarUsu":
                        // Si la acción es habilitar, llamar al método para habilitar el usuario
                        usuarioDAO.habilitarUsuario(usuario);
                        // Establecer un mensaje de éxito en la sesión
                        session.setAttribute("success", tipoUsuario + " habilitado exitosamente");
                        break;
                    case "inhabilitarUsu":
                        // Si la acción es inhabilitar, llamar al método para inhabilitar el usuario
                        usuarioDAO.inhabilitarUsuario(usuario);
                        // Establecer un mensaje de éxito en la sesión
                        session.setAttribute("success", tipoUsuario + " inhabilitado correctamente");
                        break;
                }
                // Redirigir a la página anterior después de realizar la acción
                response.sendRedirect(request.getHeader("Referer"));
                return;
            } catch (Exception error) {
                // Si ocurre un error, imprimir la traza de la excepción
                error.printStackTrace();
                // Redirigir a la página de gestión de usuarios con el tipo de usuario en la URL
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
            // Si existe un mensaje de error (errorMessage) en la solicitud, proceder a manejarlo

            // Establecer el mensaje de error en la sesión
            session.setAttribute("error", errorMessage);

            // Redirigir al usuario a la página anterior desde la cual se hizo la solicitud
            response.sendRedirect(request.getHeader("Referer"));
            return; // Finalizar la ejecución del método para evitar procesar más lógica
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
            // Verificar si se ha enviado una solicitud para editar un docente, estudiante o administrador
            if (request.getParameter("editDocente") != null || 
                request.getParameter("editEstudiante") != null || 
                request.getParameter("editAdmin") != null) {

                // Obtener el documento del usuario y el rol desde los parámetros de la solicitud
                int docUsuario = Integer.parseInt(docUsuStr);
                int rol = Integer.parseInt(request.getParameter("rol"));
                String tipoEdicion = request.getParameter("tipoEdicion");

                // Establecer el rol en el objeto usuario
                usuario.setRol(rol);

                // Determinar si la edición es para un docente
                boolean isDocente = request.getParameter("editDocente") != null;

                // Llamar al método para editar el usuario en la base de datos
                usuarioDAO.editarUsuario(docUsuario, usuario, isDocente);

                // Si el tipo de edición es "misDatos", actualizar la sesión con el nuevo documento del usuario
                if ("misDatos".equals(tipoEdicion)) {
                    session.setAttribute("UserDoc", NewdocUsuStr);
                }

                // Establecer un mensaje de éxito en la sesión
                session.setAttribute("success", "Datos del " + tipoUsuario + " actualizados correctamente");

                // Obtener la página desde la cual se hizo la solicitud
                String referer = request.getHeader("Referer");

                // Verificar si la página anterior es la gestión de usuarios y redirigir adecuadamente
                if (referer != null && referer.contains("sv_gestUsuario")) {
                    response.sendRedirect("sv_usuario?tipoUsuario=" + tipoUsuario);
                } else {
                    response.sendRedirect(referer);
                }
                return; // Finalizar la ejecución del método
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL que ocurra durante la edición
            e.printStackTrace(); // Imprimir la traza de la excepción
            response.getWriter().print("Error: " + e.getMessage()); // Mostrar el mensaje de error en la respuesta
            return; // Finalizar la ejecución del método en caso de error
        }

        // REGISTRAR O AGREGAR USUARIO
        try {
            // Verificar si se ha enviado una solicitud para registrar un docente, estudiante o administrador
            if (request.getParameter("regDocente") != null || 
                request.getParameter("regEstudiante") != null || 
                request.getParameter("regAdmin") != null) {

                // Determinar el rol del usuario a partir del tipo de usuario (docente, estudiante, administrador)
                int rol = tipoUsuario.equals("docente") ? 2 : tipoUsuario.equals("estudiante") ? 1 : 3;
                usuario.setRol(rol); // Establecer el rol en el objeto usuario

                // Llamar al método para agregar el usuario en la base de datos
                usuarioDAO.agregarUsuario(usuario);

                // Manejar la respuesta en función del tipo de usuario registrado
                if (tipoUsuario.equals("estudiante")) {
                    // Si es un estudiante, redirigir a la página de inicio de sesión con un mensaje de éxito
                    request.setAttribute("success", "Estudiante registrado exitosamente, ahora puedes iniciar sesión");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } else {
                    // Para docentes y administradores, mostrar un comprobante de registro
                    request.setAttribute("usuario", usuario); // Pasar el objeto usuario como atributo para mostrar en el comprobante
                    session.setAttribute("success", tipoUsuario + " registrado exitosamente");
                    request.getRequestDispatcher("vistas/vistas_admin/comprob_reg.jsp").forward(request, response);
                }
                return; // Finalizar la ejecución del método
            }
        } catch (SQLException e) {
            // Manejar cualquier excepción SQL que ocurra durante el registro
            e.printStackTrace(); // Imprimir la traza de la excepción
            response.getWriter().print("Error: " + e.getMessage()); // Mostrar el mensaje de error en la respuesta
            return; // Finalizar la ejecución del método en caso de error
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
