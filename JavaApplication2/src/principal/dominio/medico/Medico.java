/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.medico;

import Interface.personas.Personas;

/**
 *
 * @author PC
 */
public class Medico implements Personas{
    private String idDoc;
    private String nombre;
    private String apellido;
    private String especializacion;

    public Medico(String idDoc, String nombre, String apellido, String especializacion) {
        this.idDoc = idDoc;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especializacion = especializacion;
    }
    
    public Medico(){
       
    }
    
    @Override
    public String getInstance(){
        return this.getClass().getSimpleName();
    }

    @Override
    public String getId() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
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

    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    @Override
    public String toString() {
        return "Medico{" + "idDoc=" + idDoc + ", nombre=" + nombre + ", apellido=" + apellido + ", especializacion=" + especializacion + '}';
    }
    
    
    
    
}
