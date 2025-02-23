package DTO;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public class UsuarioNuevoDTO {
    
    private String usuario;
    private String contraseña;
    private String tipo;
    
    public UsuarioNuevoDTO() {
        
    }

    public UsuarioNuevoDTO(String usuario, String contraseña, String tipo) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "UsuarioNuevoDTO{" + "usuario=" + usuario + ", contrase\u00f1a=" + contraseña + ", tipo=" + tipo + '}';
    }
}
