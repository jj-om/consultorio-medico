package BO;

import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.PacienteNuevoDTO;
import Entidades.Paciente;
import Exception.NegocioException;
import Exception.PersistenciaException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna Andujo
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */
public class PacienteBO {

    private final IPacienteDAO pacienteDAO;

    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
    }

    public Paciente registrarPaciente(PacienteNuevoDTO pacienteDTO) throws NegocioException, PersistenciaException {
        Paciente paciente = null;
        validarPacienteDTO(pacienteDTO);
        try {
            pacienteDAO.registrarPaciente(convertirDTOaPaciente(pacienteDTO));
            paciente = convertirDTOaPaciente(pacienteDTO);
            return paciente;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al registrar al paciente: " + ex.getMessage(), ex);
        }
    }

    public int generarCitaEmergencia(int idPaciente) throws NegocioException {
        if (idPaciente <= 0) {
            throw new NegocioException("ID de paciente inválido.");
        }
        try {
            return pacienteDAO.generarCitaEmergencia(idPaciente);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al generar cita de emergencia: " + ex.getMessage(), ex);
        }
    }

    private void validarPacienteDTO(PacienteNuevoDTO pacienteDTO) throws NegocioException, PersistenciaException {
        if (pacienteDTO == null || pacienteDTO.getNombres() == null || pacienteDTO.getCorreoElectronico() == null || pacienteDTO.getFechaNacimiento() == null) {
            throw new NegocioException("Los datos del paciente no pueden ser nulos.");
        }
        
        if (pacienteDTO.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new NegocioException("Fecha de nacimiento inválida.");
        }

        String regexTelefono = "^[0-9]{10}$";
        if (!Pattern.matches(regexTelefono, pacienteDTO.getTelefono())) {
            throw new NegocioException("Número de teléfono inválido.");
        }

        String regexCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!Pattern.matches(regexCorreo, pacienteDTO.getCorreoElectronico())) {
            throw new NegocioException("Correo electrónico inválido.");
        }
        
        if (pacienteDAO.recuperarPacienteCorreoElectronico(pacienteDTO.getCorreoElectronico())) {
            throw new NegocioException("Correo electrónico ya registrado");
        }
        
    }

    private Paciente convertirDTOaPaciente(PacienteNuevoDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setNombres(pacienteDTO.getNombres());
        paciente.setApellidoPaterno(pacienteDTO.getApellidoPaterno());
        paciente.setApellidoMaterno(pacienteDTO.getApellidoMaterno());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setCorreoElectronico(pacienteDTO.getCorreoElectronico());
        paciente.setUsuario(pacienteDTO.getUsuario());
        paciente.setTelefono(pacienteDTO.getTelefono());
        paciente.setDireccion(pacienteDTO.getDireccion());
        return paciente;
    }

    // METODO PRIVADO PARA ENCRIPTAR LA contraseña DEL Usuario 
    public String hash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
}
