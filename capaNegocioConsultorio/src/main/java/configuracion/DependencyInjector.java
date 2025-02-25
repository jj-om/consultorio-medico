package configuracion;

import BO.MedicoBO;
import BO.PacienteBO;
import BO.UsuarioBO;
import Conexion.ConexionBD;
import Conexion.IConexionBD;


// su función es inyectar dependencias para evitar acoplamientos directos
/**
 * @author Ethan Valdez
 * @author Daniel Buelna Andujo
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */
public class DependencyInjector {
    
    public static UsuarioBO crearUsuarioBO() {
        IConexionBD conexion = new ConexionBD();
        UsuarioBO usuarioBO = new UsuarioBO(conexion);
        return usuarioBO;
    }
    
    public static PacienteBO crearPacienteBO() {
        IConexionBD conexion = new ConexionBD();
        PacienteBO pacienteBO = new PacienteBO(conexion);
        
        return pacienteBO;
    }
    
    public static MedicoBO crearMedicoBO() {
        IConexionBD conexion = new ConexionBD();
        MedicoBO medicoBO = new MedicoBO(conexion);
        
        return medicoBO;
    }
    
}
