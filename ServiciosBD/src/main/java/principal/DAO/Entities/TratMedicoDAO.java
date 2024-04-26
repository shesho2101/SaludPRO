/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import principal.DAO.Abstract.DAO;
import principal.dominio.medico.MedicoServices;
import principal.dominio.tratMedico.TratamientoMedico;
import principal.dominio.tratamiento.TratamientoServices;

/**
 *
 * @author PC
 */
public class TratMedicoDAO extends DAO{
    
    private MedicoServices ms;
    private TratamientoServices ts;
    
    public TratMedicoDAO(){
        this.ms = new MedicoServices();
        this.ts = new TratamientoServices();
                
    }
    
    public void saveTratMedico(TratamientoMedico tratMedi) throws Exception{
        try {
            if(tratMedi == null){
                throw new Exception("Debe indicar un tratamiento y su respectivo medico a guardar");
            }
            
            String sql = "INSERT INTO tratamiento_medicamento(codigo_tratamiento, ID_Medico)" 
                    + "VALUES ( '" + tratMedi.getTrat().getCodigo() + "' , ' " + tratMedi.getMed().getID()+ "')"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delPerTratamiento(int codTrat) throws Exception{
        try {
            String sql = "DELETE FROM tratamiento_medico WHERE codigo_tratamiento = '" + codTrat + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delPerMedico(String codMedico) throws Exception{
        try {
            String sql = "DELETE FROM tratamiento_medico WHERE ID_Medico = '" + codMedico + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public TratamientoMedico searchPerTrat(int codTrat) throws Exception{
        try {
            
            String sql = "SELECT * FROM tratamiento_medico "
                    + "WHERE codigo_tratamiento = '" + codTrat + "'";
            
            consultarBase(sql);
            
            TratamientoMedico tratMedico = null;
            
            while(result.next()){
                tratMedico = new TratamientoMedico();
                tratMedico.setTrat(ts.searchPerCode(result.getInt(1)));
                tratMedico.setMed(ms.searchPerId(result.getString(2)));
            }
            desconectarBase();
            return tratMedico;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public TratamientoMedico searchPerMedico(String codMedico) throws Exception{
        try {
            
            String sql = "SELECT * FROM tratamiento_medico "
                    + "WHERE ID_Medico = '" + codMedico + "'";
            
            consultarBase(sql);
            
            TratamientoMedico tratMedico = null;
            
            while(result.next()){
                tratMedico = new TratamientoMedico();
                tratMedico.setTrat(ts.searchPerCode(result.getInt(1)));
                tratMedico.setMed(ms.searchPerId(result.getString(2)));
            }
            desconectarBase();
            return tratMedico;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public List<TratamientoMedico> listTratMedico() throws Exception{
        try {
            String sql = "SELECT * FROM tratamiento_medico";
            
            consultarBase(sql);
            
            List<TratamientoMedico> tratMedis = new ArrayList();
            TratamientoMedico tratMedico = null;
            while(result.next()){
                tratMedico = new TratamientoMedico();
                tratMedico.setTrat(ts.searchPerCode(result.getInt(1)));
                tratMedico.setMed(ms.searchPerId(result.getString(2)));
                tratMedis.add(tratMedico);
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
