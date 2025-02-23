package Mapper;

import DTO.DireccionNuevaDTO;
import Entidades.Direccion;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public class DireccionMapper {
    
    public Direccion toEntity(DireccionNuevaDTO direccionNueva) {
        if (direccionNueva == null) {
            return null;
        }
        return new Direccion(
            direccionNueva.getCalle(),
            direccionNueva.getNumero(),
            direccionNueva.getColonia()
        );
    }
}
