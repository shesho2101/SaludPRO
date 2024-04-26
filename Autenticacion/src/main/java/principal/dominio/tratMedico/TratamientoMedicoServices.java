/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.tratMedico;

import java.util.Collection;
import java.util.List;
import principal.DAO.Entities.TratMedicoDAO;
import principal.dominio.medico.MedicoServices;
import principal.dominio.tratamiento.TratamientoServices;

/**
 *
 * @author PC
 */
public class TratamientoMedicoServices {
    
    private TratMedicoDAO cbd;
    private TratamientoServices ts;
    private MedicoServices ms;
    
    public TratamientoMedicoServices(){
        this.cbd = new TratMedicoDAO();
        this.ts = new TratamientoServices();
        this.ms = new MedicoServices();
    }
    
    public void createTratMedi(int codTrat, String codMedico) throws Exception{
        try {
            if(codTrat == 0){
                throw new Exception("El codigo del tratamiento no puede ser 0");
            }
            if(codMedico == null || codMedico.trim().isEmpty()){
                throw new Exception("El codigo del medico no puede ser nulo");
            }
            
            TratamientoMedico tratMedico = new TratamientoMedico();
            tratMedico.setTrat(ts.searchPerCode(codTrat));
            tratMedico.setMed(ms.searchPerId(codMedico));
            cbd.saveTratMedico(tratMedico);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delPerTrat(int codTrat) throws Exception{
        try {
            if(codTrat == 0){
                throw new Exception("El codigo del tratamiento no puede ser 0");
            }
            
            cbd.delPerTratamiento(codTrat);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delPerMedico(String codMedico) throws Exception{
        try {
            if(codMedico == null || codMedico.trim().isEmpty()){
                throw new Exception("El codigo del medico no puede ser nulo");
            }
            
            cbd.delPerMedico(codMedico);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public TratamientoMedico searchPerTrat(int codTrat) throws Exception{
        try {
            if(codTrat == 0){
                throw new Exception("El codigo del tratamiento no puede ser 0");
            }
            
            return cbd.searchPerTrat(codTrat);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public TratamientoMedico searchPerMedi(String codMedico) throws Exception{
        try {
            if(codMedico == null || codMedico.trim().isEmpty()){
                throw new Exception("El codigo del medico no puede ser nulo");
            }
            
            return cbd.searchPerMedico(codMedico);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<TratamientoMedico> listTratMedi() throws Exception{
        try {
            return cbd.listTratMedico();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimir() throws Exception{
        try {
            List<TratamientoMedico> tratMedicos = listTratMedi();
            if(tratMedicos == null){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(tratMedicos.toString());
            
        } catch (Exception e) {
            throw e;
        }
    }
}
