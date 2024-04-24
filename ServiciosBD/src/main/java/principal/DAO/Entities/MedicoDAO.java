/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import principal.DAO.Abstract.DAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import principal.dominio.consultorio.ConsultorioServices;
import principal.dominio.medico.Medico;
import principal.dominio.user.UsuarioServices;
/**
 *
 * @author PC
 */
@SuppressWarnings("unchecked")
public class MedicoDAO extends DAO{
    
    
    ConsultorioServices cs;
    UsuarioServices us;
    
    public MedicoDAO(){
        this.cs = new ConsultorioServices();
        this.us = new UsuarioServices();
    }
    
    public void saveDoctor(Medico med) throws Exception{
        try {
            if(med == null){
                throw new Exception("Debe indicar un medico");
            }
            
            String sql = "INSERT INTO Medico(ID_Medico, Especializacion, NumHab)" 
                    + "VALUES ( '" + med.getUsr().getId()  + "' ,  '" + med.getEspecializacion()+ "' , " + med.getCons().getNumHab() + ");"; 
            
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
                         " WHERE ID_Medico = '" + med.getUsr().getId() + "'"; 
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
                med.setUsr(us.searchPerID(result.getString(1)));
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
    
    public List<Medico> listPac() throws Exception{
        try {
            String sql = "SELECT * FROM medico";
            
            consultarBase(sql);
            
            List<Medico> medicos = new ArrayList();
            Medico med = null;
            
            while(result.next()){
                med = new Medico();
                med.setUsr(us.searchPerID(result.getString(1)));
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
