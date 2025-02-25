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
import Entidades.Medico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Exception.PersistenciaException;

public class MedicoDAO implements IMedicoDAO {
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
    public void iniciarConsulta(int idCita) throws PersistenciaException {
    String sentenciaSQL = "UPDATE Citas SET estado = 'atendida' WHERE id_cita = ?";

    try (Connection con = conexion.crearConexion();
         PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {

        stmt.setInt(1, idCita);
        int filasAfectadas = stmt.executeUpdate();

        if (filasAfectadas == 0) {
            throw new PersistenciaException("No se pudo iniciar la consulta. Verifica el ID de la cita.");
        }

        System.out.println("La consulta ha sido iniciada.");

    } catch (SQLException ex) {
        throw new PersistenciaException("Error al iniciar la consulta: " + ex.getMessage(), ex);
    }
}
    public String verDetalleConsulta(int idCita) throws PersistenciaException {
    String sentenciaSQL = "SELECT c.fechaHoraConsulta, c.motivo, c.diagnostico, c.tratamiento, p.nombres, p.apellidoPaterno, p.apellidoMaterno " +
                          "FROM Consultas c " +
                          "JOIN Citas ci ON c.id_cita = ci.id_cita " +
                          "JOIN Pacientes p ON ci.id_paciente = p.id_paciente " +
                          "WHERE c.id_cita = ?";

    try (Connection con = conexion.crearConexion();
         PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {

        stmt.setInt(1, idCita);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return "Fecha: " + rs.getTimestamp("fechaHoraConsulta") +
                   "\n Paciente: " + rs.getString("nombres") + " " + rs.getString("apellidoPaterno") + " " + rs.getString("apellidoMaterno") +
                   "\n Motivo: " + rs.getString("motivo") +
                   "\n Diagnóstico: " + rs.getString("diagnostico") +
                   "\n Tratamiento: " + rs.getString("tratamiento");
        } else {
            return "No se encontró información para esta cita.";
        }

    } catch (SQLException ex) {
        throw new PersistenciaException("Error al obtener detalles de la consulta: " + ex.getMessage(), ex);
    }
    }
    public List<String> consultarHistorialMedico(int idMedico, Date fechaInicio, Date fechaFin) throws PersistenciaException {
        List<String> historial = new ArrayList<>();
        String sentenciaSQL = "SELECT c.fechaHoraConsulta, p.nombres, p.apellidoPaterno, p.apellidoMaterno, c.diagnostico, c.tratamiento " +
                              "FROM Consultas c " +
                              "JOIN Citas ci ON c.id_cita = ci.id_cita " +
                              "JOIN Pacientes p ON ci.id_paciente = p.id_paciente " +
                              "WHERE ci.id_medico = ? " +
                              "AND c.fechaHoraConsulta BETWEEN ? AND ? " +
                              "ORDER BY c.fechaHoraConsulta DESC";

        try (Connection con = conexion.crearConexion();
             PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {

            stmt.setInt(1, idMedico);
            stmt.setDate(2, fechaInicio);
            stmt.setDate(3, fechaFin);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                historial.add("Fecha: " + rs.getTimestamp("fechaHoraConsulta") +
                              "Paciente: " + rs.getString("nombres") + " " + rs.getString("apellidoPaterno") + " " + rs.getString("apellidoMaterno") +
                              "Diagnóstico: " + rs.getString("diagnostico") +
                              "Tratamiento: " + rs.getString("tratamiento"));
            }

        } catch (SQLException ex) {
            throw new PersistenciaException("Error al consultar historial de consultas: " + ex.getMessage(), ex);
        }

        return historial;
    }
    public void cambiarEstadoMedico(int idMedico, boolean activo) throws PersistenciaException {
    String nuevoEstado = activo ? "Activo" : "Inactivo";
    String sentenciaSQL = "UPDATE Medicos SET estado = ? WHERE id_medico = ?";

    try (Connection con = conexion.crearConexion();
         PreparedStatement stmt = con.prepareStatement(sentenciaSQL)) {

        stmt.setString(1, nuevoEstado);
        stmt.setInt(2, idMedico);

        int filasAfectadas = stmt.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("Estado del médico actualizado a: " + nuevoEstado);
        } else {
            System.out.println("No se encontró el médico con el ID proporcionado.");
        }

    } catch (SQLException ex) {
        throw new PersistenciaException("Error al cambiar estado del médico: " + ex.getMessage(), ex);
    }
}
    
    public List<Medico> consultarTodosMedicos() throws PersistenciaException {
        String consultaSQL = "SELECT id_medico, cedulaProfesional, nombres, apellidoPaterno, apellidoMaterno, especialidad, estado FROM Medicos WHERE estado = 'Activo'";

        List<Medico> medicos = new ArrayList<>();

        // iniciamos el intento de ejecutar el comando/consulta en la bd
        try (Connection con = this.conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(consultaSQL);
                ResultSet rs = ps.executeQuery() // Se ejecuta la consulta y se obtiene el resultado en un ResultSet
                ) {
            while (rs.next()) {
            Medico medico = new Medico(
                        rs.getInt("id_medico"),
                        rs.getString("cedulaProfesional"),
                        rs.getString("nombres"), 
                        rs.getString("apellidPaterno"), 
                        rs.getString("apellidoMaterno"), 
                        rs.getString("especialidad"),
                        rs.getString("estado")
                );
            }

            // Se retorna la lista con todos los pacientes obtenidos
            return medicos;

        } catch (SQLException ex) {
            // Se lanza una excepción personalizada si hay un error en la consulta
            throw new PersistenciaException("Error al obtener la lista de medicos.", ex);
        }

    }
}


