/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.tratamiento;

import java.util.Collection;
import java.util.List;
import principal.DAO.Entities.TratamientoDAO;
import principal.dominio.paciente.PacienteServices;

/**
 *
 * @author PC
 */
public class TratamientoServices {
    
    private PacienteServices ps;
    private TratamientoDAO cbd;
    
    public TratamientoServices(){
        this.cbd = new TratamientoDAO();
        this.ps = new PacienteServices();
    }
    
    public void createTratamiento(String desc, String fechaInicio, String fechaFin, String idPac, String obs) throws Exception{
        try {
            //Validaciones
            if(desc == null || desc.trim().isEmpty()){
                throw new Exception("La descripcion no puede ser nula");
            }
            if(fechaInicio == null || fechaInicio.trim().isEmpty()){
                throw new Exception("La descripcion no puede ser nula");
            }
            if(fechaFin == null || fechaFin.trim().isEmpty()){
                throw new Exception("La descripcion no puede ser nula");
            }
            if(obs == null || obs.trim().isEmpty()){
                throw new Exception("La descripcion no puede ser nula");
            }
            if(ps.searchPerId(idPac) == null){
                throw new Exception("El paciente no existe");
            }
            
            Tratamiento trat = new Tratamiento();
            trat.setDescripcion(desc);
            trat.setFechaInicio(fechaInicio);
            trat.setFechaFin(fechaFin);
            trat.setPac(ps.searchPerId(idPac));
            trat.setObs(obs);
            cbd.saveTratamiento(trat);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    public void modificarTratamiento(int cod, String fechaFin, String fechaInicio){
        try {
            if(fechaInicio == null || fechaInicio.trim().isEmpty()){
                throw new Exception("La descripcion no puede ser nula");
            }
            if(fechaFin == null || fechaFin.trim().isEmpty()){
                throw new Exception("La descripcion no puede ser nula");
            }
            if(cod == 0){
                throw new Exception("El codigo no puede ser 0");
            }
            
            Tratamiento trat = searchPerCode(cod);
            trat.setFechaFin(fechaFin);
            trat.setFechaInicio(fechaInicio);
            cbd.modTratamiento(trat);
        } catch (Exception e) {
        }
    }
    
    public void delTratamiento(int cod) throws Exception{
        try {
            if(cod == 0){
                throw new Exception("El codigo no puede ser 0");
            }
            
            cbd.delTratamiento(cod);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Tratamiento searchPerCode(int cod) throws Exception{
        try {
            if(cod == 0){
                throw new Exception("El codigo no puede ser 0");
            }
            
            return cbd.searchPerCod(cod);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<Tratamiento> listTrat() throws Exception{
        try {
            return cbd.listTrat();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimir() throws Exception{
        try {
            List<Tratamiento> trats = listTrat();
            if(trats == null){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(trats.toString());
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    
}
