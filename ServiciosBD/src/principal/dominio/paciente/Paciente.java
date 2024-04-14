/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.paciente;

import java.util.Date;
import principal.dominio.user.Usuario;


/**
 *
 * @author PC
 */
public class Paciente {
    private Usuario usr;
    private String fechaNacimiento;
    private int edad;
    private String sexo;

    public Paciente(String fechaNacimiento, String sexo) {
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
    }

    public Paciente() {
    }
    
    public String getID(){
        return getUsr().getId();
    }

    public Usuario getUsr() {
        return usr;
    }

    public void setUsr(Usuario usr) {
        this.usr = usr;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Paciente{" + usr + ", fechaNacimiento=" + fechaNacimiento + ", edad=" + edad + ", sexo=" + sexo + '}';
    }
    
    
    
    public String getInstance(){
        return this.getClass().getSimpleName();
    }

  
    
    
    
}
