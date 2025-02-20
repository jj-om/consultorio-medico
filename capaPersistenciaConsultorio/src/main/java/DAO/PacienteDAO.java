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
}
