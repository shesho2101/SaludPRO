/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import principal.DAO.Abstract.DAO;
import principal.dominio.PersonalAtencion.PersonalAtencion;
import principal.dominio.user.UsuarioServices;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class PersonalAtencionDAO extends DAO{
    
    private UsuarioServices us;
    
    public PersonalAtencionDAO(){
        this.us = new UsuarioServices();
    }
    
    public void savePA(PersonalAtencion pa) throws Exception{
        try {
            if(pa == null){
                throw new Exception("Debe indicar una personal de atencion");
            }
            
            String sql = "INSERT INTO pa VALUES ('" + pa.getID() + "' , '" + pa.getSede() + "')";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modPA(PersonalAtencion pa) throws Exception{
        try {
            if(pa == null){
                throw new Exception("Debe indicar una persona de atencion a modificar");
            }
            String sql = "UPDATE pa SET area_atencion = '" + pa.getSede()+
                    "' WHERE ID_PA = '" + pa.getID() + "'"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delPA(String codPa) throws Exception{
        try {
            String sql = "DELETE FROM pa WHERE ID_PA = '" + codPa + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public PersonalAtencion searchPerCod(String codPa) throws Exception{
        try {
            
            String sql = "SELECT * FROM pa "
                    + "WHERE ID_PA = '" + codPa + "'";
            
            consultarBase(sql);
            
            PersonalAtencion pa = null;
            
            while(result.next()){
                pa = new PersonalAtencion();
                pa.setUser(us.searchPerID(result.getString(1)));
                pa.setSede(result.getString(2));
            }
            desconectarBase();
            return pa;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public List<PersonalAtencion> listCita() throws Exception{
        try {
            String sql = "SELECT * FROM cita";
            
            consultarBase(sql);
            
            List<PersonalAtencion> pas = new ArrayList();
            PersonalAtencion pa = null;
            while(result.next()){
                pa = new PersonalAtencion();
                pa.setUser(us.searchPerID(result.getString(1)));
                pa.setSede(result.getString(2));
                pas.add(pa);
            }
            desconectarBase();
            return pas;
            
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
    
}
