/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.sede;

import java.util.Collection;
import java.util.List;
import principal.DAO.Entities.SedeDAO;

/**
 *
 * @author PC
 */
public class SedeServices {
    
    private SedeDAO cbd;

    public SedeServices() {
        this.cbd = new SedeDAO();
    }
    
    public void createSede(String nombre, String direccion) throws Exception{
        try {
            //Validaciones
            if(nombre == null ||nombre.trim().isEmpty()){
                throw new Exception("El nombre no puede ser nulo");
            }
            if(direccion == null || direccion.trim().isEmpty()){
                throw new Exception("La direccion no puede ser nula");
            }
            
            Sede sede = new Sede();
            sede.setNombre(nombre);
            sede.setDireccion(direccion);
            //Call database
            cbd.saveSede(sede);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modificarSede(int cod, String nombre, String direccion) throws Exception{
        try {
            //Validaciones
            if(cod == 0){
                throw new Exception("El codigo no puede ser 0");
            }
            if(nombre == null ||nombre.trim().isEmpty()){
                throw new Exception("El nombre no puede ser nulo");
            }
            if(direccion == null || direccion.trim().isEmpty()){
                throw new Exception("La direccion no puede ser nula");
            }
            
            Sede sede = new Sede();
            sede.setCod(cod);
            sede.setNombre(nombre);
            sede.setDireccion(direccion);
            //Call database
            cbd.modSede(sede);
        } catch (Exception e) {
            throw e;
        }    
    }
    
    public void deleteSede(int cod) throws Exception{
        try {
            //Validaciones
            if(cod == 0){
                throw new Exception("El codigo no puede ser 0");
            }
            //Call database
            cbd.delSede(cod);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Sede searchSedePerCod(int cod) throws Exception{
        try {
            //Validacion
            if(cod == 0){
                throw new Exception("El codigo no puede ser 0");
            }
            //call database
            return cbd.searchSedePerCod(cod);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Sede searchPerNombre(String name) throws Exception{
        try {
            //Validacion
            if(name == null || name.trim().isEmpty()){
                throw new Exception("El nombre no puede ser nulo");
            }
            //call database
            return cbd.searchPerName(name);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<Sede> listSede() throws Exception{
        try {

            return cbd.listSede();
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void printSede() throws Exception{
        try {
            List<Sede> sedes = listSede();
            //Valida
            if(sedes.isEmpty()){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(sedes.toString());
        } catch (Exception e) {
            throw e;
        }
    }
}
