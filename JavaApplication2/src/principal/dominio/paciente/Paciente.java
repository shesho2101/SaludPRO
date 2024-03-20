/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.paciente;

import Interface.personas.Personas;

/**
 *
 * @author PC
 */
public class Paciente implements Personas {
    private String idPac;
    private String nombre;
    private String apellido;
    private String edad;

    public Paciente(String idPac, String nombre, String apellido, String edad) {
        this.idPac = idPac;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public Paciente() {
    }
    
    @Override
    public String getInstance(){
        return this.getClass().getSimpleName();
    }

    @Override
    public String getId() {
        return idPac;
    }

    public void setIdPac(String idPac) {
        this.idPac = idPac;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Paciente{" + "idPac=" + idPac + ", nombre=" + nombre + ", apellido=" + apellido + ", edad=" + edad + '}';
    }
    
    
    
    
}
