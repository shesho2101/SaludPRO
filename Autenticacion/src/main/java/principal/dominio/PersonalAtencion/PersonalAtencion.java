/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.PersonalAtencion;

import principal.dominio.user.Usuario;

/**
 *
 * @author PC
 */
public class PersonalAtencion {
    private Usuario user;
    private String sede;

    public PersonalAtencion() {
    }

    public PersonalAtencion(String sede) {
        this.sede = sede;
    }
    
    public String getID(){
        return getUser().getId();
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    @Override
    public String toString() {
        return "PersonalAtencion{" + user + ", sede=" + sede + '}';
    }
    
    
}
