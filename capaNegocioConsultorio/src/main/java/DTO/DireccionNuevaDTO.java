/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 * @author Ethan Valdez
 * @author Daniel Buelna
 * @author Manuel Guerrero
 * @author Jesus Osuna
 */

public class DireccionNuevaDTO {
    
    private String calle;
    private int numero;
    private String colonia;
    
    public DireccionNuevaDTO() {
        
    }

    public DireccionNuevaDTO(String calle, int numero, String colonia) {
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    @Override
    public String toString() {
        return "DireccionNuevaDTO{" + "calle=" + calle + ", numero=" + numero + ", colonia=" + colonia + '}';
    }
}
