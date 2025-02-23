package Pruebas;

import BO.PacienteBO;
import Conexion.ConexionBD;
import Conexion.IConexionBD;
import DTO.DireccionNuevaDTO;
import DTO.PacienteNuevoDTO;
import DTO.UsuarioNuevoDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

/**
 *
 * @author Daniel Buelna
 */

public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PersistenciaException, NegocioException, NoSuchAlgorithmException {
        
        PacienteNuevoDTO pacientenNuevo = new PacienteNuevoDTO("daniel", "buelna", "andujo", "correo", "telefono");
        DireccionNuevaDTO direccionNueva = new DireccionNuevaDTO("calle", 1, "colonia");
        UsuarioNuevoDTO usuarioNuevo = new UsuarioNuevoDTO("daniel", "123", "Paciente");
        
        IConexionBD conexion = new ConexionBD();

        PacienteBO pacienteBO = new PacienteBO(conexion);
        
        pacienteBO.registrarPaciente(pacientenNuevo, direccionNueva, usuarioNuevo);
        
    }
    
}
