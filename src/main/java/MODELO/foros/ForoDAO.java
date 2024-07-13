/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO.foros;

import MODELO.Conexion;
import java.io.InputStream;
import static java.lang.System.out;
import java.sql.*;

/**
 *
 * @author Abelito
 */
public class ForoDAO {

    public void SubirForo(String tit, String descrip, int UserDoc, String asig, String idioma, String tipo) throws SQLException {

        Conexion conexion = new Conexion();
        Connection conex = null;
        PreparedStatement stat = null;

        try {
            conex = conexion.Conexion();

            String query = "insert into tb_foro(tit_foro, descrip_foro, fecha_creacion, id_asig_fk, doc_usua_fk, id_idioma_fk, id_tpfr_fk) values(?,?,NOW(),?,?,?,?)";
            stat = conex.prepareStatement(query);
            stat.setString(1, tit);
            stat.setString(2, descrip);
            stat.setInt(3, Integer.parseInt(asig));
            stat.setInt(4, UserDoc);
            stat.setInt(5, Integer.parseInt(idioma));
            stat.setInt(6, Integer.parseInt(tipo));

            stat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            conexion.close(conex, stat, null);
        }
    }
}
