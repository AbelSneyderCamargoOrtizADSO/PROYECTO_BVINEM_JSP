/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets;

import MODELO.AsignaturaClass;
import MODELO.DocumentoClass;
import MODELO.DocumentoDAO;
import MODELO.FormDoc;
import MODELO.IdiomaClass;
import MODELO.TipoClass;
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
        FormDoc formDoc = new FormDoc();

        List<AsignaturaClass> asignaturas = formDoc.obtenerAsignaturas();
        List<IdiomaClass> idiomas = formDoc.obtenerIdiomas();
        List<TipoClass> tipos = formDoc.obtenerTipos();

        request.setAttribute("asignaturas", asignaturas);
        request.setAttribute("idiomas", idiomas);
        request.setAttribute("tipos", tipos);

        request.getRequestDispatcher("vistas/vistas_docente/subirdoc.jsp").forward(request, response);

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
        
        // Manejar el archivo PDF
        // Declaración de la variable inputStream para almacenar el flujo de entrada del archivo PDF.
        InputStream inputStream = null;
        try {
            // Obtiene la parte del archivo PDF de la solicitud HTTP, usando el nombre del campo del formulario "documentoPDF".
            Part filePart = request.getPart("documentoPDF");

            // Verifica si la parte del archivo no es nula y si su tamaño es mayor que 0 (es decir, que se haya cargado un archivo).
            if (filePart != null && filePart.getSize() > 0) {
                if (filePart.getSize() > 3 * 1024 * 1024) { // 3 MB
                    session.setAttribute("error", "El archivo PDF es demasiado grande. Máximo 3 MB.");
                    response.sendRedirect("subir_doc");
                    return;
                }
                // Si el archivo es válido, obtiene el flujo de entrada del archivo PDF.
                inputStream = filePart.getInputStream();
            }
        } catch (Exception ex) {
            session.setAttribute("error", "Error al procesar el archivo, revise si lo ha cargado");
            response.sendRedirect("subir_doc");
            return;
        }
        
        // Manejar la carga de imágenes (miniaturas del libro)
        String rutaMiniatura = null;
        try {
            String UPLOAD_DIR = "miniaturas";
            String contextPath = getServletContext().getRealPath("/");
            String alteredFilePath = contextPath.replace("\\target\\PROYECTO_BVINEM-1.0-SNAPSHOT\\", "\\src\\main\\webapp");
            String uploadFilePath = alteredFilePath + File.separator + UPLOAD_DIR;

            File uploadDir = new File(uploadFilePath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            Part filePart = request.getPart("miniatura");
            if (filePart.getSize() > 1024 * 1024) { // 1 MB
                session.setAttribute("error", "La imagen es demasiado grande. Máximo 1 MB.");
                response.sendRedirect("subir_doc");
                return;
            }
            String fileName = filePart.getSubmittedFileName();
            String filePath = uploadFilePath + File.separator + fileName;
            filePart.write(filePath);
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

        // ASIGNAMOS A LA CLASE
        documento.setTitulo(tit);
        documento.setAutor(autor);
        documento.setDescripcion(descrip);
        documento.setYear(year_publi);
        documento.setUserDoc(UserDoc);
        documento.setAsignaturaId(Integer.parseInt(asig));
        documento.setIdiomaId(Integer.parseInt(idioma));
        documento.setTipoId(Integer.parseInt(tipo));
        documento.setMiniaturaPath(rutaMiniatura);
        documento.setArchivoPDF(inputStream);

        if (request.getParameter("subirDoc") != null) {
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
