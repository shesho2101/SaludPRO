/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.paciente;

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
    private String telefono;
    private String correo;
    private GrupoSanguineo sangre;
    
    public Paciente(String fechaNacimiento, String sexo, String telefono, String correo, GrupoSanguineo sangre) {
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.telefono = telefono;
        this.correo = correo;
        this.sangre = sangre;
    }

    public Paciente() {
    }

    public GrupoSanguineo getSangre() {
        return sangre;
    }

    public void setSangre(GrupoSanguineo sangre) {
        this.sangre = sangre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getID(){
        return this.getUsr().getId();
    }

    public Usuario getUsr() {
        return usr;
    }

    public void setEdad(int edad) {
        this.edad = edad;
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
