package Mapper;

import DTO.UsuarioNuevoDTO;
import Entidades.Usuario;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */
public class UsuarioMapper {

    public Usuario toEntity(UsuarioNuevoDTO usuarioNuevo) {
        if (usuarioNuevo == null) {
            return null;
        }
        return new Usuario(
                usuarioNuevo.getUsuario(),
                usuarioNuevo.getContraseña(),
                usuarioNuevo.getTipo()
        );
    }

    public UsuarioNuevoDTO toNuevoDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioNuevoDTO(
                usuario.getUsuario(),
                usuario.getContraseña(),
                usuario.getTipo()
        );
    }

}
