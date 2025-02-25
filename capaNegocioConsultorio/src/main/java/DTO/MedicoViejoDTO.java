package DTO;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna Andujo
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public class MedicoViejoDTO {
    
    private String id_medico;
    private String cedulaProfesional;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String especialidad;
    private String estado;

    public MedicoViejoDTO() {
        
    }

    public MedicoViejoDTO(String id_medico, String cedulaProfesional, String nombres, String apellidoPaterno, String apellidoMaterno, String especialidad, String estado) {
        this.id_medico = id_medico;
        this.cedulaProfesional = cedulaProfesional;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public String getId_medico() {
        return id_medico;
    }

    public void setId_medico(String id_medico) {
        this.id_medico = id_medico;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "MedicoViejoDTO{" + "id_medico=" + id_medico + ", cedulaProfesional=" + cedulaProfesional + ", nombres=" + nombres + ", apelldoPaterno=" + apellidoPaterno + ", apelldoMaterno=" + apellidoMaterno + ", especialidad=" + especialidad + ", estado=" + estado + '}';
    }
}