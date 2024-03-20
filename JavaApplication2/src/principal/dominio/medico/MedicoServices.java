/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.medico;

import java.util.Collection;
import principal.cbd.MedicoCBD;

/**
 *
 * @author PC
 */
public class MedicoServices {
    
    private MedicoCBD cbd;
    
    public MedicoServices(){
        this.cbd = new MedicoCBD();
    }
    
    public void createDoc(String id, String nombre, String apellido, String especializacion) throws Exception{
        try {
            //Validaciones
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(nombre == null || nombre.trim().isEmpty()){
                throw new Exception("Inserte un nombre valido");
            }
            if(apellido == null || apellido.trim().isEmpty()){
                throw new Exception("Inserte un apellido valido");
            }
            if(especializacion == null || especializacion.trim().isEmpty()){
                throw new Exception("Inserte una especializacion valida");
            }
            if(searchPerId(id) != null){
                throw new Exception("Ya esta registrado");
            }
            
            Medico med = new Medico();
            med.setIdDoc(id);
            med.setNombre(nombre);
            med.setApellido(apellido);
            med.setEspecializacion(especializacion);
            cbd.saveDoctor(med);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modDoc(String id, String espAct, String espNew) throws Exception{
        try {
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(espAct == null || espAct.trim().isEmpty()){
                throw new Exception("La especializacion puede ser nula");
            }
            if(espNew == null || espNew.trim().isEmpty()){
                throw new Exception("La nueva especializacion no puede ser nula");
            }
            
            Medico med = searchPerId(id);
            med.setEspecializacion(espNew);
            cbd.modMedico(med);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delDoc(String id) throws Exception{
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
