/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.consultorio;

import java.util.Collection;
import java.util.List;
import principal.DAO.Entities.ConsultorioDAO;
import principal.dominio.sede.SedeServices;

/**
 *
 * @author PC
 */
public class ConsultorioServices {
    
    private ConsultorioDAO cbd;
    private SedeServices ss = new SedeServices();
    
    public ConsultorioServices(){
        this.cbd = new ConsultorioDAO();
    }
    
    public void saveConsultorio(String nombre, int codSede) throws Exception{
        try {
            //Validaciones
            if(nombre == null || nombre.trim().isEmpty()){
                throw new Exception("El nombre no puede ser nulo");
            }
            if(ss.searchSedePerCod(codSede) == null){
                throw new Exception("No existe esa sede");
            }
            
            Consultorio cns = new Consultorio();
            cns.setNombre(nombre);
            cns.setSede(ss.searchSedePerCod(codSede));
            //Call database
            cbd.saveConsultorio(cns);
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    //Modificar
    public void modificarConsultorio(int cod, String nombre) throws Exception{
        try {
            //Validaciones
            if(cod == 0){
                throw new Exception("El codigo no puede ser 0");
            }
            if(nombre == null ||nombre.trim().isEmpty()){
                throw new Exception("El nombre no puede ser nulo");
            }
            
            Consultorio consultorio = new Consultorio();
            consultorio.setNumHab(cod);
            consultorio.setNombre(nombre);
            consultorio.setSede(ss.searchSedePerCod(cod));
            //Call database
            cbd.modConsultorio(consultorio);
        } catch (Exception e) {
            throw e;
        }
        
    }
    
    
    public void deleteConsultorio(int cod){
        try {
            //Validaciones
            if(cod == 0){
                throw new Exception("El codigo no puede ser 0");
            }
            
            cbd.delConsult(cod);
        } catch (Exception e) {
        }
    }
    
    public Consultorio searchPerCod(int cod) throws Exception{
        try {
            //Validaciones
            if(cod == 0){
                throw new Exception("El codigo no puede ser 0");
            }
            
            //Call Database
            return cbd.searchPerCod(cod);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Consultorio searchPerSede(int codSede, String nombre) throws Exception{
        try {
            //Validaciones
            if(codSede == 0){
                throw new Exception("El codigo no puede ser 0");
            }
            if(nombre == null || nombre.trim().isEmpty()){
                throw new Exception("El nombre no puede ser nulo");
            }
            
            //Call Database
            return cbd.searchPerSede(codSede, nombre);
        } catch (Exception e) {
            throw e;
        }
    }
    
    //Lista e imprimir
    
    public List<Consultorio> listCons() throws Exception{
        try {
            
            return cbd.listCon();
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimirConsultorios() throws Exception{
        try {
            
            List<Consultorio> consultorios = listCons();
            
            //Validaciones
            if(consultorios.isEmpty()){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(consultorios.toString());
        } catch (Exception e) {
            throw e;
        }
    }
}
