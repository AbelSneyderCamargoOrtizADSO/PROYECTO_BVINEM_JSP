/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.foros;

import MODELO.categorias.TipoForoClass;
import MODELO.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Método para obtener todos los tipos de foro activos. Envía la lista de tipos
 * de foro al controlador del formulario para agregar el foro.
 *
 * @return Una lista de objetos {@link TipoForoClass} que contiene todos los tipos de foro activos.
 * @see <a href="https://www.w3schools.com/java/java_arraylist.asp">Referencia - Arraylist en java</a>
 */
public class FormForo {
    // Crea una nueva lista de objetos TipoForoClass utilizando ArrayList para almacenar los tipos de foro.
    // Método para obtener todos tipos de foro
    public List<TipoForoClass> obtenerTiposForo() {
        List<TipoForoClass> tiposforo = new ArrayList<>();

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tb_tipo_foro WHERE estado = true";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_tp_foro");
                String nombre = rs.getString("nom_tp_foro");
                boolean estado = rs.getBoolean("estado");

                tiposforo.add(new TipoForoClass(id, nombre, estado));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de conexión, declaración y resultados
            conexion.close(conex, stat, rs);
        }

        return tiposforo;
    }
}
