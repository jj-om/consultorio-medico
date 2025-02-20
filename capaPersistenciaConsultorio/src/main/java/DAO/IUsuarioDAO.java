package DAO;

import Entidades.Usuario;
import Exception.PersistenciaException;

/**
 * @author Carmen Beltran
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public interface IUsuarioDAO {

    /**
     * Retorna el usuario con el ID asignado. Inserta el usuario del
     * parámetro en la base de datos.
     */
    public Usuario registrarUsuario(Usuario usuario) throws PersistenciaException;
    
}
