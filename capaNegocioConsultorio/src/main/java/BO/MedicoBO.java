/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BO;

import Conexion.IConexionBD;
import DAO.CitaDAO;
import DAO.MedicoDAO;
import DTO.CitaDTO;
import DTO.MedicoViejoDTO;
import Entidades.Medico;
import Exception.NegocioException;
import Exception.PersistenciaException;
import Mapper.MedicoMapper;
import static com.mysql.cj.conf.PropertyKey.logger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna Andujo
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public class MedicoBO {
    
    private static final Logger logger = Logger.getLogger(MedicoBO.class.getName());
    private final MedicoDAO medicoDAO;
    private final MedicoMapper mapper = new MedicoMapper();

    public MedicoBO(IConexionBD conexion) {
        this.medicoDAO = new MedicoDAO(conexion);
    }

    public List<String> consultarAgenda(int idMedico, Date fecha) throws NegocioException {
        if (idMedico <= 0 || fecha == null) {
            throw new NegocioException("Datos inválidos para consultar la agenda.");
        }
        try {
            return medicoDAO.consultarAgenda(idMedico, fecha);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al consultar la agenda: " + ex.getMessage(), ex);
        }
    }

    public void iniciarConsulta(int idCita) throws NegocioException {
        if (idCita <= 0) {
            throw new NegocioException("ID de cita inválido.");
        }
        try {
            medicoDAO.iniciarConsulta(idCita);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al iniciar la consulta: " + ex.getMessage(), ex);
        }
    }

    public String verDetalleConsulta(int idCita) throws NegocioException {
        if (idCita <= 0) {
            throw new NegocioException("ID de cita inválido.");
        }
        try {
            return medicoDAO.verDetalleConsulta(idCita);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener detalles de la consulta: " + ex.getMessage(), ex);
        }
    }

    public List<String> consultarHistorialMedico(int idMedico, Date fechaInicio, Date fechaFin) throws NegocioException {
        if (idMedico <= 0 || fechaInicio == null || fechaFin == null) {
            throw new NegocioException("Datos inválidos para consultar el historial médico.");
        }
        try {
            return medicoDAO.consultarHistorialMedico(idMedico, fechaInicio, fechaFin);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al consultar el historial médico: " + ex.getMessage(), ex);
        }
    }

    public void cambiarEstadoMedico(int idMedico, boolean activo) throws NegocioException {
        if (idMedico <= 0) {
            throw new NegocioException("ID de médico inválido.");
        }
        try {
            medicoDAO.cambiarEstadoMedico(idMedico, activo);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al cambiar el estado del médico: " + ex.getMessage(), ex);
        }
    }
    
    public List<MedicoViejoDTO> obtenerTodos() throws NegocioException {
        try {
            // Consultar todos los activistas en la base de datos
            List<Medico> listaActivistas = medicoDAO.consultarTodosMedicos();

            // Convertir la lista de entidades en una lista de DTOs y devolverla
            return mapper.toViejoDTOList(listaActivistas);
        } catch (PersistenciaException ex) {
            // Registrar el error en los logs
            logger.log(Level.SEVERE, "Error al obtener la lista de activistas", ex);

            // Lanzar una excepción de negocio con un mensaje más amigable
            throw new NegocioException("No se pudo obtener la lista de activistas.", ex);
        }
    }
}


