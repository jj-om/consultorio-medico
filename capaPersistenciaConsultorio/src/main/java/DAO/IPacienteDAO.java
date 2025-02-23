package DAO;

import Entidades.Paciente;
import Exception.PersistenciaException;

/**
 * @author Carmen Beltran
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public interface IPacienteDAO {

    /**
     * Retorna el paciente con el ID asignado. Inserta el paciente del
     * parámetro en la base de datos.
     */
    public Paciente registrarPaciente(Paciente paciente) throws PersistenciaException;
    
    
    /**
     * Agendar una cita medica
     * 
     */
  
    
    
}
