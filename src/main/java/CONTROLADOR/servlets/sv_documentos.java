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
        // Obtener la sesión existente, pero no crear una nueva si no existe
        HttpSession session = request.getSession(false);

        // Verificar si la sesión es nula o si el usuario no ha iniciado sesión
        if (session == null || session.getAttribute("logueado") == null) {
            // Si no hay sesión o el usuario no ha iniciado sesión, redirigir a la página de inicio de sesión con un mensaje de error
            request.setAttribute("error", "Por favor, inicie sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return; // Finalizar la ejecución del método para evitar más procesamiento
        }

        // Instanciar los objetos necesarios para manejar los datos del formulario y los documentos
        FormDoc formDoc = new FormDoc();
        DocumentoDAO documentoDAO = new DocumentoDAO();
        DocumentoClass documento = new DocumentoClass();

        // Obtener los parámetros de filtro (asignatura, idioma, tipo) desde la solicitud
        String asignatura = request.getParameter("asignatura");
        String idioma = request.getParameter("idioma");
        String tipo = request.getParameter("tipo");

        // Obtener las listas de opciones para los filtros desde FormDoc
        List<AsignaturaClass> asignaturas = formDoc.obtenerAsignaturas();
        List<IdiomaClass> idiomas = formDoc.obtenerIdiomas();
        List<TipoClass> tipos = formDoc.obtenerTipos();

        // Asignar los valores de los filtros al objeto documento usando los setters
        if (asignatura != null) {
            documento.setAsignaturaId(Integer.parseInt(asignatura)); // Asignar el ID de la asignatura al documento
        }
        if (idioma != null) {
            documento.setIdiomaId(Integer.parseInt(idioma)); // Asignar el ID del idioma al documento
        }
        if (tipo != null) {
            documento.setTipoId(Integer.parseInt(tipo)); // Asignar el ID del tipo de documento
        }

        // Lista para almacenar los documentos según los filtros aplicados o todos si no hay filtros
        List<DocumentoClass> documentos;
        if (asignatura == null && idioma == null && tipo == null) {
            // Si no hay filtros seleccionados, listar todos los documentos
            documentos = documentoDAO.ListarDocumentos();
        } else {
            // Si hay filtros seleccionados, filtrar los documentos según los criterios
            documentos = documentoDAO.FiltrarDocumentos(documento); // Filtrar los documentos según los valores asignados en el objeto documento
        }

        // Establecer los atributos para enviar a la vista
        request.setAttribute("documentos", documentos); // Lista de documentos filtrados o completos
        request.setAttribute("asignaturas", asignaturas); // Lista de asignaturas para el filtro
        request.setAttribute("idiomas", idiomas); // Lista de idiomas para el filtro
        request.setAttribute("tipos", tipos); // Lista de tipos de documento para el filtro

        // Redirigir a la vista home.jsp con los datos cargados
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
        
        // Inicializamos las clases
        DocumentoDAO documentoDAO = new DocumentoDAO();
        DocumentoClass documento = new DocumentoClass();
        
        // Establecer el ID del documento en el objeto documento
        documento.setId(idDocumento);
        
        if (request.getParameter("eliminarDocumento") != null) {
            try {
                documentoDAO.eliminarDocumento(documento);

                // Eliminar la miniatura del sistema de archivos
                // Obtener la ruta del contexto del servlet en el sistema de archivos
                String rutaContexto = getServletContext().getRealPath("/"); // PROYECTO_BVINEM_JSP\target\PROYECTO_BVINEM-1.0-SNAPSHOT\
                // Ajusta la ruta para apuntar al directorio de la aplicación web en lugar del directorio de compilación
                String rutaAlterada = rutaContexto.replace("\\target\\PROYECTO_BVINEM-1.0-SNAPSHOT\\", "\\src\\main\\webapp");
                 // Construye la ruta completa del archivo de miniatura
                String rutaArchivoMiniatura = rutaAlterada + File.separator + rutaMiniatura;
                // Construye la ruta completa del archivo PDF
                String rutaArchivoPDF = rutaAlterada + File.separator + rutaPDF;
                
                // Crea un objeto File para la miniatura
                File archivoMiniatura = new File(rutaArchivoMiniatura);
                // Verifica si el archivo de miniatura existe y, de ser así, lo elimina
                if (archivoMiniatura.exists()) {
                    archivoMiniatura.delete();
                }
                
                // Crea un objeto File para el PDF
                File archivoPDF = new File(rutaArchivoPDF);
                // Verifica si el archivo PDF existe y, de ser así, lo elimina
                if (archivoPDF.exists()) {
                    archivoPDF.delete();
                }

                // Establecer un mensaje de éxito en la sesión y redirigir a la página anterior
                session.setAttribute("success", "Documento o libro eliminado exitosamente");
                response.sendRedirect(request.getHeader("Referer"));
                return; // Finalizar la ejecución del método 
            } catch (SQLException e) {
                // Manejar cualquier excepción SQL que ocurra durante la eliminación del documento
                e.printStackTrace(); // Imprimir la traza del error para depuración
                response.getWriter().print("Error: " + e.getMessage()); // Mostrar el mensaje de error en la respuesta
                return; // Finalizar la ejecución del método en caso de error
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

        // Verificar si se ha solicitado la edición del documento
        if (request.getParameter("editDocumento") != null) {
            try {
                // Intentar actualizar el documento en la base de datos utilizando el método editarDocumento del DAO
                documentoDAO.editarDocumento(documento);

                // Si la actualización es exitosa, establecer un mensaje de éxito en la sesión
                session.setAttribute("success", "Documento actualizado exitosamente");

                // Redirigir al usuario a la página anterior (referida en el encabezado "Referer")
                response.sendRedirect(request.getHeader("Referer"));
            } catch (SQLException e) {
                // Si ocurre un error SQL durante la actualización, capturar la excepción y manejarla
                e.printStackTrace(); // Imprimir la traza del error para depuración

                // Establecer un mensaje de error en la sesión con detalles sobre el problema
                session.setAttribute("error", "Error al actualizar el documento: " + e.getMessage());

                // Redirigir al usuario a la página principal de documentos
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
