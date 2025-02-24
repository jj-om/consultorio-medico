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
import Exception.PersistenciaException;
import java.sql.*;

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
                System.out.println("✅ Cita actualizada a 'No asistió paciente'");
            } else {
                System.out.println("🕒 Aún en tiempo o ya asistió.");
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

    
    
}

