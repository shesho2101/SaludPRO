/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import principal.DAO.Abstract.DAO;
import principal.dominio.historialClinico.HistorialClinico;
import principal.dominio.paciente.PacienteServices;

/**
 *
 * @author PC
 */
public class HistorialClinicoDAO extends DAO{
    
    private PacienteServices ps;
    
    public HistorialClinicoDAO(){
        this.ps = new PacienteServices();
    }
    
    public void saveHistorialClinico(HistorialClinico hc) throws Exception{
        try {
            if(hc == null){
                throw new Exception("Debe indicar un historial clinico");
            }
            String sql = "INSERT INTO historial_clinico "
                    + "VALUES ('" + hc.getHistorial() + "' , '" + hc.getPac().getID() + "')";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modHistorial(HistorialClinico hc) throws Exception{
        try {
            if(hc == null){
                throw new Exception("Debe indicar un historial clinico a modificar");
            }
            String sql = "UPDATE historial_clinico SET historial = '" + hc.getHistorial()+ 
                    "' WHERE ID_Paciente = '" + hc.getPac().getID() + "'"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public HistorialClinico searchHistorialPerPaciente(String id) throws Exception{
        try {
            
            String sql = "SELECT * FROM historial_clinico "
                    + "WHERE ID_Paciente = '" + id + "'";
            
            consultarBase(sql);
            
            HistorialClinico hc = null;
            
            while(result.next()){
                hc = new HistorialClinico();
                StringBuilder historial = new StringBuilder();
                hc.setHistorial(historial.append(result.getString(1)));
                hc.setPac(ps.searchPerId(result.getString(2)));
            }
            desconectarBase();
            return hc;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public List<HistorialClinico> listHistoriales() throws Exception{
        try {
            String sql = "SELECT * FROM historial_clinico";
            
            consultarBase(sql);
            
            List<HistorialClinico> hcs = new ArrayList();
            HistorialClinico hc = null;
            while(result.next()){
                hc = new HistorialClinico();
                StringBuilder historial = new StringBuilder();
                hc.setHistorial(historial.append(result.getString(1)));
                hc.setPac(ps.searchPerId(result.getString(2)));
                hcs.add(hc);
            }
            desconectarBase();
            return hcs;
            
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
    
}
