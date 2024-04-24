/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.medico;

import principal.dominio.consultorio.Consultorio;
import principal.dominio.user.Usuario;

/**
 *
 * @author PC
 */
public class Medico {

    private Usuario usr;
    private String especializacion;
    private Consultorio cons;

    private String sede;

    public Medico(String especializacion, String sede) {
        this.especializacion = especializacion;
        this.sede = sede;
    }

    public Medico(){
    }

    public String getID(){
        return getUsr().getId();
    }

    public Consultorio getCons() {
        return cons;
    }

    public void setCons(Consultorio cons) {
        this.cons = cons;
    }

    public String getInstance(){
        return this.getClass().getSimpleName();
    }

    public Usuario getUsr() {
        return usr;
    }

    public void setUsr(Usuario usr) {
        this.usr = usr;
    }


    public String getEspecializacion() {
        return especializacion;
    }

    public void setEspecializacion(String especializacion) {
        this.especializacion = especializacion;
    }

    @Override
    public String toString() {
        return "Medico{" + usr + ", especializacion=" + especializacion + ", cons=" + cons + '}';
    }


    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }
}
