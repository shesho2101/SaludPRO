/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.medico;

import java.util.Collection;
import principal.cbd.MedicoCBD;
import principal.dominio.consultorio.ConsultorioServices;

/**
 *
 * @author PC
 */
public class MedicoServices {
    
    private MedicoCBD cbd;
    private ConsultorioServices cs = new ConsultorioServices();
    //Hacer el usr services
    
    public MedicoServices(){
        this.cbd = new MedicoCBD();
    }
    
    public void createMed(String id, String especializacion, int numHab) throws Exception{
        try {
            //Validaciones
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(especializacion == null || especializacion.trim().isEmpty()){
                throw new Exception("Inserte una especializacion valida");
            }
            if(searchPerId(id) != null){
                throw new Exception("Ya esta registrado");
            }
            if(cs.searchPerCod(numHab) == null){
                throw new Exception("No existe el consultorio");
            }
            
            
            Medico med = new Medico();
            //usr services
            med.setEspecializacion(especializacion);
            med.setCons(cs.searchPerCod(numHab));
            cbd.saveDoctor(med);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modMed(String id, int numHab) throws Exception{
        try {
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(cs.searchPerCod(numHab) == null){
                throw new Exception("No existe el consultorio");
            }
            
            
            Medico med = searchPerId(id);
            med.setCons(cs.searchPerCod(numHab));
            cbd.modMedico(med);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delMed(String id) throws Exception{
        try {
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            cbd.delDoc(id);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Medico searchPerId(String id) throws Exception{
        try {
            if(id == null || id.isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            
            Medico pac = cbd.searchDocPerId(id);
            
            return pac;
        } catch (Exception e) {
            throw e;
        }
    }
    
    private Collection<Medico> listMed() throws Exception{
        try {
            Collection<Medico> medicos = cbd.listPac();
            return medicos;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimirMedicos() throws Exception{
        try {
            Collection<Medico> medicos = listMed();
            
            if(medicos.isEmpty()){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(medicos.toString());
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    
}
