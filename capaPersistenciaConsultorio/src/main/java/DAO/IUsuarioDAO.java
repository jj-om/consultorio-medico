package DAO;

import Entidades.Usuario;
import Exception.PersistenciaException;

/**
 * @author Jesús Osuna 240549
 */

public interface IUsuarioDAO {

    public Usuario recuperarUsuario(String usuario, String contraseña) throws PersistenciaException;
    
}
