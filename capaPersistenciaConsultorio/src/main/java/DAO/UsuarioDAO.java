/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Gael
 */
import Conexion.IConexionBD;
import java.sql.*;
import Exception.PersistenciaException;

public class UsuarioDAO {
    IConexionBD conexion;

    public UsuarioDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    public String validarCredenciales(String usuario, String contraseña) throws PersistenciaException {
        String sentenciaSQL = "SELECT id_usuario, tipo FROM Usuarios WHERE usuario = ? AND contraseña = ?";

        try (Connection con = conexion.crearConexion();
             PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tipoUsuario = rs.getString("tipo");
                int idUsuario = rs.getInt("id_usuario");

                System.out.println("Inicio de sesión exitoso. Tipo: " + tipoUsuario + " | ID: " + idUsuario);
                return tipoUsuario; // Puede ser "Paciente" o "Medico"
            } else {
                throw new PersistenciaException("Usuario o contraseña incorrectos.");
            }

        } catch (SQLException ex) {
            throw new PersistenciaException("Error al validar credenciales: " + ex.getMessage(), ex);
        }
    }
}

