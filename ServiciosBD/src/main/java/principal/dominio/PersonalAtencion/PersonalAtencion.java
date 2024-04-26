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
    private String areaAtencion;

    public PersonalAtencion() {
    }

    public PersonalAtencion(String areaAtencion) {
        this.areaAtencion = areaAtencion;
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

    public String getAreaAtencion() {
        return areaAtencion;
    }

    public void setAreaAtencion(String areaAtencion) {
        this.areaAtencion = areaAtencion;
    }

    @Override
    public String toString() {
        return "PersonalAtencion{" + user + ", areaAtencion=" + areaAtencion + '}';
    }
    
    
}
