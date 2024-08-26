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
import MODELO.categorias.TipoForoClass;
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
        // Obtener la sesión existente, pero no crear una nueva si no existe
        HttpSession session = request.getSession(false);

        // Verificar si la sesión es nula o si el usuario no ha iniciado sesión
        if (session == null || session.getAttribute("logueado") == null) {
            // Si no hay sesión o el usuario no ha iniciado sesión, redirigir a la página de inicio de sesión con un mensaje de error
            request.setAttribute("error", "Por favor, inicie sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return; // Finalizar la ejecución del método 
        }

        // Instanciar los formularios necesarios para obtener datos de asignaturas, idiomas y tipos de foro
        FormDoc formDoc = new FormDoc();
        FormForo formForo = new FormForo();

        // Obtener las listas de asignaturas, idiomas y tipos de foro
        List<AsignaturaClass> asignaturas = formDoc.obtenerAsignaturas();
        List<IdiomaClass> idiomas = formDoc.obtenerIdiomas();
        List<TipoForoClass> tiposforo = formForo.obtenerTiposForo();

        // Establecer las listas como atributos en la solicitud para que puedan ser utilizadas en la vista
        request.setAttribute("asignaturas", asignaturas); // Lista de asignaturas
        request.setAttribute("idiomas", idiomas); // Lista de idiomas
        request.setAttribute("tiposforo", tiposforo); // Lista de tipos de foro

        // Redirigir a la página de subir foro con los datos cargados
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

        // Si hubo algún error en las validaciones anteriores, se redirige al usuario a la página de subir foro.
        if (errorMessage != null) {
            session.setAttribute("error", errorMessage); // Guardar el mensaje de error en la sesión
            response.sendRedirect("subir_foro"); // Redirigir a la página de subida del foro
            return; // Finalizar la ejecución del método
        }

        // ASIGNAMOS LOS SETTERS
        foro.setUsuarioDoc(UserDoc);
        foro.setTitulo(tit);
        foro.setDescripcion(descrip);
        foro.setAsignaturaId(Integer.parseInt(asig));
        foro.setIdiomaId(Integer.parseInt(idioma));
        foro.setTipoId(Integer.parseInt(tipo));

        // Verificar si se ha enviado el formulario
        if (request.getParameter("enviar") != null) {
            try {
                // Intentar subir el foro a la base de datos
                forodao.subirForo(foro);
                session.setAttribute("success", "Foro publicado correctamente"); // Mensaje de éxito
                response.sendRedirect("sv_foros"); // Redirigir a la lista de foros
            } catch (Exception error) {
                // Manejar cualquier excepción que ocurra durante el proceso de subida
                error.printStackTrace(); // Imprimir la traza del error para depuración
                response.getWriter().print("Error: " + error.getMessage()); // Mostrar el mensaje de error en la respuesta
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
