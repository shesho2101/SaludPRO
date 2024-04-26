/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.cita;

import java.util.Collection;
import java.util.List;
import principal.DAO.Entities.CitaDAO;
import principal.dominio.consultorio.ConsultorioServices;
import principal.dominio.medico.MedicoServices;
import principal.dominio.paciente.PacienteServices;

/**
 *
 * @author PC
 */
public class CitaServices {
    
    private CitaDAO cbd;
    private MedicoServices ms;
    private PacienteServices ps;
    private ConsultorioServices cs;
    
    public CitaServices(){
        this.cbd = new CitaDAO();
        this.ms = new MedicoServices();
        this.ps = new PacienteServices();
        this.cs = new ConsultorioServices();
    }
    
    public void createCita(int NumHab, String idMed, String date, String idPac) throws Exception{
        try {
            //Validaciones
            if(cs.searchPerCod(NumHab) == null){
                throw new Exception("No existe el consultorio");
            }
            if(idMed == null || idMed.trim().isEmpty()){
                throw new Exception("El id del medico no puede ser nulo");
            }
            if(date == null || date.trim().isEmpty()){
                throw new Exception("La fecha no puede ser nula");
            }
            if(ms.searchPerId(idMed) == null){
                throw new Exception("No existe ese medico");
            }
            if(ps.searchPerId(idPac) == null){
                throw new Exception("No existe ese paciente");
            }
            
            Cita cita = new Cita();
            cita.setMed(ms.searchPerId(idMed));
            cita.setCons(cs.searchPerCod(NumHab));
            cita.setPac(ps.searchPerId(idPac));
            cita.setDate(date);
           
            //Call database
            cbd.saveCita(cita);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modificarCita(int numCita, String date) throws Exception{
        try {
            //Validaciones
            if(numCita == 0){
                throw new Exception("El numero de la cita no puede ser 0");
            }
            if(date == null ||date.trim().isEmpty()){
                throw new Exception("La fecha no puede ser nula");
            }
            if(searchCita(numCita) == null){
                throw new Exception("No existe esa cita");
            }
            
            Cita cita = searchCita(numCita);
            cita.setDate(date);
            //Call database
            cbd.modCita(cita);
        } catch (Exception e) {
            throw e;
        }    
    }
    
    public void modificarAsistencia(int numCita, boolean asistencia) throws Exception{
        try {
            //Validaciones
            if(numCita == 0){
                throw new Exception("El numero de la cita no puede ser 0");
            }
            if(searchCita(numCita) == null){
                throw new Exception("No existe esa cita");
            }
            Cita cita = searchCita(numCita);
            cita.setAsistencia(asistencia);
            //Call database
            cbd.modAsistencia(cita);
        } catch (Exception e) {
            throw e;
        }    
    }
    
    public void deleteCita(int numCita) throws Exception{
        try {
            //Validaciones
            if(numCita == 0){
                throw new Exception("El numero de cita no puede ser 0");
            }
            if(searchCita(numCita) == null){
                throw new Exception("No existe esa cita");
            }
            //Call database
            cbd.delCita(numCita);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Cita searchCita(int numCita) throws Exception{
        try {
            //Validacion
            if(numCita == 0){
                throw new Exception("El numero de la cita no puede ser 0");
            }
            //call database
            return cbd.searchPerCod(numCita);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Cita searchCita(String id) throws Exception{
        try {
            //Validacion
            if(id == null || id.trim().isEmpty()){
                throw new Exception("El id del paciente no puede ser nulo");
            }
            //call database
            return cbd.searchPerPac(id);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public StringBuilder getCitasLikeFecha(String fecha) throws Exception{
        try {
            if(fecha == null || fecha.trim().isEmpty()){
                throw new Exception("La fecha no puede estar vacia");
            }
            
            return cbd.getCitasLikeFecha(fecha);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<Cita> listCita() throws Exception{
        try {
            
            return cbd.listCita();
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void printSede() throws Exception{
        try {
            List<Cita> citas = listCita();
            //Valida
            if(citas.isEmpty()){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(citas.toString());
        } catch (Exception e) {
            throw e;
        }
    }
    
}
