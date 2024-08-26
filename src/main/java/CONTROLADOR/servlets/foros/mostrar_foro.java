/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets.foros;

import MODELO.foros.ForoClass;
import MODELO.foros.ForoDAO;
import MODELO.foros.RespuestaClass;
import MODELO.foros.RespuestaForoDAO;
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
@WebServlet(name = "mostrar_foro", urlPatterns = {"/mostrar_foro"})
public class mostrar_foro extends HttpServlet {

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

        // Instanciar el DAO para manejar los datos del foro
        ForoDAO foroDAO = new ForoDAO();
        ForoClass foro = new ForoClass();

        // Obtener el ID del foro desde los parámetros de la solicitud
        String id = request.getParameter("id"); // id del foro

        // Verificar si se proporcionó un ID válido
        if (id != null && !id.isEmpty()) {
            foro.setId(Integer.parseInt(id)); // Asignar el ID al objeto foro
            foro = foroDAO.mostrarForoPorId(foro); // Obtener los detalles del foro por su ID

            // Verificar si se encontró un foro con el ID proporcionado
            if (foro != null) {
                RespuestaForoDAO respuestaDAO = new RespuestaForoDAO(); // Instanciar el DAO para manejar las respuestas del foro
                try {
                    // Obtener las respuestas asociadas con el foro
                    List<RespuestaClass> respuestas = respuestaDAO.mostrarRespuestasPorForo(foro);
                    // Establecer el foro y las respuestas como atributos en la solicitud
                    request.setAttribute("foro", foro);
                    request.setAttribute("respuestas", respuestas);
                } catch (Exception e) {
                    // Manejar cualquier excepción que ocurra al obtener las respuestas del foro
                    e.printStackTrace(); // Imprimir la traza del error para depuración
                    request.setAttribute("error", "Error al obtener las respuestas del foro."); // Establecer un mensaje de error
                }
                // Redirigir a la vista que muestra los detalles del foro
                request.getRequestDispatcher("vistas/mostrarforo.jsp").forward(request, response);
            } else {
                // Si no se encontró un foro con el ID proporcionado, redirigir a la lista de foros
                response.sendRedirect("sv_foros");
            }
        } else {
            // Si no se proporcionó un ID válido, redirigir a la lista de foros
            response.sendRedirect("sv_foros");
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        ForoDAO foroDAO = new ForoDAO();
        ForoClass foro = new ForoClass();

        // VARIABLES
        String titulo = request.getParameter("tituloForo");
        String descripcion = request.getParameter("foroEditado");
        String idf = request.getParameter("foroId");
        
        foro.setTitulo(titulo);
        foro.setId(Integer.parseInt(idf));
        foro.setDescripcion(descripcion);

        // Obtén el parámetro de acción
        String action = request.getParameter("action");

        try {
            // Evaluar la acción solicitada por el usuario
            switch (action) {
                case "editarForo":
                    // Verificar si el título del foro es nulo, está vacío o contiene solo un párrafo vacío
                    if (titulo == null || titulo.trim().isEmpty() || titulo.equals("<p><br></p>")) {
                        session.setAttribute("error", "El título del foro no puede estar vacío");
                        response.sendRedirect("mostrar_foro?id=" + idf);
                        return; // Finalizar la ejecución para evitar más procesamiento
                    }

                    // Verificar si la descripción del foro es nula, está vacía o contiene solo un párrafo vacío
                    if (descripcion == null || descripcion.trim().isEmpty() || descripcion.equals("<p><br></p>")) {
                        session.setAttribute("error", "La descripción del foro no puede estar vacía");
                        response.sendRedirect("mostrar_foro?id=" + idf);
                        return; // Finalizar la ejecución para evitar más procesamiento
                    }

                    // Editar el foro en la base de datos utilizando el objeto foroDAO
                    foroDAO.editarForo(foro);
                    session.setAttribute("success", "Foro editado correctamente");
                    response.sendRedirect("mostrar_foro?id=" + idf); // Redirigir al foro después de la edición
                    break;

                case "eliminarForo":
                    // Eliminar el foro de la base de datos utilizando el objeto foroDAO
                    foroDAO.eliminarForo(foro);
                    response.sendRedirect("sv_foros"); // Redirigir a la lista de foros después de la eliminación
                    break;
            }
        } catch (Exception error) {
            // Manejar cualquier excepción que ocurra durante la ejecución del código
            error.printStackTrace(); // Imprimir la traza del error para depuración
            response.getWriter().print("Error: " + error.getMessage()); // Mostrar el mensaje de error en la respuesta
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
