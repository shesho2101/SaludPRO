/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal.DAO.Entities;

import principal.DAO.Abstract.DAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import principal.dominio.cita.Cita;
import principal.dominio.consultorio.ConsultorioServices;
import principal.dominio.medico.MedicoServices;
import principal.dominio.paciente.PacienteServices;

/**
 *
 * @author PC
 */
public class CitaDAO extends DAO{
    
    private ConsultorioServices cs;
    private MedicoServices ms;
    private PacienteServices ps;
    
    public CitaDAO(){
        this.cs = new ConsultorioServices();
        this.ms = new MedicoServices();
        this.ps = new PacienteServices();
    }
    
    public void saveCita(Cita cita) throws Exception{
        try {
            if(cita == null){
                throw new Exception("Debe indicar una cita");
            }
            
            String sql = "INSERT INTO cita(NumHab, ID_Medico, fecha, ID_Paciente, asistencia)" 
                        + "VALUES ('" + cita.getCons().getNumHab() + "' , '" + cita.getMed().getID() + "' , '" + cita.getDate() + "' , '" + cita.getPac().getID() + ""
                        + "' , '" + cita.isAsistencia() + "')";
            
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    
    public void modCita(Cita cita) throws Exception{
        try {
            if(cita == null){
                throw new Exception("Debe indicar una cita a modificar");
            }
            String sql = "UPDATE cita SET fecha = '" + cita.getDate()+
                    "' WHERE NumCita = '" + cita.getNumCita()+ "'"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void modAsistencia(Cita cita) throws Exception{
        try {
            if(cita == null){
                throw new Exception("Debe indicar una cita a modificar");
            }
            String sql = "UPDATE cita SET asistencia = '" + cita.isAsistencia()+
                    "' WHERE NumCita = '" + cita.getNumCita()+ "'"; 
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void delCita(int codCita) throws Exception{
        try {
            String sql = "DELETE FROM cita WHERE NumCita = '" + codCita + "'";
            insertModDel(sql);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public Cita searchPerCod(int cod) throws Exception{
        try {
            
            String sql = "SELECT * FROM cita "
                    + "WHERE NumHab = '" + cod + "'";
            
            consultarBase(sql);
            
            Cita cita = null;
            
            while(result.next()){
                cita = new Cita();
                cita.setNumCita(result.getInt(1));
                cita.setCons(cs.searchPerCod(result.getInt(2)));
                cita.setMed(ms.searchPerId(result.getString(3)));
                cita.setDate(result.getDate(4).toString());
                cita.setPac(ps.searchPerId(result.getString(5)));
                cita.setAsistencia(result.getBoolean(6));
            }
            desconectarBase();
            return cita;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public Cita searchPerPac(String id) throws Exception{
        try {
            
            String sql = "SELECT * FROM cita "
                    + "WHERE ID_Paciente = '" + id + "'";
            
            consultarBase(sql);
            
            Cita cita = null;
            
            while(result.next()){
                cita = new Cita();
                cita.setNumCita(result.getInt(1));
                cita.setCons(cs.searchPerCod(result.getInt(2)));
                cita.setMed(ms.searchPerId(result.getString(3)));
                cita.setDate(result.getDate(4).toString());
                cita.setPac(ps.searchPerId(result.getString(5)));
                cita.setAsistencia(result.getBoolean(6));
            }
            desconectarBase();
            return cita;
            
        } catch (Exception e) {
            desconectarBase();
            throw e;
        }
    }
    
    public List<Cita> listCita() throws Exception{
        try {
            String sql = "SELECT * FROM cita";
            
            consultarBase(sql);
            
            List<Cita> citas = new ArrayList();
            Cita cita = null;
            while(result.next()){
                cita = new Cita();
                cita.setNumCita(result.getInt(1));
                cita.setCons(cs.searchPerCod(result.getInt(2)));
                cita.setMed(ms.searchPerId(result.getString(3)));
                cita.setDate(result.getDate(4).toString());
                cita.setPac(ps.searchPerId(result.getString(5)));
                cita.setAsistencia(result.getBoolean(6));
                citas.add(cita);
            }
            desconectarBase();
            return citas;
            
        } catch (Exception e) {
            e.printStackTrace();
            desconectarBase();
            throw new Exception("Error de sistema");
        }
    }
    
}
