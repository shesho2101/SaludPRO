/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.dominio.tratMedicamento;

import java.util.Collection;
import java.util.List;
import principal.DAO.Entities.TratMediDAO;
import principal.dominio.medicamento.MedicamentoServices;
import principal.dominio.tratamiento.TratamientoServices;

/**
 *
 * @author PC
 */
public class TratamientoMedicamentoServices {
    
    private TratMediDAO cbd;
    private TratamientoServices ts;
    private MedicamentoServices ms;
    
    public TratamientoMedicamentoServices(){
        this.cbd = new TratMediDAO();
        this.ts = new TratamientoServices();
        this.ms = new MedicamentoServices();
    }
    
    public void createTratMedi(int codTrat, int codMedi) throws Exception{
        try {
            if(codTrat == 0){
                throw new Exception("El codigo del tratamiento no puede ser 0");
            }
            if(codMedi == 0){
                throw new Exception("El codigo del medicamento no puede ser 0");
            }
            
            TratamientoMedicamento tratMedi = new TratamientoMedicamento();
            tratMedi.setTrat(ts.searchPerCode(codTrat));
            tratMedi.setMedi(ms.searchPerCod(codMedi));
            cbd.saveTratMedi(tratMedi);
            
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
    
    public void delPerMedi(int codMedi) throws Exception{
        try {
            if(codMedi == 0){
                throw new Exception("El codigo del medicamento no puede ser 0");
            }
            
            cbd.delPerMedicamento(codMedi);
            
        } catch (Exception e) {
            throw e;
        }
    }
    
    public TratamientoMedicamento searchPerTrat(int codTrat) throws Exception{
        try {
            if(codTrat == 0){
                throw new Exception("El codigo del tratamiento no puede ser 0");
            }
            
            return cbd.searchPerTrat(codTrat);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public TratamientoMedicamento searchPerMedi(int codMedi) throws Exception{
        try {
            if(codMedi == 0){
                throw new Exception("El codigo del medicamento no puede ser 0");
            }
            
            return cbd.searchPerMedi(codMedi);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<TratamientoMedicamento> listTratMedi() throws Exception{
        try {
            return cbd.listTratMedi();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void imprimir() throws Exception{
        try {
            List<TratamientoMedicamento> tratMedis = listTratMedi();
            if(tratMedis == null){
                throw new Exception("La lista esta vacia");
            }
            
            System.out.println(tratMedis.toString());
            
        } catch (Exception e) {
            throw e;
        }
    }
}
