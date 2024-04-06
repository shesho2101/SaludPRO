/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.cbd;

import java.util.ArrayList;
import java.util.Collection;
import principal.dominio.sede.Sede;

/**
 *
 * @author PC
 */
public class SedeCBD extends CBD{
    
    public void saveSede(Sede sede) throws Exception{
        try {
            if(sede == null){
                throw new Exception("Debe indicar una sede");
            }
            
            String sql = "INSERT INTO sede(nombre, direccion)" 
                    + "VALUES ( '" + sede.getNombre() + "' , ' " + sede.getDireccion()+ "')"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modSede(Sede sede) throws Exception{
        try {
            if(sede == null){
                throw new Exception("Debe indicar una sede a modificar");
            }
            String sql = "UPDATE sede SET nombre = '" + sede.getNombre()+ "' , direccion = '" + sede.getDireccion() +
                    "' WHERE Codigo = '" + sede.getCod()+ "'"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delSede(int codSede) throws Exception{
        try {
            String sql = "DELETE FROM sede WHERE Codigo = '" + codSede + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Sede searchSedePerCod(int cod) throws Exception{
        try {
            
            String sql = "SELECT * FROM sede "
                    + "WHERE Codigo = '" + cod + "'";
            
            consultarBase(sql);
            
            Sede sede = null;
            
            while(result.next()){
                sede = new Sede();
                sede.setCod(result.getInt(1));
                sede.setNombre(result.getString(2));
                sede.setDireccion(result.getString(3));
            }
            desconectarBase();
            return sede;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public Collection<Sede> listSede() throws Exception{
        try {
            String sql = "SELECT * FROM sede";
            
            consultarBase(sql);
            
            Collection<Sede> sedes = new ArrayList();
            while(result.next()){
                sedes.add(new Sede(result.getInt(1),result.getString(2), result.getString(3)));
                //Lo mismo que en el anterior//
            }
            desconectarBase();
            return sedes;
            
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
    
}
 