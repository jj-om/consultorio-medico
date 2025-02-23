package DAO;

import Conexion.IConexionBD;
import Entidades.Paciente;
import Exception.PersistenciaException;
import static com.mysql.cj.conf.PropertyKey.logger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Carmen Beltran
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */
public class PacienteDAO implements IPacienteDAO {

    IConexionBD conexion;

    public PacienteDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());

    @Override
    public Paciente registrarPaciente(Paciente paciente) throws PersistenciaException {
        // SENTENCIA DE COMANDO MYSQL
        String sentenciaSQL = "CALL registrarPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // COMENZAMOS EL BLOQUE TRY-CATCH
        try (Connection con = conexion.crearConexion(); CallableStatement cs = con.prepareCall(sentenciaSQL)) {

            // MANDAR PARAMETROS
            cs.setString(1, paciente.getNombres());
            cs.setString(2, paciente.getApellidoPaterno());
            cs.setString(3, paciente.getApellidoMaterno());
            cs.setString(4, paciente.getCorreoElectronico());
            cs.setString(5, paciente.getUsuario().getUsuario());
            cs.setString(6, paciente.getUsuario().getContraseña());
            cs.setString(7, paciente.getTelefono());
            cs.setString(8, paciente.getDireccion().getCalle());
            cs.setInt(9, paciente.getDireccion().getNumero());
            cs.setString(10, paciente.getDireccion().getColonia());

            // EJECUTAR LINEA
            boolean tieneResultados = cs.execute();
            if (tieneResultados) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) {
                        paciente.setId_paciente(rs.getInt("id_paciente"));
                        logger.info("Paciente registrado exitosamente con ID: " + paciente.getId_paciente());
                    } else {
                        throw new PersistenciaException("El registro del paciente falló, no se obtuvo ID.");
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al registrar al paciente", e);
            throw new PersistenciaException("Error al registrar al paciente", e);
        }
        return null;
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
        public void agendarCita(CitaDAO cita) throws PersistenciaException {
    String sentenciaSQL = "CALL AgendarCita(?, ?, ?)"; //

    try (Connection con = conexion.crearConexion();
         CallableStatement cs = con.prepareCall(sentenciaSQL)) { 

        cs.setInt(1, cita.getIdPaciente()); 
        cs.setInt(2, cita.getIdMedico());   
        cs.setTimestamp(3, Timestamp.valueOf(cita.getFechaHora())); // fecha_hora (DATETIME en MySQL)

        cs.executeUpdate(); 
        System.out.println("Cita agendada con exito");

    } catch (SQLException ex) {
        throw new PersistenciaException("Error al agendar la cita: " + ex.getMessage(), ex);
    }
}
     //CANCELAR CITA   
        
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
    
        
}
