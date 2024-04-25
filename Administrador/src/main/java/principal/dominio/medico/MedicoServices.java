/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.medico;

import principal.DAO.Entities.MedicoDAO;
import principal.dominio.consultorio.ConsultorioServices;
import principal.dominio.user.UsuarioServices;

import java.util.List;

/**
 *
 * @author PC
 */
public class MedicoServices {
    
    private MedicoDAO cbd;
    private ConsultorioServices cs;
    private UsuarioServices us;
    
    public MedicoServices(){
        this.cbd = new MedicoDAO();
        this.cs = new ConsultorioServices();
        this.us = new UsuarioServices();
    }
    
    public void createMed(String id, String especializacion, String numHab) throws Exception{
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
            if(numHab == null){
                throw new Exception("No existe el consultorio");
            }
            
            
            Medico med = new Medico();
            med.setUsr(us.searchPerID(id));
            med.setEspecializacion(especializacion);
            med.setCons(numHab);
            cbd.saveDoctor(med);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modMed(String id, String numHab) throws Exception{
        try {
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id no puede ser nulo");
            }
            if(cs.searchPerCod(Integer.parseInt(numHab)) == null){
                throw new Exception("No existe el consultorio");
            }
            
            
            Medico med = searchPerId(id);
            med.setCons(numHab);
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
    
    public List<Medico> listMed() throws Exception{
        try {
            return cbd.listPac();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimirMedicos() throws Exception{
        try {
            List<Medico> medicos = listMed();
            
            if(medicos.isEmpty()){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(medicos.toString());
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    
}
