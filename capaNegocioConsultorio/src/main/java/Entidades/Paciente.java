package Entidades;

import java.sql.Date;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */



public class Paciente {
    private int id_paciente;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaNacimiento;
    private int edad;
    private String correoElectronico;
    private String telefono;
    private Direccion direccion;
    
    public Paciente() {
    }

    public Paciente(int id_paciente, String nombres, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, int edad, String correoElectronico, String telefono, Direccion direccion) {
        this.id_paciente = id_paciente;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccion = direccion;
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

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Paciente{" + "id_paciente=" + id_paciente + ", nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", fechaNacimiento=" + fechaNacimiento + ", edad=" + edad + ", correoElectronico=" + correoElectronico + ", telefono=" + telefono + ", direccion=" + direccion + '}';
    }

   
}

