package DAO;

import Entidades.Paciente;
import Exception.PersistenciaException;
import java.util.Date;
import java.util.List;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public interface IPacienteDAO {

    /**
     * Registra un paciente en la base de datos.
     *
     * @param paciente El objeto Paciente con los datos a registrar.
     * @return El paciente con el ID asignado.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
    public Paciente registrarPaciente(Paciente paciente) throws PersistenciaException;

    /**
     * Consulta si un paciente con un correo electrónico ya está registrado.
     *
     * @param correo El correo electrónico del paciente.
     * @return true si el paciente con ese correo ya existe, false si no.
     * @throws PersistenciaException Si ocurre un error al consultar la base de datos.
     */
    public boolean recuperarPacienteCorreoElectronico(String correo) throws PersistenciaException;

    /**
     * Actualiza los datos de un paciente en la base de datos.
     *
     * @param paciente El paciente con los datos actualizados.
     * @return true si la actualización fue exitosa.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
    public boolean actualizarDatosPaciente(Paciente paciente) throws PersistenciaException;

    /**
     * Genera una cita de emergencia para un paciente.
     *
     * @param idPaciente El ID del paciente para generar la cita de emergencia.
     * @return El folio de la cita de emergencia generada.
     * @throws PersistenciaException Si ocurre un error al generar la cita.
     */
    public int generarCitaEmergencia(int idPaciente) throws PersistenciaException;

    /**
     * Consulta el historial de consultas de un paciente.
     *
     * @param idPaciente El ID del paciente para consultar su historial.
     * @param especialidad La especialidad médica para filtrar (opcional).
     * @param fechaInicio La fecha de inicio del rango de consultas.
     * @param fechaFin La fecha de fin del rango de consultas.
     * @return Una lista de cadenas con el historial de consultas del paciente.
     * @throws PersistenciaException Si ocurre un error al consultar el historial.
     */
    public List<String> consultarHistorialConsultas(int idPaciente, String especialidad, Date fechaInicio, Date fechaFin) throws PersistenciaException;
}
