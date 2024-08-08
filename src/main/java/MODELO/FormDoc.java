/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import MODELO.categorias.AsignaturaClass;
import MODELO.categorias.TipoClass;
import MODELO.categorias.IdiomaClass;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja la obtención de datos necesarios para los formularios de documentos.
 * Utiliza la clase {@link Conexion} para manejar las conexiones a la base de datos.
 * 
 * @see Conexion
 * @see AsignaturaClass
 * @see IdiomaClass
 * @see TipoClass
 */
public class FormDoc {
    /**
     * Método para obtener todas las asignaturas activas de la base de datos.
     * 
     * @return Una lista de objetos {@link AsignaturaClass} que contiene todas las asignaturas activas.
     */
    // Método para obtener todas las asignaturas
    public List<AsignaturaClass> obtenerAsignaturas() {
        // Crear una lista vacía para almacenar las asignaturas que se obtendrán de la base de datos
        List<AsignaturaClass> asignaturas = new ArrayList<>();

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        // Definir la consulta SQL para obtener las asignaturas
        String sql = "SELECT * FROM tb_asignaturas WHERE estado = true";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            // Iterar sobre los resultados de la consulta
            while (rs.next()) {
                // Obtener el valor de la columna 'id_asig' del resultado actual
                int id = rs.getInt("id_asig");

                // Obtener el valor de la columna 'nom_asig' del resultado actual
                String nombre = rs.getString("nom_asig");
                boolean estado = rs.getBoolean("estado");

                // Crear un nuevo objeto AsignaturaClass usando los valores obtenidos y agregarlo a la lista
                asignaturas.add(new AsignaturaClass(id, nombre, estado));
            }
        } catch (Exception e) {
            // Capturar cualquier excepción que pueda ocurrir y imprimir el stack trace
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de conexión, declaración y resultados
            conexion.close(conex, stat, rs);
        }

        // Devolver la lista de asignaturas obtenidas de la base de datos
        return asignaturas;
    }
    
    /**
     * Método para obtener todos los idiomas activos de la base de datos.
     * 
     * @return Una lista de objetos {@link IdiomaClass} que contiene todos los idiomas activos.
     */
    // Método para obtener todos los idiomas
    public List<IdiomaClass> obtenerIdiomas() {
        List<IdiomaClass> idiomas = new ArrayList<>();

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tb_idiomas WHERE estado = true";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_idioma");
                String nombre = rs.getString("nom_idioma");
                boolean estado = rs.getBoolean("estado");

                idiomas.add(new IdiomaClass(id, nombre, estado));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de conexión, declaración y resultados
            conexion.close(conex, stat, rs);
        }

        return idiomas;
    }
    
    /**
     * Método para obtener todos los tipos de documentos activos de la base de datos.
     * 
     * @return Una lista de objetos {@link TipoClass} que contiene todos los tipos de documentos activos.
     */
    // Método para obtener todos los tipos de documentos
    public List<TipoClass> obtenerTipos() {
        List<TipoClass> tipos = new ArrayList<>();

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM tb_tipo_doc WHERE estado = true";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(sql);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_tipo");
                String nombre = rs.getString("nom_tipo");
                boolean estado = rs.getBoolean("estado");

                tipos.add(new TipoClass(id, nombre, estado));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de conexión, declaración y resultados
            conexion.close(conex, stat, rs);
        }

        return tipos;
    }
}
