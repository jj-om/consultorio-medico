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
    
    public Paciente toEntity(PacienteNuevoDTO pacienteNuevo, Direccion direccion, Usuario usuario) {
    if (Optional.ofNullable(pacienteNuevo).isEmpty() || Optional.ofNullable(direccion).isEmpty() || Optional.ofNullable(usuario).isEmpty()) {
        throw new IllegalArgumentException("Datos del paciente incompletos");
    }
    return new Paciente(
        pacienteNuevo.getNombres(),
        pacienteNuevo.getApellidoPaterno(),
        pacienteNuevo.getApellidoMaterno(),
        pacienteNuevo.getCorreoElectronico(),
        pacienteNuevo.getTelefono(),
        direccion,
        usuario
    );
}

    
    public Paciente toEntity(int idPaciente, PacienteNuevoDTO pacienteNuevo, Direccion direccion) {
        if (pacienteNuevo == null || direccion == null) {
            return null;
        }
        return new Paciente(
            idPaciente,
            pacienteNuevo.getNombres(),
            pacienteNuevo.getApellidoPaterno(),
            pacienteNuevo.getApellidoMaterno(),
            pacienteNuevo.getCorreoElectronico(),
            pacienteNuevo.getTelefono(),
            direccion
        );
    }
}