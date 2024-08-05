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
   
    private Conexion conexion;
    
    public UsuarioDAO() {
        this.conexion = new Conexion();
    }

    public void agregarUsuario(UsuarioClass usuario) throws SQLException {
        Connection conex = null;
        PreparedStatement statUsuario = null;

        try {
            conex = conexion.Conexion();

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
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, statUsuario, null);
        }
    }

    public void editarUsuario(int docAnterior, UsuarioClass usuario, boolean actualizarDocumentos) throws SQLException {
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

    private List<UsuarioClass> mostrarUsuarios(String query, int parametro1, String parametro2) {
        List<UsuarioClass> usuarios = new ArrayList<>();
        Connection conex = null;
        PreparedStatement stat = null;
        ResultSet rs = null;

        try {
            conex = conexion.Conexion();
            stat = conex.prepareStatement(query);

            stat.setInt(1, parametro1);
            if (parametro2 != null) {
                stat.setString(2, parametro2);
            }

            rs = stat.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("doc_usua");
                String nombres = rs.getString("nom_usua");
                String apellidos = rs.getString("ape_usua");
                String correo = rs.getString("correo_usua");
                String fecha_reg = rs.getString("fecha_registro");
                int estadoId = rs.getInt("id_estado_fk");
                int rol = rs.getInt("id_rol_fk");


                usuarios.add(new UsuarioClass(id, nombres, apellidos, correo, fecha_reg, estadoId, rol));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conexion.close(conex, stat, rs);
        }

        return usuarios;
    }

    public List<UsuarioClass> listarUsuarios(int rol) {
        String query = "SELECT * FROM tb_usuarios WHERE id_rol_fk = ? ORDER BY fecha_registro DESC";
        return mostrarUsuarios(query, rol, null);
    }

    public List<UsuarioClass> buscarUsuarioPorDocumento(int rol, String docUsuario) {
        String query = "SELECT * FROM tb_usuarios WHERE id_rol_fk = ? AND doc_usua LIKE ?";
        return mostrarUsuarios(query,rol, docUsuario + "%");
    }
}
