
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import principal.DAO.Abstract.DAO;
import principal.dominio.medicamento.MedicamentoServices;
import principal.dominio.tratMedicamento.TratamientoMedicamento;
import principal.dominio.tratamiento.TratamientoServices;

/**
 *
 * @author PC
 */
public class TratMediDAO extends DAO{
    
    private MedicamentoServices ms;
    private TratamientoServices ts;
    
    public TratMediDAO(){
        this.ms = new MedicamentoServices();
        this.ts = new TratamientoServices();
                
    }
    
    public void saveTratMedi(TratamientoMedicamento tratMedi) throws Exception{
        try {
            if(tratMedi == null){
                throw new Exception("Debe indicar un medicamento y su respectivo tratamiento a guardar");
            }
            
            String sql = "INSERT INTO tratamiento_medicamento(cod_trat, cod_medicamento)" 
                    + "VALUES ( '" + tratMedi.getTrat().getCodigo() + "' , ' " + tratMedi.getMedi().getCodigo() + "')"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delPerTratamiento(int codTrat) throws Exception{
        try {
            String sql = "DELETE FROM tratamiento_medicamento WHERE cod_trat = '" + codTrat + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delPerMedicamento(int codMedi) throws Exception{
        try {
            String sql = "DELETE FROM tratamiento_medicamento WHERE cod_medicamento = '" + codMedi + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public TratamientoMedicamento searchPerTrat(int codTrat) throws Exception{
        try {
            
            String sql = "SELECT * FROM tratamiento_medicamento "
                    + "WHERE cod_trat = '" + codTrat + "'";
            
            consultarBase(sql);
            
            TratamientoMedicamento tratMedi = null;
            
            while(result.next()){
                tratMedi = new TratamientoMedicamento();
                tratMedi.setTrat(ts.searchPerCode(result.getInt(1)));
                tratMedi.setMedi(ms.searchPerCod(result.getInt(2)));
            }
            desconectarBase();
            return tratMedi;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public TratamientoMedicamento searchPerMedi(int codMedi) throws Exception{
        try {
            
            String sql = "SELECT * FROM tratamiento_medicamento "
                    + "WHERE cod_medicamento = '" + codMedi + "'";
            
            consultarBase(sql);
            
            TratamientoMedicamento tratMedi = null;
            
            while(result.next()){
                tratMedi = new TratamientoMedicamento();
                tratMedi.setTrat(ts.searchPerCode(result.getInt(1)));
                tratMedi.setMedi(ms.searchPerCod(result.getInt(2)));
            }
            desconectarBase();
            return tratMedi;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public List<TratamientoMedicamento> listTratMedi() throws Exception{
        try {
            String sql = "SELECT * FROM tratamiento_medicamento";
            
            consultarBase(sql);
            
            List<TratamientoMedicamento> tratMedis = new ArrayList();
            TratamientoMedicamento tratMedi = null;
            while(result.next()){
                tratMedi = new TratamientoMedicamento();
                tratMedi.setTrat(ts.searchPerCode(result.getInt(1)));
                tratMedi.setMedi(ms.searchPerCod(result.getInt(2)));
                tratMedis.add(tratMedi);
            }
            desconectarBase();
            return tratMedis;
            
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
}
