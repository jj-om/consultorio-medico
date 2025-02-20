package DAO;

import Conexion.IConexionBD;
import Entidades.Paciente;
import Exception.PersistenciaException;
import static com.mysql.cj.conf.PropertyKey.logger;
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

public class PacienteDAO implements IPacienteDAO{

    IConexionBD conexion; 
    
    public PacienteDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }
    
    private static final Logger logger = Logger.getLogger(PacienteDAO.class.getName());
    
    @Override
    public Paciente registrarPaciente(Paciente paciente) throws PersistenciaException {
        // SENTENCIA DE COMANDO MYSQL
        String sentenciaSQL = "CALL registrarPaciente(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        
        // COMENZAMOS EL BLOQUE TRY-CATCH
        try (Connection con = conexion.crearConexion();
                PreparedStatement ps = con.prepareStatement(sentenciaSQL, Statement.RETURN_GENERATED_KEYS)) { // Statement.RETURN_GENERATED_KEYS es una opción que se usa al crear un PreparedStatement para indicar que la consulta devolverá las claves generadas automáticamente, como un ID autoincremental. Esto permite obtener el valor generado tras ejecutar una inserción (INSERT)

            // ESTABLECER LOS PARAMETROS PARA LAS ENTIDADES CORRESPONDIENTES(DE AQUI SALE USUARIO, DIRECCION Y PACIENTE)
            ps.setString(1, paciente.getNombres()); 
            ps.setString(2, paciente.getApellidoPaterno());
            ps.setString(3, paciente.getApellidoMaterno());
            ps.setString(4, paciente.getCorreoElectronico());
            ps.setString(6, paciente.getUsuario().getContraseña());
            ps.setString(6, paciente.getTelefono()); 
            ps.setString(8, paciente.getDireccion().getCalle()); 
            ps.setInt(8, paciente.getDireccion().getNumero()); 
            ps.setString(9, paciente.getDireccion().getColonia()); 
            
            // EJECUTAMOS EL REGISTRO Y SE COMPRUEBA SI SE REALIZO LA INSERCION O NO
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas == 0) {
                logger.severe("El registro del paciente falló, no se insertó ninguna fila.");
                throw new PersistenciaException("El registro del paciente falló, no se insertó ninguna fila.");
            }

            // OBTENEMOS EL ID GENERADO POR LA BASE DE DAOTS
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) { 
                if (generatedKeys.next()) { 
                    paciente.setId_paciente(generatedKeys.getInt(1));
                    logger.info("Paciente registrado exitosamente con ID: " + paciente.getId_paciente());
                } else {
                    logger.severe("El registro del paciente falló, no se obtuvo ID.");
                    throw new PersistenciaException("El registro del paciente falló, no se obtuvo ID.");
                }
            }
            return paciente;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al registrar al paciente", e);
            throw new PersistenciaException("Error al registrar al paciente", e);
        }
    }
    
    

}
