package CONTROLADOR.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import MODELO.usuarios.UsuarioClass;
import MODELO.usuarios.UsuarioDAO;
import MODELO.usuarios.Validador;

@WebServlet(name = "sv_register", urlPatterns = {"/sv_register"})
public class sv_register extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("submit") != null) {

            // TRAEMOS O TOMAMOS LOS DATOS INGRESADOS MEDIANTE getParameter TOMANDO COMO REFERENCIA EL NAME DE CADA INPUT
            String dniSt = request.getParameter("dni");
            String nom = request.getParameter("nombres");
            String ape = request.getParameter("apellidos");
            String correo = request.getParameter("correo");
            String contra = request.getParameter("password");
            String grado = request.getParameter("grado");

            // VALIDACIONES
            String errorMessage = Validador.validarDocumento(dniSt);
            if (errorMessage == null) errorMessage = Validador.validarDocumentoEnUso(dniSt);       
            if (errorMessage == null) errorMessage = Validador.validarNombre(nom);       
            if (errorMessage == null) errorMessage = Validador.validarApellido(ape); 
            if (errorMessage == null) errorMessage = Validador.validarCorreo(correo);   
            if (errorMessage == null) errorMessage = Validador.validarCorreoEnUso(correo, Integer.parseInt(dniSt));       
            if (errorMessage == null) errorMessage = Validador.validarContrasena(contra);       
            if (errorMessage == null) errorMessage = Validador.validarGrado(grado);    
            

            if (errorMessage != null) {
                request.setAttribute("error", errorMessage);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            
            int dni = Integer.parseInt(dniSt);
            
            // Crear instancia de UsuarioClass
            UsuarioClass usuario = new UsuarioClass();
            usuario.setDocUsu(dni);
            usuario.setNombre(nom);
            usuario.setApellido(ape);
            usuario.setCorreo(correo);
            usuario.setPass(contra);
            usuario.setGrado(grado);
            usuario.setRol(1);

            
            UsuarioDAO usuariodao =  new UsuarioDAO();

            try {
                usuariodao.agregarUsuario(usuario, "tb_estudiante");
                request.setAttribute("success", "Estudiante registrado exitosamente, ahora puedes iniciar sesión");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (Exception error) {
                error.printStackTrace();
                response.getWriter().print("Error: " + error.getMessage());
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
