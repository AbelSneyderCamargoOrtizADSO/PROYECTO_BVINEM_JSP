/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets;

import MODELO.DocumentoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Abelito
 */
@WebServlet(name = "mostrar_pdf", urlPatterns = {"/mostrar_pdf"})
public class mostrar_pdf extends HttpServlet {

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
        int id = Integer.parseInt(request.getParameter("id"));
        // Obtiene el parámetro "id" de la solicitud HTTP (que se pasa en la URL) y lo convierte en un entero. 
        // Este ID se utiliza para identificar el documento PDF que se va a mostrar.   

        DocumentoDAO documentoDAO = new DocumentoDAO(); // Se crea una nueva instancia de la clase DocumentoDAO, que maneja las operaciones de acceso a datos para los documentos.

        byte[] pdfData = documentoDAO.MostrarDocumento(id);
        // Llama al método obtenerPDF del DocumentoDAO, pasando el ID del documento. 
        // Este método devuelve un array de bytes que contiene los datos del PDF almacenado en la base de datos.

        if (pdfData != null) {
            // Verifica si los datos del PDF no son nulos. Esto significa que se encontró un documento PDF con el ID proporcionado.

            response.setContentType("application/pdf");
            // Establece el tipo de contenido de la respuesta HTTP como "application/pdf". 
            // Esto indica al navegador que el contenido de la respuesta es un archivo PDF.

            response.setContentLength(pdfData.length);
            // Establece la longitud del contenido de la respuesta HTTP a la longitud del array de bytes del PDF.

            response.getOutputStream().write(pdfData);
            // Escribe los datos del PDF en el flujo de salida de la respuesta HTTP. 
            // Esto envía el archivo PDF al navegador del cliente para que se muestre.
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            // Si los datos del PDF son nulos, significa que no se encontró un documento con el ID proporcionado.
            // Envía un error 404 (Not Found) como respuesta HTTP.
        }
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
