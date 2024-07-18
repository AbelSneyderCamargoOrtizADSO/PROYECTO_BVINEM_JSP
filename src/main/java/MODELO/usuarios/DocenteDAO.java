/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.usuarios;

import MODELO.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abelito
 */
public class DocenteDAO {

    public void agregarDocente(int docDocente, String nombre, String apellido, String correo, String password) throws SQLException {
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement statUsuario = null;
        PreparedStatement statDocente = null;

        try {
            conex = conexion.Conexion();
            conex.setAutoCommit(false);

            // Insertar en tb_usuarios
            String queryUsuario = "INSERT INTO tb_usuarios (doc_usua, nom_usua, ape_usua, correo_usua, password, id_rol_fk, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, NOW())";
            statUsuario = conex.prepareStatement(queryUsuario);
            statUsuario.setInt(1, docDocente);
            statUsuario.setString(2, nombre);
            statUsuario.setString(3, apellido);
            statUsuario.setString(4, correo);
            statUsuario.setString(5, password);
            statUsuario.setInt(6, 2);
            statUsuario.executeUpdate();

            // Insertar en tb_docente
            String queryDocente = "INSERT INTO tb_docente (doc_usua_fk) VALUES (?)";
            statDocente = conex.prepareStatement(queryDocente);
            statDocente.setInt(1, docDocente);
            statDocente.executeUpdate();

            conex.commit();
        } catch (SQLException e) {
            if (conex != null) {
                conex.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, statUsuario, null);
            conexion.close(null, statDocente, null);
        }
    }

    public List<UsuarioClass> listarDocentes() {
        List<UsuarioClass> docentes = new ArrayList<>();

        // Crear una instancia de la clase de conexión
        Conexion conexion = new Conexion();

        // Inicializar las variables de conexión, declaración y resultados en null
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "SELECT tb_usuarios.doc_usua, tb_usuarios.nom_usua, tb_usuarios.ape_usua, tb_usuarios.correo_usua, tb_usuarios.password, tb_usuarios.fecha_registro "
                + "FROM tb_docente "
                + "JOIN tb_usuarios ON tb_docente.doc_usua_fk = tb_usuarios.doc_usua "
                + "ORDER BY tb_usuarios.fecha_registro DESC";

        try {
            // Obtener una conexión a la base de datos
            conex = conexion.Conexion();

            // Preparar la declaración SQL
            stat = conex.prepareStatement(query);

            // Ejecutar la consulta y obtener el resultado
            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("doc_usua");
                String nombres = rs.getString("nom_usua");
                String apellidos = rs.getString("ape_usua");
                String correo = rs.getString("correo_usua");
                String fecha_reg = rs.getString("fecha_registro");
                String pass = rs.getString("password");

                docentes.add(new UsuarioClass(id, nombres, apellidos, correo, fecha_reg, pass));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Cerrar
            conexion.close(conex, stat, rs);
        }

        return docentes;
    }

    public void editarDocente(int docDocente, int nuevoDocDocente, String nombre, String apellido, String correo, String password) throws SQLException {
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement statUsuario = null;
        PreparedStatement statDocente = null;
        PreparedStatement statDocumentos = null;
        PreparedStatement statForos = null;
        PreparedStatement statRespuestas = null;
        PreparedStatement disableFKChecks = null;
        PreparedStatement enableFKChecks = null;

        try {
            conex = conexion.Conexion();
            conex.setAutoCommit(false);

            // Deshabilitar la verificación de claves foráneas
            disableFKChecks = conex.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            disableFKChecks.executeUpdate();

            // Actualizar en tb_usuarios
            String queryUsuario = "UPDATE tb_usuarios SET doc_usua = ?, nom_usua = ?, ape_usua = ?, correo_usua = ?, password = ? WHERE doc_usua = ?";
            statUsuario = conex.prepareStatement(queryUsuario);
            statUsuario.setInt(1, nuevoDocDocente);
            statUsuario.setString(2, nombre);
            statUsuario.setString(3, apellido);
            statUsuario.setString(4, correo);
            statUsuario.setString(5, password);
            statUsuario.setInt(6, docDocente);
            statUsuario.executeUpdate();

            // Actualizar en tb_docente
            String queryDocente = "UPDATE tb_docente SET doc_usua_fk = ? WHERE doc_usua_fk = ?";
            statDocente = conex.prepareStatement(queryDocente);
            statDocente.setInt(1, nuevoDocDocente);
            statDocente.setInt(2, docDocente);
            statDocente.executeUpdate();

            // Actualizar en tb_documentos
            String queryDocumentos = "UPDATE tb_documento SET doc_docente_fk = ? WHERE doc_docente_fk = ?";
            statDocumentos = conex.prepareStatement(queryDocumentos);
            statDocumentos.setInt(1, nuevoDocDocente);
            statDocumentos.setInt(2, docDocente);
            statDocumentos.executeUpdate();

            // Actualizar en tb_foro
            String queryForos = "UPDATE tb_foro SET doc_usua_fk = ? WHERE doc_usua_fk = ?";
            statForos = conex.prepareStatement(queryForos);
            statForos.setInt(1, nuevoDocDocente);
            statForos.setInt(2, docDocente);
            statForos.executeUpdate();

            // Actualizar en tb_respuestas
            String queryRespuestas = "UPDATE tb_respuesta_foro SET doc_usua_fk = ? WHERE doc_usua_fk = ?";
            statRespuestas = conex.prepareStatement(queryRespuestas);
            statRespuestas.setInt(1, nuevoDocDocente);
            statRespuestas.setInt(2, docDocente);
            statRespuestas.executeUpdate();

            // Habilitar la verificación de claves foráneas
            enableFKChecks = conex.prepareStatement("SET FOREIGN_KEY_CHECKS=1");
            enableFKChecks.executeUpdate();

            conex.commit();
        } catch (SQLException e) {
            if (conex != null) {
                conex.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (disableFKChecks != null) {
                disableFKChecks.close();
            }
            if (enableFKChecks != null) {
                enableFKChecks.close();
            }
            if (statDocumentos != null) {
                statDocumentos.close();
            }
            if (statForos != null) {
                statForos.close();
            }
            if (statRespuestas != null) {
                statRespuestas.close();
            }
            conexion.close(conex, statUsuario, null);
            conexion.close(null, statDocente, null);
        }
    }

    public void eliminarDocente(int docDocente) throws SQLException {
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement statRespuestaForo = null;
        PreparedStatement statForo = null;
        PreparedStatement statDocumento = null;
        PreparedStatement statDocente = null;
        PreparedStatement statUsuario = null;

        try {
            conex = conexion.Conexion();
            conex.setAutoCommit(false);

            // Eliminar de tb_respuesta_foro
            String queryRespuestaForo = "DELETE FROM tb_respuesta_foro WHERE doc_usua_fk = ?";
            statRespuestaForo = conex.prepareStatement(queryRespuestaForo);
            statRespuestaForo.setInt(1, docDocente);
            statRespuestaForo.executeUpdate();

            // Eliminar de tb_foro
            String queryForo = "DELETE FROM tb_foro WHERE doc_usua_fk = ?";
            statForo = conex.prepareStatement(queryForo);
            statForo.setInt(1, docDocente);
            statForo.executeUpdate();

            // Eliminar de tb_documento
            String queryDocumento = "DELETE FROM tb_documento WHERE doc_docente_fk = ?";
            statDocumento = conex.prepareStatement(queryDocumento);
            statDocumento.setInt(1, docDocente);
            statDocumento.executeUpdate();

            // Eliminar de tb_docente
            String queryDocente = "DELETE FROM tb_docente WHERE doc_usua_fk = ?";
            statDocente = conex.prepareStatement(queryDocente);
            statDocente.setInt(1, docDocente);
            statDocente.executeUpdate();

            // Eliminar de tb_usuarios
            String queryUsuario = "DELETE FROM tb_usuarios WHERE doc_usua = ?";
            statUsuario = conex.prepareStatement(queryUsuario);
            statUsuario.setInt(1, docDocente);
            statUsuario.executeUpdate();

            conex.commit();
        } catch (SQLException e) {
            if (conex != null) {
                conex.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, statRespuestaForo, null);

            if (statForo != null) {
                statForo.close();
            }

            if (statDocumento != null) {
                statDocumento.close();
            }

            if (statDocente != null) {
                statDocente.close();
            }

            if (statUsuario != null) {
                statUsuario.close();
            }
        }
    }

    public List<UsuarioClass> buscarDocentePorDocumento(String docDocente) {
        List<UsuarioClass> docentes = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        String query = "SELECT tb_usuarios.doc_usua, tb_usuarios.nom_usua, tb_usuarios.ape_usua, tb_usuarios.correo_usua, tb_usuarios.password, tb_usuarios.fecha_registro "
                + "FROM tb_docente "
                + "JOIN tb_usuarios ON tb_docente.doc_usua_fk = tb_usuarios.doc_usua "
                + "WHERE tb_usuarios.doc_usua LIKE ?";

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(query);
            stat.setString(1, docDocente + "%");
            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("doc_usua");
                String nombres = rs.getString("nom_usua");
                String apellidos = rs.getString("ape_usua");
                String correo = rs.getString("correo_usua");
                String fecha_reg = rs.getString("fecha_registro");
                String pass = rs.getString("password");

                docentes.add(new UsuarioClass(id, nombres, apellidos, correo, fecha_reg, pass));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.close(conex, stat, rs);
        }

        return docentes;
    }
}
