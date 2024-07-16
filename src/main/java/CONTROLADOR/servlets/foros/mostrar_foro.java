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
        String id = request.getParameter("id"); // id del foro

        if (id != null && !id.isEmpty()) {
            ForoDAO foroDAO = new ForoDAO();
            ForoClass foro = foroDAO.mostrarForoPorId(Integer.parseInt(id));
            if (foro != null) {
                RespuestaForoDAO respuestaDAO = new RespuestaForoDAO();
                try {
                    List<RespuestaClass> respuestas = respuestaDAO.mostrarRespuestasPorForo(Integer.parseInt(id));
                    request.setAttribute("foro", foro);
                    request.setAttribute("respuestas", respuestas);
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Error al obtener las respuestas del foro.");
                }
                request.getRequestDispatcher("vistas/mostrarforo.jsp").forward(request, response);
            } else {
                response.sendRedirect("sv_foros");
            }
        } else {
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

        if (session == null) {
            request.setAttribute("error", "Sesión expirada. Por favor, vuelva a iniciar sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // Recuperar y castear a int el documento del usario
        String dni = (String) session.getAttribute("UserDoc");
        int UserDoc = Integer.parseInt((String) dni);

        // ENVIAR RESPUESTA
        if (request.getParameter("enviarRespu") != null) {
            String respu = request.getParameter("respuesta");
            String idf = request.getParameter("foroId");

            if (respu == null || respu.trim().isEmpty() || respu.equals("<p><br></p>")) {
                session.setAttribute("error", "La respuesta no puede estar vacía");
                response.sendRedirect("mostrar_foro?id=" + idf);
                return;
            }

            if (idf == null || idf.trim().isEmpty()) {
                session.setAttribute("error", "ID del foro vacio o no encontrado");
                response.sendRedirect("mostrar_foro?id=" + idf);
                return;
            }

            RespuestaForoDAO respuDAO = new RespuestaForoDAO();

            try {
                respuDAO.subirRespuesta(respu, idf, UserDoc);
                session.setAttribute("success", "Respuesta de foro publicada correctamente");
                response.sendRedirect("mostrar_foro?id=" + idf);
            } catch (Exception error) {
                error.printStackTrace();
                response.getWriter().print("Error: " + error.getMessage());
            }
        }

        // ELIMINAR RESPUESTA
        if (request.getParameter("eliminarRespu") != null) {
            int respuestaId = Integer.parseInt(request.getParameter("respuestaId"));
            String idf = request.getParameter("foroId");

            RespuestaForoDAO respuestadao = new RespuestaForoDAO();

            try {
                respuestadao.eliminarRespuesta(respuestaId);
                session.setAttribute("success", "Respuesta de foro eliminada correctamente");
                response.sendRedirect("mostrar_foro?id=" + idf);
            } catch (Exception error) {
                error.printStackTrace();
                response.getWriter().print("Error: " + error.getMessage());
            }
        }

        // EDITAR RESPUESTA
        if (request.getParameter("editarRespu") != null) {
            String respuestaEditada = request.getParameter("respuestaEditada");
            String idf = request.getParameter("foroId");
            int respuestaId = Integer.parseInt(request.getParameter("respuestaIdEdit"));

            if (respuestaEditada == null || respuestaEditada.trim().isEmpty() || respuestaEditada.equals("<p><br></p>")) {
                session.setAttribute("error", "La respuesta no puede estar vacía");
                response.sendRedirect("mostrar_foro?id=" + idf);
                return;
            }

            RespuestaForoDAO respuDAO = new RespuestaForoDAO();

            try {
                respuDAO.editarRespuesta(respuestaId, respuestaEditada);
                session.setAttribute("success", "Respuesta de foro editada correctamente");
                response.sendRedirect("mostrar_foro?id=" + idf);
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
