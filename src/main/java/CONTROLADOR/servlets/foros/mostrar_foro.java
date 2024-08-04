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
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("logueado") == null) {
            request.setAttribute("error", "Por favor, inicie sesión.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        ForoDAO foroDAO = new ForoDAO();
        ForoClass foro = new ForoClass();

        String id = request.getParameter("id"); // id del foro

        if (id != null && !id.isEmpty()) {
            foro.setId(Integer.parseInt(id));
            foro = foroDAO.mostrarForoPorId(foro);

            if (foro != null) {
                RespuestaForoDAO respuestaDAO = new RespuestaForoDAO();
                try {
                    List<RespuestaClass> respuestas = respuestaDAO.mostrarRespuestasPorForo(foro);
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
            switch (action) {
                case "editarForo":
                    if (titulo == null || titulo.trim().isEmpty() || titulo.equals("<p><br></p>")) {
                        session.setAttribute("error", "El título del foro no puede estar vacío");
                        response.sendRedirect("mostrar_foro?id=" + idf);
                        return;
                    }
                    if (descripcion == null || descripcion.trim().isEmpty() || descripcion.equals("<p><br></p>")) {
                        session.setAttribute("error", "La descripción del foro no puede estar vacía");
                        response.sendRedirect("mostrar_foro?id=" + idf);
                        return;
                    }

                    foroDAO.editarForo(foro);
                    session.setAttribute("success", "Foro editado correctamente");
                    response.sendRedirect("mostrar_foro?id=" + idf);
                    break;

                case "eliminarForo":
                    foroDAO.eliminarForo(foro);
                    response.sendRedirect("sv_foros");
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
