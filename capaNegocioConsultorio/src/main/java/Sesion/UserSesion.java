package Sesion;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna Andujo
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */
public class UserSesion {

    private static volatile UserSesion instancia;
    private String usuario;
    private String tipoUsuario;
    
    private UserSesion() {
        
    }
    
    public static UserSesion getInstance() {
        if (instancia == null) {
            synchronized (UserSesion.class) {
                if (instancia == null) {
                    instancia = new UserSesion();
                    
                }
            }
        }
        return instancia;
    }
                
    public void iniciarSesion(String usuario, String tipoUsuario) {
        this.usuario = usuario;
        this.tipoUsuario = tipoUsuario;
    }
    
    public boolean sesionActiva() {
        return this.usuario != null;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void cerrarSesion() {
        this.usuario = null;
        this.tipoUsuario = null;
    }
    
}
