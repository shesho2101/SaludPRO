/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import principal.DAO.Abstract.DAO;
import principal.dominio.paciente.PacienteServices;
import principal.dominio.tratamiento.Tratamiento;

/**
 *
 * @author PC
 */
public class TratamientoDAO extends DAO{
    
    private PacienteServices ps; 

    public TratamientoDAO() {
        this.ps = new PacienteServices();
    }
    
    public void saveTratamiento(Tratamiento trat) throws Exception{
        try {
            if(trat == null){
                throw new Exception("Debe indicar un tratamiento");
            }
            
            String sql = "INSERT INTO tratamiento(descripcion, fecha_inicio, fecha_fin, ID_Paciente, observaciones)" 
                    + "VALUES ( '" + trat.getDescripcion()+ "' , ' " + trat.getFechaInicio()+ "' , '" + trat.getFechaFin() + "' , '" 
                    + trat.getPac().getID() + "' , '" + trat.getObs() + "')"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        } 
    }
    
    public void modTratamiento(Tratamiento trat) throws Exception{
        try {
            if(trat == null){
                throw new Exception("Debe indicar un tratamiento a modificar");
            }
            String sql = "UPDATE tratamiento SET fecha_inicio = '" + trat.getFechaInicio()+ "' , fecha_fin = '" + trat.getFechaFin()
                    + "' WHERE codigo = '" + trat.getCodigo()+ "'"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delTratamiento(int cod) throws Exception{
        try {
            String sql = "DELETE FROM tratamiento WHERE codigo = '" + cod + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Tratamiento searchPerCod(int cod) throws Exception{
        try {
             
            String sql = "SELECT * FROM tratamiento "
                    + "WHERE codigo = '" + cod + "'";
            
            consultarBase(sql);
            
            Tratamiento trat = null;
            
            while(result.next()){
                trat = new Tratamiento();
                trat.setCodigo(result.getInt(1));
                trat.setDescripcion(result.getString(2));
                trat.setFechaInicio(result.getDate(3).toString());
                trat.setFechaFin(result.getDate(4).toString());
                trat.setPac(ps.searchPerId(result.getString(5)));
                trat.setObs(result.getString(6));
            }
            desconectarBase();
            return trat;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    /*
    public Tratamiento searchPerPac(String idPac) throws Exception{
        try {
             
            String sql = "SELECT * FROM tratamiento "
                    + "WHERE ID_Paciente = '" + idPac + "'";
            
            consultarBase(sql);
            
            Tratamiento trat = null;
            
            while(result.next()){
                trat = new Tratamiento();
                trat.setCodigo(result.getInt(1));
                trat.setDescripcion(result.getString(2));
                trat.setFechaInicio(result.getDate(3).toString());
                trat.setFechaFin(result.getDate(4).toString());
                trat.setPac(ps.searchPerId(result.getString(5)));
                trat.setObs(result.getString(6));
            }
            desconectarBase();
            return trat;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    */
    public List<Tratamiento> listTrat() throws Exception{
        try {
            String sql = "SELECT * FROM tratamiento";
            
            consultarBase(sql);
            
            List<Tratamiento> trats = new ArrayList<>();
            Tratamiento trat = null;
            while(result.next()){
                trat = new Tratamiento();
                trat.setCodigo(result.getInt(1));
                trat.setDescripcion(result.getString(2));
                trat.setFechaInicio(result.getDate(3).toString());
                trat.setFechaFin(result.getDate(4).toString());
                trat.setPac(ps.searchPerId(result.getString(5)));
                trat.setObs(result.getString(6));
                trats.add(trat);
            }
            desconectarBase();
            return trats;
            
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
    
}
