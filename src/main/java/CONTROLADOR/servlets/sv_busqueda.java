/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets;

import MODELO.BusquedaClass;
import MODELO.DocumentoClass;
import MODELO.foros.ForoClass;
import com.google.gson.Gson;
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
@WebServlet(name = "sv_busqueda", urlPatterns = {"/sv_busqueda"})
public class sv_busqueda extends HttpServlet {

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
    
        // Establecer la codificación de caracteres para la solicitud como UTF-8
        request.setCharacterEncoding("UTF-8");

        // Establecer el tipo de contenido de la respuesta como JSON y la codificación de caracteres como UTF-8
        response.setContentType("application/json; charset=UTF-8");

        // Obtener la sesión existente, pero no crear una nueva si no existe
        HttpSession session = request.getSession(false);

        // Verificar si la sesión es nula o si el usuario no ha iniciado sesión
        if (session == null || session.getAttribute("logueado") == null) {
            // Si no hay sesión o el usuario no ha iniciado sesión, redirigir a la página de inicio de sesión con un mensaje de error
            request.setAttribute("error", "Por favor, inicie sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return; // Finalizar la ejecución del método para evitar más procesamiento
        }

        // Obtener los parámetros de la solicitud
        String query = request.getParameter("query"); // La cadena de búsqueda proporcionada por el usuario
        String tipo = request.getParameter("tipo"); // El tipo de búsqueda (puede ser "libros" o "foros")

        // Crear una instancia de la clase de búsqueda y listas para almacenar los resultados
        BusquedaClass busqueda = new BusquedaClass();
        List<DocumentoClass> resultadosLibros = null;
        List<ForoClass> resultadosForos = null;

        // Determinar el tipo de búsqueda y realizar la búsqueda correspondiente
        if ("libros".equalsIgnoreCase(tipo)) {
            // Buscar documentos (libros) utilizando la cadena de búsqueda
            resultadosLibros = busqueda.buscarDocumentos(query);
        } else if ("foros".equalsIgnoreCase(tipo)) {
            // Buscar foros utilizando la cadena de búsqueda
            resultadosForos = busqueda.buscarForos(query);
        }

        // Establecer el tipo de contenido de la respuesta como JSON 
        response.setContentType("application/json");
        PrintWriter out = response.getWriter(); // Obtener el objeto PrintWriter para escribir la respuesta

        // Convertir los resultados de la búsqueda a JSON y escribirlos en la respuesta
        if ("libros".equalsIgnoreCase(tipo)) {
            out.write(new Gson().toJson(resultadosLibros)); // Convertir la lista de libros a JSON y escribir en la respuesta
        } else if ("foros".equalsIgnoreCase(tipo)) {
            out.write(new Gson().toJson(resultadosForos)); // Convertir la lista de foros a JSON y escribir en la respuesta
        }

        out.flush(); // Asegurar que todo el contenido escrito sea enviado al cliente
        // Link de consulta https://github.com/google/gson/blob/main/UserGuide.md 
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
