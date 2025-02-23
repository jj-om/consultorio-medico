/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Gael
 */
import Conexion.IConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Exception.PersistenciaException;

public class MedicoDAO {
    IConexionBD conexion;

    public MedicoDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    public List<String> consultarAgenda(int idMedico, Date fecha) throws PersistenciaException {
        List<String> agenda = new ArrayList<>();
        String sentenciaSQL = "SELECT c.id_cita, c.fechaHoraCita, c.estado, p.nombres, p.apellidoPaterno, p.apellidoMaterno " +
                              "FROM Citas c " +
                              "JOIN Pacientes p ON c.id_paciente = p.id_paciente " +
                              "WHERE c.id_medico = ? " +
                              "AND DATE(c.fechaHoraCita) = ? " +
                              "ORDER BY c.fechaHoraCita ASC";

        try (Connection con = conexion.crearConexion();
             PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {

            stmt.setInt(1, idMedico);
            stmt.setDate(2, fecha);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                agenda.add("Fecha: " + rs.getTimestamp("fechaHoraCita") +
                          "Paciente: " + rs.getString("nombres") + " " + rs.getString("apellidoPaterno") + " " + rs.getString("apellidoMaterno") +
                          "Estado: " + rs.getString("estado"));
            }

        } catch (SQLException ex) {
            throw new PersistenciaException("Error al consultar la agenda: " + ex.getMessage(), ex);
        }

        return agenda;
    }
}

