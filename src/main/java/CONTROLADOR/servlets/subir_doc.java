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
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Abelito
 */
@WebServlet(name = "subir_doc", urlPatterns = {"/subir_doc"})
@MultipartConfig(maxFileSize = 10177215)
public class subir_doc extends HttpServlet {

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
        } else if(!(session.getAttribute("rol").equals("2")) && !(session.getAttribute("rol").equals("4"))) {
            session.setAttribute("error", "Solo se permite el ingreso de docentes");
            response.sendRedirect("sv_documentos");
            return;
        }
        
        FormDoc formDoc = new FormDoc(); // Instancia a la clase FormDoc que tiene lso metodos para listas las categorias de lso documentos o libros

        List<AsignaturaClass> asignaturas = formDoc.obtenerAsignaturas(); // Obtener la lista de asignaturas utilizando el método obtenerAsignaturas() del objeto formDoc
        List<IdiomaClass> idiomas = formDoc.obtenerIdiomas();
        List<TipoClass> tipos = formDoc.obtenerTipos();

        request.setAttribute("asignaturas", asignaturas); // Asigna la lista de asignaturas al atributo "asignaturas" en el objeto request
        request.setAttribute("idiomas", idiomas);
        request.setAttribute("tipos", tipos);

        request.getRequestDispatcher("vistas/vistas_docente/subirdoc.jsp").forward(request, response); // Se redirige la solicitud y la respuesta a la pagina subir doc
        // response se usa para pasar el objeto de respuesta HTTP a la página JSP, permitiendo a la JSP generar la respuesta final al cliente.
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

        HttpSession session = request.getSession(false); // Obtiene la sesión actual sin crear una nueva si no existe. Devuelve null si no hay una sesión existente.

        if (session == null) {
            request.setAttribute("error", "Sesión expirada. Por favor, vuelva a iniciar sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // VARIABLES
        String dni = (String) session.getAttribute("UserDoc"); // Recuperar el ID del usuario de la sesión
        int UserDoc = Integer.parseInt((String) dni);
        String tit = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String descrip = request.getParameter("descripcion");
        String year_publi = request.getParameter("año");
        String asig = request.getParameter("asignatura");
        String idioma = request.getParameter("idioma");
        String tipo = request.getParameter("tipo");
        
        // Obtiene la ruta del contexto del servlet en el sistema de archivos
        String contextPath = getServletContext().getRealPath("/");
        // Reemplaza una parte específica de la ruta del archivo para apuntar al directorio de la aplicación web en lugar del directorio de compilación
        String alteredFilePath = contextPath.replace("\\target\\PROYECTO_BVINEM-1.0-SNAPSHOT\\", "\\src\\main\\webapp");
        
        // Manejar el archivo PDF
        String rutaPDF = null;
        try {
            // Define el directorio de subida para los archivos PDF
            String UPLOAD_DIR = "pdfs";
            // Combina la ruta alterada con el directorio de subida para obtener la ruta completa de subida
            String uploadFilePath = alteredFilePath + File.separator + UPLOAD_DIR;
            
            // Obtiene la parte del archivo PDF del objeto request
            Part filePart = request.getPart("documentoPDF");
            if (filePart != null && filePart.getSize() > 0) {
                 // Verifica si el tamaño del archivo es mayor a 3 MB
                if (filePart.getSize() > 3 * 1024 * 1024) { // 3 MB
                    // Si el archivo es demasiado grande, establece un mensaje de error en la sesión y redirige a la página de subida de documentos
                    session.setAttribute("error", "El archivo PDF es demasiado grande. Máximo 3 MB.");
                    response.sendRedirect("subir_doc");
                    return;
                }
            }
            // Obtiene el nombre del archivo subido
            String fileName = filePart.getSubmittedFileName();
            // Construye la ruta completa donde se guardará el archivo
            String filePath = uploadFilePath + File.separator + fileName;
            // Escribe el archivo en la ruta especificada
            filePart.write(filePath);
            // Guarda la ruta relativa del archivo para almacenarla en la base de datos
            rutaPDF = UPLOAD_DIR + File.separator + fileName;
            
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            session.setAttribute("error", "Error al cargar el PDF");
            response.sendRedirect("subir_doc");
            return;
        }
        
        // Manejar la carga de imágenes (miniaturas del libro)
        String rutaMiniatura = null;
        try {
            // Define el directorio de subida para las miniaturas
            String UPLOAD_DIR = "miniaturas";
            // Combina la ruta alterada con el directorio de subida para obtener la ruta completa de subida
            String uploadFilePath = alteredFilePath + File.separator + UPLOAD_DIR;
            
            // Obtiene la parte del archivo de la miniatura del objeto request
            Part filePart = request.getPart("miniatura");
            // Verifica si el tamaño de la imagen es mayor a 1 MB
            if (filePart.getSize() > 1024 * 1024) { // 1 MB
                // Si la imagen es demasiado grande, establece un mensaje de error en la sesión y redirige a la página de subida de documentos
                session.setAttribute("error", "La imagen es demasiado grande. Máximo 1 MB.");
                response.sendRedirect("subir_doc");
                return;
            }
            // Obtiene el nombre del archivo subido
            String fileName = filePart.getSubmittedFileName();
            // Construye la ruta completa donde se guardará el archivo
            String filePath = uploadFilePath + File.separator + fileName;
            // Escribe el archivo en la ruta especificada
            filePart.write(filePath);
            // Guarda la ruta relativa de la imagen para almacenarla en la base de datos
            rutaMiniatura = UPLOAD_DIR + File.separator + fileName;
            
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            session.setAttribute("error", "Error al cargar la miniatura");
            response.sendRedirect("subir_doc");
            return;
        }
        
        // VALIDACIONES
        String errorMessage = Validador.validarTitulo(tit);        
        if (errorMessage == null) errorMessage = Validador.validarAutor(autor);
        if (errorMessage == null) errorMessage = Validador.validarDescripcion(descrip);        
        if (errorMessage == null) errorMessage = Validador.validarYear(year_publi);
        if (errorMessage == null) errorMessage = Validador.validarAsignatura(asig);
        if (errorMessage == null) errorMessage = Validador.validarIdioma(idioma);   
        if (errorMessage == null) errorMessage = Validador.validarTipoForo(tipo);     

        if (errorMessage != null) {
            session.setAttribute("error", errorMessage);
            response.sendRedirect(request.getHeader("Referer"));
            return;
        }

        // INSTANCIAMOS LA CLASE DOCUMENTO Y DOCUMENTODAO
        DocumentoDAO subirdoc = new DocumentoDAO();
        DocumentoClass documento = new DocumentoClass();

        // ASIGNAMOS LOS ATRIBUTOS A LA INSTANDICA DE LA CLASE DocumentoClass
        documento.setTitulo(tit);
        documento.setAutor(autor);
        documento.setDescripcion(descrip);
        documento.setYear(year_publi);
        documento.setUserDoc(UserDoc);
        documento.setAsignaturaId(Integer.parseInt(asig));
        documento.setIdiomaId(Integer.parseInt(idioma));
        documento.setTipoId(Integer.parseInt(tipo));
        documento.setMiniaturaPath(rutaMiniatura);
        documento.setArchivoPDF(rutaPDF);

        if (request.getParameter("subirDoc") != null) { // Verifica si el parámetro "subirDoc" está presente en la solicitud
            // Guardar la información en la base de datos
            try {
                subirdoc.SubirDocumento(documento);
                session.setAttribute("success", "Documento cargado correctamente");
                response.sendRedirect("sv_documentos");
            } catch (Exception error) {
                error.printStackTrace();
                response.getWriter().print("Error: " + error.getMessage());
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
