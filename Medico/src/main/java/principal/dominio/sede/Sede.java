/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.sede;

/**
 *
 * @author PC
 */
public class Sede {
    private int cod;
    private String nombre;
    private String direccion;

    public Sede() {
    }

    public Sede(int cod,String nombre, String direccion) {
        this.cod = cod;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Sede(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "cod=" + cod + ", nombre=" + nombre + ", direccion=" + direccion ;
    }
    
    
}
