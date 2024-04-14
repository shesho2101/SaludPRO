/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import principal.DAO.Abstract.DAO;
import java.util.ArrayList;
import java.util.Collection;
import principal.dominio.consultorio.Consultorio;
import principal.dominio.sede.Sede;
import principal.dominio.sede.SedeServices;

/**
 *
 * @author PC
 */
public class ConsultorioDAO extends DAO{
    
    SedeServices sedeService = new SedeServices();
    
    public void saveConsultorio(Consultorio cons) throws Exception{
        try {
            if(cons == null){
                throw new Exception("Debe indicar un consultorio");
            }
            
            String sql = "INSERT INTO consultorio(Nombre, CodSede)" 
                    + "VALUES ( '" + cons.getNombre()  + "' , " + cons.getSede().getCod() + ");"; 
            
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modConsultorio(Consultorio consultorio) throws Exception{
        try {
            if(consultorio == null){
                throw new Exception("Debe indicar un consultorio a modificar");
            }
            String sql = "UPDATE consultorio SET Nombre = '" + consultorio.getNombre()+ "'" +
                    " WHERE NumHab = '" + consultorio.getNumHab() + "'"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    public void delConsult(int cod) throws Exception{
        try {
            String sql = "DELETE FROM consultorio WHERE NumHab = '" + cod + "'";
            
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Consultorio searchPerCod(int cod) throws Exception{
        try {
            String sql = "SELECT * FROM consultorio WHERE NumHab = '" + cod + "'";
            
            consultarBase(sql);
            
            Consultorio cons = null;
            
            while(result.next()){
                cons = new Consultorio();
                cons.setNumHab(result.getInt(1));
                cons.setNombre(result.getString(2));
                cons.setSede(sedeService.searchSedePerCod(result.getInt(3)));
            }
            return cons;
        } catch (Exception e) {
            throw e;
        }
    }
    //Lista e imprimir
    
    public Collection<Consultorio> listCon() throws Exception{
        try {
            String sql = "SELECT * FROM consultorio";
            
            consultarBase(sql);
            
            Collection<Consultorio> consultorios = new ArrayList();
            
             Consultorio c = null;
            while(result.next()){
               c = new Consultorio();
               c.setNumHab(result.getInt(1));
               c.setNombre(result.getString(2));
               c.setSede(sedeService.searchSedePerCod(result.getInt(3)));
               consultorios.add(c);
            }
            desconectarBase();
            return consultorios;
            
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
}
