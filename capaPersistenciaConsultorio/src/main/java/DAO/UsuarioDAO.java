package DAO;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna Andujo
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */
import Conexion.IConexionBD;
import Entidades.Usuario;
import java.sql.*;
import Exception.PersistenciaException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO implements IUsuarioDAO {

    IConexionBD conexion;

    public UsuarioDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public Usuario recuperarUsuario(String usuario, String contraseña) throws PersistenciaException {
        Usuario usEncontrado = null;
        String sentenciaSQL = "SELECT id_usuario, usuario, contraseña, tipo FROM Usuarios WHERE usuario = ?";

        try (Connection con = conexion.crearConexion(); PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {

            stmt.setString(1, usuario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String contraseñaHashBD = rs.getString("contraseña");
                String tipoUsuario = rs.getString("tipo");
                int idUsuario = rs.getInt("id_usuario");

                if (hash(contraseña).equals(hash(contraseñaHashBD))) {
                    usEncontrado = new Usuario();
                    usEncontrado.setId_usuario(idUsuario);
                    usEncontrado.setUsuario(usuario);
                    usEncontrado.setContraseña(hash(contraseñaHashBD));
                    usEncontrado.setTipo(tipoUsuario);
                    return usEncontrado;
                } else {
                    throw new PersistenciaException("Contraseña incorrecta.");
                }
            } else {
                throw new PersistenciaException("Usuario no encontrado.");
            }

        } catch (SQLException ex) {
            throw new PersistenciaException("Error al validar las credenciales: " + ex.getMessage(), ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usEncontrado;
    }

    private String hash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
}
