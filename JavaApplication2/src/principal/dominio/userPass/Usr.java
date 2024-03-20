/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.userPass;

import Interface.personas.Personas;
import principal.dominio.medico.Medico;
import principal.dominio.paciente.Paciente;

/**
 *
 * @author PC
 */
public class Usr {
    
    private Personas obj;
    private String usuario;

    public Usr() {
    }

    public Usr(Personas obj, String usuario) {
        this.obj = obj;
        this.usuario = usuario;
    }

    public Personas getObj() {
        return obj;
    }

    public void setObj(Personas obj) {
        this.obj = obj;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    
    
    
    
    
}
