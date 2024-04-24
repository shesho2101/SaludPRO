/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.consultorio;

import principal.dominio.sede.Sede;

/**
 *
 * @author PC
 */
public class Consultorio {
    private int numHab;
    private String nombre;
    private Sede sede;

    public Consultorio() {
    }

    public Consultorio(int numHab, String nombre) {
        this.numHab = numHab;
        this.nombre = nombre;
    }

    public Consultorio(String nombre, Sede sede) {
        this.nombre = nombre;
        this.sede = sede;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNumHab(int numHab) {
        this.numHab = numHab;
    }
    
    public int getNumHab() {
        return numHab;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    @Override
    public String toString() {
        return "numHab=" + numHab + ", nombre=" + nombre + ", sede=" + sede;
    }
    
    
}
