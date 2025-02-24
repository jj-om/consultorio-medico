package DTO;

/** 
 * @author Ethan Valdez
 * @author Daniel Buelna Andujo
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public class SesionDTO {

    private String usuario;
    private String contraseña;

    public SesionDTO() {
        
    }

    public SesionDTO(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return "SesionDTO{" + "usuario=" + usuario + ", contrase\u00f1a=" + contraseña + '}';
    }
    
    
    
}
