/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets;

import MODELO.categorias.AsignaturaClass;
import MODELO.DocumentoClass;
import MODELO.DocumentoDAO;
import MODELO.FormDoc;
import MODELO.categorias.IdiomaClass;
import MODELO.categorias.TipoClass;
import MODELO.usuarios.Validador;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
@WebServlet(name = "sv_documentos", urlPatterns = {"/sv_documentos"})
public class sv_documentos extends HttpServlet {

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
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("logueado") == null) {
            request.setAttribute("error", "Por favor, inicie sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        FormDoc formDoc = new FormDoc();
        DocumentoDAO documentoDAO = new DocumentoDAO();
        DocumentoClass documento = new DocumentoClass();

        String asignatura = request.getParameter("asignatura");
        String idioma = request.getParameter("idioma");
        String tipo = request.getParameter("tipo");

        List<AsignaturaClass> asignaturas = formDoc.obtenerAsignaturas();
        List<IdiomaClass> idiomas = formDoc.obtenerIdiomas();
        List<TipoClass> tipos = formDoc.obtenerTipos();

        if (asignatura != null) {
            documento.setAsignaturaId(Integer.parseInt(asignatura));
        }
        if (idioma != null) {
            documento.setIdiomaId(Integer.parseInt(idioma));
        }
        if (tipo != null) {
            documento.setTipoId(Integer.parseInt(tipo));
        }

        List<DocumentoClass> documentos;
        if (asignatura == null && idioma == null && tipo == null) {
            documentos = documentoDAO.ListarDocumentos();
        } else {
            documentos = documentoDAO.FiltrarDocumentos(documento);
        }

        request.setAttribute("documentos", documentos);
        request.setAttribute("asignaturas", asignaturas);
        request.setAttribute("idiomas", idiomas);
        request.setAttribute("tipos", tipos);
        request.getRequestDispatcher("vistas/home.jsp").forward(request, response);
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

        // VARIABLES
        int idDocumento = Integer.parseInt(request.getParameter("docId"));
        String rutaMiniatura = request.getParameter("rutaMiniatura");
        String rutaPDF = request.getParameter("rutaPDF");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String descripcion = request.getParameter("descripcion");
        String year = request.getParameter("year");

        DocumentoDAO documentoDAO = new DocumentoDAO();
        DocumentoClass documento = new DocumentoClass();
        
        documento.setId(idDocumento);
        
        if (request.getParameter("eliminarDocumento") != null) {
            try {
                documentoDAO.eliminarDocumento(documento);

                // Eliminar la miniatura del sistema de archivos
                String rutaContexto = getServletContext().getRealPath("/"); // PROYECTO_BVINEM_JSP\target\PROYECTO_BVINEM-1.0-SNAPSHOT\
                String rutaAlterada = rutaContexto.replace("\\target\\PROYECTO_BVINEM-1.0-SNAPSHOT\\", "\\src\\main\\webapp");
                String rutaArchivoMiniatura = rutaAlterada + File.separator + rutaMiniatura;
                String rutaArchivoPDF = rutaAlterada + File.separator + rutaPDF;

                File archivoMiniatura = new File(rutaArchivoMiniatura);
                if (archivoMiniatura.exists()) {
                    archivoMiniatura.delete();
                }

                File archivoPDF = new File(rutaArchivoPDF);
                if (archivoPDF.exists()) {
                    archivoPDF.delete();
                }

                System.out.println(rutaContexto);
                session.setAttribute("success", "Documento o libro eliminado exitosamente");
                response.sendRedirect(request.getHeader("Referer"));
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                response.getWriter().print("Error: " + e.getMessage());
                return;
            }
        }
        
        // VALIDACIONES
        String errorMessage = Validador.validarTitulo(titulo);
        if (errorMessage == null) errorMessage = Validador.validarAutor(autor);
        if (errorMessage == null) errorMessage = Validador.validarDescripcion(descripcion);       
        if (errorMessage == null) errorMessage = Validador.validarYear(year);        

        if (errorMessage != null) {
            session.setAttribute("error", errorMessage);
            response.sendRedirect(request.getHeader("Referer"));
            return;
        }
        
        documento.setTitulo(titulo);
        documento.setAutor(autor);
        documento.setDescripcion(descripcion);
        documento.setYear(year);

        if (request.getParameter("editDocumento") != null) {
            try {
                documentoDAO.editarDocumento(documento);
                session.setAttribute("success", "Documento actualizado exitosamente");
                response.sendRedirect(request.getHeader("Referer"));
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("error", "Error al actualizar el documento: " + e.getMessage());
                response.sendRedirect("sv_documentos");
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
