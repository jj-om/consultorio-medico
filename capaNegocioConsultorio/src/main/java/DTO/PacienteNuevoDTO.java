/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Gael
 */
import Entidades.Paciente;

import java.sql.Date;

public class PacienteNuevoDTO {
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Date fechaNacimiento;
    private int edad;
    private String correoElectronico;
    private String telefono;
    private DireccionNuevaDTO direccion;

    public PacienteNuevoDTO() {
    }

    public PacienteNuevoDTO(String nombres, String apellidoPaterno, String apellidoMaterno, Date fechaNacimiento, int edad, String correoElectronico, String telefono) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        
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

    @Override
    public String toString() {
        return "PacienteDTO{" + "nombres=" + nombres + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", fechaNacimiento=" + fechaNacimiento + ", edad=" + edad + ", correoElectronico=" + correoElectronico + ", telefono=" + telefono + '}';
    }
}


