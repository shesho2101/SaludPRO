/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.PersonalAtencion;

import java.util.Collection;
import java.util.List;
import principal.DAO.Entities.PersonalAtencionDAO;
import principal.dominio.user.UsuarioServices;

/**
 *
 * @author PC
 */
public class PersonalAtencionServices {
    
    private PersonalAtencionDAO cbd;
    private UsuarioServices us;

    public PersonalAtencionServices() {
        this.cbd = new PersonalAtencionDAO();
        this.us = new UsuarioServices();
    }
    
    public void createPersonalAtencion(String id, String areaAtencion) throws Exception{
        try {
            //Validaciones
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(areaAtencion == null || areaAtencion.trim().isEmpty()){
                throw new Exception("La area de atencion no puede ser nula");
            }
            if(us.searchPerID(id) == null){
                throw new Exception("El usuario no existe");
            }
            if(searchPerId(id) != null){
                throw new Exception("Ya existe ese usuario");
            }
            
            PersonalAtencion pa = new PersonalAtencion();
            pa.setUser(us.searchPerID(id));
            pa.setAreaAtencion(areaAtencion);
            //Call database
            cbd.savePA(pa);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modificarPA(String id, String areaAtencion) throws Exception{
        try {
            //Validaciones
            if(id == null ||id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(areaAtencion == null || areaAtencion.trim().isEmpty()){
                throw new Exception("La area de atencion no puede ser nula");
            }
            if(us.searchPerID(id) == null){
                throw new Exception("El usuario no existe");
            }
            
            PersonalAtencion pa = new PersonalAtencion();
            pa.setUser(us.searchPerID(id));
            pa.setAreaAtencion(areaAtencion);
            
            //Call database
            cbd.modPA(pa);
        } catch (Exception e) {
            throw e;
        } 
    }
    
    public void deletePA(String id) throws Exception{
        try {
            //Validaciones
            if(id == null ||id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            cbd.delPA(id);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public PersonalAtencion searchPerId(String id) throws Exception{
        try {
            //Validaciones
            if(id == null ||id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            return cbd.searchPerCod(id);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<PersonalAtencion> listPA() throws Exception{
        try {
            return cbd.listCita();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimir() throws Exception{
        try {
            List<PersonalAtencion> pas = listPA();
            if(pas == null){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(pas.toString());
            
        } catch (Exception e) {
            throw e;
        }
    }
    
}
