package BO;

import Conexion.IConexionBD;
import DAO.IUsuarioDAO;
import DAO.UsuarioDAO;
import DTO.SesionDTO;
import DTO.UsuarioNuevoDTO;
import Entidades.Usuario;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.UsuarioMapper;
import Sesion.UserSesion;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna Andujo
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */
public class UsuarioBO {

    private static final Logger logger = Logger.getLogger(UsuarioBO.class.getName());
    private final UsuarioDAO usuarioDAO;
    private final UsuarioMapper mapper = new UsuarioMapper();

    public UsuarioBO(IConexionBD conexion) {
        this.usuarioDAO = new UsuarioDAO(conexion);
    }

    public UsuarioNuevoDTO recuperarUsuario(String usuario, String contraseña) throws NegocioException, PersistenciaException {
        if (usuario == null || usuario.isEmpty() || contraseña == null || contraseña.isEmpty()) {
            throw new NegocioException("Usuario y contraseña son obligatorios.");
        }

        try {
            Usuario usRecuperado = usuarioDAO.recuperarUsuario(usuario, contraseña);

            if (usRecuperado == null) {
                return null;
            }
            return mapper.toNuevoDTO(usRecuperado);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error en la autenticación.", ex);
            throw new NegocioException("Error en la autenticación: " + ex.getMessage(), ex);
        }
    }

    public boolean validarCredenciales(SesionDTO sesion) throws NegocioException {
        if (sesion == null) {
            throw new NegocioException("No puede dejar la sesión vacía.");
        }

        if (sesion.getUsuario() == null || sesion.getUsuario().isEmpty()) {
            throw new NegocioException("No puede dejar el usuario vacío.");
        }

        if (sesion.getContraseña() == null || sesion.getContraseña().isEmpty()) {
            throw new NegocioException("No puede dejar la contraseña vacía.");
        }

        try {
            Usuario usuarioConsultado = usuarioDAO.recuperarUsuario(sesion.getUsuario(), sesion.getContraseña());

            if (usuarioConsultado == null) {
                throw new NegocioException("Usuario inexistente o contraseña incorrecta.");
            }

            UserSesion.getInstance().iniciarSesion(usuarioConsultado.getUsuario(), usuarioConsultado.getTipo());
            return true;

        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al validar las credenciales del usuario.", ex);
            throw new NegocioException("Error al validar las credenciales del usuario: " + ex.getMessage());
        }
    }
}
