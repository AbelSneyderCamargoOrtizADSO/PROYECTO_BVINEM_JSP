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
@WebServlet(name = "sv_foros", urlPatterns = {"/sv_foros"})
public class sv_foros extends HttpServlet {

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
            return; // Finalizar la ejecución del método para evitar más procesamiento
        }

        // Instanciar los DAOs y formularios necesarios
        ForoDAO forodao = new ForoDAO();
        FormDoc formDoc = new FormDoc();
        FormForo formForo = new FormForo();

        // SELECT FILTROS
        // Obtener los parámetros de filtro (asignatura, idioma, tipo) desde la solicitud
        String asignatura = request.getParameter("asignatura");
        String idioma = request.getParameter("idioma");
        String tipo = request.getParameter("tipof");

        // Obtener las listas de opciones para los filtros
        List<AsignaturaClass> asignaturas = formDoc.obtenerAsignaturas();
        List<IdiomaClass> idiomas = formDoc.obtenerIdiomas();
        List<TipoForoClass> tiposforo = formForo.obtenerTiposForo();

        // Crear un objeto ForoClass para almacenar los filtros seleccionados
        ForoClass filtro = new ForoClass();
        if (asignatura != null) filtro.setAsignaturaId(Integer.parseInt(asignatura)); // Filtro por asignatura
        if (idioma != null) filtro.setIdiomaId(Integer.parseInt(idioma)); // Filtro por idioma
        if (tipo != null) filtro.setTipoId(Integer.parseInt(tipo)); // Filtro por tipo de foro

        // Obtener la lista de foros aplicando los filtros si están presentes
        List<ForoClass> foros;
        if (asignatura == null && idioma == null && tipo == null) {
            // Si no se han seleccionado filtros, listar todos los foros
            foros = forodao.listarForos();
        } else {
            // Si hay filtros seleccionados, filtrar los foros según los criterios
            foros = forodao.filtrarForos(filtro);
        }

        // Establecer los atributos para enviar a la vista
        request.setAttribute("asignaturas", asignaturas); // Lista de asignaturas para el filtro
        request.setAttribute("idiomas", idiomas); // Lista de idiomas para el filtro
        request.setAttribute("tiposforo", tiposforo); // Lista de tipos de foro para el filtro
        request.setAttribute("foros", foros); // Lista de foros filtrados o completos

        // Redirigir a la vista de foros con los datos cargados
        request.getRequestDispatcher("vistas/foros.jsp").forward(request, response);
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
