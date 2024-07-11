/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets;

import MODELO.AsignaturaClass;
import MODELO.DocumentoDAO;
import MODELO.FormDoc;
import MODELO.IdiomaClass;
import MODELO.TipoClass;
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
@MultipartConfig(maxFileSize = 50177215)
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

        String dni = (String) session.getAttribute("UserDoc"); // Recuperar el ID del usuario de la sesión

        int UserDoc = Integer.parseInt((String) dni);

        if (request.getParameter("submit") != null) {

            // TRAEMOS O TOMAMOS LOS DATOS INGRESADOS MEDIANTE getParameter TOMANDO COMO REFERENCIA EL NAME DE CADA INPUT
            String tit = request.getParameter("titulo");
            String autor = request.getParameter("autor");
            String descrip = request.getParameter("descripcion");
            String year_publi = request.getParameter("año");
            String asig = request.getParameter("asignatura");
            String idioma = request.getParameter("idioma");
            String tipo = request.getParameter("tipo");

            if (tit == null || tit.trim().isEmpty()) {
                session.setAttribute("error", "El titulo no puede estar vacio");
                response.sendRedirect("subir_doc");
                return;
            }

            if (autor == null || autor.trim().isEmpty()) {
                session.setAttribute("error", "El autor no puede estar vacio");
                response.sendRedirect("subir_doc");
                return;
            }

            if (descrip == null || descrip.trim().isEmpty()) {
                session.setAttribute("error", "La descripcion no puede estar vacia");
                response.sendRedirect("subir_doc");
                return;
            }

            // Manejar el archivo PDF
            // Declaración de la variable inputStream para almacenar el flujo de entrada del archivo PDF.
            InputStream inputStream = null;
            try {
                // Obtiene la parte del archivo PDF de la solicitud HTTP, usando el nombre del campo del formulario "documentoPDF".
                Part filePart = request.getPart("documentoPDF");

                // Verifica si la parte del archivo no es nula y si su tamaño es mayor que 0 (es decir, que se haya cargado un archivo).
                if (filePart != null && filePart.getSize() > 0) {
                    // Si el archivo es válido, obtiene el flujo de entrada del archivo PDF.
                    inputStream = filePart.getInputStream();
                }
            } catch (Exception ex) {
                // Establece un atributo de sesión llamado "error" con un mensaje de error que incluye el mensaje de la excepción.
                session.setAttribute("error", "Error al procesar el archivo: " + ex.getMessage());

                // Redirige al usuario a la página "subir_doc" para que pueda intentar nuevamente.
                response.sendRedirect("subir_doc");

                // Termina la ejecución del método doPost, ya que ocurrió un error.
                return;
            }

//            // Manejar la carga de imagenes (miniaturas del libro)
            String rutaMiniatura = null;
//            try {
//                Part parteImagen = request.getPart("miniatura");
//                if (parteImagen != null && parteImagen.getSize() > 0) {
//                    String nombreArchivo = Paths.get(parteImagen.getSubmittedFileName()).getFileName().toString();
//                    String directorioSubida = getServletContext().getRealPath("/") + File.separator + "miniaturas";
//                    File directorio = new File(directorioSubida);
//                    if (!directorio.exists()) {
//                        directorio.mkdirs();
//                    }
//                    File archivo = new File(directorio, nombreArchivo);
//                    parteImagen.write(archivo.getAbsolutePath());
//                    rutaMiniatura = "miniaturas/" + nombreArchivo;
//                }
//            } catch (Exception ex) {
//                session.setAttribute("error", "Error al procesar la miniatura: " + ex.getMessage());
//                response.sendRedirect("subir_doc");
//                return;
//            }

            // Guardar la información en la base de datos
            DocumentoDAO subirdoc = new DocumentoDAO();
            try {
                subirdoc.SubirDocumento(tit, autor, descrip, year_publi, UserDoc, asig, idioma, tipo, inputStream, rutaMiniatura);
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
