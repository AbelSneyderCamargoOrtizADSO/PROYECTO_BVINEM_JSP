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
        // Obtener el parámetro categoria de la solicitud
        String categoria = request.getParameter("categoria");

        // Creamos las isntancias
        AsignaturasDAO asignaturaDAO = new AsignaturasDAO();
        IdiomasDAO idiomaDAO = new IdiomasDAO();
        TipoDocDAO tipodocDAO = new TipoDocDAO();
        TipoForoDAO tipoforoDAO = new TipoForoDAO();

        // Variable para almacenar la lista de resultados
        List<?> resultados = null;

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
            String successMessage = "";
            switch (categoria) {
                case "asignatura":
                    AsignaturasDAO asignaturaDAO = new AsignaturasDAO();
                    AsignaturaClass asignatura = new AsignaturaClass();
                    asignatura.setId(id);
                    asignatura.setNombre(nombre);

                    switch (action) {
                        case "Add":
                            asignaturaDAO.agregarAsignatura(asignatura);
                            successMessage = "Asignatura agregada exitosamente";
                            break;
                        case "Edit":
                            asignaturaDAO.editarAsignatura(asignatura);
                            successMessage = "Asignatura editada exitosamente";
                            break;
                        case "habilitar":
                            asignaturaDAO.habilitarAsignatura(asignatura);
                            successMessage = "Asignatura habilitada exitosamente";
                            break;
                        case "inhabilitar":
                            asignaturaDAO.inhabilitarAsignatura(asignatura);
                            successMessage = "Asignatura inhabilitada exitosamente";
                            break;
                    }
                    break;

                case "idioma":
                    IdiomasDAO idiomaDAO = new IdiomasDAO();
                    IdiomaClass idioma = new IdiomaClass();
                    idioma.setId(id);
                    idioma.setNombre(nombre);

                    switch (action) {
                        case "Add":
                            idiomaDAO.agregarIdioma(idioma);
                            successMessage = "Idioma agregado exitosamente";
                            break;
                        case "Edit":
                            idiomaDAO.editarIdioma(idioma);
                            successMessage = "Idioma editado exitosamente";
                            break;
                        case "habilitar":
                            idiomaDAO.habilitarIdioma(idioma);
                            successMessage = "Idioma habilitado exitosamente";
                            break;
                        case "inhabilitar":
                            idiomaDAO.inhabilitarIdioma(idioma);
                            successMessage = "Idioma inhabilitado exitosamente";
                            break;
                    }
                    break;

                case "tipo documento":
                    TipoDocDAO tipodocDAO = new TipoDocDAO();
                    TipoClass tipodoc = new TipoClass();
                    tipodoc.setId(id);
                    tipodoc.setNombre(nombre);

                    switch (action) {
                        case "Add":
                            tipodocDAO.agregarTipoDoc(tipodoc);
                            successMessage = "Tipo de documento agregado exitosamente";
                            break;
                        case "Edit":
                            tipodocDAO.editarTipoDoc(tipodoc);
                            successMessage = "Tipo de documento editado exitosamente";
                            break;
                        case "habilitar":
                            tipodocDAO.habilitarTipoDoc(tipodoc);
                            successMessage = "Tipo de documento habilitado exitosamente";
                            break;
                        case "inhabilitar":
                            tipodocDAO.inhabilitarTipoDoc(tipodoc);
                            successMessage = "Tipo de documento inhabilitado exitosamente";
                            break;
                    }
                    break;

                case "tipo foro":
                    TipoForoDAO tipoforoDAO = new TipoForoDAO();
                    TipoForoClass tipoforo = new TipoForoClass();
                    tipoforo.setId(id);
                    tipoforo.setNombre(nombre);

                    switch (action) {
                        case "Add":
                            tipoforoDAO.agregarTipoForo(tipoforo);
                            successMessage = "Tipo de foro agregado exitosamente";
                            break;
                        case "Edit":
                            tipoforoDAO.editarTipoForo(tipoforo);
                            successMessage = "Tipo de foro editado exitosamente";
                            break;
                        case "habilitar":
                            tipoforoDAO.habilitarTipoForo(tipoforo);
                            successMessage = "Tipo de foro habilitado exitosamente";
                            break;
                        case "inhabilitar":
                            tipoforoDAO.inhabilitarTipoForo(tipoforo);
                            successMessage = "Tipo de foro inhabilitado exitosamente";
                            break;
                    }
                    break;
            }
            session.setAttribute("success", successMessage);
            response.sendRedirect(request.getHeader("Referer"));
        } catch (Exception error) {
            error.printStackTrace();
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
