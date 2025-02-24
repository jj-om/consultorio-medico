package Mapper;

import DTO.PacienteNuevoDTO;
import Entidades.Direccion;
import Entidades.Paciente;
import Entidades.Usuario;
import java.util.Optional;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */
public class PacienteMapper {
    
    // Método para crear un Paciente a partir de un DTO, Direccion y Usuario
    public Paciente toEntity(PacienteNuevoDTO pacienteNuevo, Direccion direccion, Usuario usuario) {
        if (Optional.ofNullable(pacienteNuevo).isEmpty() 
                || Optional.ofNullable(direccion).isEmpty() 
                || Optional.ofNullable(usuario).isEmpty()) {
            throw new IllegalArgumentException("Datos del paciente incompletos");
        }
        return new Paciente(
                0,
            pacienteNuevo.getNombres(),
            pacienteNuevo.getApellidoPaterno(),
            pacienteNuevo.getApellidoMaterno(),
            pacienteNuevo.getFechaNacimiento(), 
            pacienteNuevo.getEdad(),          
            pacienteNuevo.getCorreoElectronico(),
            pacienteNuevo.getTelefono(),
            direccion
        );
    }

    // Método para crear un Paciente a partir de un ID, un DTO y una Dirección
    public Paciente toEntity(int idPaciente, PacienteNuevoDTO pacienteNuevo, Direccion direccion) {
        if (pacienteNuevo == null || direccion == null) {
            return null;
        }
        return new Paciente(
            idPaciente,
            pacienteNuevo.getNombres(),
            pacienteNuevo.getApellidoPaterno(),
            pacienteNuevo.getApellidoMaterno(),
            pacienteNuevo.getFechaNacimiento(), 
            pacienteNuevo.getEdad(),            
            pacienteNuevo.getCorreoElectronico(),
            pacienteNuevo.getTelefono(),
            direccion
        );
    }
}
