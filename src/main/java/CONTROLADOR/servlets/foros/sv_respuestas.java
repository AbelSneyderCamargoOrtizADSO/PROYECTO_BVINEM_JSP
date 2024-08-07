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
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("logueado") == null) {
            request.setAttribute("error", "Por favor, inicie sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
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

        if (session == null) {
            request.setAttribute("error", "Sesión expirada. Por favor, vuelva a iniciar sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

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
            switch (action) {
                case "enviarRespu":
                    if (respu == null || respu.trim().isEmpty() || respu.equals("<p><br></p>")) {
                        session.setAttribute("error", "La respuesta no puede estar vacía");
                        response.sendRedirect("mostrar_foro?id=" + idforo);
                        return;
                    }
                    if (idforo == null || idforo.trim().isEmpty()) {
                        session.setAttribute("error", "ID del foro vacio o no encontrado");
                        response.sendRedirect("mostrar_foro?id=" + idforo);
                        return;
                    }
                    respuDAO.subirRespuesta(respuesta);
                    session.setAttribute("success", "Respuesta de foro publicada correctamente");
                    response.sendRedirect("mostrar_foro?id=" + idforo);
                    break;

                case "editarRespu":
                    if (respu == null || respu.trim().isEmpty() || respu.equals("<p><br></p>")) {
                        session.setAttribute("error", "La respuesta no puede estar vacía");
                        response.sendRedirect("mostrar_foro?id=" + idforo);
                        return;
                    }
                    respuDAO.editarRespuesta(respuesta);
                    session.setAttribute("success", "Respuesta de foro editada correctamente");
                    response.sendRedirect("mostrar_foro?id=" + idforo);
                    break;

                case "eliminarRespu":
                    respuDAO.eliminarRespuesta(respuesta);
                    session.setAttribute("success", "Respuesta de foro eliminada correctamente");
                    response.sendRedirect("mostrar_foro?id=" + idforo);
                    break;

                default:
                    session.setAttribute("error", "Acción no reconocida");
                    response.sendRedirect("mostrar_foro?id=" + idforo);
                    break;
            }
        } catch (Exception error) {
            error.printStackTrace();
            response.getWriter().print("Error: " + error.getMessage());
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
