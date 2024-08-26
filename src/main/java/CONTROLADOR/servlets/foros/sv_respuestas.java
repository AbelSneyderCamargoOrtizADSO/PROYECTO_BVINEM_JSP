/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CONTROLADOR.servlets.foros;

import MODELO.foros.RespuestaClass;
import MODELO.foros.RespuestaForoDAO;
import MODELO.usuarios.Validador;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "sv_respuestas", urlPatterns = {"/sv_respuestas"})
public class sv_respuestas extends HttpServlet {

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
            return; // Finalizar la ejecución del método para evitar que continúe
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

        RespuestaForoDAO respuDAO = new RespuestaForoDAO();
        RespuestaClass respuesta = new RespuestaClass();

        // VARIABLES
        // Recuperar y castear a int el documento del usario
        String dni = (String) session.getAttribute("UserDoc");
        int UserDoc = Integer.parseInt((String) dni);
        String respuestaIdStr = request.getParameter("respuestaId");
        if (respuestaIdStr != null && !respuestaIdStr.trim().isEmpty()) {
            int respuestaId = Integer.parseInt(respuestaIdStr);
            respuesta.setId(respuestaId);
        }
        String respu = request.getParameter("respuesta");
        String idforo = request.getParameter("foroId");

        // ASIGNAMOS LOS SETTERS
        respuesta.setContenido(respu);
        respuesta.setIdForo(Integer.parseInt(idforo));
        respuesta.setUsuarioId(UserDoc);

        // Acción a realizar
        String action = request.getParameter("action");

        try {
            // Evaluar la acción solicitada por el usuario
            switch (action) {
                case "enviarRespu":
                    // Verificar si la respuesta es nula, está vacía o contiene solo un párrafo vacío
                    if (respu == null || respu.trim().isEmpty() || respu.equals("<p><br></p>")) {
                        session.setAttribute("error", "La respuesta no puede estar vacía");
                        response.sendRedirect("mostrar_foro?id=" + idforo);
                        return; // Finalizar la ejecución para evitar más procesamiento
                    }
                    // Verificar si el ID del foro es nulo o está vacío
                    if (idforo == null || idforo.trim().isEmpty()) {
                        session.setAttribute("error", "ID del foro vacio o no encontrado");
                        response.sendRedirect("mostrar_foro?id=" + idforo);
                        return; // Finalizar la ejecución para evitar más procesamiento
                    }
                    // Subir la respuesta a la base de datos
                    respuDAO.subirRespuesta(respuesta);
                    session.setAttribute("success", "Respuesta de foro publicada correctamente");
                    response.sendRedirect("mostrar_foro?id=" + idforo);
                    break;

                case "editarRespu":
                    // Verificar si la respuesta es nula, está vacía o contiene solo un párrafo vacío
                    if (respu == null || respu.trim().isEmpty() || respu.equals("<p><br></p>")) {
                        session.setAttribute("error", "La respuesta no puede estar vacía");
                        response.sendRedirect("mostrar_foro?id=" + idforo);
                        return; // Finalizar la ejecución para evitar más procesamiento
                    }
                    // Editar la respuesta en la base de datos
                    respuDAO.editarRespuesta(respuesta);
                    session.setAttribute("success", "Respuesta de foro editada correctamente");
                    response.sendRedirect("mostrar_foro?id=" + idforo);
                    break;

                case "eliminarRespu":
                    // Eliminar la respuesta de la base de datos
                    respuDAO.eliminarRespuesta(respuesta);
                    session.setAttribute("success", "Respuesta de foro eliminada correctamente");
                    response.sendRedirect("mostrar_foro?id=" + idforo);
                    break;

                default:
                    // Si la acción no es reconocida, establecer un mensaje de error
                    session.setAttribute("error", "Acción no reconocida");
                    response.sendRedirect("mostrar_foro?id=" + idforo);
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
