/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.usuarios;

import MODELO.Conexion;
import MODELO.HashUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Abelito
 */
public class UsuarioDAO {

    public void agregarUsuario(UsuarioClass usuario, String tablaAdicional) throws SQLException {
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement statUsuario = null;
        PreparedStatement statAdicional = null;

        try {
            conex = conexion.Conexion();
            conex.setAutoCommit(false);

            // Encriptar la contraseña antes de guardarla
            String hashedPassword = HashUtil.hashPassword(usuario.getPass());
            usuario.setPass(hashedPassword);

            // Insertar en tb_usuarios
            String queryUsuario = "INSERT INTO tb_usuarios (doc_usua, nom_usua, ape_usua, correo_usua, password, id_rol_fk, fecha_registro) VALUES (?, ?, ?, ?, ?, ?, NOW())";
            statUsuario = conex.prepareStatement(queryUsuario);
            statUsuario.setInt(1, usuario.getDocUsu());
            statUsuario.setString(2, usuario.getNombre());
            statUsuario.setString(3, usuario.getApellido());
            statUsuario.setString(4, usuario.getCorreo());
            statUsuario.setString(5, usuario.getPass());
            statUsuario.setInt(6, usuario.getRol());
            statUsuario.executeUpdate();

            // Insertar en la tabla adicional (tb_docente o tb_estudiante)
            String queryAdicional;
            if ("tb_estudiante".equals(tablaAdicional) && usuario.getGrado() != null) {
                queryAdicional = "INSERT INTO " + tablaAdicional + " (id_grado_fk, doc_usua_fk) VALUES (?, ?)";
                statAdicional = conex.prepareStatement(queryAdicional);
                statAdicional.setString(1, usuario.getGrado());
                statAdicional.setInt(2, usuario.getDocUsu());
            } else {
                queryAdicional = "INSERT INTO " + tablaAdicional + " (doc_usua_fk) VALUES (?)";
                statAdicional = conex.prepareStatement(queryAdicional);
                statAdicional.setInt(1, usuario.getDocUsu());
            }
            statAdicional.executeUpdate();

            conex.commit();
        } catch (SQLException e) {
            if (conex != null) {
                conex.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, statUsuario, null);
            conexion.close(null, statAdicional, null);
        }
    }

    public void editarUsuario(int docAnterior, UsuarioClass usuario, String tablaAdicional, boolean actualizarDocumentos) throws SQLException {
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement statement = null;
        PreparedStatement disableFKChecks = null;
        PreparedStatement enableFKChecks = null;

        try {
            conex = conexion.Conexion();
            conex.setAutoCommit(false);

            // Deshabilitar la verificación de claves foráneas
            disableFKChecks = conex.prepareStatement("SET FOREIGN_KEY_CHECKS=0");
            disableFKChecks.executeUpdate();

            // Actualizar en tb_respuestas
            String queryRespuestas = "UPDATE tb_respuesta_foro SET doc_usua_fk = ? WHERE doc_usua_fk = ?";
            statement = conex.prepareStatement(queryRespuestas);
            statement.setInt(1, usuario.getDocUsu());
            statement.setInt(2, docAnterior);
            statement.executeUpdate();

            // Actualizar en tb_foro
            String queryForos = "UPDATE tb_foro SET doc_usua_fk = ? WHERE doc_usua_fk = ?";
            statement = conex.prepareStatement(queryForos);
            statement.setInt(1, usuario.getDocUsu());
            statement.setInt(2, docAnterior);
            statement.executeUpdate();

            // Si es necesario actualizar tb_documentos (solo para docentes)
            if (actualizarDocumentos) {
                String queryDocumentos = "UPDATE tb_documento SET doc_docente_fk = ? WHERE doc_docente_fk = ?";
                statement = conex.prepareStatement(queryDocumentos);
                statement.setInt(1, usuario.getDocUsu());
                statement.setInt(2, docAnterior);
                statement.executeUpdate();
            }

            // Actualizar en la tabla adicional (tb_docente o tb_estudiante)
            String queryAdicional = "UPDATE " + tablaAdicional + " SET doc_usua_fk = ? WHERE doc_usua_fk = ?";
            statement = conex.prepareStatement(queryAdicional);
            statement.setInt(1, usuario.getDocUsu());
            statement.setInt(2, docAnterior);
            statement.executeUpdate();

            // Actualizar en tb_usuarios
            String queryUsuario;
            if (usuario.getPass() != null) {
                // Encriptar la nueva contraseña antes de actualizar
                String hashedPassword = HashUtil.hashPassword(usuario.getPass());
                usuario.setPass(hashedPassword);
                queryUsuario = "UPDATE tb_usuarios SET doc_usua = ?, nom_usua = ?, ape_usua = ?, correo_usua = ?, password = ? WHERE doc_usua = ?";
            } else {
                queryUsuario = "UPDATE tb_usuarios SET doc_usua = ?, nom_usua = ?, ape_usua = ?, correo_usua = ? WHERE doc_usua = ?";
            }
            statement = conex.prepareStatement(queryUsuario);
            statement.setInt(1, usuario.getDocUsu());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getApellido());
            statement.setString(4, usuario.getCorreo());
            if (usuario.getPass() != null) {
                statement.setString(5, usuario.getPass());
                statement.setInt(6, docAnterior);
            } else {
                statement.setInt(5, docAnterior);
            }
            statement.executeUpdate();

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

            conexion.close(conex, statement, null);
        }
    }

    public void cambiarEstadoUsuario(UsuarioClass docente, int estado) throws SQLException {
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement statUsuario = null;

        try {
            conex = conexion.Conexion();
            String query = "UPDATE tb_usuarios SET id_estado_fk = ? WHERE doc_usua = ?";
            statUsuario = conex.prepareStatement(query);
            statUsuario.setInt(1, estado);
            statUsuario.setInt(2, docente.getDocUsu());
            statUsuario.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void inhabilitarUsuario(UsuarioClass docente) throws SQLException {
        cambiarEstadoUsuario(docente, 0);
    }

    public void habilitarUsuario(UsuarioClass docente) throws SQLException {
        cambiarEstadoUsuario(docente, 1);
    }

    private List<UsuarioClass> mostrarUsuarios(String query, String parametro) {
        List<UsuarioClass> usuarios = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(query);

            if (parametro != null) {
                stat.setString(1, parametro);
            }

            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("doc_usua");
                String nombres = rs.getString("nom_usua");
                String apellidos = rs.getString("ape_usua");
                String correo = rs.getString("correo_usua");
                String fecha_reg = rs.getString("fecha_registro");
                int estadoId = rs.getInt("id_estado_fk");

                usuarios.add(new UsuarioClass(id, nombres, apellidos, correo, fecha_reg, estadoId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.close(conex, stat, rs);
        }

        return usuarios;
    }

    public List<UsuarioClass> listarUsuarios(String tablaAdicional) {
        String query = "SELECT tb_usuarios.*, " + tablaAdicional + ".doc_usua_fk "
                + "FROM " + tablaAdicional + " "
                + "JOIN tb_usuarios ON " + tablaAdicional + ".doc_usua_fk = tb_usuarios.doc_usua "
                + "ORDER BY tb_usuarios.fecha_registro DESC";
        return mostrarUsuarios(query, null);
    }

    public List<UsuarioClass> buscarUsuarioPorDocumento(String tablaAdicional, String docUsuario) {
        String query = "SELECT tb_usuarios.*, " + tablaAdicional + ".doc_usua_fk "
                + "FROM " + tablaAdicional + " "
                + "JOIN tb_usuarios ON " + tablaAdicional + ".doc_usua_fk = tb_usuarios.doc_usua "
                + "WHERE tb_usuarios.doc_usua LIKE ?";
        return mostrarUsuarios(query, docUsuario + "%");
    }
}
