/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets;

import MODELO.Conexion;
import MODELO.login_class;
import MODELO.usuarios.UsuarioClass;
import MODELO.usuarios.Validador;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.*;
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
@WebServlet(name = "sv_login", urlPatterns = {"/sv_login"})
public class sv_login extends HttpServlet {

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
        if (request.getParameter("ingresarEstu") != null || request.getParameter("ingresarDocen") != null || request.getParameter("ingresarAdmin") != null) {

            String dniStr = request.getParameter("dni");
            String pass = request.getParameter("password");
            String rol = null;

            if (request.getParameter("ingresarEstu") != null) {
                rol = "1"; // Estudiante
            } else if (request.getParameter("ingresarDocen") != null) {
                rol = "2"; // Docente
            } else if (request.getParameter("ingresarAdmin") != null) {
                rol = "3"; // Administrador
            }

            // VALIDACIONES
            String errorMessage = Validador.validarLogin(dniStr, pass);

            if (errorMessage != null) {
                request.setAttribute("error", errorMessage);
                // response.sendRedirect("sv_usuario");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            int dni = Integer.parseInt(dniStr);
            // Crear instancia de UsuarioClass
            UsuarioClass usuario = new UsuarioClass();
            usuario.setDocUsu(dni);
            usuario.setPass(pass);
            usuario.setRol(Integer.parseInt(rol));

            login_class validar = new login_class();

            try {
                String resultadoValidacion = validar.validarUsuario(usuario);
                switch (resultadoValidacion) {
                    case "valido":
                        HttpSession session = request.getSession();
                        session.setAttribute("logueado", "1");
                        session.setAttribute("rol", rol);
                        session.setAttribute("UserDoc", dniStr);
                        response.sendRedirect("sv_documentos");
                        break;
                    case "inhabilitado":
                        request.setAttribute("error", "Usuario inhabilitado");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;
                    case "contraseña_incorrecta":
                        request.setAttribute("error", "Contraseña incorrecta");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;
                    case "usuario_no_encontrado":
                        request.setAttribute("error", "Usuario no encontrado");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;
                    default:
                        request.setAttribute("error", "Ha ocurrido un error inesperado, intenta nuevamente");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Error al conectar con la base de datos");
                request.getRequestDispatcher("index.jsp").forward(request, response);
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
