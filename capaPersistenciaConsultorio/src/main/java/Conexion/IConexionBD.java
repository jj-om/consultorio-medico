package Conexion;

import Exception.PersistenciaException;
import java.sql.Connection;

/**
 * @author Carmen Beltran
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public interface IConexionBD {

     public Connection crearConexion() throws PersistenciaException;
    
}
