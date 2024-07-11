
package my.proyecto_bvinem.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import MODELO.register_class;

@WebServlet(name = "sv_register", urlPatterns = {"/sv_register"})
public class sv_register extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        if (request.getParameter("submit") != null) {
            
                // TRAEMOS O TOMAMOS LOS DATOS INGRESADOS MEDIANTE getParameter TOMANDO COMO REFERENCIA EL NAME DE CADA INPUT
                String dniSt = request.getParameter("dni");
                String nom = request.getParameter("nombres");
                String ape = request.getParameter("apellidos");
                String correo = request.getParameter("correo");
                String contra = request.getParameter("password");
                String grado = request.getParameter("grado");
                
                //HACEMOS LA CONVERSION DE LOS DATOS QUE SON ENTEROS EN LA BASE DE DATOS
                int dni = Integer.parseInt(dniSt);
                
                register_class registerClass = new register_class();
                
                try {
                    registerClass.registrarUsuario(dni, nom, ape, correo, contra, grado);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } catch (Exception error) {
                    error.printStackTrace();
                    response.getWriter().print("Error: " + error.getMessage());
                }
            }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
