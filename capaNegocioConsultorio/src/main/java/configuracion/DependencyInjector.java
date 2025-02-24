package configuracion;

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
}
