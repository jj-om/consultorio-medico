package DAO;

import Conexion.IConexionBD;
import Entidades.Cita;
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
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    //METODO PARA GENERAR CITA DE EMERGENCIA
 public int generarCitaEmergencia(int idPaciente) throws PersistenciaException {
    String sentenciaSQL = "CALL GenerarCitaEmergencia(?, ?)";  //Llamada al procedimiento almacenado
    int folio = -1;  // Para almacenar el folio generado

    try (Connection con = conexion.crearConexion();
         CallableStatement cs = con.prepareCall(sentenciaSQL)) { 
        cs.setInt(1, idPaciente);  
        cs.registerOutParameter(2, Types.INTEGER);  
        cs.executeUpdate();

        // Obtener el folio generado
        folio = cs.getInt(2);
        System.out.println("Cita de emergencia generada con folio: " + folio);

    } catch (SQLException ex) {
        throw new PersistenciaException("Error al generar cita de emergencia: " + ex.getMessage(), ex);
    }
    return folio;
}
    // METODO PARA ACTUALIZAR LOS DATOS DEL CLIENTE
   public void actualizarDatosPaciente(Paciente paciente) throws PersistenciaException {
    String sentenciaSQL = "CALL ActualizarDatosPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = conexion.crearConexion();
         CallableStatement cs = con.prepareCall(sentenciaSQL)) { 

        System.out.println("Enviando datos a ActualizarDatosPaciente:");
        System.out.println("   - ID Paciente: " + paciente.getId_paciente());
        System.out.println("   - Nombre: " + paciente.getNombres());
        System.out.println("   - Apellido Paterno: " + paciente.getApellidoPaterno());
        System.out.println("   - Apellido Materno: " + paciente.getApellidoMaterno());
        System.out.println("   - Correo: " + paciente.getCorreoElectronico());
        System.out.println("   - Teléfono: " + paciente.getTelefono());
        System.out.println("   - Dirección: " + paciente.getDireccion().getCalle() + 
                           ", " + paciente.getDireccion().getNumero() + 
                           ", " + paciente.getDireccion().getColonia());

        cs.setInt(1, paciente.getId_paciente());
        cs.setString(2, paciente.getNombres());
        cs.setString(3, paciente.getApellidoPaterno());
        cs.setString(4, paciente.getApellidoMaterno());
        cs.setString(5, paciente.getCorreoElectronico());
        cs.setString(6, paciente.getTelefono());
        cs.setString(7, paciente.getDireccion().getCalle());
        cs.setInt(8, paciente.getDireccion().getNumero());
        cs.setString(9, paciente.getDireccion().getColonia());

        cs.executeUpdate();
        System.out.println("Datos del paciente actualizados correctamente.");

    } catch (SQLException ex) {
        throw new PersistenciaException("Error al actualizar datos del paciente: " + ex.getMessage(), ex);
    }
   }
       public List<String> consultarHistorialConsultas(int idPaciente, String especialidad, Date fechaInicio, Date fechaFin) throws PersistenciaException {
        List<String> historial = new ArrayList<>();
        StringBuilder sentenciaSQL = new StringBuilder(
            "SELECT c.fechaHoraConsulta, c.motivo, c.diagnostico, c.tratamiento, m.especialidad " +
            "FROM Consultas c " +
            "JOIN Citas ci ON c.id_cita = ci.id_cita " +
            "JOIN Medicos m ON ci.id_medico = m.id_medico " +
            "WHERE ci.id_paciente = ? " +  
            "AND c.fechaHoraConsulta BETWEEN ? AND ?"
        );

        if (especialidad != null && !especialidad.isEmpty()) {
            sentenciaSQL.append(" AND m.especialidad = ?");
        }

        sentenciaSQL.append(" ORDER BY c.fechaHoraConsulta DESC");

        try (Connection con = conexion.crearConexion();
             PreparedStatement stmt = con.prepareStatement(sentenciaSQL.toString())) {

            stmt.setInt(1, idPaciente);
            stmt.setDate(2, (java.sql.Date) fechaInicio);
            stmt.setDate(3, (java.sql.Date) fechaFin);

            if (especialidad != null && !especialidad.isEmpty()) {
                stmt.setString(4, especialidad);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                historial.add("Fecha: " + rs.getTimestamp("fechaHoraConsulta") +
                              "Especialidad: " + rs.getString("especialidad") +
                              "Motivo: " + rs.getString("motivo") +
                              "Diagnóstico: " + rs.getString("diagnostico") +
                              "Tratamiento: " + rs.getString("tratamiento"));
            }

        } catch (SQLException ex) {
            throw new PersistenciaException("Error al consultar historial de consultas: " + ex.getMessage(), ex);
        }

        return historial;
    }
}
   
   
   



    
    
    
   







