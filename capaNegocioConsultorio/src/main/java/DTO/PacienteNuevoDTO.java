package DTO;

import Entidades.Usuario;
import java.sql.Date;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public class PacienteNuevoDTO {
    private int id_paciente;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaNacimiento;
    private int edad;
    private String correoElectronico;
    private String telefono;
    private DireccionNuevaDTO direccion;
    private Usuario usuario;

    public PacienteNuevoDTO() {
    }

    public PacienteNuevoDTO(int id_paciente, String nombres, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, int edad, String correoElectronico, String telefono, DireccionNuevaDTO direccion, Usuario usuario) {
        this.id_paciente = id_paciente;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccion = direccion;
        this.usuario = usuario;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public DireccionNuevaDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionNuevaDTO direccion) {
        this.direccion = direccion;
    }

    public Usuario getUsuario() { 
        return usuario;
    }

    public void setUsuario(Usuario usuario) { 
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "PacienteNuevoDTO{id_paciente=" + id_paciente + ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno +
               ", apellidoMaterno=" + apellidoMaterno + ", fechaNacimiento=" + fechaNacimiento + ", edad=" + edad +
               ", correoElectronico=" + correoElectronico + ", telefono=" + telefono + ", direccion=" + direccion +
               ", usuario=" + usuario + '}';
    }
}
