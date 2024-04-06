/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.cbd;

import java.util.ArrayList;
import java.util.Collection;
import principal.dominio.consultorio.ConsultorioServices;
import principal.dominio.medico.Medico;
/**
 *
 * @author PC
 */
public class MedicoCBD extends CBD{
    
    ConsultorioServices cs = new ConsultorioServices();
    
    public void saveDoctor(Medico med) throws Exception{
        try {
            if(med == null){
                throw new Exception("Debe indicar un medico");
            }
            
            String sql = "INSERT INTO Medico(ID_Medico, Especializacion, NumHab)" 
                    + "VALUES ( '" + med.getId()  + "' ,  '" + med.getEspecializacion()+ "' , " + med.getCons().getNumHab() + ");"; 
            
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modMedico(Medico med) throws Exception{
        try {
            if(med == null){
                throw new Exception("Debe indicar un medico a modificar");
            }
            String sql = "UPDATE medico SET NumHab = '" + med.getCons().getNumHab() + "'" + 
                         " WHERE ID_Medico = '" + med.getId() + "'"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delDoc(String idMedico) throws Exception{
        try {
            String sql = "DELETE FROM medico WHERE ID_Medico = '" + idMedico + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Medico searchDocPerId(String id) throws Exception{
        try {
            
            String sql = "SELECT * FROM medico "
                    + "WHERE ID_Medico = '" + id + "';";
            
            consultarBase(sql);
            
            Medico med = null;
            
            while(result.next()){
                med = new Medico();
                med.setIdMedico(result.getString(1));
                med.setEspecializacion(result.getString(2));
                med.setCons(cs.searchPerCod(result.getInt(3)));
            }
            desconectarBase();
            return med;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public Collection<Medico> listPac() throws Exception{
        try {
            String sql = "SELECT * FROM medico";
            
            consultarBase(sql);
            
            Collection<Medico> medicos = new ArrayList();
            Medico med = null;
            
            while(result.next()){
                med = new Medico();
                med.setIdMedico(result.getString(1));
                med.setEspecializacion(result.getString(2));
                med.setCons(cs.searchPerCod(result.getInt(3)));
                medicos.add(med);
            }
            desconectarBase();
            return medicos;
            
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
}
