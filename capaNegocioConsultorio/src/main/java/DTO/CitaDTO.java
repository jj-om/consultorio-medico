/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author Gael
 */


import Entidades.Cita;
import java.time.LocalDateTime;

public class CitaDTO {
    private int idPaciente;
    private int idMedico;
    private LocalDateTime fechaHora;
    private String estado;

    public CitaDTO(int idPaciente, int idMedico, LocalDateTime fechaHora, String estado) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.fechaHora = fechaHora;
        this.estado = estado;
    }

    public int getIdPaciente(){
     return idPaciente; 
    }
    public int getIdMedico() {
     return idMedico; 
    }
    public LocalDateTime getFechaHora(){
    return fechaHora; 
    }
    public String getEstado(){
     return estado; 
}
    public void setEstado(String estado) {
     this.estado = estado; 
}

    @Override
    public String toString() {
        return "CitaDTO{" + "idPaciente=" + idPaciente + ", idMedico=" + idMedico + ", fechaHora=" + fechaHora + ", estado=" + estado + '}';
    }

    public Cita toCita() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}

