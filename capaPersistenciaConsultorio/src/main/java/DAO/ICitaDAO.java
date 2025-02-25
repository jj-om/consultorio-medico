/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import Entidades.Cita;
import Exception.PersistenciaException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Gael
 */
public interface ICitaDAO {

    /**
     * Verifica la asistencia de una cita. Si han transcurrido más de 15 minutos desde
     * la fecha de la cita y ésta aún está en estado "Agendada", se actualiza a "No asistió paciente".
     *
     * @param idCita ID de la cita a verificar.
     * @throws PersistenciaException si ocurre un error en la base de datos.
     */
    public void verificarAsistenciaCita(int idCita) throws PersistenciaException;

    /**
     * Registra el diagnóstico y tratamiento para una consulta asociada a una cita.
     *
     * @param idCita       ID de la cita.
     * @param diagnostico  Diagnóstico registrado.
     * @param tratamiento Tratamiento recomendado.
     * @throws PersistenciaException si ocurre un error en la base de datos.
     */
    public void registrarDiagnosticoTratamiento(int idCita, String diagnostico, String tratamiento) throws PersistenciaException;

    /**
     * Expira las consultas de emergencia que tienen más de 10 minutos de antigüedad y cuyo estado es "Agendada",
     * cambiándolas a "No atendida".
     *
     * @throws PersistenciaException si ocurre un error en la base de datos.
     */
    public void expirarConsultasEmergencia() throws PersistenciaException;

    /**
     * Cancela una cita a través de un procedimiento almacenado.
     *
     * @param idCita    ID de la cita a cancelar.
     * @param idPaciente ID del paciente que cancela la cita.
     * @throws PersistenciaException si ocurre un error en la base de datos.
     */
    public void cancelarCita(int idCita, int idPaciente) throws PersistenciaException;

    /**
     * Verifica si un paciente ya tiene una cita con un médico en la misma fecha.
     *
     * @param idPaciente ID del paciente.
     * @param idMedico   ID del médico.
     * @param fecha      Fecha de la cita.
     * @return true si ya existe la cita, false en caso contrario.
     * @throws PersistenciaException si ocurre un error en la base de datos.
     */
    public boolean existeCitaElMismoDia(int idPaciente, int idMedico, LocalDate fecha) throws PersistenciaException;

    /**
     * Verifica si un médico está disponible en un horario dado.
     *
     * @param idMedico  ID del médico.
     * @param fechaHora Fecha y hora de la cita.
     * @return true si el médico está disponible, false si no.
     * @throws PersistenciaException si ocurre un error en la base de datos.
     */
    public boolean medicoDisponible(int idMedico, LocalDateTime fechaHora) throws PersistenciaException;

    /**
     * Agenda una cita en la base de datos utilizando un procedimiento almacenado.
     *
     * @param cita Objeto Cita con los datos de la cita.
     * @throws PersistenciaException si ocurre un error en la base de datos.
     */
    public void agendarCita(Cita cita) throws PersistenciaException;
}
    /**
     * Genera una cita de emergencia para un paciente, asignando el primer médico disponible
     * y generando un folio aleatorio de 8 dígitos.
     *
     * @param idPaciente ID del paciente.
     * @return El folio generado.
     * @throws PersistenciaException si ocurre un error en la base de datos.
     */
