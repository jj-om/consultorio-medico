/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Gael
 */
import Conexion.ConexionBD;
import Conexion.IConexionBD;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Conexion.IConexionBD;
import Entidades.Cita;
import Exception.PersistenciaException;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CitaDAO {
    IConexionBD conexion;

    public CitaDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    public void verificarAsistenciaCita(int idCita) throws PersistenciaException {
        String sentenciaSQL = "UPDATE Citas SET estado = 'No asistió paciente' " +
                              "WHERE id_cita = ? AND estado = 'Agendada' " +
                              "AND TIMESTAMPDIFF(MINUTE, fechaHoraCita, NOW()) > 15";

        try (Connection con = conexion.crearConexion();
             PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {

            stmt.setInt(1, idCita);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println(" Cita actualizada a 'No asistió paciente'");
            } else {
                System.out.println(" Aún en tiempo o ya asistió.");
            }

        } catch (SQLException ex) {
            throw new PersistenciaException("Error al verificar asistencia: " + ex.getMessage(), ex);
        }
    }
    public void registrarDiagnosticoTratamiento(int idCita, String diagnostico, String tratamiento) throws PersistenciaException {
    String sentenciaSQL = "UPDATE Consultas SET diagnostico = ?, tratamiento = ? WHERE id_cita = ?";

    try (Connection con = conexion.crearConexion();
         PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {

        stmt.setString(1, diagnostico);
        stmt.setString(2, tratamiento);
        stmt.setInt(3, idCita);

        int filasAfectadas = stmt.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("Diagnóstico y tratamiento registrados correctamente.");
        } else {
            System.out.println("No se encontró la consulta para la cita especificada.");
        }

    } catch (SQLException ex) {
        throw new PersistenciaException("Error al registrar diagnóstico y tratamiento: " + ex.getMessage(), ex);
    }
}
    public void expirarConsultasEmergencia() throws PersistenciaException {
    String sentenciaSQL = "UPDATE Citas SET estado = 'No atendida' " +
                          "WHERE id_cita IN (SELECT id_citaEmergencia FROM Citas_Emergencias) " +
                          "AND estado = 'Agendada' " +
                          "AND TIMESTAMPDIFF(MINUTE, fechaHoraCita, NOW()) > 10";

    try (Connection con = conexion.crearConexion();
         PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {

        int filasAfectadas = stmt.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("Consultas de emergencia expiradas correctamente.");
        } else {
            System.out.println("No hay consultas de emergencia vencidas.");
        }

    } catch (SQLException ex) {
        throw new PersistenciaException("Error al expirar consultas de emergencia: " + ex.getMessage(), ex);
    }
}
         //METODO PARA CANCELAR LA CITA
        
    public void cancelarCita(int idCita, int idPaciente) throws PersistenciaException {
        //LLAMAMOS AL PROCEDIMIENTO ALMACENADO PARA CANCELAR CITA
        String sentenciaSQL = "CALL CancelarCita(?, ?)"; // 

    try (Connection con = conexion.crearConexion();
         CallableStatement cs = con.prepareCall(sentenciaSQL)) { 
        System.out.println("   - Cita ID: " + idCita);
        System.out.println("   - Paciente ID: " + idPaciente);
        cs.setInt(1, idCita); 
        cs.setInt(2, idPaciente);  
        cs.executeUpdate();
        System.out.println("Cita cancelada con éxito.");

    } catch (SQLException ex) {
        throw new PersistenciaException("Error al cancelar la cita: " + ex.getMessage(), ex);
    }
}
       // Verificar si el paciente ya tiene una cita con el médico en la misma fecha
    public boolean existeCitaElMismoDia(int idPaciente, int idMedico, LocalDate fecha) throws PersistenciaException {
        //SENTENCIA SQL
        String sentenciaSQL = "SELECT COUNT(*) FROM Citas WHERE id_paciente = ? AND id_medico = ? AND DATE(fecha_hora) = ?";
        try (Connection con = conexion.crearConexion(); CallableStatement cs = con.prepareCall(sentenciaSQL);
             PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {
            stmt.setInt(1, idPaciente);
            stmt.setInt(2, idMedico);
            stmt.setDate(3, java.sql.Date.valueOf(fecha));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Verificar disponibilidad del médico
    public boolean medicoDisponible(int idMedico, LocalDateTime fechaHora) throws PersistenciaException {
                String sentenciaSQL = "SELECT COUNT(*) FROM Horarios_Medicos hm " +"JOIN Horarios h ON hm.id_horario = h.id_horario " +"WHERE hm.id_medico = ? " + "AND ? BETWEEN h.horaInicio AND h.horaFinal";
        try (Connection con = conexion.crearConexion(); CallableStatement cs = con.prepareCall(sentenciaSQL);
             PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {
            stmt.setInt(1, idMedico);
            stmt.setTime(2, Time.valueOf(fechaHora.toLocalTime()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Insertar la cita en la base de datos 
        public void agendarCita(Cita cita) throws PersistenciaException {
    String sentenciaSQL = "CALL AgendarCita(?, ?, ?)"; //

    try (Connection con = conexion.crearConexion();
         CallableStatement cs = con.prepareCall(sentenciaSQL)) { 

        cs.setInt(1, cita.getIdPaciente()); 
        cs.setInt(2, cita.getIdMedico());   
        cs.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora())); 

        cs.executeUpdate(); 
        System.out.println("Cita agendada con exito");

    } catch (SQLException ex) {
        throw new PersistenciaException("Error al agendar la cita: " + ex.getMessage(), ex);
    }
}
        public int generarCitaEmergencia(int idPaciente) throws PersistenciaException {
        String sentenciaSQL = "CALL GenerarCitaEmergencia(?, ?)";
        int folio = -1;
        
        try (Connection con = conexion.crearConexion();
             CallableStatement cs = con.prepareCall(sentenciaSQL)) {
             
            cs.setInt(1, idPaciente);
            cs.registerOutParameter(2, Types.INTEGER);
            cs.executeUpdate();
            
            folio = cs.getInt(2);
            System.out.println("Cita de emergencia generada con folio: " + folio);
            
        } catch (SQLException ex) {
            throw new PersistenciaException("Error al generar cita de emergencia: " + ex.getMessage(), ex);
        }
        
        return folio;
    }
}

