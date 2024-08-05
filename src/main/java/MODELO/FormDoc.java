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
 *
 * @author Abelito
 */
public class FormDoc {

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
        String sql = "SELECT id_asig, nom_asig FROM tb_asignaturas";

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

                // Crear un nuevo objeto AsignaturaClass usando los valores obtenidos y agregarlo a la lista
                asignaturas.add(new AsignaturaClass(id, nombre));
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

    // Método para obtener todos los idiomas
    public List<IdiomaClass> obtenerIdiomas() {
        List<IdiomaClass> idiomas = new ArrayList<>();

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT id_idioma, nom_idioma FROM tb_idiomas";

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
                idiomas.add(new IdiomaClass(id, nombre));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos de conexión, declaración y resultados
            conexion.close(conex, stat, rs);
        }

        return idiomas;
    }

    // Método para obtener todos los tipos de documentos
    public List<TipoClass> obtenerTipos() {
        List<TipoClass> tipos = new ArrayList<>();

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String sql = "SELECT id_tipo, nom_tipo FROM tb_tipo_doc";

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
                tipos.add(new TipoClass(id, nombre));
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
