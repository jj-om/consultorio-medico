package BO;

import Conexion.IConexionBD;
import DAO.IPacienteDAO;
import DAO.PacienteDAO;
import DTO.DireccionNuevaDTO;
import DTO.PacienteDTO;
import DTO.PacienteNuevoDTO;
import DTO.UsuarioNuevoDTO;
import Entidades.Direccion;
import Entidades.Paciente;
import Entidades.Usuario;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.DireccionMapper;
import Mapper.PacienteMapper;
import Mapper.UsuarioMapper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna Andujo
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */


    public class PacienteBO {
    private final PacienteDAO pacienteDAO;

    public PacienteBO(IConexionBD conexion) {
        this.pacienteDAO = new PacienteDAO(conexion);
    }

    public void registrarPaciente(PacienteDTO pacienteDTO) throws NegocioException {
        validarPacienteDTO(pacienteDTO);
        try {
            pacienteDAO.registrarPaciente(convertirDTOaPaciente(pacienteDTO));
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

    private void validarPacienteDTO(PacienteDTO pacienteDTO) throws NegocioException {
        if (pacienteDTO == null || pacienteDTO.getNombres() == null || pacienteDTO.getCorreoElectronico() == null || pacienteDTO.getFechaNacimiento() == null) {
            throw new NegocioException("Los datos del paciente no pueden ser nulos.");
        }
    }

    private Paciente convertirDTOaPaciente(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setNombres(pacienteDTO.getNombres());
        paciente.setApellidoPaterno(pacienteDTO.getApellidoPaterno());
        paciente.setApellidoMaterno(pacienteDTO.getApellidoMaterno());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setEdad(pacienteDTO.getEdad());
        paciente.setCorreoElectronico(pacienteDTO.getCorreoElectronico());
        paciente.setTelefono(pacienteDTO.getTelefono());
        return paciente;
    }




    // METODO PRIVADO PARA ENCRIPTAR LA contraseña DEL Usuario 
    private String hash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = digest.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hashedBytes);
    }
    }
