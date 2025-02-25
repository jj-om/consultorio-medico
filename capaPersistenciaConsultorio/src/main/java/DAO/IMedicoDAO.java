/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Exception.PersistenciaException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Gael
 */


public interface IMedicoDAO {

    /**
     * Consulta la agenda del médico para una fecha específica.
     *
     * @param idMedico ID del médico.
     * @param fecha Fecha de la consulta.
     * @return Lista de cadenas con la información de las citas.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
    public List<String> consultarAgenda(int idMedico, Date fecha) throws PersistenciaException;

    /**
     * Inicia una consulta, actualizando el estado de la cita a 'atendida'.
     *
     * @param idCita ID de la cita a iniciar.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
    public void iniciarConsulta(int idCita) throws PersistenciaException;

    /**
     * Obtiene los detalles de una consulta asociada a una cita.
     *
     * @param idCita ID de la cita.
     * @return Una cadena con los detalles de la consulta.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
    public String verDetalleConsulta(int idCita) throws PersistenciaException;

    /**
     * Consulta el historial de consultas de un médico en un rango de fechas.
     *
     * @param idMedico ID del médico.
     * @param fechaInicio Fecha de inicio del rango.
     * @param fechaFin Fecha de fin del rango.
     * @return Lista de cadenas con el historial de consultas.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
    public List<String> consultarHistorialMedico(int idMedico, Date fechaInicio, Date fechaFin) throws PersistenciaException;

    /**
     * Cambia el estado de un médico a 'Activo' o 'Inactivo'.
     *
     * @param idMedico ID del médico.
     * @param activo true para activar, false para desactivar.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
    public void cambiarEstadoMedico(int idMedico, boolean activo) throws PersistenciaException;
}

