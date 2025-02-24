/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.CitaDAO;
import DAO.PacienteDAO;
import DTO.CitaDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;

/**
 *
 * @author Gael
 */
public class CitaBO {
    private final CitaDAO citaDAO;

    public CitaBO(IConexionBD conexion) {
        this.citaDAO = new CitaDAO(conexion);
    }

    public boolean agendarCita(CitaDTO citaDTO) throws NegocioException {
        if (citaDTO == null || citaDTO.getFechaHora() == null) {
            throw new NegocioException("Los datos de la cita no pueden ser nulos.");
        }

        try {
            if (citaDAO.existeCitaElMismoDia(citaDTO.getIdPaciente(), citaDTO.getIdMedico(), citaDTO.getFechaHora().toLocalDate())) {
                throw new NegocioException("El paciente ya tiene una cita con este médico en la misma fecha.");
            }
            if (!citaDAO.medicoDisponible(citaDTO.getIdMedico(), citaDTO.getFechaHora())) {
                throw new NegocioException("El médico no está disponible en ese horario.");
            }
            citaDAO.agendarCita(citaDTO.toCita());
            return true;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al agendar la cita: " + ex.getMessage(), ex);
        }
    }

    public boolean cancelarCita(int idCita, int idPaciente) throws NegocioException {
        if (idCita <= 0 || idPaciente <= 0) {
            throw new NegocioException("ID de cita o paciente inválido.");
        }

        try {
            citaDAO.cancelarCita(idCita, idPaciente);
            return true;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al cancelar la cita: " + ex.getMessage(), ex);
        }
    }

    public void verificarAsistenciaCita(int idCita) throws NegocioException {
        if (idCita <= 0) {
            throw new NegocioException("ID de cita inválido.");
        }

        try {
            citaDAO.verificarAsistenciaCita(idCita);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al verificar asistencia de la cita: " + ex.getMessage(), ex);
        }
    }

    public void expirarConsultasEmergencia() throws NegocioException {
        try {
            citaDAO.expirarConsultasEmergencia();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al expirar consultas de emergencia: " + ex.getMessage(), ex);
        }
    }

    public void registrarDiagnosticoTratamiento(int idCita, String diagnostico, String tratamiento) throws NegocioException {
        if (idCita <= 0 || diagnostico == null || diagnostico.isEmpty() || tratamiento == null || tratamiento.isEmpty()) {
            throw new NegocioException("Datos inválidos para registrar diagnóstico y tratamiento.");
        }

        try {
            citaDAO.registrarDiagnosticoTratamiento(idCita, diagnostico, tratamiento);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al registrar diagnóstico y tratamiento: " + ex.getMessage(), ex);
        }
    }
        public int generarCitaEmergencia(int idPaciente) throws NegocioException {
        if (idPaciente <= 0) {
            throw new NegocioException("ID de paciente inválido.");
        }
        try {
            return citaDAO.generarCitaEmergencia(idPaciente);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al generar la cita de emergencia: " + ex.getMessage(), ex);
        }
    }
}




