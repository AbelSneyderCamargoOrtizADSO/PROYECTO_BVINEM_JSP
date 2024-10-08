/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets;

import MODELO.FormDoc;
import MODELO.categorias.AsignaturaClass;
import MODELO.categorias.CategoriaClass;
import MODELO.categorias.AsignaturasDAO;
import MODELO.categorias.IdiomaClass;
import MODELO.categorias.IdiomasDAO;
import MODELO.categorias.TipoClass;
import MODELO.categorias.TipoDocDAO;
import MODELO.categorias.TipoForoClass;
import MODELO.categorias.TipoForoDAO;
import MODELO.foros.FormForo;
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
@WebServlet(name = "sv_categorias", urlPatterns = {"/sv_categorias"})
public class sv_categorias extends HttpServlet {

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
        // Verificar si el usuario tiene un rol diferente al de administrador
        else if (!(session.getAttribute("rol").equals("3")) && !(session.getAttribute("rol").equals("4"))) {
            // Si el rol no es de administrador (ni 3 ni 4), establecer un mensaje de error en la sesión
            session.setAttribute("error", "Solo se permite el ingreso de administradores");

            // Redirigir al usuario a la página de documentos
            response.sendRedirect("sv_documentos");
            return; // Finalizar la ejecución del método
        }

        
        // Obtener el parámetro categoria de la solicitud
        String categoria = request.getParameter("categoria");

        // Creamos las isntancias
        AsignaturasDAO asignaturaDAO = new AsignaturasDAO();
        IdiomasDAO idiomaDAO = new IdiomasDAO();
        TipoDocDAO tipodocDAO = new TipoDocDAO();
        TipoForoDAO tipoforoDAO = new TipoForoDAO();

        // Variable para almacenar la lista de resultados
        List<?> resultados = null; // El ? es una lista que puede contener cualquier tipo de objeto. 

        // Lógica para determinar qué lista obtener según el parámetro categoria
        if (categoria != null) {
            switch (categoria) {
                case "asignatura":
                    resultados = asignaturaDAO.obtenerAsignaturas();
                    break;
                case "idioma":
                    resultados = idiomaDAO.obtenerIdiomas();
                    break;
                case "tipo documento":
                    resultados = tipodocDAO.obtenerTipos();
                    break;
                case "tipo foro":
                    resultados = tipoforoDAO.obtenerTiposForo();
                    break;
                default:
                    resultados = null;
                    break;
            }
        }

        // Añadir la lista de resultados al request
        request.setAttribute("resultados", resultados); // Agregar un atributo a la solicitud HTTP (HttpServletRequest)
        request.setAttribute("categoria", categoria);

        // Enviar la solicitud al JSP para mostrar los resultados
        request.getRequestDispatcher("vistas/vistas_admin/categorias.jsp").forward(request, response);
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

        HttpSession session = request.getSession();

        // VARIABLES
        String action = request.getParameter("action");
        String categoria = request.getParameter("categoria");
        String nombre = request.getParameter("nombre");
        if (nombre != null) nombre = nombre.trim();
        String idStr = request.getParameter("id");
        int id = idStr != null ? Integer.parseInt(idStr) : 0;

        // VALIDACIONES
        String errorMessage = Validador.validarCategoria(nombre, categoria, id);

        if (errorMessage != null) {
            session.setAttribute("error", errorMessage);
            response.sendRedirect(request.getHeader("Referer"));
            return;
        }

        try {
            String successMessage = ""; // Variable para almacenar el mensaje de éxito
            // Evaluar la categoría seleccionada para realizar la operación correspondiente
            switch (categoria) {
                case "asignatura":
                    // Crear instancias del DAO y la clase de Asignatura
                    AsignaturasDAO asignaturaDAO = new AsignaturasDAO();
                    AsignaturaClass asignatura = new AsignaturaClass();
                    asignatura.setId(id);
                    asignatura.setNombre(nombre);
                    
                    // Evaluar la acción a realizar (Agregar, Editar, Habilitar, Inhabilitar)
                    switch (action) {
                        case "Add":
                            asignaturaDAO.agregarAsignatura(asignatura); // Agregar una nueva asignatura
                            successMessage = "Asignatura agregada exitosamente"; // Mensaje de éxito
                            break;
                        case "Edit":
                            asignaturaDAO.editarAsignatura(asignatura); // Editar la asignatura existente
                            successMessage = "Asignatura editada exitosamente"; // Mensaje de éxito
                            break;
                        case "habilitar":
                            asignaturaDAO.habilitarAsignatura(asignatura); // Habilitar la asignatura
                            successMessage = "Asignatura habilitada exitosamente"; // Mensaje de éxito
                            break;
                        case "inhabilitar":
                            asignaturaDAO.inhabilitarAsignatura(asignatura); // Inhabilitar la asignatura
                            successMessage = "Asignatura inhabilitada exitosamente"; // Mensaje de éxito
                            break;
                    }
                    break;

                case "idioma":
                    IdiomasDAO idiomaDAO = new IdiomasDAO();
                    IdiomaClass idioma = new IdiomaClass();
                    idioma.setId(id);
                    idioma.setNombre(nombre);

                    // Evaluar la acción a realizar (Agregar, Editar, Habilitar, Inhabilitar)
                    switch (action) {
                        case "Add":
                            idiomaDAO.agregarIdioma(idioma); // Agregar un nuevo idioma
                            successMessage = "Idioma agregado exitosamente"; // Mensaje de éxito
                            break;
                        case "Edit":
                            idiomaDAO.editarIdioma(idioma); // Editar el idioma existente
                            successMessage = "Idioma editado exitosamente"; // Mensaje de éxito
                            break;
                        case "habilitar":
                            idiomaDAO.habilitarIdioma(idioma); // Habilitar el idioma
                            successMessage = "Idioma habilitado exitosamente"; // Mensaje de éxito
                            break;
                        case "inhabilitar":
                            idiomaDAO.inhabilitarIdioma(idioma); // Inhabilitar el idioma
                            successMessage = "Idioma inhabilitado exitosamente"; // Mensaje de éxito
                            break;
                    }
                    break;

                case "tipo documento":
                    TipoDocDAO tipodocDAO = new TipoDocDAO();
                    TipoClass tipodoc = new TipoClass();
                    tipodoc.setId(id);
                    tipodoc.setNombre(nombre);

                    // Evaluar la acción a realizar (Agregar, Editar, Habilitar, Inhabilitar)
                    switch (action) {
                        case "Add":
                            tipodocDAO.agregarTipoDoc(tipodoc); // Agregar un nuevo tipo de documento
                            successMessage = "Tipo de documento agregado exitosamente"; // Mensaje de éxito
                            break;
                        case "Edit":
                            tipodocDAO.editarTipoDoc(tipodoc); // Editar el tipo de documento existente
                            successMessage = "Tipo de documento editado exitosamente"; // Mensaje de éxito
                            break;
                        case "habilitar":
                            tipodocDAO.habilitarTipoDoc(tipodoc); // Habilitar el tipo de documento
                            successMessage = "Tipo de documento habilitado exitosamente"; // Mensaje de éxito
                            break;
                        case "inhabilitar":
                            tipodocDAO.inhabilitarTipoDoc(tipodoc); // Inhabilitar el tipo de documento
                            successMessage = "Tipo de documento inhabilitado exitosamente"; // Mensaje de éxito
                            break;
                    }
                    break;

                case "tipo foro":
                    TipoForoDAO tipoforoDAO = new TipoForoDAO();
                    TipoForoClass tipoforo = new TipoForoClass();
                    tipoforo.setId(id);
                    tipoforo.setNombre(nombre);

                    // Evaluar la acción a realizar (Agregar, Editar, Habilitar, Inhabilitar)
                    switch (action) {
                        case "Add":
                            tipoforoDAO.agregarTipoForo(tipoforo); // Agregar un nuevo tipo de foro
                            successMessage = "Tipo de foro agregado exitosamente"; // Mensaje de éxito
                            break;
                        case "Edit":
                            tipoforoDAO.editarTipoForo(tipoforo); // Editar el tipo de foro existente
                            successMessage = "Tipo de foro editado exitosamente"; // Mensaje de éxito
                            break;
                        case "habilitar":
                            tipoforoDAO.habilitarTipoForo(tipoforo); // Habilitar el tipo de foro
                            successMessage = "Tipo de foro habilitado exitosamente"; // Mensaje de éxito
                            break;
                        case "inhabilitar":
                            tipoforoDAO.inhabilitarTipoForo(tipoforo); // Inhabilitar el tipo de foro
                            successMessage = "Tipo de foro inhabilitado exitosamente"; // Mensaje de éxito
                            break;
                    }
                    break;
            }
            // Establecer el mensaje de éxito en la sesión y redirigir al usuario a la página anterior
            session.setAttribute("success", successMessage);
            response.sendRedirect(request.getHeader("Referer"));
        } catch (Exception error) {
            // Manejar cualquier excepción que ocurra durante la ejecución
            error.printStackTrace(); // Imprimir la traza del error para depuración
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
